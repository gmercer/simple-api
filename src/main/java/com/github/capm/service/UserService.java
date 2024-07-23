package com.github.capm.service;

import com.github.capm.entity.User;

import java.util.List;

public interface UserService {

    User getUserByName(String username);

    List<User> getUsers();
}