package com.example.pharmate;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText userEmail;
    Button userPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        userEmail = findViewById(R.id.etUserEmail);
        userPass = findViewById(R.id.buttonForgetPass);

        firebaseAuth = FirebaseAuth.getInstance();

        userPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                AlertDialog.Builder alert = new AlertDialog.Builder(ForgetPassword.this);
                                alert.setTitle("Information");
                                alert.setMessage("Do you want to change your password?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ForgetPassword.this,
                                                    "Password send to your email", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ForgetPassword.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(ForgetPassword.this,
                                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    }

                                });
                                alert.create().show();
                            }
                        });
            }
        });
    }
}
