package com.github.capm.controller;

import com.github.capm.data.DataManager;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{type}/{name}")
    String getDataByNameAndType(
            @PathVariable String type,
            @PathVariable String name
    ) {
        return "We would love to accept your data for type: {" + type + "} and name: {" + name + "}" +
                "\nBUT we are not currently accepting data. Talk to the team about your needs";
    }
}
