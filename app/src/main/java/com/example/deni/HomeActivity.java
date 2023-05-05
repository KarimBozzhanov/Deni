package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kotlin.jvm.internal.Lambda;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String LOG_TAG = CreateSummaryFragment.class.getName();
    private HomeFragment homeFragment = new HomeFragment();
    Toolbar toolBar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Button home_btn, vacancies_btn, projects_btn, my_projects_btn;
    LinearLayout hamMenu;
    Context con = this;

    TextView userName;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_activity);
        navigationView = findViewById(R.id.menuNavigationView);
        View navigationHeader = navigationView.inflateHeaderView(R.layout.menu_header);
        userName = (TextView) navigationHeader.findViewById(R.id.userName);
        userName.setText(user.getDisplayName());
        hamMenu = (LinearLayout) findViewById(R.id.hamMenu);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolBar, R.string.OpenDrawer, R.string.CloseDrawer);
        mDrawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();



        navigationView.bringToFront();

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

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.portfolio) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent i = new Intent(HomeActivity.this, PortfolioActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.signOut) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(HomeActivity.this, "До свидания " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
            finishAffinity();
            return true;
        }

        return false;
    }
}