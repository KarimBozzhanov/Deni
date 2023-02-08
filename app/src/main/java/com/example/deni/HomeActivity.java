package com.example.deni;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {
    private HomeFragment homeFragment = new HomeFragment();
    Toolbar toolBar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ImageButton userButton;
    Button home_btn, vacancies_btn, projects_btn, my_projects_btn;
    LinearLayout hamMenu;
    Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_activity);
        hamMenu = (LinearLayout) findViewById(R.id.hamMenu);
        userButton = (ImageButton) findViewById(R.id.userBtn);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(hamMenu)){
                    mDrawerLayout.closeDrawer(hamMenu);
                } else if (!mDrawerLayout.isDrawerOpen(hamMenu)){
                    mDrawerLayout.openDrawer(hamMenu);
                }
            }
        });
        home_btn = (Button) findViewById(R.id.home_btn);
        vacancies_btn = (Button) findViewById(R.id.vacancies_btn);
        projects_btn = (Button) findViewById(R.id.projects_btn);
        my_projects_btn = (Button) findViewById(R.id.my_projects_btn);
        setFragment(homeFragment);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(homeFragment);
                Drawable golden_home_btn = con.getResources().getDrawable(R.drawable.home_btn);
                Drawable grey_layers_btn = con.getResources().getDrawable(R.drawable.layers_btn);
                Drawable grey_projects_btn = con.getResources().getDrawable(R.drawable.grey_document);
                Drawable grey_my_projects_btn = con.getResources().getDrawable(R.drawable.grey_widget);
                home_btn.setTextColor(getResources().getColor(R.color.golden));
                vacancies_btn.setTextColor(getResources().getColor(R.color.grey));
                projects_btn.setTextColor(getResources().getColor(R.color.grey));
                my_projects_btn.setTextColor(getResources().getColor(R.color.grey));
                home_btn.setCompoundDrawablesWithIntrinsicBounds(null, golden_home_btn, null, null);
                vacancies_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_layers_btn, null, null);
                projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_projects_btn, null, null);
                my_projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_my_projects_btn, null, null);
            }
        });

        vacancies_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VacanciesFragment vacanciesFragment = new VacanciesFragment();
                setFragment(vacanciesFragment);
                Drawable grey_home_btn = con.getResources().getDrawable(R.drawable.grey_home_button);
                Drawable goldy_layers_btn = con.getResources().getDrawable(R.drawable.golden_layers_btn);
                Drawable grey_projects_btn = con.getResources().getDrawable(R.drawable.grey_document);
                Drawable grey_my_projects_btn = con.getResources().getDrawable(R.drawable.grey_widget);
                home_btn.setTextColor(getResources().getColor(R.color.grey));
                vacancies_btn.setTextColor(getResources().getColor(R.color.golden));
                projects_btn.setTextColor(getResources().getColor(R.color.grey));
                my_projects_btn.setTextColor(getResources().getColor(R.color.grey));
                home_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_home_btn, null, null);
                vacancies_btn.setCompoundDrawablesWithIntrinsicBounds(null, goldy_layers_btn, null, null);
                projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_projects_btn, null, null);
                my_projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_my_projects_btn, null, null);
            }
        });

        projects_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectsFragment projectsFragment = new ProjectsFragment();
                setFragment(projectsFragment);
                Drawable grey_home_btn = con.getResources().getDrawable(R.drawable.grey_home_button);
                Drawable grey_layers_btn = con.getResources().getDrawable(R.drawable.layers_btn);
                Drawable goldy_projects_btn = con.getResources().getDrawable(R.drawable.gold_document);
                Drawable grey_my_projects_btn = con.getResources().getDrawable(R.drawable.grey_widget);
                home_btn.setTextColor(getResources().getColor(R.color.grey));
                vacancies_btn.setTextColor(getResources().getColor(R.color.grey));
                projects_btn.setTextColor(getResources().getColor(R.color.goldy));
                my_projects_btn.setTextColor(getResources().getColor(R.color.grey));
                home_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_home_btn, null, null);
                vacancies_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_layers_btn, null, null);
                projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, goldy_projects_btn, null, null);
                my_projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_my_projects_btn, null, null);
            }
        });

        my_projects_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProjectsFragment myProjects = new myProjectsFragment();
                setFragment(myProjects);
                Drawable grey_home_btn = con.getResources().getDrawable(R.drawable.grey_home_button);
                Drawable grey_layers_btn = con.getResources().getDrawable(R.drawable.layers_btn);
                Drawable grey_projects_btn = con.getResources().getDrawable(R.drawable.grey_document);
                Drawable gold_my_projects_btn = con.getResources().getDrawable(R.drawable.gold_widget);
                home_btn.setTextColor(getResources().getColor(R.color.grey));
                vacancies_btn.setTextColor(getResources().getColor(R.color.grey));
                projects_btn.setTextColor(getResources().getColor(R.color.grey));
                my_projects_btn.setTextColor(getResources().getColor(R.color.goldy));
                home_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_home_btn, null, null);
                vacancies_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_layers_btn, null, null);
                projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, grey_projects_btn, null, null);
                my_projects_btn.setCompoundDrawablesWithIntrinsicBounds(null, gold_my_projects_btn, null, null);
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}