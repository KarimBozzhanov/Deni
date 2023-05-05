package com.example.deni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MySummaryAdapter extends RecyclerView.Adapter<MySummaryAdapter.MySummaryViewHolder> {

    Context context;
    ArrayList<Summary> summaryArrayList;
    mySummaryClickListener listener;

    public MySummaryAdapter(Context context, ArrayList<Summary> summaryArrayList, mySummaryClickListener listener) {
        this.context = context;
        this.summaryArrayList = summaryArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MySummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_summary_card, parent, false);
        return new MySummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySummaryViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        holder.mySummaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSummaryClickListener(summaryArrayList.get(position), position);
            }
        });

        holder.refreshInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSummaryRefreshClickListener(summaryArrayList.get(position), position);
            }
        });

        holder.changeSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSummaryChangeSummaryClickListener(summaryArrayList.get(position), position);
            }
        });

        holder.deleteSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSummaryDeleteSummaryClickListener(summaryArrayList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return summaryArrayList.size();
    }

    public static class MySummaryViewHolder extends RecyclerView.ViewHolder {

        TextView cardDesignerName, cardCity, cardJobType, cardViews, cardSpeciality, cardCategory, cardSalary, cardCurrency, cardTimeCreating, summaryCount;
        Button refreshInList, changeSummary;
        ImageButton deleteSummary;
        CardView mySummaryCard;
        public MySummaryViewHolder(@NonNull View itemView) {
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
            mySummaryCard = itemView.findViewById(R.id.my_summary_card);
            refreshInList = (Button) itemView.findViewById(R.id.refreshInList);
            changeSummary = (Button) itemView.findViewById(R.id.changeSummary);
            deleteSummary = (ImageButton) itemView.findViewById(R.id.deleteSummary);
        }
    }

}
