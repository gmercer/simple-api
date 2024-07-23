package com.github.capm.dao;

import com.github.capm.entity.User;

import java.util.List;

public interface UserDao {
    User getUserByName(String name);

    List<User> getUsers();
}
