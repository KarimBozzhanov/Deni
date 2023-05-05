package com.example.deni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {

    Context context;
    ArrayList<Summary> summaryArrayList;
    summaryClickListener listener;


    public SummaryAdapter(Context context, ArrayList<Summary> summaryArrayList, summaryClickListener listener) {
        this.context = context;
        this.summaryArrayList = summaryArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.summary_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Summary summary = summaryArrayList.get(position);
        holder.cardDesignerName.setText(summary.getDesignerName());
        holder.cardCity.setText(summary.getCity());
        holder.cardJobType.setText(summary.getJobType());
        String views = String.valueOf(summary.getViews());
        holder.cardViews.setText(views);
        holder.cardSpeciality.setText(summary.getSpeciality());
        holder.cardCategory.setText(summary.getCategory());
        holder.cardSalary.setText(summary.getSalary());
        if (summary.getCurrency().equals("Доллар")){
            holder.cardCurrency.setText("$");
        } else if(summary.getCurrency().equals("Тенге")){
            holder.cardCurrency.setText("тг");
        } else if (summary.getCurrency().equals("Евро")){
            holder.cardCurrency.setText("EUR");
        } else {
            holder.cardCurrency.setText("руб");
        }
        holder.cardTimeCreating.setText(summary.getCreationDate());

        holder.summaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSummaryClicked(summaryArrayList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return summaryArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cardDesignerName, cardCity, cardJobType, cardViews, cardSpeciality, cardCategory, cardSalary, cardCurrency, cardTimeCreating;
        CardView summaryCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardDesignerName = (TextView) itemView.findViewById(R.id.card_designerName);
            cardCity = (TextView) itemView.findViewById(R.id.card_city);
            cardJobType = (TextView) itemView.findViewById(R.id.card_jobType);
            cardViews = (TextView) itemView.findViewById(R.id.card_views);
            cardSpeciality = (TextView) itemView.findViewById(R.id.card_speciality);
            cardCategory = (TextView) itemView.findViewById(R.id.card_category);
            cardSalary = (TextView) itemView.findViewById(R.id.card_salary);
            cardCurrency = (TextView) itemView.findViewById(R.id.card_currency);
            cardTimeCreating = (TextView) itemView.findViewById(R.id.card_timeCreating);
            summaryCard = itemView.findViewById(R.id.summary_card);
        }
    }
}
