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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;

public class EditVacancyActivity extends AppCompatActivity {

    EditText vacancyName, vacancyCompany, vacancySchedule, vacancyExperience, vacancyDuty, vacancyRequirements, vacancyConditions, vacancySalary, vacancyCity, vacancyJobPlace, vacancyPhone, vacancyInstagram, vacancyTelegram, vacancyEmail;
    Spinner vacancyCategory, vacancyJobType, vacancyCurrency;

    Button changeVacancy;

    String category, jobType, currency;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vacancy);

        vacancyName = (EditText) findViewById(R.id.editVacancyName);
        vacancyCompany = (EditText) findViewById(R.id.editVacancyCompany);
        vacancySchedule = (EditText) findViewById(R.id.editSchedule);
        vacancyExperience = (EditText) findViewById(R.id.editVacancyExperience);
        vacancyDuty = (EditText) findViewById(R.id.editDuty);
        vacancyRequirements = (EditText) findViewById(R.id.editRequirements);
        vacancyConditions = (EditText) findViewById(R.id.editConditions);
        vacancySalary = (EditText) findViewById(R.id.editVacancySalary);
        vacancyCity = (EditText) findViewById(R.id.editVacancyCity);
        vacancyJobPlace = (EditText) findViewById(R.id.editJobPlace);
        vacancyPhone = (EditText) findViewById(R.id.editVacancyPhone);
        vacancyInstagram = (EditText) findViewById(R.id.editVacancyInstagram);
        vacancyTelegram = (EditText) findViewById(R.id.editVacancyTelegram);
        vacancyEmail = (EditText) findViewById(R.id.editVacancyEmail);

        vacancyCategory = (Spinner) findViewById(R.id.edit_vacancy_categories_spinner);
        vacancyJobType = (Spinner) findViewById(R.id.edit_vacancy_job_type);
        vacancyCurrency = (Spinner) findViewById(R.id.editVacancySalaryCurrency);

        Bundle bundle = getIntent().getExtras();

        vacancyName.append(bundle.getString("vacancyName"));
        vacancyCompany.append(bundle.getString("vacancyCompany"));
        vacancySchedule.append(bundle.getString("vacancySchedule"));
        vacancyExperience.append(bundle.getString("vacancyExperience"));
        vacancyDuty.append(bundle.getString("vacancyDuty"));
        vacancyRequirements.append(bundle.getString("vacancyRequirements"));
        vacancyConditions.append(bundle.getString("vacancyConditions"));
        vacancySalary.append(bundle.getString("vacancySalary"));
        vacancyCity.append(bundle.getString("vacancyCity"));
        vacancyJobPlace.append(bundle.getString("vacancyJobPlace"));
        vacancyPhone.append(bundle.getString("vacancyPhone"));
        vacancyInstagram.append(bundle.getString("vacancyInstagram"));
        vacancyTelegram.append(bundle.getString("vacancyTelegram"));
        vacancyEmail.append(bundle.getString("vacancyEmail"));

        if(bundle.getString("vacancyCategory").equals("3D моделирование")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryModel, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCategory.setAdapter(categoryAdapter);
        } else if (bundle.getString("vacancyCategory").equals("Визуализация")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryVisual, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCategory.setAdapter(categoryAdapter);
        } else if (bundle.getString("vacancyCategory").equals("Дизайн и архитектура")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDesign, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCategory.setAdapter(categoryAdapter);
        } else if (bundle.getString("vacancyCategory").equals("Рабочая документация")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDoc, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCategory.setAdapter(categoryAdapter);
        }

        vacancyCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (bundle.getString("vacancyJobType").equals("Удаленная работа")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.distantWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyJobType.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("vacancyJobType").equals("Работа в офисе")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.workInOffice, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyJobType.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("vacancyJobType").equals("Непол.Занятость")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.incompleteWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyJobType.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("vacancyJobType").equals("Пол.Занятость")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.fullWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyJobType.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("vacancyJobType").equals("Частич.Занятость")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.partialWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyJobType.setAdapter(jobTypeAdapter);
        }

        vacancyJobType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (bundle.getString("vacancyCurrency").equals("Доллар")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.dollar, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCurrency.setAdapter(currencyAdapter);
        } else if (bundle.getString("vacancyCurrency").equals("Евро")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.euro, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCurrency.setAdapter(currencyAdapter);
        } else if (bundle.getString("vacancyCurrency").equals("Рубль")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.ruble, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCurrency.setAdapter(currencyAdapter);
        } else if (bundle.getString("vacancyCurrency").equals("Тенге")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.tenge, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vacancyCurrency.setAdapter(currencyAdapter);
        }

        vacancyCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currency = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        changeVacancy = (Button) findViewById(R.id.changeVacancy);
        changeVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference dc = firebaseFirestore.collection("Vacancies").document(bundle.getString("vacancyId"));

                HashMap hashMap = new HashMap();
                hashMap.put("vacancyName", vacancyName.getText().toString());
                hashMap.put("company", vacancyCompany.getText().toString());
                hashMap.put("category", category);
                hashMap.put("jobType", jobType);
                hashMap.put("schedule", vacancySchedule.getText().toString());
                hashMap.put("experience", vacancyExperience.getText().toString());
                hashMap.put("duty", vacancyDuty.getText().toString());
                hashMap.put("requirements", vacancyRequirements.getText().toString());
                hashMap.put("conditions", vacancyConditions.getText().toString());
                hashMap.put("salary", vacancySalary.getText().toString());
                hashMap.put("currency", currency);
                hashMap.put("city", vacancyCity.getText().toString());
                hashMap.put("jobPlace", vacancyJobPlace.getText().toString());
                hashMap.put("phone", vacancyPhone.getText().toString());
                hashMap.put("instagram", vacancyInstagram.getText().toString());
                hashMap.put("telegram", vacancyTelegram.getText().toString());
                hashMap.put("email", vacancyEmail.getText().toString());
                dc.update(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(EditVacancyActivity.this, "Вакансия изменена", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditVacancyActivity.this, HomeActivity.class);
                        startActivity(i);
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditVacancyActivity.this, "Не удалось изменить вакансию", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });


    }
}