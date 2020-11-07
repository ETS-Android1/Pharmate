package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class PersonalInformation extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

 // KULLANICININ BU FORMU DOLDURDUĞUNU UYGULAMA BOYUNCA KONTROL EDİLMESİ GEREKİYOR.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_page);

//        // Instance
//        firebaseStorage = FirebaseStorage.getInstance();
//        storageReference = firebaseStorage.getReference();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//
//        EditText userType, name, userSurname, userTurkishID, userContact, userAddress;
//        Button submitButton;
//
//
//        //defining textFields
//        userType = findViewById(R.id.userTypeText);
//        name = findViewById(R.id.personNameText);
//        userSurname = findViewById(R.id.personSurnameText);
//        userTurkishID = findViewById(R.id.personTurkishIDText);
//        userContact = findViewById(R.id.personContactText);
//        userAddress = findViewById(R.id.personAddressText);
//        submitButton = findViewById(R.id.personInfoSubmitButton);
//
//        if (firebaseUser  != null) {
//            String userEmail = firebaseUser.getEmail();
//            String userID = firebaseUser.getUid();
//
//            HashMap<String, Object> postData = new HashMap<>();
//            postData.put("type", userType);
//            postData.put("userEmail", userEmail);
//            postData.put("name", name);
//            postData.put("surname", userSurname);
//            postData.put("turkishId", userTurkishID);
//            postData.put("contact", userContact);
//            postData.put("address", userAddress);
//            firebaseFirestore.collection("userType").document(userID).set(postData);
//        }
//    }


    }}

