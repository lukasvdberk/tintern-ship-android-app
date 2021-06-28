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
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.models.intern.Intern;

import java.util.ArrayList;

public class InternLikesAdapter extends ArrayAdapter<Intern> {

    public InternLikesAdapter(Context context, ArrayList<Intern> companyArrayList){
        super(context, R.layout.likes_item, companyArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Intern intern = getItem(position);

        if(convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.likes_item,parent,false);

        }
        System.out.println("____________________________________________");
        System.out.println(intern.getName());

//        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView name = convertView.findViewById(R.id.personName);
        TextView age = convertView.findViewById(R.id.phoneNumber);

//        imageView.setImageResource();
        name.setText(intern.getName());
        age.setText(intern.getAgeAsAString());


        return convertView;
    }
}
