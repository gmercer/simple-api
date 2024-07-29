package com.github.capm.controller;

import com.github.capm.data.DataManager;
import com.github.capm.util.RelativeDateTimeParser;
import com.github.sisyphsu.dateparser.DateParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@PreAuthorize("hasRole('VIEWER')")
@RestController
@RequestMapping("/retrieve")
public class DataRetrieveController {

    private DataManager dataManager;

    public DataRetrieveController(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @GetMapping("/{type}/{name}")
    List<String> getDataByNameAndType(
            @PathVariable String type,
            @PathVariable String name,
            @RequestParam Optional<String> start,
            @RequestParam Optional<String> end
    ) {
        RelativeDateTimeParser relativeDateTimeParser = new RelativeDateTimeParser();
        DateParser dateParser = DateParser.newBuilder().preferMonthFirst(false).build();

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        try {
            Supplier<String> nowFunc = () -> "now";

            String startText = start.orElseGet(nowFunc);
            try {
                startTime = dateParser.parseDateTime(startText);
            } catch (DateTimeParseException e) {
                startTime = relativeDateTimeParser.asDateTime(startText);
            }

            String endText = end.orElseGet(nowFunc);
            try {
                endTime = dateParser.parseDateTime(endText);
            } catch (DateTimeParseException e) {
                endTime = relativeDateTimeParser.asDateTime(endText);
            }
        } catch (IllegalArgumentException e) {
            // need to return 400 Bad Request and the reason why ?
        }

        return dataManager.query(name, type, startTime, endTime);

    }
}
