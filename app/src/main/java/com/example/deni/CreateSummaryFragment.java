package com.example.deni;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CreateSummaryFragment extends Fragment {

    private static final String LOG_TAG = CreateSummaryFragment.class.getName();
    Spinner categories, jobType, currency;
    int views = 0;
    FirebaseFirestore db;
    DatabaseReference summaryTable;
    long Id = 0;
    String categoryString, jobTypeString, currencyString;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_summary, container, false);
        categories = (Spinner) view.findViewById(R.id.categories_spinner);
        jobType = (Spinner) view.findViewById(R.id.job_type);
        currency = (Spinner) view.findViewById(R.id.salary_currency);
        ArrayAdapter<?> categoriesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.jobType, android.R.layout.simple_spinner_item);
        ArrayAdapter<?> salaryCurrencyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.salary_currancy, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        categories.setAdapter(categoriesAdapter);
        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        jobType.setAdapter(jobTypeAdapter);
        salaryCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        currency.setAdapter(salaryCurrencyAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = (String) adapterView.getItemAtPosition(i);
                categoryString = category;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jobType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String jobType = (String) adapterView.getItemAtPosition(i);
                jobTypeString = jobType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currency = (String) adapterView.getItemAtPosition(i);
                currencyString = currency;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        db = FirebaseFirestore.getInstance();

        AggregateQuery count =  db.collection("Summary").count();
        count.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                AggregateQuerySnapshot snapshot = task.getResult();
                Id = snapshot.getCount();
            }
        });

        Button createSummary = (Button) view.findViewById(R.id.createSummary);
        createSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText designerNameEdit = (EditText) view.findViewById(R.id.designerName);
                String designerName = (String) designerNameEdit.getText().toString();

                EditText specialityEdit = (EditText) view.findViewById(R.id.speciality);
                String speciality = (String) specialityEdit.getText().toString();

                EditText experienceEdit = (EditText) view.findViewById(R.id.experience);
                String experience = (String) experienceEdit.getText().toString();

                EditText educationEdit = (EditText) view.findViewById(R.id.education);
                String education = (String) educationEdit.getText().toString();

                EditText skillsEdit = (EditText) view.findViewById(R.id.skills);
                String skills = (String) skillsEdit.getText().toString();

                EditText informationEdit = (EditText) view.findViewById(R.id.info);
                String information = (String) informationEdit.getText().toString();

                EditText cityEdit = (EditText) view.findViewById(R.id.city);
                String city = (String) cityEdit.getText().toString();

                EditText salaryEdit = (EditText) view.findViewById(R.id.salary);
                String salary = (String) salaryEdit.getText().toString();

                EditText phoneNumberEdit = (EditText) view.findViewById(R.id.phone);
                String phoneNumber = (String) phoneNumberEdit.getText().toString();

                EditText instagramEdit = (EditText) view.findViewById(R.id.instagram);
                String instagram = (String) instagramEdit.getText().toString();

                EditText telegramEdit = (EditText) view.findViewById(R.id.telegram);
                String telegram = (String) telegramEdit.getText().toString();

                if (designerName.equals("") || speciality.equals("") || experience.equals("") || education.equals("") || skills.equals("") || information.equals("")
                || city.equals("") || salary.equals("") || phoneNumber.equals("") || instagram.equals("") || telegram.equals("")) {
                    Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                } else {
                    if (categoryString.equals("Категории")){
                        Toast.makeText(getContext(), "Выберите категорию", Toast.LENGTH_SHORT).show();
                    } else {
                        if (jobTypeString.equals("Тип работы")){
                            Toast.makeText(getContext(), "Выберите тип работы", Toast.LENGTH_SHORT).show();
                        } else {
                            if (currencyString.equals("Валюта")){
                                Toast.makeText(getContext(), "Выберите валюту", Toast.LENGTH_SHORT).show();
                            } else {
                                addSummary(designerName, speciality, categoryString, jobTypeString, experience, education, skills,
                                        information, city, salary, currencyString, phoneNumber, instagram, telegram);
                            }
                        }
                    }
                }

            }
        });
        return view;
    }

    public void addSummary(String designerName, String speciality, String category, String jobType,
                           String experience, String education, String skills, String information,
                           String city, String salary, String currency, String phoneNumber,
                           String instagram, String telegram) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
        String dateCreating = dateFormat.format(new Date());
        HashMap hashMap = new HashMap();
        hashMap.put("Id", Id + 1);
        hashMap.put("designerName", designerName);
        hashMap.put("speciality", speciality);
        hashMap.put("category", category);
        hashMap.put("jobType", jobType);
        hashMap.put("experience", experience);
        hashMap.put("education", education);
        hashMap.put("skills", skills);
        hashMap.put("information", information);
        hashMap.put("city", city);
        hashMap.put("salary", salary);
        hashMap.put("currency", currency);
        hashMap.put("phoneNumber", phoneNumber);
        hashMap.put("instagram", instagram);
        hashMap.put("telegram", telegram);
        hashMap.put("userId", user.getUid());
        hashMap.put("views", views);
        hashMap.put("creationDate", dateCreating);
        String document = String.valueOf(Id + 1);
        db.collection("Summary").document(document).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Резюме добавлено", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), HomeActivity.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось добавить проект", Toast.LENGTH_SHORT).show();
            }
        });
    }
}