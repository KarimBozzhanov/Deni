package com.example.deni;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class summaryDetailsActivity extends AppCompatActivity {

    TextView detailsDesignerName, detailsSpeciality, detailsCity, detailsSalary, detailsCurrency, detailsSkills, detailsExperience, detailsJobType, detailsEducation,
            detailsInfo, detailsPhoneNumber, detailsEmail, detailsTelegram, detailsInstagram;

    public summaryDetailsActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_details);

        Button closeDetails = (Button) findViewById(R.id.closeDetails);
        closeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(summaryDetailsActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        detailsDesignerName = (TextView) findViewById(R.id.detailsDesignerName);
        detailsSpeciality = (TextView) findViewById(R.id.detailsSpeciality);
        detailsCity = (TextView) findViewById(R.id.detailsCity);
        detailsSalary = (TextView) findViewById(R.id.detailsSalary);
        detailsCurrency = (TextView) findViewById(R.id.detailsCurrency);
        detailsSkills = (TextView) findViewById(R.id.detailsSkills);
        detailsExperience = (TextView) findViewById(R.id.detailsExperience);
        detailsJobType = (TextView) findViewById(R.id.detailsJobType);
        detailsEducation = (TextView) findViewById(R.id.detailsEducation);
        detailsInfo = (TextView) findViewById(R.id.detailsInfo);
        detailsPhoneNumber = (TextView) findViewById(R.id.detailsPhoneNumber);
        detailsEmail = (TextView) findViewById(R.id.detailsEmail);
        detailsTelegram = (TextView) findViewById(R.id.detailsTelegram);
        detailsInstagram = (TextView) findViewById(R.id.detailsInstagram);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            detailsDesignerName.setText(bundle.getString("designerName"));
            detailsSpeciality.setText(bundle.getString("speciality"));
            detailsCity.setText(bundle.getString("city"));
            detailsSalary.setText(bundle.getString("salary"));
            detailsCurrency.setText(bundle.getString("currency"));
            detailsSkills.setText(bundle.getString("skills"));
            detailsJobType.setText(bundle.getString("jobType"));
            detailsEducation.setText(bundle.getString("education"));
            detailsInfo.setText(bundle.getString("information"));
            detailsEmail.setText(bundle.getString("userName"));
            detailsTelegram.setText(bundle.getString("telegram"));
            detailsInstagram.setText(bundle.getString("instagram"));

        }
    }


}