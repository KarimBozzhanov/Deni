package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditPortfolioActivity extends AppCompatActivity {

    private static final String LOG_TAG = EditProfileActivity.class.getName();
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    EditText editPortfolioLogin, editPortfolioCity, editPortfolioSalary, editPortfolioExperience, editPortfolioEmployment, editPortfolioEducation, editPortfolioInfo, editPortfolioPhoneNumber, editPortfolioEmail;
    Spinner editPortfolioSpecialitySpinner, editPortfolioCurrencySpinner;
    CheckBox portfolioMale, portfolioFemale;
    String speciality, currency;
    ProgressDialog progressDialog;
    Button savePortfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_portfolio);
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setMessage("Изменение портфолио...");
        firebaseFirestore = FirebaseFirestore.getInstance();
        editPortfolioLogin = findViewById(R.id.editPortfolioLogin);
        editPortfolioCity = findViewById(R.id.editPortfolioCity);
        editPortfolioSalary = findViewById(R.id.editPortfolioSalary);
        editPortfolioExperience = findViewById(R.id.editPortfolioExperience);
        editPortfolioEmployment = findViewById(R.id.editPortfolioEmployment);
        editPortfolioEducation = findViewById(R.id.editPortfolioEducation);
        editPortfolioInfo = findViewById(R.id.editPortfolioInfo);
        editPortfolioPhoneNumber = findViewById(R.id.editPortfolioPhoneNumber);
        editPortfolioEmail = findViewById(R.id.editPortfolioEmail);
        editPortfolioSpecialitySpinner = (Spinner) findViewById(R.id.editPortfolioSpecialitySpinner);
        editPortfolioCurrencySpinner = (Spinner) findViewById(R.id.editPortfolioCurrencySpinner);
        portfolioMale = findViewById(R.id.portfolioMale);
        portfolioFemale = findViewById(R.id.portfolioFemale);
        savePortfolio = findViewById(R.id.savePortfolio);
        Bundle bundle = getIntent().getExtras();
        editPortfolioLogin.append(bundle.getString("login"));
        editPortfolioCity.append(bundle.getString("city"));
        editPortfolioSalary.append(bundle.getString("salary"));
        editPortfolioExperience.append(bundle.getString("experience"));
        editPortfolioEmployment.append(bundle.getString("employment"));
        editPortfolioEducation.append(bundle.getString("education"));
        editPortfolioInfo.append(bundle.getString("information"));
        editPortfolioPhoneNumber.append(bundle.getString("phoneNumber"));
        editPortfolioEmail.append(bundle.getString("email"));
        if (bundle.getString("speciality").equals("Не указана")) {
            ArrayAdapter<?> specialityAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
            specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioSpecialitySpinner.setAdapter(specialityAdapter);
        } else if (bundle.getString("speciality").equals("3D моделирование")) {
            ArrayAdapter<?> specialityAdapter = ArrayAdapter.createFromResource(this, R.array.categoryModel, android.R.layout.simple_spinner_item);
            specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioSpecialitySpinner.setAdapter(specialityAdapter);
        } else if (bundle.getString("speciality").equals("Визуализация")) {
            ArrayAdapter<?> specialityAdapter = ArrayAdapter.createFromResource(this, R.array.categoryVisual, android.R.layout.simple_spinner_item);
            specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioSpecialitySpinner.setAdapter(specialityAdapter);
        } else if (bundle.getString("speciality").equals("Дизайн и архитектура")) {
            ArrayAdapter<?> specialityAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDesign, android.R.layout.simple_spinner_item);
            specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioSpecialitySpinner.setAdapter(specialityAdapter);
        } else if (bundle.getString("speciality").equals("Рабочая документация")) {
            ArrayAdapter<?> specialityAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDoc, android.R.layout.simple_spinner_item);
            specialityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioSpecialitySpinner.setAdapter(specialityAdapter);
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (bundle.getString("currency").equals("Не указано")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.salary_currancy, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioCurrencySpinner.setAdapter(currencyAdapter);
        } else if (bundle.getString("currency").equals("Доллар")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.dollar, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioCurrencySpinner.setAdapter(currencyAdapter);
        } else if (bundle.getString("currency").equals("Евро")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.euro, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioCurrencySpinner.setAdapter(currencyAdapter);
        } else if (bundle.getString("currency").equals("Рубль")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.ruble, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioCurrencySpinner.setAdapter(currencyAdapter);
        } else if (bundle.getString("currency").equals("Тенге")) {
            ArrayAdapter<?> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.tenge, android.R.layout.simple_spinner_item);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editPortfolioCurrencySpinner.setAdapter(currencyAdapter);
        }

        editPortfolioSpecialitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                speciality = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editPortfolioCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currency = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (bundle.getString("gender").equals("Мужской")) {
            portfolioMale.setChecked(true);
            portfolioFemale.setChecked(false);
        } else if (bundle.getString("gender").equals("Женский")) {
            portfolioMale.setChecked(false);
            portfolioFemale.setChecked(true);
        } else if (bundle.getString("gender").equals("Не указан")) {
            portfolioMale.setChecked(false);
            portfolioFemale.setChecked(false);
        }


        portfolioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    portfolioMale.setChecked(false);
                }
            }
        });
        portfolioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    portfolioFemale.setChecked(false);
                }
            }
        });

        savePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                DocumentReference dc = firebaseFirestore.collection("Portfolio").document(user.getUid());
                HashMap portfolio = new HashMap();
                portfolio.put("login", editPortfolioLogin.getText().toString());
                portfolio.put("city", editPortfolioCity.getText().toString());
                portfolio.put("speciality", speciality);
                portfolio.put("salary", editPortfolioSalary.getText().toString());
                portfolio.put("currency", currency);
                portfolio.put("experience", editPortfolioExperience.getText().toString());
                portfolio.put("employment", editPortfolioEmployment.getText().toString());
                portfolio.put("education", editPortfolioEducation.getText().toString());
                portfolio.put("information", editPortfolioInfo.getText().toString());
                portfolio.put("phoneNumber", editPortfolioPhoneNumber.getText().toString());
                portfolio.put("email", editPortfolioEmail.getText().toString());
                if (portfolioFemale.isChecked()) {
                    portfolio.put("gender", "Женский");
                } else if (portfolioMale.isChecked()) {
                    portfolio.put("gender", "Мужской");
                }
                dc.update(portfolio).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        progressDialog.cancel();
                        Toast.makeText(EditPortfolioActivity.this, "Портфолио изменено", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditPortfolioActivity.this, PortfolioActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }
                });
            }
        });
    }
}