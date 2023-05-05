package com.example.deni;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class VacanciesFragment extends Fragment implements vacancyClickListener{

    private static final String LOG_TAG = CreateSummaryFragment.class.getName();

    Spinner vacancy_categories_spinner;

    FirebaseFirestore firebaseFirestore;

    RecyclerView vacancyRecycleView;

    ArrayList<Vacancy> vacancyArrayList;
    VacancyAdapter vacancyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacancies, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        TextView vacanciesCount = (TextView) view.findViewById(R.id.vacancies_count);
        CollectionReference db = firebaseFirestore.collection("Vacancies");
        AggregateQuery aggregateQuery = db.count();
        aggregateQuery.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                vacanciesCount.setText(String.valueOf(aggregateQuerySnapshot.getCount()));
            }
        });
        vacancy_categories_spinner = (Spinner) view.findViewById(R.id.vacancy_categories_spinner);
        ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacancy_categories_spinner.setAdapter(categoryAdapter);

        vacancy_categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] categoriesArray = getResources().getStringArray(R.array.categories);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vacancyRecycleView = view.findViewById(R.id.vacanciesList);
        vacancyRecycleView.setHasFixedSize(true);
        vacancyRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        vacancyArrayList = new ArrayList<>();
        vacancyAdapter = new VacancyAdapter(getContext(), vacancyArrayList, this);
        vacancyRecycleView.setAdapter(vacancyAdapter);

        db.orderBy("dateCreating", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(LOG_TAG, error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        vacancyArrayList.add(dc.getDocument().toObject(Vacancy.class));
                    }
                    vacancyAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    @Override
    public void onVacancyClick(Vacancy vacancy, int position) {
        long views = vacancy.getViews();
        String id = String.valueOf(vacancy.getId());
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference db = firebaseFirestore.collection("Vacancies").document(id);
        db.update("views", views + 1);
        getActivity().recreate();

        String companyName, userName, creationTime, vacancyName, vacancyCategory, vacancyJobType, vacancySalary, vacancyCurrency, vacancySchedule, vacancyExperience, vacancyDuty, vacancyRequirements, vacancyConditions, vacancyJobPlace, phone, email, telegram, instagram;
        companyName = vacancy.getCompany();
        userName = vacancy.getUserName();
        creationTime = vacancy.getDateCreating();
        vacancyName = vacancy.getVacancyName();
        vacancyCategory = vacancy.getCategory();
        vacancyJobType = vacancy.getJobType();
        vacancySalary = vacancy.getSalary();
        vacancyCurrency = vacancy.getCurrency();
        vacancySchedule = vacancy.getSchedule();
        vacancyExperience = vacancy.getExperience();
        vacancyDuty = vacancy.getDuty();
        vacancyRequirements = vacancy.getRequirements();
        vacancyConditions = vacancy.getConditions();
        vacancyJobPlace = vacancy.getJobPlace();
        phone = vacancy.getPhone();
        instagram = vacancy.getInstagram();
        telegram = vacancy.getTelegram();
        email = vacancy.getEmail();

        Intent intent = new Intent (getContext(), vacancyDetailsActivity.class);
        intent.putExtra("companyName", companyName);
        intent.putExtra("userName", userName);
        intent.putExtra("vacancyName", vacancyName);
        intent.putExtra("vacancyCategory", vacancyCategory);
        intent.putExtra("vacancyJobType", vacancyJobType);
        intent.putExtra("vacancySalary", vacancySalary);
        intent.putExtra("vacancyCurrency", vacancyCurrency);
        intent.putExtra("vacancySchedule", vacancySchedule);
        intent.putExtra("vacancyExperience", vacancyExperience);
        intent.putExtra("vacancyDuty", vacancyDuty);
        intent.putExtra("vacancyRequirements", vacancyRequirements);
        intent.putExtra("vacancyConditions", vacancyConditions);
        intent.putExtra("vacancyJobPlace", vacancyJobPlace);
        intent.putExtra("phone", phone);
        intent.putExtra("instagram", instagram);
        intent.putExtra("telegram", telegram);
        intent.putExtra("email", email);
        intent.putExtra("creationTime", creationTime);
        startActivity(intent);
    }
}