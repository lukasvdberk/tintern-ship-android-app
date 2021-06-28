package com.example.tinternshipbackend.viewUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class DownloadImageAndSet extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public DownloadImageAndSet(ImageView imageView, Context context) {
        this.imageView=imageView;
        Toast.makeText(context, "Please wait, it may take a few minutes...",Toast.LENGTH_SHORT).show();
    }
    protected Bitmap doInBackground(String... urls) {
        String imageURL=urls[0];
        Bitmap bimage=null;
        try {
            InputStream in=new java.net.URL(imageURL).openStream();
            bimage= BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}