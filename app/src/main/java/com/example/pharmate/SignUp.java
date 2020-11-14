package com.example.pharmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    EditText emailText, passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.signUpEmailText);
        passwordText = findViewById(R.id.signUpPasswordText);

        // girilen inputları kontrol edicez.
    }
    public void signUpClick (View view) {
        System.out.println("button pressed");
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        // bir ust satirda kullanicidan aldigimiz sifreyi asagidaki firebase'e gondermeden once
        // validation islemlerini yapacagiz


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUp.this,"User Created", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUp.this, HomePage.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}