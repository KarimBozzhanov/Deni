package com.example.deni;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class myProjectsFragment extends Fragment implements mySummaryClickListener, myProjectsClickListener, myVacancyClickListener  {

    private static final String LOG_TAG = CreateSummaryFragment.class.getName();

    Spinner myProjectsCategoriesSpinner;
    RecyclerView mySummaryList, myProjectsList, myVacanciesList;
    ArrayList<Summary> mySummaryArrayList;
    ArrayList<Project> myProjectArrayList;
    ArrayList<Vacancy> myVacanciesArrayList;
    MySummaryAdapter mySummaryAdapter;

    FirebaseFirestore summariesTable;
    FirebaseFirestore vacanciesTable;
    FirebaseFirestore projectsTable;

    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_projects, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();

        myProjectsCategoriesSpinner = (Spinner) view.findViewById(R.id.my_projects_categories_spinner);
        ArrayAdapter<?> myProjectsCategoriesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        myProjectsCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myProjectsCategoriesSpinner.setAdapter(myProjectsCategoriesAdapter);

        myProjectsCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] category = getResources().getStringArray(R.array.categories);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mySummaryList = view.findViewById(R.id.mySummaryList);
        mySummaryList.setHasFixedSize(true);
        mySummaryList.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mySummaryArrayList = new ArrayList<>();
        mySummaryAdapter = new MySummaryAdapter(getContext(), mySummaryArrayList, this);
        mySummaryList.setAdapter(mySummaryAdapter);


        summariesTable = FirebaseFirestore.getInstance();
        CollectionReference summaryCollection = summariesTable.collection("Summary");
        summaryCollection.whereEqualTo("userId", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(LOG_TAG, error.getMessage());
                    return;
                }

                for (DocumentChange documentChange: value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        mySummaryArrayList.add(documentChange.getDocument().toObject(Summary.class));
                    }
                    mySummaryAdapter.notifyDataSetChanged();
                }
            }
        });

        myProjectsList = view.findViewById(R.id.myProjectsList);
        myProjectsList.setHasFixedSize(true);
        myProjectsList.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        myProjectArrayList = new ArrayList<>();
        myProjectsAdapter projectsAdapter = new myProjectsAdapter(getContext(), myProjectArrayList, this);
        myProjectsList.setAdapter(projectsAdapter);

        projectsTable = FirebaseFirestore.getInstance();

        CollectionReference projects = projectsTable.collection("Projects");
        projects.whereEqualTo("userId", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(LOG_TAG, error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        myProjectArrayList.add(dc.getDocument().toObject(Project.class));
                    }
                    projectsAdapter.notifyDataSetChanged();
                }
            }
        });


        myVacanciesList = view.findViewById(R.id.myVacanciesList);
        myVacanciesList.setHasFixedSize(true);
        myVacanciesList.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        myVacanciesArrayList = new ArrayList<>();
        MyVacancyAdapter vacancyAdapter = new MyVacancyAdapter(getContext(), myVacanciesArrayList, this);
        myVacanciesList.setAdapter(vacancyAdapter);

        vacanciesTable = FirebaseFirestore.getInstance();

        CollectionReference vacancy = vacanciesTable.collection("Vacancies");
        vacancy.whereEqualTo("userId", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(LOG_TAG, error.getMessage());
                    return;
                }

                for (DocumentChange dc :  value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        myVacanciesArrayList.add(dc.getDocument().toObject(Vacancy.class));
                    }
                    vacancyAdapter.notifyDataSetChanged();
                }
            }
        });


        Button addBtn = (Button) view.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CreateProjectsActivity.class);
                startActivity(i);
            }
        });
        return view;
    }



    @Override
    public void onSummaryClickListener(Summary summary, int position) {
        String designerName, speciality, jobType, experience, education, skills, information, city, salary,
                currency, phoneNumber, instagram, telegram, userName;

        designerName = summary.getDesignerName();
        speciality = summary.getSpeciality();
        jobType = summary.getJobType();
        experience = summary.getExperience();
        education = summary.getEducation();
        skills = summary.getSkills();
        information = summary.getInformation();
        city = summary.getCity();
        salary = summary.getSalary();
        currency = summary.getCurrency();
        phoneNumber = summary.getPhoneNumber();
        instagram = summary.getInstagram();
        telegram = summary.getTelegram();
        userName = summary.getUserName();

        Intent intent = new Intent(getContext(), summaryDetailsActivity.class);
        intent.putExtra("designerName", designerName);
        intent.putExtra("speciality", speciality);
        intent.putExtra("jobType", jobType);
        intent.putExtra("experience", experience);
        intent.putExtra("education", education);
        intent.putExtra("skills", skills);
        intent.putExtra("information", information);
        intent.putExtra("city", city);
        intent.putExtra("salary", salary);
        intent.putExtra("currency", currency);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("instagram", instagram);
        intent.putExtra("telegram", telegram);
        intent.putExtra("userName", userName);

        startActivity(intent);
    }

    @Override
    public void onSummaryRefreshClickListener(Summary summary, int position) {
        String Id = String.valueOf(summary.getId());
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        String creationDate = dateFormat.format(new Date());
        summariesTable = FirebaseFirestore.getInstance();
        DocumentReference refreshSummary = summariesTable.collection("Summary").document(Id);
        refreshSummary.update("creationDate", creationDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Резюме обновлено", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось обновить резюме", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.getMessage());
            }
        });
    }

    @Override
    public void onSummaryChangeSummaryClickListener(Summary summary, int position) {
        Intent intent = new Intent(getContext(), EditSummaryActivity.class);
        intent.putExtra("Id", String.valueOf(summary.getId()));
        Log.e(LOG_TAG, "Id - " + summary.getId());
        intent.putExtra("designerName", summary.getDesignerName());
        intent.putExtra("category", summary.getCategory());
        intent.putExtra("jobType", summary.getJobType());
        intent.putExtra("speciality", summary.getSpeciality());
        intent.putExtra("jobType", summary.getJobType());
        intent.putExtra("experience", summary.getExperience());
        intent.putExtra("education", summary.getEducation());
        intent.putExtra("skills", summary.getSkills());
        intent.putExtra("information", summary.getInformation());
        intent.putExtra("city", summary.getCity());
        intent.putExtra("salary", summary.getSalary());
        intent.putExtra("currency", summary.getCurrency());
        intent.putExtra("phoneNumber", summary.getPhoneNumber());
        intent.putExtra("instagram", summary.getInstagram());
        intent.putExtra("telegram", summary.getTelegram());

        startActivity(intent);
    }

    @Override
    public void onSummaryDeleteSummaryClickListener(Summary summary, int position) {
        String Id = String.valueOf(summary.getId());
        summariesTable = FirebaseFirestore.getInstance();
        DocumentReference deleteSummary = summariesTable.collection("Summary").document(Id);
        deleteSummary.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                FragmentTransaction fragmentTransaction = (getActivity().getSupportFragmentManager().beginTransaction());
                fragmentTransaction.detach(fragment).commitNow();
                fragmentTransaction.attach(fragment).commitNow();
                Toast.makeText(getContext(), "Резюме удалено", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ну удалось удалить резюме", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.getMessage());
            }
        });
    }

    @Override
    public void onMyProjectClickListener(Project project, int position) {
        String userName = project.getUserName();
        String projectName = project.getProjectName();
        String projectCategory = project.getProjectCategory();
        String projectTerm = project.getProjectTerm();
        String projectDescription = project.getProjectDescription();
        String projectSalary = project.getProjectSalary();
        String projectCurrency = project.getProjectCurrency();
        String projectPhone = project.getProjectPhone();
        String projectInstagram = project.getProjectInstagram();
        String projectTelegram = project.getProjectTelegram();
        String projectEmail = project.getProjectEmail();

        Intent intent = new Intent(getContext(), ProjectDetailsActivity.class);
        intent.putExtra("userName", userName);
        intent.putExtra("projectName", projectName);
        intent.putExtra("projectCategory", projectCategory);
        intent.putExtra("projectTerm", projectTerm);
        intent.putExtra("projectDescription", projectDescription);
        intent.putExtra("projectSalary", projectSalary);
        intent.putExtra("projectCurrency", projectCurrency);
        intent.putExtra("projectPhone", projectPhone);
        intent.putExtra("projectInstagram", projectInstagram);
        intent.putExtra("projectTelegram", projectTelegram);
        intent.putExtra("projectEmail", projectEmail);
        startActivity(intent);
    }

    @Override
    public void onRefreshProjectClickListener(Project project, int position) {
        projectsTable = FirebaseFirestore.getInstance();
        String Id = String.valueOf(project.getId());
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd:MM:yyyy");
        DocumentReference projects = projectsTable.collection("Project").document(Id);
        String creationTime = dateFormat.format(new Date());
        projects.update("creationTime", creationTime).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Проект обновлен", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось обновить проект", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.getMessage());
            }
        });
    }

    @Override
    public void onChangeMyProjectClickListener(Project project, int position) {
        Intent intent = new Intent(getContext(), EditProjectActivity.class);
        intent.putExtra("projectId", String.valueOf(project.getId()));
        intent.putExtra("projectName", project.getProjectName());
        intent.putExtra("projectCategory", project.getProjectCategory());
        intent.putExtra("projectTerm", project.getProjectTerm());
        intent.putExtra("projectDescription", project.getProjectDescription());
        intent.putExtra("projectSalary", project.getProjectSalary());
        intent.putExtra("projectCurrency", project.getProjectCurrency());
        intent.putExtra("projectPhone", project.getProjectPhone());
        intent.putExtra("projectInstagram", project.getProjectInstagram());
        intent.putExtra("projectTelegram", project.getProjectTelegram());
        intent.putExtra("projectEmail", project.getProjectEmail());
        startActivity(intent);
    }

    @Override
    public void onDeleteMyProjectClickListener(Project project, int position) {
        projectsTable = FirebaseFirestore.getInstance();
        DocumentReference dc = projectsTable.collection("Projects").document(String.valueOf(project.getId()));
        dc.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                FragmentTransaction ft = (getActivity().getSupportFragmentManager().beginTransaction());
                ft.detach(fragment).commitNow();
                ft.attach(fragment).commitNow();
                Toast.makeText(getContext(), "Проект удален", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось удалить проект", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.getMessage());
            }
        });
    }

    @Override
    public void onMyVacancyClickListener(Vacancy vacancy, int position) {
        String companyName, userName, creationTime, vacancyName, vacancyCategory, vacancyJobType, vacancySalary, vacancyCurrency, vacancySchedule, vacancyExperience, vacancyDuty, vacancyRequirements, vacancyConditions, vacancyJobPlace, phone, email, telegram, instagram;
        companyName = vacancy.getCompany();
        userName = vacancy.getUserName();
        creationTime = vacancy.getDateCreating();
        vacancyName = vacancy.getVacancyName();
        vacancyCategory = vacancy.getCategory();
        vacancyJobType = vacancy.getJobType();
        vacancySalary = vacancy.getSalary();
        vacancyCurrency = vacancy.getCurrency();
        vacancySchedule = vacancy.getSchedule();
        vacancyExperience = vacancy.getExperience();
        vacancyDuty = vacancy.getDuty();
        vacancyRequirements = vacancy.getRequirements();
        vacancyConditions = vacancy.getConditions();
        vacancyJobPlace = vacancy.getJobPlace();
        phone = vacancy.getPhone();
        instagram = vacancy.getInstagram();
        telegram = vacancy.getTelegram();
        email = vacancy.getEmail();

        Intent intent = new Intent (getContext(), vacancyDetailsActivity.class);
        intent.putExtra("companyName", companyName);
        intent.putExtra("userName", userName);
        intent.putExtra("vacancyName", vacancyName);
        intent.putExtra("vacancyCategory", vacancyCategory);
        intent.putExtra("vacancyJobType", vacancyJobType);
        intent.putExtra("vacancySalary", vacancySalary);
        intent.putExtra("vacancyCurrency", vacancyCurrency);
        intent.putExtra("vacancySchedule", vacancySchedule);
        intent.putExtra("vacancyExperience", vacancyExperience);
        intent.putExtra("vacancyDuty", vacancyDuty);
        intent.putExtra("vacancyRequirements", vacancyRequirements);
        intent.putExtra("vacancyConditions", vacancyConditions);
        intent.putExtra("vacancyJobPlace", vacancyJobPlace);
        intent.putExtra("phone", phone);
        intent.putExtra("instagram", instagram);
        intent.putExtra("telegram", telegram);
        intent.putExtra("email", email);
        intent.putExtra("creationTime", creationTime);
        startActivity(intent);
    }

    @Override
    public void refreshMyVacancyClickListener(Vacancy vacancy, int position) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd:MM:yyyy");
        String creationDate = dateFormat.format(new Date());
        vacanciesTable = FirebaseFirestore.getInstance();
        DocumentReference dc = vacanciesTable.collection("Vacancies").document(String.valueOf(vacancy.getId()));
        dc.update("dateCreating", creationDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Вакансия обновлена", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось обновить вакансию", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.getMessage());
            }
        });
    }

    @Override
    public void editMyVacancyClickListener(Vacancy vacancy, int position) {
        Intent intent = new Intent(getContext(), EditVacancyActivity.class);
        intent.putExtra("vacancyId", String.valueOf(vacancy.getId()));
        intent.putExtra("vacancyName", vacancy.getVacancyName());
        intent.putExtra("vacancyCompany", vacancy.getCompany());
        intent.putExtra("vacancyCategory", vacancy.getCategory());
        intent.putExtra("vacancyJobType", vacancy.getJobType());
        intent.putExtra("vacancySchedule", vacancy.getSchedule());
        intent.putExtra("vacancyExperience", vacancy.getExperience());
        intent.putExtra("vacancyDuty", vacancy.getDuty());
        intent.putExtra("vacancyRequirements", vacancy.getRequirements());
        intent.putExtra("vacancyConditions", vacancy.getConditions());
        intent.putExtra("vacancySalary", vacancy.getSalary());
        intent.putExtra("vacancyCurrency", vacancy.getCurrency());
        intent.putExtra("vacancyCity", vacancy.getCity());
        intent.putExtra("vacancyJobPlace", vacancy.getJobPlace());
        intent.putExtra("vacancyPhone", vacancy.getPhone());
        intent.putExtra("vacancyInstagram", vacancy.getInstagram());
        intent.putExtra("vacancyTelegram", vacancy.getTelegram());
        intent.putExtra("vacancyEmail", vacancy.getEmail());
        startActivity(intent);
    }

    @Override
    public void deleteMyVacancyClickListener(Vacancy vacancy, int position) {
        vacanciesTable = FirebaseFirestore.getInstance();
        DocumentReference dc = vacanciesTable.collection("Vacancies").document(String.valueOf(vacancy.getId()));
        dc.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                FragmentTransaction ft = (getActivity().getSupportFragmentManager().beginTransaction());
                ft.detach(fragment).commitNow();
                ft.attach(fragment).commitNow();
                Toast.makeText(getContext(), "Вакансия удалена", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Не удалось удалить вакансию", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.getMessage());
            }
        });
    }
}