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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LikesAdapter extends ArrayAdapter<Company> {

    public LikesAdapter(Context context, ArrayList<Company> companyArrayList){
        super(context, R.layout.likes_item, companyArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Company company = getItem(position);

        if(convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.likes_item,parent,false);

        }

//        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView name = convertView.findViewById(R.id.personName);
        TextView phoneNumber = convertView.findViewById(R.id.phoneNumber);
        TextView description = convertView.findViewById(R.id.relative_description);

//        imageView.setImageResource();
        name.setText(company.getName());
        phoneNumber.setText(company.getPhoneNumber());
//        description.setText(company.getDescription());


        return convertView;
    }
}
