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

public class InternMatchesAdapter extends ArrayAdapter<Company> {

    public InternMatchesAdapter(Context context, ArrayList<Company> companyArrayList){
        super(context, R.layout.matches_item, companyArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Company company = getItem(position);

        if(convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.matches_item,parent,false);

        }

//        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView name = convertView.findViewById(R.id.personName);
        TextView phoneNumber = convertView.findViewById(R.id.phoneNumber);

//        imageView.setImageResource();
        name.setText(company.getName());
        phoneNumber.setText(company.getPhoneNumber());


        return convertView;
    }
}
