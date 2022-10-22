package com.example.umonshoraire;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AmphiAdapter extends RecyclerView.Adapter<AmphiAdapter.ViewHolder> {

    private final ArrayList<Amphi> listeAmphi;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView summaryView;
        private final TextView dateStartView;
        private final TextView dateEndView;

        public ViewHolder(View itemView) {
            super(itemView);

            summaryView = (TextView) itemView.findViewById(R.id.summaryView);
            dateStartView = (TextView) itemView.findViewById(R.id.dateStartView);
            dateEndView = (TextView) itemView.findViewById(R.id.dateEndView);

        }

        public TextView getSummaryView() {
            return summaryView;
        }
        public TextView getDateStartView() { return dateStartView;}
        public TextView getDateEndView() { return dateEndView; }
    }

    public AmphiAdapter(ArrayList<Amphi> listeAmphi) {
        this.listeAmphi = listeAmphi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getSummaryView().setText(listeAmphi.get(position).summary);
        holder.getSummaryView().setTypeface(holder.getSummaryView().getTypeface(), Typeface.BOLD);
        holder.getDateStartView().setText(listeAmphi.get(position).getDateStart());
        holder.getDateEndView().setText(listeAmphi.get(position).getDateEnd());
    }

    @Override
    public int getItemCount() {
        return listeAmphi.size();
    }


}
