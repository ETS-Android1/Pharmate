package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PersonalInformation extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public PersonalInformation() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_informatin);

        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        EditText userType, name, userSurname, userTurkishID, userContact, userAddress;
        Button submitButton;


        //defining textFields
        userType = (EditText) findViewById(R.id.userTypeText);
        name = (EditText) findViewById(R.id.personNameText);
        userSurname = (EditText) findViewById(R.id.personSurnameText);
        userTurkishID = (EditText) findViewById(R.id.personTurkishIDText);
        userContact = (EditText) findViewById(R.id.personContactText);
        userAddress = (EditText) findViewById(R.id.personAddressText);
        submitButton = (Button) findViewById(R.id.personInfoSubmitButton);


    }


}

