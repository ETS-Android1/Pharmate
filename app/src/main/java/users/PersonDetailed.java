package users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pharmate.R;

import homepage.HomePage;
import homepage.HomePageOrg;
import medicine.CallPhone;
import medicine.ReachOrg;

public class PersonDetailed extends AppCompatActivity {

    EditText name, surname, phone, address;
    String userID;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detailed);

        name = findViewById(R.id.PersonName);
        surname = findViewById(R.id.PersonSurname);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.Adress);


        Intent intent = getIntent();
        String nameprsn = intent.getStringExtra("name");
        name.setText(nameprsn);
        name.setEnabled(false);
        String surnameprsn = intent.getStringExtra("surname");
        surname.setText(surnameprsn);
        surname.setEnabled(false);
        String phonenum = intent.getStringExtra("contact");
        phone.setText(phonenum);
        phone.setEnabled(false);
        String addressprsn = intent.getStringExtra("address");
        address.setText(addressprsn);
        address.setEnabled(false);
    }

    public void inform(View view) {
        Intent intent = new Intent(PersonDetailed.this, HomePageOrg.class);
        Toast.makeText(this, "The person has been informed", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

   // public void callClick(View view) {
     //   String phoneNum = phone.getText().toString();
       // Intent intent = new Intent(PersonDetailed.this, CallPhone.class);
       // intent.putExtra("contact", phonenum);
      //  startActivity(intent);
    //}
}