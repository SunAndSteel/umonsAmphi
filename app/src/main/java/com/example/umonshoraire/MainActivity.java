package com.example.umonshoraire;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    public static String amphi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ScheduledExecutorService backgroundReq = Executors.newSingleThreadScheduledExecutor();


        AutoCompleteTextView spinner = findViewById(R.id.menu_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.amphi_array,  R.layout.textinput_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(this);

        Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(view -> {

            backgroundReq.execute(new Search(MainActivity.this, MainActivity.this).makeSearchRunnable());

        });
        Button buttonUpdate = findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(view -> {
            backgroundReq.execute(new Request(MainActivity.this, MainActivity.this).makeRequestRunnable());
        });
    }

    public void error() {
        Toast.makeText(this, "Aucun amphi sélectionné", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
        Log.d("ArrayAdapter", "onItemSelected: " + parent.getItemAtPosition(pos));
        amphi = parent.getItemAtPosition(pos).toString();
    }
}