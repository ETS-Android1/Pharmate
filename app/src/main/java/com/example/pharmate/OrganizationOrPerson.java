package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrganizationOrPerson extends AppCompatActivity {
Button Btnperson,Btnorganization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_or_person);

        Btnperson=findViewById(R.id.Btnperson);
        Btnorganization=findViewById(R.id.Btnorganization);
    }

    public void organizationCreate(View view) {
        Intent intent=new Intent(OrganizationOrPerson.this,SignUpOrg.class);
        startActivity(intent);

    }

    public void personCreate(View view) {
        Intent intent=new Intent(OrganizationOrPerson.this,registerPerson.class);
        startActivity(intent);
    }
}