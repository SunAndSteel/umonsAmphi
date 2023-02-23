package com.example.umonshoraire

import android.app.Activity
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class Request(private var context: Context, private var activity: Activity) : UImanager() {


    /**
     * Performing requests using "link.txt" text file
     * @return Runnable object
     */
    fun makeRequestRunnable() : Runnable {
        return Runnable {
            try {
                runOnUiThread(updateButtons(false, activity))
                runOnUiThread(resetProgressBar(activity))
                runOnUiThread(updateSpinnerRunnable(false, activity))

                // Récupérer la fichier contenant les liens dans /assets/
                val assetManager1 = context.assets
                val links = assetManager1.open("link.txt")
                val reader = BufferedReader(InputStreamReader(links))

                // Récuperer la date au moment de l'execution de la méthode, conversion en un objet calendar
                val dateToday = Date()
                val calToday = Calendar.getInstance()
                calToday.time = dateToday


                // Itération sur tous les liens
                var i = 0
                while (reader.ready()) {
                    val line = reader.readLine()

                    // Construire la requête
                    val client = OkHttpClient()
                    val request: Request = Request.Builder().url(line).build()
                    runOnUiThread(updateProgressBar(activity))

                    // Faire la requête
                    try {
                        client.newCall(request).execute().use { response ->
                            context.openFileOutput("horaire$i", Context.MODE_PRIVATE)
                                .use { fos ->
                                    fos.write(
                                        response.body!!.bytes()
                                    )
                                }
                        }
                    } catch (ignored: IOException) {
                    }
                    i++
                }
                runOnUiThread(updateButtons(true, activity))
                runOnUiThread(updateSpinnerRunnable(true, activity))
            } catch (ignored: Exception) {
            }
        }

    }
}