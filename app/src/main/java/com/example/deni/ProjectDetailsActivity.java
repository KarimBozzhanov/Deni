package com.example.deni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProjectDetailsActivity extends AppCompatActivity {


    TextView detailsProjectUserName, detailsProjectName, detailsProjectCategory, detailsProjectSalary, detailsProjectCurrency, detailsProjectTerm, detailsProjectDescription, detailsProjectPhone, detailsProjectEmail, detailsProjectTelegram, detailsProjectInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        detailsProjectUserName = (TextView) findViewById(R.id.projectDetailsUserName);
        detailsProjectName = (TextView) findViewById(R.id.detailsProjectName);
        detailsProjectCategory = (TextView) findViewById(R.id.detailsProjectCategory);
        detailsProjectSalary = (TextView) findViewById(R.id.detailsProjectSalary);
        detailsProjectCurrency = (TextView) findViewById(R.id.detailsProjectCurrency);
        detailsProjectTerm = (TextView) findViewById(R.id.detailsProjectTerm);
        detailsProjectDescription = (TextView) findViewById(R.id.detailsProjectDescription);
        detailsProjectPhone = (TextView) findViewById(R.id.detailsProjectPhoneNumber);
        detailsProjectEmail = (TextView) findViewById(R.id.detailsProjectEmail);
        detailsProjectTelegram = (TextView) findViewById(R.id.detailsProjectTelegram);
        detailsProjectInstagram = (TextView) findViewById(R.id.detailsProjectInstagram);

        Bundle bundle = getIntent().getExtras();
        detailsProjectUserName.setText(bundle.getString("userName"));
        detailsProjectName.setText(bundle.getString("projectName"));
        detailsProjectCategory.setText(bundle.getString("projectCategory"));
        detailsProjectSalary.setText(bundle.getString("projectSalary"));
        detailsProjectCurrency.setText(bundle.getString("projectCurrency"));
        detailsProjectTerm.setText(bundle.getString("projectTerm"));
        detailsProjectDescription.setText(bundle.getString("projectDescription"));
        detailsProjectPhone.setText(bundle.getString("projectPhone"));
        detailsProjectEmail.setText(bundle.getString("projectEmail"));
        detailsProjectTelegram.setText(bundle.getString("projectTelegram"));
        detailsProjectInstagram.setText(bundle.getString("projectInstagram"));
    }
}