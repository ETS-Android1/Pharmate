package com.example.pharmate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswrd extends AppCompatActivity {
    EditText passwordnew,passwordcurrent;
    Button update;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwrd);

        passwordnew=findViewById(R.id.newpassword);
        passwordcurrent=findViewById(R.id.currentpassword);
        update=findViewById(R.id.updatepassword);
        firebaseAuth = FirebaseAuth.getInstance();



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword=passwordcurrent.getText().toString().trim();
                String newPassword=passwordnew.getText().toString().trim();
                if (TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(getApplicationContext(),"Enter your current password",Toast.LENGTH_LONG).show();
                }
                if(newPassword.length()<6){
                    Toast.makeText(getApplicationContext(),"Your password too short",Toast.LENGTH_LONG).show();
                }
                updatePassword(oldPassword,newPassword);

            }
        });
    }

    private void updatePassword(String oldPassword, String newPassword) {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        AuthCredential authCredential= EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        Toast.makeText(getApplicationContext(),"Password updated...",Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
Toast.makeText(getApplicationContext(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}