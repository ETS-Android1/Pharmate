package com.example.pharmate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import fragments.Choose;
import location.LocationTracker;

public class MainActivity extends AppCompatActivity {

    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

    }


    public void goToSignInClick(View view) {
        Intent intent = new Intent(MainActivity.this, Choose.class);
        startActivity(intent);
        finish();

    }

    public void goToSignUpClick(View view) {
        Intent intent = new Intent(MainActivity.this, OrganizationOrPerson.class);
        startActivity(intent);

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    23);

        }
    }

}