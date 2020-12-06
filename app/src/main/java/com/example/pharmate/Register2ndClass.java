package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Register2ndClass extends AppCompatActivity {
    ImageView backBtn;
    Button next,login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2nd_class);

        backBtn=findViewById(R.id.back_button);
        next=findViewById(R.id.signup_next_button);
        login=findViewById(R.id.signup_login_button);
        titleText=findViewById(R.id.signup_title_text);

    }

    public void callNextSigupScreen(View view) {
        Intent intent=new Intent(Register2ndClass.this,Register3rdClass.class);

        Pair[] pairs=new Pair[4];
        pairs[0]=new Pair<View,String>(backBtn,"transition_back_arrow_btn");
        pairs[1]=new Pair<View,String>(next,"transition_next_btn");
        pairs[2]=new Pair<View,String>(login,"transition_login_btn");
        pairs[3]=new Pair<View,String>(titleText,"transition_title_text");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Register2ndClass.this,pairs);
        startActivity(intent,options.toBundle());

    }

    public void callLoginFromSignUp(View view) {

    }
}