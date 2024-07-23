package com.github.capm.compassapi;

import com.github.capm.service.RoleService;
import com.github.capm.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = "com.github.capm")
public class CompassApiApplication {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(CompassApiApplication.class, args);
    }

}
