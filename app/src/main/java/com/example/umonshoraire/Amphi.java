package com.example.umonshoraire;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Amphi {

    public String location;
    public String summary;
    public Date dateStart;
    public Date dateEnd;


    public Amphi(String location, String summary, Date dateStart, Date dateEnd) {
        this.location = location;
        this.summary = summary;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(dateStart);
        tempCal.setTimeZone(TimeZone.getTimeZone("Europe/Brussels"));



        return "Début : " + tempCal.get(Calendar.HOUR_OF_DAY) + ":" + tempCal.get(Calendar.MINUTE);
    }

    public String getDateEnd() {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(dateEnd);
        tempCal.setTimeZone(TimeZone.getTimeZone("Europe/Brussels"));

        return "Fin : " + tempCal.get(Calendar.HOUR_OF_DAY) + ":" + tempCal.get(Calendar.MINUTE);
    }
}
