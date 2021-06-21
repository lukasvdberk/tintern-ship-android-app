package com.example.tinternshipbackend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.models.Education;
import com.example.tinternshipbackend.models.User;
import com.example.tinternshipbackend.models.company.CompanyProject;

import java.util.List;

public class ProjectCompanyAdapter extends ArrayAdapter<CompanyProject> {
    OnClose onClose;

    public ProjectCompanyAdapter(@NonNull Context context, int resource, List<CompanyProject> projects) {
        super(context, resource, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectCompanyViewHolder vh;

        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.company_project_list_item, parent, false);

            vh = new ProjectCompanyViewHolder();
            vh.description = (TextView) convertView.findViewById(R.id.description);
            vh.educationText = (Button) convertView.findViewById(R.id.educationText);
            vh.close = (Button) convertView.findViewById(R.id.close);

            convertView.setTag(vh);

        } else {
            vh = (ProjectCompanyViewHolder) convertView.getTag();
        }

        CompanyProject project = getItem(position);

        vh.description.setText(project.getDescription());
        vh.educationText.setText(project.getEducationId());
        vh.close.setOnClickListener(v -> onClose.onClose(project));

        return convertView;
    }

    public void onClose(OnClose onClose) {
        this.onClose = onClose;
    }

    private static class ProjectCompanyViewHolder {
        Button educationText;
        TextView description;
        Button close;

    }

    public interface OnClose {
        void onClose(CompanyProject project);
    }
}
