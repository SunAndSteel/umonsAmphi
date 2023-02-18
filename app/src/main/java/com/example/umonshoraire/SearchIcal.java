package com.example.umonshoraire;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SearchIcal extends UImanager {
    Context context;
    Activity activity;
    private ArrayList<Amphi> listAmphi = new ArrayList<>();

    public SearchIcal(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }
    /**
     * Updating recycler view on mainActivityUI
     * @return Runnable object
     */
    private Runnable updateRecyclerView() {
        return () -> {
            RecyclerView recyclerView = activity.findViewById(R.id.amphiDispo);
            AmphiAdapter ad = new AmphiAdapter(listAmphi);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(ad);
        };
    }
    /**
     * Sorting list by hours
     */
    private void sortList() {

        Collections.sort(listAmphi);
    }


    public Runnable makeSearchRunnable() {
        return () -> {

            if(MainActivity.amphi.equals("")) {
                return;
            }
            try {
                runOnUiThread(resetProgressBar(activity));
                //runOnUiThread(updateSpinnerRunnable(false, activity));
                runOnUiThread(updateButtons(false, activity));

                //Setting up the filter to find today's events in calendar
                java.util.Calendar today = java.util.Calendar.getInstance();
                today.set(java.util.Calendar.HOUR_OF_DAY, 0);
                today.clear(java.util.Calendar.MINUTE);
                today.clear(java.util.Calendar.SECOND);

                // create a period starting now with a duration of one (1) day..
                Period period = new Period(new DateTime(today.getTime()), new Dur(1, 0, 0, 0));
                Filter filter = new Filter(new PeriodRule(period));


                String[] files = context.fileList();

                for (String name: files) {
                    runOnUiThread(updateProgressBar(activity));
                    try {
                        InputStreamReader fis = new InputStreamReader(context.openFileInput(name), StandardCharsets.UTF_8);
                        CalendarBuilder builder = new CalendarBuilder();
                        net.fortuna.ical4j.model.Calendar calendar = builder.build(fis);

                        //Creating a calendar of all today's events
                        Collection eventsToday = filter.filter(calendar.getComponents(Component.VEVENT));

                        //Looking in calendar of today's events those matching the location selected by the user
                        for (Object o: eventsToday) {
                            Component component = (Component)o;
                            if(component.getProperty("LOCATION").toString().contains(MainActivity.amphi)) {
                                listAmphi.add(new Amphi(
                                        component.getProperty(Property.LOCATION).getValue(),
                                        component.getProperty(Property.SUMMARY).getValue(),
                                        component.getProperty(Property.DTSTART).getValue(),
                                        component.getProperty(Property.DTEND).getValue()
                                ));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sortList();
            if(listAmphi.isEmpty()) {
                listAmphi.add(new Amphi("Libre toute la journée.","Aucun cours trouvé.", new DateTime(), new DateTime() ));
            }
            //runOnUiThread(updateSpinnerRunnable(true, activity));
            runOnUiThread(updateRecyclerView());
            runOnUiThread(updateButtons(true, activity));
        };
    }
}
