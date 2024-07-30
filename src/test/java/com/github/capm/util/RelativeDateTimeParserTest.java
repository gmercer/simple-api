package com.github.capm.util;

import com.github.sisyphsu.dateparser.DateParser;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class RelativeDateTimeParserTest {

    @Test
    void parseNowMinus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.parse("[now-1d]");
        LocalDateTime expected = LocalDateTime.ofInstant(instant.minus(Period.ofDays(1)), ZoneId.systemDefault());
        assertEquals(expected, actual);
    }

    @Test
    void parseNowPlus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.parse("[now+1d]");
        LocalDateTime expected = LocalDateTime.ofInstant(instant.plus(Period.ofDays(1)), ZoneId.systemDefault());
        assertEquals(expected, actual);
    }


    @Test
    void parseNow() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime now = parser.asDateTime("now");
        LocalDateTime Now = parser.asDateTime("Now");
        LocalDateTime nOw = parser.asDateTime("nOw");
        LocalDateTime NOW = parser.asDateTime("NOW");
        LocalDateTime expected = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        assertEquals(expected, now);
        assertEquals(expected, Now);
        assertEquals(expected, nOw);
        assertEquals(expected, NOW);
    }

    @Test
    void parseMinus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.asDateTime("-1d");
        LocalDateTime expected = LocalDateTime.ofInstant(instant.minus(Period.ofDays(1)), ZoneId.systemDefault());
        assertEquals(expected, actual);
    }

    @Test
    void parsePlus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.asDateTime("+1d");
        LocalDateTime expected = LocalDateTime.ofInstant(instant.plus(Period.ofDays(1)), ZoneId.systemDefault());
        assertEquals(expected, actual);
    }

    @Test
    void parseFallback() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        String testString = "-1d";
        LocalDateTime actual = null;
        DateParser dateParser = DateParser.newBuilder().build();
        boolean relativeUsed = false;
        try {
            actual = dateParser.parseDateTime(testString);
        } catch (DateTimeParseException e) {
            relativeUsed = true;
            actual = parser.asDateTime(testString);
        }
        LocalDateTime expected = LocalDateTime.ofInstant(instant.minus(Period.ofDays(1)), ZoneId.systemDefault());
        assertEquals(expected, actual);
        assertTrue(relativeUsed);
    }

    @Test
    void parseNoFallback() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        String testString = instant.minus(Period.ofDays(1)).toString();
        LocalDateTime actual = null;
        DateParser dateParser = DateParser.newBuilder().build();
        boolean relativeUsed = false;
        try {
            actual = dateParser.parseDateTime(testString);
        } catch (DateTimeParseException e) {
            relativeUsed = true;
            actual = parser.asDateTime(testString);
        }
        LocalDateTime expected = LocalDateTime.ofInstant(instant.minus(Period.ofDays(1)), ZoneId.systemDefault());
        assertEquals(expected, actual);
        assertFalse(relativeUsed);
    }

}