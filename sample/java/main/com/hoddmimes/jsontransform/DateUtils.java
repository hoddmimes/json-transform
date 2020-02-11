package com.hoddmimes.jsontransform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils
{
    static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    static final String DATE_FILLER = "1970-01-01 00:00:00.000";

    /**
     * Convert routines date to localdate/locaDateTime and vice versa
     */
    public static LocalDate dateToLocalDate(Date pDate ) {
        return Instant.ofEpochMilli(pDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date localDateToDate(LocalDate pDate ) {
            return java.util.Date.from(pDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date pDate) {
        return Instant.ofEpochMilli(pDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime pDate ) {
        return java.util.Date.from(pDate.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * String to LocalDate / LocalDateTime and vice versa
     */
     public static LocalDate stringToLocalDate( String pDateString ) {
         return LocalDate.parse( pDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
     }

    public static LocalDateTime stringToLocalDateTime( String pDateString ) {
        String tDateStr = pDateString;
        if (tDateStr.length() > DATE_FILLER.length()) {
            tDateStr = tDateStr.substring(0, DATE_FILLER.length());
        } else if (tDateStr.length() < DATE_FILLER.length()) {
            tDateStr = tDateStr + DATE_FILLER.substring( tDateStr.length(), DATE_FILLER.length());
        }
        return LocalDateTime.parse( tDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public static String localDateToString( LocalDate  pLocalDate ) {
        return pLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String localDateTimeToString( LocalDateTime  pLocalDateTime ) {
        return pLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }



    public static String dateToString(Date pDate) {
        if (pDate == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT );
        return sdf.format( pDate );
    }

    public static Date stringToDate(String pDateString) {
        if (pDateString == null){
            return null;
        }

        String tDateStr = pDateString;
        if (tDateStr.length() > DATE_FILLER.length()) {
            tDateStr = tDateStr.substring(0, DATE_FILLER.length());
        } else if (tDateStr.length() < DATE_FILLER.length()) {
            tDateStr = tDateStr + DATE_FILLER.substring( tDateStr.length(), DATE_FILLER.length());
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT );
            return sdf.parse( tDateStr );
        }
        catch( ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String justDate( String pDateStr ) {
        if (pDateStr.length() > "yyyy-MM-dd".length()) {
            return pDateStr.substring(0, "yyyy-MM-dd".length());
        }
        return pDateStr;
    }
    public static String justDate( Date pDate ) {
        String tDateStr  = dateToString( pDate );
        return justDate( tDateStr );
    }

    public static String justTime( String pDateStr ) {
        if (pDateStr.length() >= DATE_FILLER.length()) {
            return pDateStr.substring("yyyy-MM-dd".length() + 1, DATE_FILLER.length());
        }
        throw new RuntimeException("invalid date and time string \"" + pDateStr  + "\"");
    }

    public static String justTime( Date pDate ) {
        String tDateStr  = dateToString( pDate );
        return justTime( tDateStr );
    }

/*
    public static void main( String args[] ) {
        Date d =stringToDate("2019-12-22 00");
        System.out.println( "Date: " + d );
        System.out.println( "Date: " + DateUtils.justDate("2019-12-22 14:32:12"));
        System.out.println( "Date: " + DateUtils.justTime("2019-12-22 14:32:12:123"));
    }
*/
}
