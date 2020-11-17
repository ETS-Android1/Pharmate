package com.example.pharmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInOrg extends AppCompatActivity {
    // Defining Firebase Instance Variables
    private FirebaseAuth firebaseAuth;
    EditText mailSign, passwordSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_org);
        // defining firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        // defining email and password text
        mailSign= findViewById(R.id.mailSign);
        passwordSign= findViewById(R.id.passwordSign);

        // Getting current user
        // if exists user will directly access for homepage
        // else login register page
        //  FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //if (firebaseUser != null) {
        // Intent _intent = new Intent(SignIn.this, HomePageOrg.class);
        //  startActivity(_intent);
        //finish();
        //   System.out.println(firebaseUser.getUid());

        //}

    }

    public void loginClick(View view) {
        String email = mailSign.getText().toString();
        String password = passwordSign.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    Toast.makeText(SignInOrg.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent _intent = new Intent(SignInOrg.this, HomePageOrg.class);
                    startActivity(_intent);
                }else{
                    Toast.makeText(SignInOrg.this, "please verify your email address", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInOrg.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }}
