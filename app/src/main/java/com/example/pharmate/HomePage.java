package com.example.pharmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



    }
   public void goPersonalInformationPageClick(View v){Intent _intent = new Intent(this, PersonalInformation.class);
       startActivity(_intent);}
    public void goDonateMedicinePageClick(View v){Intent _intent = new Intent(this, UploadMedicine.class);
        startActivity(_intent);}
    public void goSearchMedicinePageClick(View v){Intent _intent = new Intent(this, SearchMedicine.class);
        startActivity(_intent);}
    public void goOrganizationPageClick(View v){Intent _intent = new Intent(this, OrganizatonListPage.class);
        startActivity(_intent);}






}