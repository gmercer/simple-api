package com.github.capm.service;

import com.github.capm.dao.UserDao;
import com.github.capm.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
