

package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class Register3rdClass extends AppCompatActivity {
    ScrollView scrollView;
    EditText phone;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3rd_class);
        scrollView = findViewById(R.id.scrollView);
        phone = findViewById(R.id.personContactText);
        countryCodePicker = findViewById(R.id.countrypicker);

    }


    public void next(View view) {
        if (!validatePhoneNumber()) {
            return;

        }
        String usertype = getIntent().getStringExtra("type");
        String name = getIntent().getStringExtra("name");
        String userSurname = getIntent().getStringExtra("surname");
        String userTurkishID = getIntent().getStringExtra("turkishID");
        String email = getIntent().getStringExtra("email");
        String userAddress = getIntent().getStringExtra("address");
        String password = getIntent().getStringExtra("password");

        String getUserEnteredPhoneNumber = phone.getText().toString().trim();
        String phone = "+" + countryCodePicker.getFullNumber() + getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        intent.putExtra("type", usertype);
        intent.putExtra("name", name);
        intent.putExtra("surname", userSurname);
        intent.putExtra("turkishID", userTurkishID);
        intent.putExtra("email", email);
        intent.putExtra("address", userAddress);
        intent.putExtra("password", password);
        intent.putExtra("contact", phone);




        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }


    }

    private boolean validatePhoneNumber() {
        Toast.makeText(this, "fsgdhd", Toast.LENGTH_SHORT).show();
        return true;
    }
}
