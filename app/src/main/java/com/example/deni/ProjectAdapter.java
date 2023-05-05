package com.example.deni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    Context context;
    ArrayList<Project> projectArrayList;
    projectClickListener listener;


    public ProjectAdapter(Context context, ArrayList<Project> projectArrayList, projectClickListener listener) {
        this.context = context;
        this.projectArrayList = projectArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_card, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Project project = projectArrayList.get(position);
        holder.projectUserName.setText(project.getUserName());
        String views = String.valueOf(project.getViews());
        holder.projectViews.setText(views);
        holder.projectCreationTime.setText(project.getCreationTime());
        holder.projectName.setText(project.getProjectName());
        holder.projectCategory.setText(project.getProjectCategory());
        holder.projectSalary.setText(project.getProjectSalary());
        if (project.getProjectCurrency().equals("Доллар")){
            holder.projectCurrency.setText("$");
        } else if (project.getProjectCurrency().equals("Евро")){
            holder.projectCurrency.setText("EUR");
        } else if (project.getProjectCurrency().equals("Рубль")) {
            holder.projectCurrency.setText("руб");
        } else {
            holder.projectCurrency.setText("тг");
        }

        holder.projectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onProjectClickListener(projectArrayList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectArrayList.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView projectUserName, projectViews, projectName, projectCategory, projectSalary, projectCurrency, projectCreationTime;
        CardView projectCard;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectUserName = (TextView) itemView.findViewById(R.id.project_card_userName);
            projectViews = (TextView) itemView.findViewById(R.id.project_card_views);
            projectName = (TextView) itemView.findViewById(R.id.project_card_name);
            projectCategory = (TextView) itemView.findViewById(R.id.project_card_category);
            projectSalary = (TextView) itemView.findViewById(R.id.project_card_salary);
            projectCurrency = (TextView) itemView.findViewById(R.id.project_card_currency);
            projectCreationTime = (TextView) itemView.findViewById(R.id.project_card_timeCreating);

            projectCard = (CardView) itemView.findViewById(R.id.project_card);
        }


    }

}
