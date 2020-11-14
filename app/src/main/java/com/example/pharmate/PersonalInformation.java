package com.example.pharmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class PersonalInformation extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    EditText userType, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;

    // KULLANICININ BU FORMU DOLDURDUĞUNU UYGULAMA BOYUNCA KONTROL EDİLMESİ GEREKİYOR.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_page);
        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

//
//        //defining textFields
        userType = findViewById(R.id.userTypeText);
        name = findViewById(R.id.personNameText);
        userSurname = findViewById(R.id.personSurnameText);
        userTurkishID = findViewById(R.id.turkishIdText);
        userContact = findViewById(R.id.personContactText);
        userAddress = findViewById(R.id.personAddressText);
        userBirthDate = findViewById(R.id.birthDateText);


    }

    public void submitPersonInfoClick(View v) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String nameText = name.getText().toString();
        String userSurnameText = userSurname.getText().toString();
        String userTurkishIDText = userTurkishID.getText().toString();
        String userAddressText = userAddress.getText().toString();
        String userContactText = userContact.getText().toString();
        String userTypeText = userType.getText().toString();
        String userBirthDayText = userBirthDate.getText().toString();


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameText+userSurnameText)
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Task Successful");
                        }
                    }
                });


        String userEmail = firebaseUser.getEmail();
        String userID = firebaseUser.getUid();
        HashMap<String, Object> postUserData = new HashMap<>();
        postUserData.put("type", userTypeText);
        postUserData.put("name", nameText);
        postUserData.put("surname", userSurnameText);
        postUserData.put("turkishId", userTurkishIDText);
        postUserData.put("contact", userContactText);
        postUserData.put("address",userAddressText);
        postUserData.put("birthDate",userBirthDayText);
        firebaseFirestore.collection("userType").document(userID).set(postUserData);
    }
    }






