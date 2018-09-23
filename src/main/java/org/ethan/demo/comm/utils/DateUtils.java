package org.ethan.demo.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateUtils {

    private static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateStr(String dateStr) throws ParseException {
        return dateStr.split(" ")[0];
    }
}
