package com.example.deni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class vacancyDetailsActivity extends AppCompatActivity {

    TextView companyName, vacancyCreationTime, vacancyUserName, detailsVacancyName, detailsVacancySalary, detailsVacancyCurrency,
            detailsVacancyJobType, detailsVacancySchedule, detailsVacancyExperience, detailsVacancyDuty, detailsVacancyRequirements,
            detailsVacancyConditions, detailsVacancyJobPlace, detailsVacancyPhoneNumber, detailsVacancyEmail, detailsVacancyTelegram, detailsVacancyInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_details);

        companyName = (TextView) findViewById(R.id.companyName);
        vacancyCreationTime = (TextView) findViewById(R.id.vacancyCreationTime);
        vacancyUserName = (TextView) findViewById(R.id.vacancyUserName);
        detailsVacancyName = (TextView) findViewById(R.id.detailsVacancyName);
        detailsVacancySalary = (TextView) findViewById(R.id.detailsVacancySalary);
        detailsVacancyCurrency = (TextView) findViewById(R.id.detailsVacancyCurrency);
        detailsVacancyJobType = (TextView) findViewById(R.id.detailsVacancyJobType);
        detailsVacancySchedule = (TextView) findViewById(R.id.detailsVacancySchedule);
        detailsVacancyExperience = (TextView) findViewById(R.id.detailsVacancyExperience);
        detailsVacancyDuty = (TextView) findViewById(R.id.detailsVacancyDuty);
        detailsVacancyRequirements = (TextView) findViewById(R.id.detailsVacancyRequirements);
        detailsVacancyConditions = (TextView) findViewById(R.id.detailsVacancyConditions);
        detailsVacancyJobPlace = (TextView) findViewById(R.id.detailsVacancyJobPlace);
        detailsVacancyPhoneNumber = (TextView) findViewById(R.id.detailsVacancyPhoneNumber);
        detailsVacancyEmail = (TextView) findViewById(R.id.detailsVacancyEmail);
        detailsVacancyTelegram = (TextView) findViewById(R.id.detailsVacancyTelegram);
        detailsVacancyInstagram = (TextView) findViewById(R.id.detailsVacancyInstagram);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            companyName.setText(bundle.getString("companyName"));
            vacancyCreationTime.setText(bundle.getString("creationTime"));
            vacancyUserName.setText(bundle.getString("userName"));
            detailsVacancyName.setText(bundle.getString("vacancyName"));
            detailsVacancySalary.setText(bundle.getString("vacancySalary"));
            detailsVacancyCurrency.setText(bundle.getString("vacancyCurrency"));
            detailsVacancyJobType.setText(bundle.getString("vacancyJobType"));
            detailsVacancySchedule.setText(bundle.getString("vacancySchedule"));
            detailsVacancyExperience.setText(bundle.getString("vacancyExperience"));
            detailsVacancyDuty.setText(bundle.getString("vacancyDuty"));
            detailsVacancyRequirements.setText(bundle.getString("vacancyRequirements"));
            detailsVacancyConditions.setText(bundle.getString("vacancyConditions"));
            detailsVacancyJobPlace.setText(bundle.getString("vacancyJobPlace"));
            detailsVacancyPhoneNumber.setText(bundle.getString("phone"));
            detailsVacancyEmail.setText(bundle.getString("email"));
            detailsVacancyTelegram.setText(bundle.getString("telegram"));
            detailsVacancyInstagram.setText(bundle.getString("instagram"));
        }

        Button closeVacancyDetails = (Button) findViewById(R.id.closeVacancyDetails);
        closeVacancyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vacancyDetailsActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}