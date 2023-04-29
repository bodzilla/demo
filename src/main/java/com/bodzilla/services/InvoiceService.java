package com.bodzilla.services;

import com.bodzilla.models.Invoice;
import java.sql.Statement;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component // This annotation tells Spring that it should turn your UserService and your
// InvoiceService into @Beans.
public class InvoiceService {
  private final JdbcTemplate jdbcTemplate;

  private final UserService userService;

  private final String cdnUrl;

  public InvoiceService(
      UserService userService, JdbcTemplate jdbcTemplate, @Value("${cdn.url}") String cdnUrl) {
    this.userService = userService;
    this.cdnUrl = cdnUrl;
    this.jdbcTemplate = jdbcTemplate;
  }

  @PostConstruct
  public void init() {
    System.out.println("Fetching PDF Template from S3...");
  }

  @PreDestroy
  public void shutdown() {
    System.out.println("Deleting downloaded templates...");
  }

  @Transactional
  public List<Invoice> findAll() {
    System.out.println(
        "Is a database transaction open? = "
            + TransactionSynchronizationManager.isActualTransactionActive());

    return jdbcTemplate.query(
        "select id, user_id, pdf_url, amount from invoices",
        (resultSet, rowNum) -> {
          Invoice invoice = new Invoice();
          invoice.setId(resultSet.getObject("id").toString());
          invoice.setPdfUrl(resultSet.getString("pdf_url"));
          invoice.setUserId(resultSet.getString("user_id"));
          invoice.setAmount(resultSet.getInt("amount"));
          return invoice;
        });
  }

  @Transactional
  public Invoice create(String userId, Integer amount) {
    System.out.println(
        "Is a database transaction open? = "
            + TransactionSynchronizationManager.isActualTransactionActive());

    var generatedPdfUrl = cdnUrl + "/images/default/sample.pdf";

    var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        connection -> {
          var preparedStatement =
              connection.prepareStatement(
                  "insert into invoices (user_id, pdf_url, amount) values (?, ?, ?)",
                  Statement.RETURN_GENERATED_KEYS);
          preparedStatement.setString(1, userId); //
          preparedStatement.setString(2, generatedPdfUrl);
          preparedStatement.setInt(3, amount);
          return preparedStatement;
        },
        keyHolder);

    String uuid =
        !keyHolder.getKeys().isEmpty()
            ? keyHolder.getKeys().values().iterator().next().toString()
            : null;

    var invoice = new Invoice();
    invoice.setId(uuid);
    invoice.setPdfUrl(generatedPdfUrl);
    invoice.setAmount(amount);
    invoice.setUserId(userId);
    return invoice;
  }
}
