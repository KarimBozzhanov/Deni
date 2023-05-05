package com.example.deni;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectsFragment extends Fragment implements projectClickListener{

    private static final String LOG_TAG = CreateSummaryFragment.class.getName();
    Spinner projects_categories;
    RecyclerView projectRecycleView;
    ArrayList<Project> projectArrayList;
    ProjectAdapter projectAdapter;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference db = firebaseFirestore.collection("Projects");
        TextView projectCountText = (TextView) view.findViewById(R.id.projects_count);
        AggregateQuery collectionCount = db.count();
        collectionCount.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AggregateQuerySnapshot snapshot = task.getResult();
                projectCountText.setText(String.valueOf(snapshot.getCount()));
            }
        });
        projects_categories = (Spinner) view.findViewById(R.id.projects_categories_spinner);
        ArrayAdapter<?> projectsCategoriesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
        projectsCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projects_categories.setAdapter(projectsCategoriesAdapter);
        projects_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] projectsCategories = getResources().getStringArray(R.array.categories);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        projectRecycleView = view.findViewById(R.id.projectsList);
        db.orderBy("creationTime", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(LOG_TAG, error.getMessage());
                    return;
                }

                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED){
                        projectArrayList.add(documentChange.getDocument().toObject(Project.class));
                    }
                    projectAdapter.notifyDataSetChanged();
                }
            }
        });
        projectRecycleView.setHasFixedSize(true);
        projectRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        projectArrayList = new ArrayList<>();
        projectAdapter = new ProjectAdapter(getContext(), projectArrayList, this);
        projectRecycleView.setAdapter(projectAdapter);

        return view;
    }

    @Override
    public void onProjectClickListener(Project project, int position) {
        String Id = String.valueOf(project.getId());
        long views = project.getViews();
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference db = firebaseFirestore.collection("Projects").document(Id);
        db.update("views", views + 1);
        getActivity().recreate();

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
}