package com.example.deni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class myProjectsAdapter extends RecyclerView.Adapter<myProjectsAdapter.MyProjectsViewHolder> {

    Context context;
    ArrayList<Project> myProjects;
    myProjectsClickListener listener;

    public myProjectsAdapter(Context context, ArrayList<Project> myProjects, myProjectsClickListener listener) {
        this.context = context;
        this.myProjects = myProjects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyProjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_project_card, parent, false);
        return new MyProjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProjectsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Project project = myProjects.get(position);
        holder.myProjectUserName.setText(project.getUserName());
        holder.myProjectViews.setText(String.valueOf(project.getViews()));
        holder.myProjectName.setText(project.getProjectName());
        holder.myProjectCategory.setText(project.getProjectCategory());
        holder.myProjectSalary.setText(project.getProjectSalary());
        if (project.getProjectCurrency().equals("Доллар")){
            holder.myProjectCurrency.setText("$");
        } else if (project.getProjectCurrency().equals("Евро")){
            holder.myProjectCurrency.setText("EUR");
        } else if (project.getProjectCurrency().equals("Рубль")) {
            holder.myProjectCurrency.setText("руб");
        } else {
            holder.myProjectCurrency.setText("тг");
        }
        holder.myProjectCreationTime.setText(project.getCreationTime());

        holder.refreshMyProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRefreshProjectClickListener(myProjects.get(position), position);
            }
        });

        holder.editMyProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onChangeMyProjectClickListener(myProjects.get(position), position);
            }
        });

        holder.deleteMyProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteMyProjectClickListener(myProjects.get(position), position);
            }
        });

        holder.myProjectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMyProjectClickListener(myProjects.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myProjects.size();
    }


    public static class MyProjectsViewHolder extends RecyclerView.ViewHolder {

        TextView myProjectUserName, myProjectViews, myProjectName, myProjectCategory, myProjectSalary, myProjectCurrency, myProjectCreationTime;
        Button refreshMyProject, editMyProject;
        ImageButton deleteMyProject;
        CardView myProjectCard;
        public MyProjectsViewHolder(@NonNull View itemView) {
            super(itemView);

            myProjectUserName = (TextView) itemView.findViewById(R.id.my_project_card_userName);
            myProjectViews = (TextView) itemView.findViewById(R.id.my_project_card_views);
            myProjectName = (TextView) itemView.findViewById(R.id.my_project_card_name);
            myProjectCategory = (TextView) itemView.findViewById(R.id.my_project_card_category);
            myProjectSalary = (TextView) itemView.findViewById(R.id.my_project_card_salary);
            myProjectCurrency = (TextView) itemView.findViewById(R.id.my_project_card_currency);
            myProjectCreationTime = (TextView) itemView.findViewById(R.id.my_project_card_timeCreating);
            refreshMyProject = (Button) itemView.findViewById(R.id.refreshProjectInList);
            editMyProject = (Button) itemView.findViewById(R.id.changeProject);
            deleteMyProject = (ImageButton) itemView.findViewById(R.id.deleteProject);
            myProjectCard = (CardView) itemView.findViewById(R.id.my_project_card);
        }
    }
}
