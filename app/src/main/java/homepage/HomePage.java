package homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.MainActivity;
import com.example.pharmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import medicine.RequestMedicineList;
import medicine.SearchMedicine;
import medicine.UploadMedicine;
import organization.OrganizatonListPage;
import users.PersonalInformation;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    Button signout,profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
//        signout=findViewById(R.id.button4);
        profile=findViewById(R.id.profilebutton);

//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseAuth.signOut();
//                finish();
//                startActivity(new Intent(HomePage.this, MainActivity.class));
//            }
//        });
    }


    public void goPersonalInformationPageClick(View v) {

        Intent _intent = new Intent(this, PersonalInformation.class);
        startActivity(_intent);
    }

    public void goDonateMedicinePageClick(View v) {
        Intent _intent = new Intent(this, UploadMedicine.class);
        startActivity(_intent);
    }

    public void goSearchMedicinePageClick(View v) {
        Intent _intent = new Intent(this, SearchMedicine.class);
        startActivity(_intent);
    }

    public void goOrganizationPageClick(View v) {
        Intent _intent = new Intent(this, OrganizatonListPage.class);
        startActivity(_intent);
    }

    public void goAboutPageClick(View v) {
        Intent _intent = new Intent(this, AboutPage.class);
        startActivity(_intent);
    }

    public void goRequestMedicinePageClick(View v) {
        Intent _intent = new Intent(this, RequestMedicineList.class);
        startActivity(_intent);
    }

//    public void goRequestMedicineList(View v) {
//        Intent _intent = new Intent(this, RequestMedicineList.class);
//        startActivity(_intent);
//    }

    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePage.this, MainActivity.class));
        Toast.makeText(HomePage.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}