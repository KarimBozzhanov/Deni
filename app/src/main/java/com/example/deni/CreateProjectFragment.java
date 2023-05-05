package com.example.deni;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CreateProjectFragment extends Fragment {

    Spinner projectCategories, projectCurrency;
    FirebaseFirestore firebaseFirestore;
    Button createProject;

    FirebaseUser user;
    long views = 0;
    long Id = 0;
    String currency, category;

    EditText projectNameEdit, projectTermEdit, projectDescriptionEdit, projectSalaryEdit, projectPhoneEdit, projectInstagramEdit, projectTelegramEdit, projectEmailEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);
        projectCategories = (Spinner) view.findViewById(R.id.project_categories_spinner);
        projectCurrency = (Spinner) view.findViewById(R.id.project_salary_currency);
        ArrayAdapter<?> categoriesAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectCategories.setAdapter(categoriesAdapter);

        ArrayAdapter<?> currencyAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.salary_currancy, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectCurrency.setAdapter(currencyAdapter);

        user = FirebaseAuth.getInstance().getCurrentUser();

        projectCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currencyItem = (String) adapterView.getItemAtPosition(i);
                currency = currencyItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        projectCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categoryItem = (String) adapterView.getItemAtPosition(i);
                category = categoryItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        AggregateQuery recordsCount = firebaseFirestore.collection("Projects").count();
        recordsCount.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                AggregateQuerySnapshot snapshot = task.getResult();
                Id = snapshot.getCount();
            }
        });

        createProject = (Button) view.findViewById(R.id.createProject);
        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectNameEdit = (EditText) view.findViewById(R.id.projectName);
                String projectName = projectNameEdit.getText().toString();
                projectTermEdit = (EditText) view.findViewById(R.id.project_term);
                String projectTerm = projectTermEdit.getText().toString();
                projectDescriptionEdit = (EditText) view.findViewById(R.id.project_description);
                String projectDescription = projectDescriptionEdit.getText().toString();
                projectSalaryEdit = (EditText) view.findViewById(R.id.project_salary);
                String projectSalary = projectSalaryEdit.getText().toString();
                projectPhoneEdit = (EditText) view.findViewById(R.id.project_phone);
                String projectPhone = projectPhoneEdit.getText().toString();
                projectInstagramEdit = (EditText) view.findViewById(R.id.project_instagram);
                String projectInstagram = projectInstagramEdit.getText().toString();
                projectTelegramEdit = (EditText) view.findViewById(R.id.project_telegram);
                String projectTelegram = projectTelegramEdit.getText().toString();
                projectEmailEdit = (EditText) view.findViewById(R.id.project_email);
                String projectEmail = projectEmailEdit.getText().toString();

                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
                String creationTime = dateFormat.format(new Date());

                HashMap hashMap = new HashMap();
                hashMap.put("Id", Id + 1);
                hashMap.put("userName", user.getDisplayName());
                hashMap.put("userId", user.getUid());
                hashMap.put("projectName", projectName);
                hashMap.put("projectCategory", category);
                hashMap.put("projectTerm", projectTerm);
                hashMap.put("projectDescription", projectDescription);
                hashMap.put("projectSalary", projectSalary);
                hashMap.put("projectCurrency", currency);
                hashMap.put("projectPhone", projectPhone);
                hashMap.put("projectInstagram", projectInstagram);
                hashMap.put("projectTelegram", projectTelegram);
                hashMap.put("projectEmail", projectEmail);
                hashMap.put("creationTime", creationTime);
                hashMap.put("views", views);

                String document = String.valueOf(Id + 1);
                firebaseFirestore.collection("Projects").document(document).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Проект добавлен", Toast.LENGTH_SHORT).show();
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
        });

        return view;
    }
}