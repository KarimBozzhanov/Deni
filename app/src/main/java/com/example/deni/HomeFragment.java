package com.example.deni;

import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements summaryClickListener{

    Spinner categories_spinner;
    RecyclerView summaryView;
    SummaryAdapter summaryAdapter;
    ArrayList<Summary> summaryArrayList;
    FirebaseFirestore firebaseFirestore;
    Context context;
    private static final String LOG_TAG = CreateSummaryFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference db = firebaseFirestore.collection("Summary");
        TextView summaryCountText = (TextView) view.findViewById(R.id.summary_count);
        AggregateQuery collectionCount = db.count();
        collectionCount.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AggregateQuerySnapshot snapshot = task.getResult();
                summaryCountText.setText(String.valueOf(snapshot.getCount()));
            }
        });
        categories_spinner = (Spinner) view.findViewById(R.id.categories_spinner);
        ArrayAdapter<?> categoriesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories_spinner.setAdapter(categoriesAdapter);
        categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] categoriesArray = getResources().getStringArray(R.array.categories);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        summaryView = view.findViewById(R.id.summaryList);
        db.orderBy("creationDate", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(LOG_TAG, error.getMessage());
                    return;
                }

                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED){
                        summaryArrayList.add(documentChange.getDocument().toObject(Summary.class));
                    }
                    summaryAdapter.notifyDataSetChanged();
                }
            }
        });
        summaryView.setHasFixedSize(true);
        summaryView.setLayoutManager(new LinearLayoutManager(getContext()));

        summaryArrayList = new ArrayList<>();
        summaryAdapter = new SummaryAdapter(getContext(), summaryArrayList, this);
        summaryView.setAdapter(summaryAdapter);

        return view;
    }

    @Override
    public void onSummaryClicked(Summary summary, int position) {
        Toast.makeText(getContext(), "Резюме - " + summary.getDesignerName(), Toast.LENGTH_SHORT).show();
        long views = summary.getViews();
        Log.e(LOG_TAG, "id с базы данных - " + summary.getId());
        String id = String.valueOf(summary.getId());
        Log.e(LOG_TAG, "Ваш id - " + id);
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference db = firebaseFirestore.collection("Summary").document(id);
        db.update("views", views + 1);

        getActivity().recreate();

        String designerName, speciality, jobType, experience, education, skills, information, city, salary,
                currency, phoneNumber, instagram, telegram, userName;

        designerName = summary.getDesignerName();
        speciality = summary.getSpeciality();
        jobType = summary.getJobType();
        experience = summary.getExperience();
        education = summary.getEducation();
        skills = summary.getSkills();
        information = summary.getInformation();
        city = summary.getCity();
        salary = summary.getSalary();
        currency = summary.getCurrency();
        phoneNumber = summary.getPhoneNumber();
        instagram = summary.getInstagram();
        telegram = summary.getTelegram();
        userName = summary.getUserName();

        Intent intent = new Intent(getContext(), summaryDetailsActivity.class);
        intent.putExtra("designerName", designerName);
        intent.putExtra("speciality", speciality);
        intent.putExtra("jobType", jobType);
        intent.putExtra("experience", experience);
        intent.putExtra("education", education);
        intent.putExtra("skills", skills);
        intent.putExtra("information", information);
        intent.putExtra("city", city);
        intent.putExtra("salary", salary);
        intent.putExtra("currency", currency);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("instagram", instagram);
        intent.putExtra("telegram", telegram);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}