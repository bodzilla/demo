package bodzilla;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(
                """
                    <html>
                    <body>
                    <h1>Hello World</h1>
                    <p>This is my very first, embedded Tomcat, HTML Page from the bodzilla app!</p>
                    </body>
                    </html>"""
        );
    }
}