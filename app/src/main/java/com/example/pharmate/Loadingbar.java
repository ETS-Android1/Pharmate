package com.example.pharmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loadingbar {
    Activity activity;
    AlertDialog dialog;

    public Loadingbar(Activity thisactivity) {

        activity = thisactivity;
    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_upload_medicine, null));
        builder.setView(inflater.inflate(R.layout.activity_personal_information_page, null));
        builder.setView(inflater.inflate(R.layout.activity_receive_medicine, null));
        builder.setView(inflater.inflate(R.layout.activity_org_information_page, null));
        builder.setView(inflater.inflate(R.layout.activity_sign_up_org, null));
        dialog = builder.create();
        dialog.show();
    }

    void dismissbar() {

        dialog.dismiss();
    }
}
