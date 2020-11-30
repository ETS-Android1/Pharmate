package com.example.pharmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpOrg extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    EditText emailText, passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_org);

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.signUpEmail);
        passwordText = findViewById(R.id.signUpPasswrd);
    }

    public void signUp(View view) {
        System.out.println("button pressed");
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        // bir ust satirda kullanicidan aldigimiz sifreyi asagidaki firebase'e gondermeden once
        // validation islemlerini yapacagiz


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseAuth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignUpOrg.this,"please check your email", Toast.LENGTH_LONG).show();
                                            // Intent intent = new Intent(SignUp.this, HomePage.class);
                                            // startActivity(intent);
                                            //finish();
                                        }else{
                                            Toast.makeText(SignUpOrg.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpOrg.this, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void VerifySign(View view) {
        Intent intent=new Intent(SignUpOrg.this,SignInOrg.class);
        startActivity(intent);
    }
}