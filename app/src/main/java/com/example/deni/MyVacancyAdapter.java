package com.example.deni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
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

import kotlin.jvm.internal.Lambda;

public class MyVacancyAdapter extends RecyclerView.Adapter<MyVacancyAdapter.MyVacancyViewHolder> {

    Context context;

    ArrayList<Vacancy> vacancyArrayList;

    myVacancyClickListener listener;

    public MyVacancyAdapter(Context context, ArrayList<Vacancy> vacancyArrayList, myVacancyClickListener listener) {
        this.context = context;
        this.vacancyArrayList = vacancyArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyVacancyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_vacancies_card, parent, false);
        return new MyVacancyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVacancyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Vacancy vacancy = vacancyArrayList.get(position);
        holder.my_vacancy_card_companyName.setText(vacancy.getCompany());
        holder.my_vacancy_card_city.setText(vacancy.getCity());
        holder.my_vacancy_card_views.setText(String.valueOf(vacancy.getViews()));
        holder.my_vacancy_card_timeCreating.setText(vacancy.getDateCreating());
        holder.my_vacancy_card_name.setText(vacancy.getVacancyName());
        holder.my_vacancy_card_category.setText(vacancy.getCategory());
        holder.my_vacancy_card_salary.setText(vacancy.getSalary());
        holder.my_vacancy_card_currency.setText(vacancy.getCurrency());

        holder.refreshVacancyInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.refreshMyVacancyClickListener(vacancyArrayList.get(position), position);
            }
        });

        holder.changeVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.editMyVacancyClickListener(vacancyArrayList.get(position), position);
            }
        });

        holder.deleteVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deleteMyVacancyClickListener(vacancyArrayList.get(position), position);
            }
        });

        holder.myVacancyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMyVacancyClickListener(vacancyArrayList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vacancyArrayList.size();
    }

    public static class MyVacancyViewHolder extends RecyclerView.ViewHolder{

        TextView my_vacancy_card_companyName, my_vacancy_card_city, my_vacancy_card_views, my_vacancy_card_timeCreating, my_vacancy_card_name, my_vacancy_card_category, my_vacancy_card_salary, my_vacancy_card_currency;
        Button refreshVacancyInList, changeVacancy;
        ImageButton deleteVacancy;
        CardView myVacancyCard;

        public MyVacancyViewHolder(@NonNull View itemView) {
            super(itemView);

            my_vacancy_card_companyName = (TextView) itemView.findViewById(R.id.my_vacancy_card_companyName);
            my_vacancy_card_city = (TextView) itemView.findViewById(R.id.my_vacancy_card_city);
            my_vacancy_card_views = (TextView) itemView.findViewById(R.id.my_vacancy_card_views);
            my_vacancy_card_timeCreating = (TextView) itemView.findViewById(R.id.my_vacancy_card_companyName);
            my_vacancy_card_name = (TextView) itemView.findViewById(R.id.my_vacancy_card_name);
            my_vacancy_card_category = (TextView) itemView.findViewById(R.id.my_vacancy_card_category);
            my_vacancy_card_salary = (TextView) itemView.findViewById(R.id.my_vacancy_card_salary);
            my_vacancy_card_currency = (TextView) itemView.findViewById(R.id.my_vacancy_card_currency);

            refreshVacancyInList = (Button) itemView.findViewById(R.id.refreshVacancyInList);
            changeVacancy = (Button) itemView.findViewById(R.id.changeVacancy);
            deleteVacancy = (ImageButton) itemView.findViewById(R.id.deleteVacancy);
            myVacancyCard = itemView.findViewById(R.id.my_vacancy_card);
        }
    }
}
