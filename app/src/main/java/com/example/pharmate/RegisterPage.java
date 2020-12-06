package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterPage extends AppCompatActivity {
    ImageView backBtn;
    Button next,login;
    TextView titleText;
    EditText userType, name, userSurname, userTurkishID, email, userAddress, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        backBtn=findViewById(R.id.back_button);
        next=findViewById(R.id.signup_next_button);
        login=findViewById(R.id.signup_login_button);
        titleText=findViewById(R.id.signup_title_text);

        userType = findViewById(R.id.userTypeText);
        name = findViewById(R.id.personNameText);
        userSurname = findViewById(R.id.personSurnameText);
        userTurkishID = findViewById(R.id.turkishIdText);
        email = findViewById(R.id.signUpEmailText);
        userAddress = findViewById(R.id.personAddressText);
        password = findViewById(R.id.signUpPasswordText);
    }

    public void callLoginFromSignUp(View view) {
    }

    public void callNextSigupScreen(View view) {
Intent intent=new Intent(RegisterPage.this,Register2ndClass.class);

        Pair[] pairs=new Pair[4];
        pairs[0]=new Pair<View,String>(backBtn,"transition_back_arrow_btn");
        pairs[1]=new Pair<View,String>(next,"transition_next_btn");
        pairs[2]=new Pair<View,String>(login,"transition_login_btn");
        pairs[3]=new Pair<View,String>(titleText,"transition_title_text");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(RegisterPage.this,pairs);
        startActivity(intent,options.toBundle());

    }
}