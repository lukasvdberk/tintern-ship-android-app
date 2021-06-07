package com.example.tinternshipbackend.viewUtil;

import android.content.Context;

public class ToastUtil {
    public static void showLongToast(Context context, String text) {
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_LONG).show();
    }
}
