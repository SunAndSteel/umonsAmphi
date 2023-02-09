package com.example.umonshoraire;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Response;


public class Request extends UImanager{

    Context context;
    Activity activity;


    public Request(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    /**
     * Performing requests using "link.txt" text file
     * @return Runnable object
     */
    public Runnable makeRequestRunnable() {

        return () -> {
                try {

                    runOnUiThread(updateButtons(false, activity));
                    runOnUiThread(resetProgressBar(activity));
                    runOnUiThread(updateSpinnerRunnable(false, activity));

                    // Récupérer la fichier contenant les liens dans /assets/
                    AssetManager assetManager1 = context.getAssets();
                    InputStream links = assetManager1.open("link.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(links));

                    // Récuperer la date au moment de l'execution de la méthode, conversion en un objet calendar
                    Date dateToday = new Date();
                    Calendar calToday= Calendar.getInstance();
                    calToday.setTime(dateToday);


                    // Itération sur tous les liens
                    int i = 0;
                    while (reader.ready()) {
                        String line = reader.readLine();

                        // Construire la requête
                        OkHttpClient client = new OkHttpClient();
                        okhttp3.Request request = new okhttp3.Request.Builder().url(line).build();
                        runOnUiThread(updateProgressBar(activity));

                        // Faire la requête
                        try (Response response = client.newCall(request).execute()) {
                            try (FileOutputStream fos = context.openFileOutput("horaire" + i, Context.MODE_PRIVATE)) {
                                fos.write(response.body().bytes());
                            }
                        } catch (IOException ignored) {}
                        i++;
                    }
                    runOnUiThread(updateButtons(true, activity));
                    runOnUiThread(updateSpinnerRunnable(true, activity));
                } catch (Exception ignored) {
                }

        };
    }
}