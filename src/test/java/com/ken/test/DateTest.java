package com.ken.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.TimeZone;

public class DateTest {

    @Test
    public void testDateFormat() {

        String dateStr = "2019-09-20";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ZoneId zoneId = ZoneId.of("PRC");
        Instant instant = LocalDate.parse(dateStr, formatter).atStartOfDay().atZone(zoneId).toInstant();

        LocalDateTime prc = LocalDateTime.ofInstant(instant, ZoneId.of("PRC"));
        System.out.println(prc.format(formatter));
    }

    @Test
    public void compareTime() {
        Instant now = Instant.now();
        Instant last = now.minusSeconds(6 * 24 * 60 * 60);

        ZoneId zoneId = ZoneId.of("UTC");
        System.out.println(zoneId.getRules().getStandardOffset(Instant.now()));

//        ZoneId.getAvailableZoneIds();
//        TimeZone.getAvailableIDs();
    }

    @Test
    public void voidDateStr() {

        String dateStr = "2019-09-34";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate.parse(dateStr, formatter);
    }

    @Test
    public void testInstant2Hex() {

        String result = Long.toHexString(Instant.now().getEpochSecond()) + RandomStringUtils.randomNumeric(5);
        System.out.println(result);
    }

}
