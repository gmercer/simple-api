package com.github.capm.util;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RelativeDateTimeParserTest {

    @Test
    void parseNowMinus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault()) ;
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.parse("[now-1d]") ;
        LocalDateTime expected = LocalDateTime.ofInstant(instant.minus(Period.ofDays(1)),ZoneId.systemDefault());
        assertEquals(expected, actual);
    }

    @Test
    void parseNowPlus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault()) ;
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.parse("[now+1d]") ;
        LocalDateTime expected = LocalDateTime.ofInstant(instant.plus(Period.ofDays(1)),ZoneId.systemDefault());
        assertEquals(expected, actual);
    }


    @Test
    void parseNow() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault()) ;
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime now = parser.asDateTime("now") ;
        LocalDateTime Now = parser.asDateTime("Now") ;
        LocalDateTime nOw = parser.asDateTime("nOw") ;
        LocalDateTime NOW = parser.asDateTime("NOW") ;
        LocalDateTime expected = LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
        assertEquals(expected, now);
        assertEquals(expected, Now);
        assertEquals(expected, nOw);
        assertEquals(expected, NOW);
    }

    @Test
    void parseMinus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault()) ;
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.asDateTime("-1d") ;
        LocalDateTime expected = LocalDateTime.ofInstant(instant.minus(Period.ofDays(1)),ZoneId.systemDefault());
        assertEquals(expected, actual);
    }

    @Test
    void parsePlus1d() {
        Instant instant = Instant.now();
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault()) ;
        RelativeDateTimeParser parser = new RelativeDateTimeParser(clock);
        LocalDateTime actual = parser.asDateTime("+1d") ;
        LocalDateTime expected = LocalDateTime.ofInstant(instant.plus(Period.ofDays(1)),ZoneId.systemDefault());
        assertEquals(expected, actual);
    }



    @Test
    void getClock() {
    }

    @Test
    void setClock() {
    }
}