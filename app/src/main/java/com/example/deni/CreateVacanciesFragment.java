package com.example.deni;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CreateVacanciesFragment extends Fragment {

    private static final String LOG_TAG = CreateSummaryFragment.class.getName();
    Spinner vacancyCategoriesSpinner, vacancyJobTypeSpinner, vacancySalaryCurrency;
    FirebaseUser user;
    FirebaseFirestore db;
    long Id;
    int views = 0;
    String vacancyCategoriesString, vacancyJobTypeString, vacancySalaryCurrencyString;

    EditText vacancyName, company, schedule, vacancy_experience, duty, requirements, conditions, vacancySalary, vacancyCity, vacancyJobPlace, vacancyPhone, vacancyInstagram, vacancyTelegram, vacancyEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_vacancies, container, false);
        vacancyCategoriesSpinner = (Spinner) view.findViewById(R.id.vacancy_categories_spinner);
        vacancyJobTypeSpinner = (Spinner) view.findViewById(R.id.vacancy_job_type);
        vacancySalaryCurrency = (Spinner) view.findViewById(R.id.vacancy_salary_currency);
        ArrayAdapter<?> vacancyCategoriesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        ArrayAdapter<?> vacancyJobTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.jobType, android.R.layout.simple_spinner_item);
        ArrayAdapter<?> vacancySalaryCurrencyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.salary_currancy, android.R.layout.simple_spinner_item);
        vacancyCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        vacancyCategoriesSpinner.setAdapter(vacancyCategoriesAdapter);
        vacancyJobTypeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        vacancyJobTypeSpinner.setAdapter(vacancyJobTypeAdapter);
        vacancySalaryCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        vacancySalaryCurrency.setAdapter(vacancySalaryCurrencyAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();

        vacancyCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String category = (String) adapterView.getItemAtPosition(i);
                vacancyCategoriesString = category;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vacancyJobTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String jobType = (String) adapterView.getItemAtPosition(i);
                vacancyJobTypeString = jobType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vacancySalaryCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String salaryCurrency = (String) adapterView.getItemAtPosition(i);
                vacancySalaryCurrencyString = salaryCurrency;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        db = FirebaseFirestore.getInstance();
        AggregateQuery dbCount = db.collection("Vacancies").count();
        dbCount.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                AggregateQuerySnapshot snapshot = task.getResult();
                Id = snapshot.getCount();
            }
        });

        Button createVacancy = (Button) view.findViewById(R.id.createVacancy);

        createVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacancyName = (EditText) view.findViewById(R.id.vacancyName);
                String vacancyNameString = vacancyName.getText().toString();
                company = (EditText) view.findViewById(R.id.company);
                String companyString = company.getText().toString();
                schedule = (EditText) view.findViewById(R.id.schedule);
                String scheduleString = schedule.getText().toString();
                vacancy_experience = (EditText) view.findViewById(R.id.vacancy_experience);
                String vacancyExperienceString = vacancy_experience.getText().toString();
                duty = (EditText) view.findViewById(R.id.duty);
                String dutyString = duty.getText().toString();
                requirements = (EditText) view.findViewById(R.id.requirements);
                String requirementsString = requirements.getText().toString();
                conditions = (EditText) view.findViewById(R.id.conditions);
                String conditionsString = conditions.getText().toString();
                vacancySalary = (EditText) view.findViewById(R.id.vacancy_salary);
                String vacancySalaryString = vacancySalary.getText().toString();
                vacancyCity = (EditText) view.findViewById(R.id.vacancy_city);
                String vacancyCityString = vacancyName.getText().toString();
                vacancyJobPlace = (EditText) view.findViewById(R.id.job_place);
                String vacancyJobPlaceString = vacancyJobPlace.getText().toString();
                vacancyPhone = (EditText) view.findViewById(R.id.vacancy_phone);
                String vacancyPhoneString = vacancyPhone.getText().toString();
                vacancyInstagram = (EditText) view.findViewById(R.id.vacancy_instagram);
                String vacancyInstagramString = vacancyInstagram.getText().toString();
                vacancyTelegram = (EditText) view.findViewById(R.id.vacancy_telegram);
                String vacancyTelegramString = vacancyTelegram.getText().toString();
                vacancyEmail = (EditText) view.findViewById(R.id.vacancy_email);
                String vacancyEmailString = vacancyEmail.getText().toString();
                String userName = user.getDisplayName();
                String userEmail = user.getEmail();

                Log.e(LOG_TAG, "Все данные: " + vacancyNameString + " " + companyString + " " + scheduleString + " " + vacancyExperienceString + " " + dutyString + " " + requirementsString + " " + conditionsString + " " + vacancySalaryString + " " + vacancySalaryCurrencyString + " " + vacancyCategoriesString + " " + vacancyJobTypeString + " " + vacancyCityString + " " + vacancyJobPlaceString + " " + vacancyPhoneString + " " + vacancyInstagramString + " " + vacancyEmailString + " " + vacancyTelegramString);

                if (vacancyNameString.equals("") || companyString.equals("") || vacancyExperienceString.equals("") || dutyString.equals("") ||
                        requirementsString.equals("") || conditionsString.equals("") || vacancySalaryString.equals("") || vacancyCityString.equals("") || vacancyJobPlaceString.equals("") ||
                        vacancyPhoneString.equals("") || vacancyEmailString.equals("")){
                    Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    if (vacancyCategoriesString.equals("Категории")) {
                        Toast.makeText(getContext(), "Выберите категорию", Toast.LENGTH_SHORT).show();
                    } else {
                        if (vacancyJobTypeString.equals("Тип работы")){
                            Toast.makeText(getContext(), "Выберите тип работы", Toast.LENGTH_SHORT).show();
                        } else {
                            if (vacancySalaryCurrencyString.equals("Валюта")){
                                Toast.makeText(getContext(), "Выберите валюту", Toast.LENGTH_SHORT).show();
                            } else {
                                vacancyCreate(vacancyNameString, companyString, vacancyCategoriesString, vacancyJobTypeString, scheduleString, vacancyExperienceString, dutyString, requirementsString, conditionsString, vacancySalaryString, vacancySalaryCurrencyString, vacancyCityString, vacancyJobPlaceString, vacancyPhoneString, vacancyInstagramString, vacancyTelegramString, vacancyEmailString, userName, userEmail);
                            }
                        }
                    }
                }
            }
        });
        return view;
    }

    public void vacancyCreate(String vacancyName, String company, String category, String jobType, String schedule, String experience, String duty, String requirements, String conditions, String salary, String currency, String city, String jobPlace, String phone, String instagram, String telegram, String email, String userName, String userEmail){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String dateCreating = dateFormat.format(new Date());

        HashMap vacancy = new HashMap();
        vacancy.put("Id", Id + 1);
        vacancy.put("views", views);
        vacancy.put("vacancyName", vacancyName);
        vacancy.put("company", company);
        vacancy.put("category", category);
        vacancy.put("jobType", jobType);
        vacancy.put("schedule", schedule);
        vacancy.put("experience", experience);
        vacancy.put("duty", duty);
        vacancy.put("requirements", requirements);
        vacancy.put("conditions", conditions);
        vacancy.put("salary", salary);
        vacancy.put("currency", currency);
        vacancy.put("city", city);
        vacancy.put("jobPlace", jobPlace);
        vacancy.put("phone", phone);
        vacancy.put("instagram", instagram);
        vacancy.put("telegram", telegram);
        vacancy.put("email", email);
        vacancy.put("dateCreating", dateCreating);
        vacancy.put("userName", userName);
        vacancy.put("userId", user.getUid());
        String document = String.valueOf(Id + 1);
        db.collection("Vacancies").document(document).set(vacancy).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Вакансия добавлена", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), HomeActivity.class);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось добавить вакансию", Toast.LENGTH_SHORT).show();
            }
        });
    }
}