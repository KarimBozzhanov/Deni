package com.example.deni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder> {

    Context context;
    ArrayList<Vacancy> vacancyArrayList;
    vacancyClickListener listener;

    public VacancyAdapter(Context context, ArrayList<Vacancy> vacancyArrayList, vacancyClickListener listener) {
        this.context = context;
        this.vacancyArrayList = vacancyArrayList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public VacancyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.vacancy_card, parent, false);
        return new VacancyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VacancyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Vacancy vacancy = vacancyArrayList.get(position);
        String views = String.valueOf(vacancy.getViews());
        holder.vacancyCardCompanyName.setText(vacancy.getCompany());
        holder.vacancyCardCity.setText(vacancy.getCity());
        holder.vacancyCardViews.setText(views);
        holder.vacancyCardTimeCreating.setText(vacancy.getDateCreating());
        holder.vacancyCardName.setText(vacancy.getVacancyName());
        holder.vacancyCardCategory.setText(vacancy.getCategory());
        holder.vacancyCardSalary.setText(vacancy.getSalary());
        if (vacancy.getCurrency().equals("Доллар")){
            holder.vacancyCardCurrency.setText("$");
        } else if (vacancy.getCurrency().equals("Евро")){
            holder.vacancyCardCurrency.setText("EUR");
        } else if (vacancy.getCurrency().equals("Рубль")){
            holder.vacancyCardCurrency.setText("руб");
        } else {
            holder.vacancyCardCurrency.setText("тг");
        }
        holder.vacancyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onVacancyClick(vacancyArrayList.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return vacancyArrayList.size();
    }

    public static class VacancyViewHolder extends RecyclerView.ViewHolder {

        TextView vacancyCardCompanyName, vacancyCardCity, vacancyCardViews, vacancyCardTimeCreating, vacancyCardName, vacancyCardCategory, vacancyCardSalary, vacancyCardCurrency;
        CardView vacancyCard;
        public VacancyViewHolder(@NonNull View itemView) {
            super(itemView);
            vacancyCardCompanyName = (TextView) itemView.findViewById(R.id.vacancy_card_companyName);
            vacancyCardCity = (TextView) itemView.findViewById(R.id.vacancy_card_city);
            vacancyCardViews = (TextView) itemView.findViewById(R.id.vacancy_card_views);
            vacancyCardTimeCreating = (TextView) itemView.findViewById(R.id.vacancy_card_timeCreating);
            vacancyCardName = (TextView) itemView.findViewById(R.id.vacancy_card_name);
            vacancyCardCategory = (TextView) itemView.findViewById(R.id.vacancy_card_category);
            vacancyCardSalary = (TextView) itemView.findViewById(R.id.vacancy_card_salary);
            vacancyCardCurrency = (TextView) itemView.findViewById(R.id.vacancy_card_currency);
            vacancyCard = (CardView) itemView.findViewById(R.id.vacancy_card);
        }


    }
}
