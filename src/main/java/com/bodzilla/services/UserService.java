package com.bodzilla.services;

import com.bodzilla.models.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component // This annotation tells Spring that it should turn your UserService and your InvoiceService into @Beans.
public class UserService {

    public User findById(String id) {
        return new User(id, UUID.randomUUID().toString());
    }
}
