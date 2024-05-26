package com.example.lives_project;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;

import androidx.core.content.ContextCompat;

public class AboutDialog {

    public static void show(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Welcome to LiveS!");
        builder.setMessage("This is your safety companion. Please open About Us to learn more");
        builder.setPositiveButton("OK", null);

        int titleTextColor = ContextCompat.getColor(context, R.color.blue);
        builder.setTitle(Html.fromHtml("<font color='" + titleTextColor + "'>Welcome to LiveS!</font>"));
        int messageTextColor = ContextCompat.getColor(context, R.color.blue_light);
        builder.setMessage(Html.fromHtml("<font color='" + messageTextColor + "'>This is your safety companion :) Please open About Us to learn more and good luck! </font>"));
        int buttonTextColor = ContextCompat.getColor(context, R.color.blue);
        builder.setPositiveButton(Html.fromHtml("<font color='" + buttonTextColor + "'>OK</font>"), null);


        builder.show();
    }
}
