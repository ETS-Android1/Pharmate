package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText mailText,passwordText;
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_main);

        mailText=findViewById(R.id.Mailtext);
        passwordText=findViewById(R.id.passwordText);
        login=findViewById(R.id.login);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usertype, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        if(i==1){
          Intent intent=new Intent(SignMain.this,HomePage.class);
          startActivity(intent);
        }if(i==2){
            Intent intent=new Intent(SignMain.this,HomePageOrg.class);
            startActivity(intent);
        }else{
            Toast.makeText(SignMain.this,"Choose",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public void signIn(View view) {
    }
}


