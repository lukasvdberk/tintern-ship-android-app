package com.example.tinternshipbackend.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tinternshipbackend.R;
import com.example.tinternshipbackend.models.company.Company;
import com.example.tinternshipbackend.viewUtil.DownloadImageAndSet;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompanyLikesAdapter extends ArrayAdapter<Company> {

    public CompanyLikesAdapter(Context context, ArrayList<Company> companyArrayList){
        super(context, R.layout.likes_item, companyArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Company company = getItem(position);

        if(convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.likes_item,parent,false);

        }

        TextView name = convertView.findViewById(R.id.personName);
        TextView phoneNumber = convertView.findViewById(R.id.phoneNumber);

        name.setText(company.getName());
        phoneNumber.setText(company.getPhoneNumber());

        ImageView profilePicture = convertView.findViewById(R.id.profile_pic);
        new DownloadImageAndSet(profilePicture, getContext()).execute(company.getAvatarUrl());

        return convertView;
    }
}
