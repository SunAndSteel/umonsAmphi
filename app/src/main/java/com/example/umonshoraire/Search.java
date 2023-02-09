package com.example.umonshoraire;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

public class Search extends UImanager {
    Context context;
    Activity activity;
    private final ArrayList<Amphi> listAmphi = new ArrayList<>();

    public Search(Context context, Activity activity) {
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
     * Delete duplicate on "listAmphi" ArrayList
     */
    private void deleteDuplicate() {
        //TODO
    }
    /**
     * Sorting list by hours
     */
    private void sortList() {
        //TODO
        deleteDuplicate();
    }

    public Runnable makeSearchRunnable() {
        Log.d("SEARCH", MainActivity.amphi);
        return () -> {
            if(MainActivity.amphi.equals("")) {
                return;
            }
            try {

                runOnUiThread(resetProgressBar(activity));
                //runOnUiThread(updateSpinnerRunnable(false, activity));
                runOnUiThread(updateButtons(false, activity));

                String[] files = context.fileList();

                Date date = new GregorianCalendar().getTime();
                Calendar calToday= Calendar.getInstance();
                calToday.setTime(date);

                for (String name: files) {

                    runOnUiThread(updateProgressBar(activity));
                    try {
                        InputStreamReader fis = new InputStreamReader(context.openFileInput(name), StandardCharsets.UTF_8);
                        List<ICalendar> icalList = Biweekly.parse(fis).all();
                        fis.close();
                        for (ICalendar ical: icalList) {
                            int taille = ical.getEvents().size();
                            for(int i = 0; i < taille; i++) {
                                VEvent v = ical.getEvents().get(i);
                                Date dateV = v.getDateStart().getValue();

                                Calendar calV = Calendar.getInstance();
                                calV.setTime(dateV);
                                if(v.getLocation().getValue().equals(MainActivity.amphi)) {
                                    if(calV.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH)) {
                                        Log.d("DAY", "TRUE");
                                        if(calV.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) ) {
                                            Log.d("MONTH", "TRUE");
                                            if(calV.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)) {
                                                Log.d("YEAR", "TRUE");
                                                Log.d("DEBUG", "MATCH");
                                                listAmphi.add(new Amphi(v.getLocation().getValue(),v.getSummary().getValue(), v.getDateStart().getValue(), v.getDateEnd().getValue()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                }
                } catch (Exception e) {
                e.printStackTrace();
            }
            sortList();
            if(listAmphi.isEmpty()) {
                listAmphi.add(new Amphi("Libre toute la journée.","Aucun cours trouvé.", new Date(), new Date() ));
            }
            //runOnUiThread(updateSpinnerRunnable(true, activity));
            runOnUiThread(updateRecyclerView());
            runOnUiThread(updateButtons(true, activity));
        };
    }
}
