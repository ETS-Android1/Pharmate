package com.example.pharmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    // Defining Firebase Instance Variables
    private FirebaseAuth firebaseAuth;
    EditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // defining firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        // defining email and password text
        emailText = findViewById(R.id.signInEmailText);
        passwordText = findViewById(R.id.signInPasswordText);

        // Getting current user
        // if exists user will directly access for homepage
        // else login register page
        //  FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //if (firebaseUser != null) {
        // Intent _intent = new Intent(SignIn.this, HomePage.class);
        //  startActivity(_intent);
        //finish();
        //   System.out.println(firebaseUser.getUid());

        //}


    }


    public void signInClick(View v) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(authResult -> {
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent _intent = new Intent(SignIn.this, HomePage.class);
                startActivity(_intent);
            }else{
                Toast.makeText(SignIn.this, "please verify your email address", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

