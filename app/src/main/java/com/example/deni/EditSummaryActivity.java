package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.HashMap;

public class EditSummaryActivity extends AppCompatActivity {
    private static final String LOG_TAG = CreateSummaryFragment.class.getName();
    EditText editDesignerName, editSpeciality, editExperience, editEducation, editSkills, editInfo, editCity, editSalary, editPhone, editInstagram, editTelegram;
    Button changeSummary;
    String summaryCategory, summaryJobType, summaryCurrency;
    FirebaseFirestore firebaseFirestore;
    Spinner edit_categories_spinner, edit_job_type, edit_salary_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_summary);

        Bundle bundle = getIntent().getExtras();

        editDesignerName = (EditText) findViewById(R.id.editDesignerName);
        editSpeciality = (EditText) findViewById(R.id.editSpeciality);
        editExperience = (EditText) findViewById(R.id.edit_experience);
        editEducation = (EditText) findViewById(R.id.edit_education);
        editSkills = (EditText) findViewById(R.id.edit_skills);
        editInfo = (EditText) findViewById(R.id.edit_info);
        editCity = (EditText) findViewById(R.id.edit_city);
        editSalary = (EditText) findViewById(R.id.edit_salary);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editInstagram = (EditText) findViewById(R.id.editInstagram);
        editTelegram = (EditText) findViewById(R.id.editTelegram);
        changeSummary = (Button) findViewById(R.id.editSummary);

        editDesignerName.append(bundle.getString("designerName"));
        editSpeciality.append(bundle.getString("speciality"));
        editExperience.append(bundle.getString("experience"));
        editEducation.append(bundle.getString("education"));
        editSkills.append(bundle.getString("skills"));
        editInfo.append(bundle.getString("information"));
        editCity.append(bundle.getString("city"));
        editSalary.append(bundle.getString("salary"));
        editPhone.append(bundle.getString("phoneNumber"));
        editInstagram.append(bundle.getString("instagram"));
        editTelegram.append(bundle.getString("telegram"));

        edit_categories_spinner = (Spinner) findViewById(R.id.edit_categories_spinner);
        edit_job_type = (Spinner) findViewById(R.id.edit_job_type);
        edit_salary_currency = (Spinner) findViewById(R.id.edit_salary_currency);

        if(bundle.getString("category").equals("3D моделирование")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryModel, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_categories_spinner.setAdapter(categoryAdapter);
        } else if(bundle.getString("category").equals("Визуализация")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryVisual, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_categories_spinner.setAdapter(categoryAdapter);
        } else if (bundle.getString("category").equals("Дизайн и архитектура")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDesign, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_categories_spinner.setAdapter(categoryAdapter);
        } else if (bundle.getString("category").equals("Рабочая документация")) {
            ArrayAdapter<?> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDoc, android.R.layout.simple_spinner_item);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_categories_spinner.setAdapter(categoryAdapter);
        }

        edit_categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                summaryCategory = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (bundle.getString("jobType").equals("Удаленная работа")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.distantWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_job_type.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("jobType").equals("Работа в офисе")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.workInOffice, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_job_type.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("jobType").equals("Непол.Занятость")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.incompleteWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_job_type.setAdapter(jobTypeAdapter);
        } else if(bundle.getString("jobType").equals("Пол.Занятость")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.fullWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_job_type.setAdapter(jobTypeAdapter);
        } else if (bundle.getString("jobType").equals("Частич.Занятость")) {
            ArrayAdapter<?> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.partialWork, android.R.layout.simple_spinner_item);
            jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_job_type.setAdapter(jobTypeAdapter);
        }

        edit_job_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                summaryJobType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(bundle.getString("currency").equals("Доллар")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.dollar, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_salary_currency.setAdapter(currencyAdapter);
        } else if(bundle.getString("currency").equals("Евро")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.euro, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_salary_currency.setAdapter(currencyAdapter);
        } else if(bundle.getString("currency").equals("Рубль")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.ruble, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_salary_currency.setAdapter(currencyAdapter);
        } else if(bundle.getString("currency").equals("Тенге")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.tenge, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            edit_salary_currency.setAdapter(currencyAdapter);
        }

        edit_salary_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                summaryCurrency = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        changeSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore = FirebaseFirestore.getInstance();

                String designerName = editDesignerName.getText().toString();
                String speciality = editSpeciality.getText().toString();
                String experience = editExperience.getText().toString();
                String education = editEducation.getText().toString();
                String skills = editSkills.getText().toString();
                String information = editInfo.getText().toString();
                String city = editCity.getText().toString();
                String salary = editSalary.getText().toString();
                String phone = editPhone.getText().toString();
                String instagram = editInstagram.getText().toString();
                String telegram = editTelegram.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("designerName", designerName);
                hashMap.put("speciality", speciality);
                hashMap.put("category", summaryCategory);
                hashMap.put("jobType", summaryJobType);
                hashMap.put("experience", experience);
                hashMap.put("education", education);
                hashMap.put("skills", skills);
                hashMap.put("information", information);
                hashMap.put("city", city);
                hashMap.put("salary", salary);
                hashMap.put("currency", summaryCurrency);
                hashMap.put("phoneNumber", phone);
                hashMap.put("instagram", instagram);
                hashMap.put("telegram", telegram);

                Log.e(LOG_TAG, "Id поста - " + bundle.getString("Id"));

                DocumentReference dc = firebaseFirestore.collection("Summary").document(bundle.getString("Id"));
                dc.update(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(EditSummaryActivity.this, "Резюме изменено", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditSummaryActivity.this, HomeActivity.class);
                        startActivity(i);
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditSummaryActivity.this, "Не удалось изменить резюме", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });
    }


}