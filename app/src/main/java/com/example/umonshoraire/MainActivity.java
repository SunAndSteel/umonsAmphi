package com.example.umonshoraire;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public static String amphi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ScheduledExecutorService backgroundReq = Executors.newSingleThreadScheduledExecutor();


        Spinner spinner = findViewById(R.id.amphi_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.amphi_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(view -> {

            backgroundReq.execute(new Search(MainActivity.this, MainActivity.this).makeSearchRunnable());

        });
        Button buttonUpdate = findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(view -> {
            backgroundReq.execute(new Request(MainActivity.this, MainActivity.this).makeRequestRunnable());
        });
    }



    //Recupérer l'amphi
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.d("Spinner", "onItemSelected: " + parent.getItemAtPosition(pos));
        amphi = parent.getItemAtPosition(pos).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}