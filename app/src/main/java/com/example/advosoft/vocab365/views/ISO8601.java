package com.example.advosoft.vocab365.views;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ISO8601 {
    public static String fromCalendar(Calendar calendar) {
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(calendar.getTime());
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    public static Date getDate(String iso8601string) throws ParseException {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
        Date d = sdf.parse(iso8601string);
        return  d;
           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSSZ");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsedDate = sdf.parse(iso8601string);
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            return simpleDate.format(parsedDate);*/
    }

    public static String toDateAndTime(String iso8601string) throws ParseException {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date d = getDate(iso8601string);

        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = output.format(d);
        return  formattedTime;
           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSSZ");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsedDate = sdf.parse(iso8601string);
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            return simpleDate.format(parsedDate);*/
    }

    public static String aTimeAgo(String xDateStr){
        try {
            Date xDate = getDate(xDateStr);
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            String formattedTime = output.format(xDate);
            Calendar xCal = Calendar.getInstance();
            xCal.setTime(xDate);
            long xMiliSec = xCal.getTimeInMillis();

            long currentMili = Utils.getCurrentTimeInMili();
            Calendar cCal = Calendar.getInstance();
            cCal.setTimeInMillis(currentMili);

            long difMili = currentMili - xMiliSec;

            SimpleDateFormat cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cDate.setCalendar(cCal);
            String timeString = calculateDiff(xMiliSec, currentMili);
            return  timeString.isEmpty() ? formattedTime : timeString;
        }catch (ParseException e){
            e.printStackTrace();
        }

        return "";
    }

    public static String calculateDiff(long xDate, long cDate){
        //in milliseconds
        long diff = cDate - xDate;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        Log.i("Date :", diffDays+":"+diffHours+":"+diffMinutes+":"+diffSeconds);

        if(diffDays == 0){
            /*if(diffHours > 0)
                return diffHours+"h "+diffMinutes+"m ago";
            else
                return diffMinutes+"m ago";*/
            return "Today";
        }else if(diffDays == 1){
            return "Yesterday";
        }else if(diffDays > 1 && diffDays <= 7){
            return  diffDays+" days ago";
        }else if(diffDays > 7 && diffDays <= 30){
            return "Week ago";
        }/*else if(diff > 30){
            return "month ago";
        }*/
        return  "";
    }

  /*  public void checkAgo(long difMili){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(difMili);

        Calendar cCal = Calendar.getInstance();
        cCal.setTimeInMillis(UtilsClass.getCurrentTimeInMili());



        if(cal.get(Calendar.DATE) < cCal.get(Calendar.DATE) && ){

        }

    }*/

    public static String toDate(String iso8601string) throws ParseException {
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(iso8601string);
            String formattedTime = output.format(d);
            return  formattedTime;

           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSSZ");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsedDate = sdf.parse(iso8601string);
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            return simpleDate.format(parsedDate);*/
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
    }

    public static String toTime(String iso8601string) throws ParseException {
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat output = new SimpleDateFormat("HH:mm");
            Date d = sdf.parse(iso8601string);
            String formattedTime = output.format(d);
            return  formattedTime;
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
    }
}
