package com.example.umonshoraire

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    companion object {
        lateinit var amphi: String
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, pos: Int, l: Long) {
        Log.d("ArrayAdapter", "onItemSelected: " + parent!!.getItemAtPosition(pos))
        amphi = parent.getItemAtPosition(pos).toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundReq = Executors.newSingleThreadScheduledExecutor()
        val spinner: AutoCompleteTextView = findViewById(R.id.menu_text)
        val adapter = ArrayAdapter.createFromResource(this, R.array.amphi_array, R.layout.textinput_item)

        spinner.setAdapter(adapter)
        spinner.onItemClickListener = this

        val buttonSearch: Button = findViewById(R.id.button_search)
        buttonSearch.setOnClickListener {
            backgroundReq.execute(SearchIcal(this@MainActivity, this@MainActivity).makeSearchRunnable())
        }

        val buttonUpdate: Button = findViewById(R.id.button_update)
        buttonUpdate.setOnClickListener {
            backgroundReq.execute(Request(this@MainActivity, this@MainActivity).makeRequestRunnable())
        }
    }
}
