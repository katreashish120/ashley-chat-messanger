package com.vr.ashley.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ashish on 4/4/2017.
 */
public class Utils {


    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String getCurrentDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
}
