package com.example.umonshoraire

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AmphiAdapter() : RecyclerView.Adapter<AmphiAdapter.ViewHolder>() {

    constructor(listeAmphi: ArrayList<Amphi>) : this() {
        this.listAmphi = listeAmphi
    }

    private lateinit var listAmphi : ArrayList<Amphi>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var summaryView: TextView
        var dateStartView: TextView
        var dateEndView: TextView

        init {
            summaryView = itemView.findViewById(R.id.summaryView)
            dateStartView = itemView.findViewById(R.id.dateStartView)
            dateEndView = itemView.findViewById(R.id.dateEndView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAmphi.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.summaryView.text = listAmphi[position].summary
        holder.summaryView.setTypeface(holder.summaryView.typeface, Typeface.BOLD)
        holder.dateStartView.text = listAmphi[position].getDateStart()
        holder.dateEndView.text = listAmphi[position].getDateEnd()
    }

}