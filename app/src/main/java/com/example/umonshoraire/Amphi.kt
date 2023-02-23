package com.example.umonshoraire

import net.fortuna.ical4j.model.DateTime
import java.text.ParseException
import java.util.*

class Amphi() : Comparable<Amphi> {


    private lateinit var location: String
    lateinit var summary: String
    private lateinit var dateStart: DateTime
    private lateinit var dateEnd: DateTime


    constructor(location: String, summary: String, dateStart: DateTime, dateEnd: DateTime) : this() {
        this.location = location
        this.summary = summary
        this.dateStart = dateStart
        this.dateEnd = dateEnd
    }

    @Throws(ParseException::class)
    constructor(location: String, summary: String, dateStart: String, dateEnd: String) : this() {
        this.location = location
        this.summary = summary
        this.dateStart = DateTime(dateStart)
        this.dateEnd = DateTime(dateEnd)
    }
    fun getDateStart(): String {
        val tempCal = Calendar.getInstance()
        tempCal.time = dateStart
        tempCal.timeZone = TimeZone.getTimeZone("Europe/Brussels")
        return "Début : " + tempCal[Calendar.HOUR_OF_DAY] + ":" + tempCal[Calendar.MINUTE]
    }

    fun getDateEnd(): String {
        val tempCal = Calendar.getInstance()
        tempCal.time = dateEnd
        tempCal.timeZone = TimeZone.getTimeZone("Europe/Brussels")
        return "Fin : " + tempCal[Calendar.HOUR_OF_DAY] + ":" + tempCal[Calendar.MINUTE]
    }

    override fun compareTo(other: Amphi): Int {
        return dateStart.compareTo(other.dateStart)
    }
}


