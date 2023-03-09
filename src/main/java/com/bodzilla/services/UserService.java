package com.bodzilla.services;

import com.bodzilla.models.User;

import java.util.UUID;

public class UserService {

    public User findById(String id) {
        return new User(id, UUID.randomUUID().toString());
    }
}
