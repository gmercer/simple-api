package com.github.capm.controller;

import com.github.capm.data.DataManager;
import com.github.capm.util.RelativeDateTimeParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@PreAuthorize("hasRole('VIEWER')")
@RestController
@RequestMapping("/retrieve")
public class DataRetrieveController {

    private DataManager dataManager;

    @GetMapping("/{type}/{name}/{start}/{end}")
    List<String> getDataByNameAndType(
            @PathVariable String type,
            @PathVariable String name,
            @PathVariable String start,
            @PathVariable String end
    ) {
        RelativeDateTimeParser relativeDateTimeParser = new RelativeDateTimeParser();

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        try {
            startTime = relativeDateTimeParser.asDateTime(start);
            endTime = relativeDateTimeParser.asDateTime(end);
        } catch (IllegalArgumentException e) {
            // need to return 400 Bad Request and the reason why ?
        }

        return dataManager.query(name, type, startTime, endTime) ;

    }
}
