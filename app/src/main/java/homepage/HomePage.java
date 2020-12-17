package homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import medicine.RequestMedicine;
import medicine.SearchMedicine;
import medicine.UploadMedicine;
import organization.OrganizatonListPage;
import users.PersonalInformation;

public class HomePage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    Button signout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
//        signout=findViewById(R.id.button4);

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
        Intent _intent = new Intent(this, RequestMedicine.class);
        startActivity(_intent);
    }


}