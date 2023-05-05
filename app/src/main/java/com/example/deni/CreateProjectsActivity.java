package com.example.deni;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateProjectsActivity extends AppCompatActivity {

    private CreateSummaryFragment createSummaryFragment = new CreateSummaryFragment();

    DrawerLayout createLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Context con = this;
    Button createSummary, createVacancies, createProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_projects);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ImageButton closeCreate = (ImageButton) findViewById(R.id.closeCreate);
        createLayout = (DrawerLayout) findViewById(R.id.create_activity);
        createLayout.setDrawerListener(mDrawerToggle);
        createSummary = (Button) findViewById(R.id.createSummary);
        createVacancies = (Button) findViewById(R.id.createVacancies);
        createProject = (Button) findViewById(R.id.createProject);
        setFragment(createSummaryFragment);

        createSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(createSummaryFragment);
                createSummary.setBackgroundColor(getResources().getColor(R.color.headerBackground));
                createSummary.setTextColor(getResources().getColor(R.color.goldyGradient));
                createVacancies.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                createVacancies.setTextColor(getResources().getColor(R.color.white));
                createProject.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                createProject.setTextColor(getResources().getColor(R.color.white));
            }
        });

        createVacancies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateVacanciesFragment createVacanciesFragment = new CreateVacanciesFragment();
                setFragment(createVacanciesFragment);
                createVacancies.setBackgroundColor(getResources().getColor(R.color.headerBackground));
                createVacancies.setTextColor(getResources().getColor(R.color.goldyGradient));
                createProject.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                createProject.setTextColor(getResources().getColor(R.color.white));
                createSummary.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                createSummary.setTextColor(getResources().getColor(R.color.white));
            }
        });

        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateProjectFragment createProjectFragment = new CreateProjectFragment();
                setFragment(createProjectFragment);
                createProject.setBackgroundColor(getResources().getColor(R.color.headerBackground));
                createProject.setTextColor(getResources().getColor(R.color.goldyGradient));
                createSummary.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                createSummary.setTextColor(getResources().getColor(R.color.white));
                createVacancies.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                createVacancies.setTextColor(getResources().getColor(R.color.white));
            }
        });

        closeCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateProjectsActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.createFrame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}