package com.example.tinternshipbackend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.models.Education;

import java.util.List;

public class EducationArrayAdapter extends ArrayAdapter<Education> {

    public EducationArrayAdapter(@NonNull Context context, int resource, List<Education> educations) {
        super(context, resource, educations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EducationViewHolder holder;

        if(convertView == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.education_list_item, parent, false);

            holder = new EducationViewHolder();
            holder.educationName = (TextView) convertView.findViewById(R.id.educationName);

            convertView.setTag(holder);

        } else {
            holder = (EducationViewHolder) convertView.getTag();
        }

        Education education = getItem(position);
        holder.educationName.setText(education.getName());

        return super.getView(position, convertView, parent);
    }

    private static class EducationViewHolder {
        TextView educationName;
    }
}
