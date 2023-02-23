package com.example.umonshoraire

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.filter.Filter
import net.fortuna.ical4j.filter.PeriodRule
import net.fortuna.ical4j.model.*
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.Calendar

class SearchIcal(private var context: Context, private var activity: Activity) : UImanager() {
    private val listAmphi = ArrayList<Amphi>()

    /**
     * Updating recycler view on mainActivityUI
     * @return Runnable object
     */
    private fun updateRecyclerView(): Runnable {
        return Runnable {
            val recyclerView = activity.findViewById<RecyclerView>(R.id.amphiDispo)
            val ad = AmphiAdapter(listAmphi)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = ad
        }
    }

    /**
     * Sorting list by hours
     */
    private fun sortList() {
        listAmphi.sort()
    }

    fun makeSearchRunnable(): Runnable {
        return Runnable {
            if (MainActivity.amphi == "") {
                return@Runnable
            }
            try {
                runOnUiThread(resetProgressBar(activity))
                //runOnUiThread(updateSpinnerRunnable(false, activity));
                runOnUiThread(updateButtons(false, activity))

                //Setting up the filter to find today's events in calendar
                val today = Calendar.getInstance()
                today.set(Calendar.HOUR_OF_DAY, 0)
                today.clear(Calendar.MINUTE)
                today.clear(Calendar.SECOND)
                // create a period starting now with a duration of one (1) day..
                val period = Period(DateTime(today.time), Dur(1, 0, 0, 0))
                val filter = Filter(PeriodRule(period))
                val files = context.fileList()
                if (files.isEmpty()) {
                    return@Runnable
                }
                for (name in files) {
                    runOnUiThread(updateProgressBar(activity))
                    try {
                        val fis = InputStreamReader(context.openFileInput(name), StandardCharsets.UTF_8)
                        val builder = CalendarBuilder()
                        val calendar = builder.build(fis)

                        //Creating a calendar of all today's events
                        val eventsToday = filter.filter(calendar.getComponents(Component.VEVENT))

                        //Looking in calendar of today's events those matching the location selected by the user
                        for (o in eventsToday) {
                            val component = o as Component
                            if (component.getProperty("LOCATION").toString().contains(MainActivity.amphi)
                            ) {
                                listAmphi.add(
                                    Amphi(
                                        component.getProperty(Property.LOCATION).value,
                                        component.getProperty(Property.SUMMARY).value,
                                        component.getProperty(Property.DTSTART).value,
                                        component.getProperty(Property.DTEND).value
                                    )
                                )
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            sortList()
            if (listAmphi.isEmpty()) {
                listAmphi.add(
                    Amphi("Libre toute la journée.", "Aucun cours trouvé.", DateTime(), DateTime()
                    )
                )
            }
            //runOnUiThread(updateSpinnerRunnable(true, activity));
            runOnUiThread(updateRecyclerView())
            runOnUiThread(updateButtons(true, activity))
        }
    }
}