package com.example.tinternshipbackend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.models.Education;

import java.util.ArrayList;
import java.util.List;

public class EducationArrayAdapter extends BaseAdapter {
    ArrayList<Education> allEducations;
    LayoutInflater inflter;

    public EducationArrayAdapter(@NonNull Context context, ArrayList<Education> educations) {
        this.allEducations = educations;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return this.allEducations.size();
    }

    @Override
    public Education getItem(int position) {
        return this.allEducations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.allEducations.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EducationViewHolder holder;

        if(convertView == null) {
            convertView = inflter.inflate(R.layout.education_list_item, null);
            holder = new EducationViewHolder();
            holder.educationName = (TextView) convertView.findViewById(R.id.educationName);

            convertView.setTag(holder);

        } else {
            holder = (EducationViewHolder) convertView.getTag();
        }

        Education education = getItem(position);
        holder.educationName.setText(education.getName());

        return convertView;
    }

    private static class EducationViewHolder {
        TextView educationName;
    }
}
