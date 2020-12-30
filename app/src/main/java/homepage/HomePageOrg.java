package homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmate.MainActivity;
import com.example.pharmate.R;
import com.google.firebase.auth.FirebaseAuth;

import medicine.RequestMedicineList;
import medicine.SearchMedicine;
import medicine.UploadMedicine;
import organization.OrgInformationPage;
import organization.OrganizatonListPage;
import users.PersonListPage;
import users.PersonalInformation;

public class HomePageOrg extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;

    private CardView donateMed, searchMed, requestMed, listOrg,about,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_org);

        donateMed=findViewById(R.id.donate1);
        searchMed=findViewById(R.id.search1);
        requestMed=findViewById(R.id.request1);
        listOrg=findViewById(R.id.organization1);
        about=findViewById(R.id.about1);
        profile=findViewById(R.id.profile1);


        donateMed.setOnClickListener((View.OnClickListener) this);
        searchMed.setOnClickListener((View.OnClickListener) this);
        requestMed.setOnClickListener((View.OnClickListener) this);
        listOrg.setOnClickListener((View.OnClickListener) this);
        about.setOnClickListener((View.OnClickListener) this);
        profile.setOnClickListener((View.OnClickListener) this);
    }
    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.donate1:
                i = new Intent(this, UploadMedicine.class);
                startActivity(i);
                break;
            case R.id.search1:
                i = new Intent(this, SearchMedicine.class);
                startActivity(i);
                break;
            case R.id.request1:
                i = new Intent(this, RequestMedicineList.class);
                startActivity(i);
                break;
            case R.id.organization1:
                i = new Intent(this, PersonListPage.class);
                startActivity(i);
                break;
            case R.id.about1:
                i = new Intent(this, AboutPage.class);
                startActivity(i);
                break;
            case R.id.profile1:
                i = new Intent(this, OrgInformationPage.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }



    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePageOrg.this, MainActivity.class));
        Toast.makeText(HomePageOrg.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

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