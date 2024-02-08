package com.engeto.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Settings {

    public static String fileDelimiter  = "\t";

    public static String getFileDelimiter() {
        return fileDelimiter;
    }

    public static LocalTime writeFormatteTime(LocalTime time) {
        DateTimeFormatter formatte = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(formatte.format(time));
    }
}
