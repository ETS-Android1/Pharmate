package com.example.pharmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText userEmail;
    Button userPass;
    TextView goFrag1, goFrag2;


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        userEmail = findViewById(R.id.etUserEmail);
        userPass = findViewById(R.id.buttonForgetPass);
        goFrag1 = findViewById(R.id.BackTo);
        goFrag2 = findViewById(R.id.BackTo);

        firebaseAuth = FirebaseAuth.getInstance();

        userPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPassword.this,
                                            "Password send to your email", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgetPassword.this,
                                            task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }

        });

        goFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this, goFrag1.getClass());
                startActivity(intent);
                finish();
            }
        });

        goFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this, goFrag2.getClass());
                startActivity(intent);
                finish();
            }
        });

    }



}