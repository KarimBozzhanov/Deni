package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditProjectActivity extends AppCompatActivity {

    EditText projectName, projectTerm, projectDescription, projectSalary, projectPhone, projectInstagram, projectTelegram, projectEmail;
    Spinner projectCategory, projectCurrency;
    Button changeProject;
    String category, currency;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        Bundle bundle = getIntent().getExtras();

        projectName = (EditText) findViewById(R.id.edit_project_name);
        projectTerm = (EditText) findViewById(R.id.edit_project_term);
        projectDescription = (EditText) findViewById(R.id.edit_project_description);
        projectSalary = (EditText) findViewById(R.id.edit_project_salary);
        projectPhone = (EditText) findViewById(R.id.edit_project_phone);
        projectInstagram = (EditText) findViewById(R.id.edit_project_instagram);
        projectTelegram = (EditText) findViewById(R.id.edit_project_telegram);
        projectEmail = (EditText) findViewById(R.id.edit_project_email);

        changeProject = (Button) findViewById(R.id.editProject);

        projectName.append(bundle.getString("projectName"));
        projectTerm.append(bundle.getString("projectTerm"));
        projectDescription.append(bundle.getString("projectDescription"));
        projectSalary.append(bundle.getString("projectSalary"));
        projectPhone.append(bundle.getString("projectPhone"));
        projectInstagram.append(bundle.getString("projectInstagram"));
        projectTelegram.append(bundle.getString("projectTelegram"));
        projectEmail.append(bundle.getString("projectEmail"));

        projectCategory = findViewById(R.id.edit_project_categories_spinner);
        projectCurrency = findViewById(R.id.edit_project_salary_currency);

        if(bundle.getString("projectCategory").equals("3D моделирование")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryModel, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCategory.setAdapter(categoryAdapter);
        } else if (bundle.getString("projectCategory").equals("Визуализация")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryVisual, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCategory.setAdapter(categoryAdapter);
        } else if (bundle.getString("projectCategory").equals("Дизайн и архитектура")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDesign, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCategory.setAdapter(categoryAdapter);
        } else if (bundle.getString("projectCategory").equals("Рабочая документация")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDoc, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCategory.setAdapter(categoryAdapter);
        }

        projectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (bundle.getString("projectCurrency").equals("Доллар")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.dollar, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCurrency.setAdapter(currencyAdapter);
        } else if (bundle.getString("projectCurrency").equals("Евро")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.euro, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCurrency.setAdapter(currencyAdapter);
        } else if (bundle.getString("projectCurrency").equals("Рубль")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.ruble, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCurrency.setAdapter(currencyAdapter);
        } else if (bundle.getString("projectCurrency").equals("Тенге")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.tenge, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            projectCurrency.setAdapter(currencyAdapter);
        }

        projectCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currency = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        changeProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore = FirebaseFirestore.getInstance();

                String newProjectName = projectName.getText().toString();
                String newProjectTerm = projectTerm.getText().toString();
                String newProjectDescription = projectDescription.getText().toString();
                String newProjectSalary = projectSalary.getText().toString();
                String newProjectPhone = projectPhone.getText().toString();
                String newProjectInstagram = projectInstagram.getText().toString();
                String newProjectTelegram = projectTelegram.getText().toString();
                String newProjectEmail = projectEmail.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("projectName", newProjectName);
                hashMap.put("projectCategory", category);
                hashMap.put("projectTerm", newProjectTerm);
                hashMap.put("projectDescription", newProjectDescription);
                hashMap.put("projectSalary", newProjectSalary);
                hashMap.put("projectCurrency", currency);
                hashMap.put("projectPhone", newProjectPhone);
                hashMap.put("projectInstagram", newProjectInstagram);
                hashMap.put("projectTelegram", newProjectTelegram);
                hashMap.put("projectEmail", newProjectEmail);

                DocumentReference dc = firebaseFirestore.collection("Projects").document(bundle.getString("projectId"));

                dc.update(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(EditProjectActivity.this, "Проект изменён", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditProjectActivity.this, HomeActivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProjectActivity.this, "Не удалось изменить проект", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

    }
}