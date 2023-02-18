package com.example.umonshoraire;

import net.fortuna.ical4j.model.DateTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

public class Amphi implements Comparable<Amphi> {

    public String location;
    public String summary;
    public DateTime dateStart;
    public DateTime dateEnd;


    public Amphi(String location, String summary, DateTime dateStart, DateTime dateEnd) {
        this.location = location;
        this.summary = summary;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
    public Amphi(String location, String summary, String dateStart, String dateEnd) throws ParseException {
        this.location = location;
        this.summary = summary;
        this.dateStart = new DateTime(dateStart);
        this.dateEnd = new DateTime(dateEnd);
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

    @Override
    public int compareTo(Amphi o) {
        return dateStart.compareTo(o.dateStart);
    }
}
