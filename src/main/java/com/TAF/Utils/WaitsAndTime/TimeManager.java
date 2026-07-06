package com.TAF.Utils.WaitsAndTime;

import java.util.Date;

public class TimeManager {
    // Safe timestamp for filenames/logs
    public static String getTimeStamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    // Unique simple timestamp
    public static String getSimpleTimeStamp() {
        return System.currentTimeMillis() + "";
    }
}