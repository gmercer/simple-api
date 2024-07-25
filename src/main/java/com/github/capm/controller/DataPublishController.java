package com.github.capm.controller;

import com.github.capm.data.DataManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('EDITOR')")
@RestController
@RequestMapping("/publish")
public class DataPublishController {

    private DataManager dataManager;

    public DataPublishController(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
