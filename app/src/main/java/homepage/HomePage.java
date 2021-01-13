package homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmate.MainActivity;
import com.example.pharmate.R;
import com.example.pharmate.ResetPasswrd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import medicine.RequestMedicineList;
import medicine.SearchMedicine;
import medicine.UploadMedicine;
import organization.OrganizatonListPage;
import users.PersonalInformation;
import static users.PersonalInformation.imageUri;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private CardView donateMed, searchMed, requestMed, listOrg, about, profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        signout=findViewById(R.id.button4);
        donateMed = findViewById(R.id.donate);
        searchMed = findViewById(R.id.search);
        requestMed = findViewById(R.id.request);
        listOrg = findViewById(R.id.organization);
        about = findViewById(R.id.about);
        profile = findViewById(R.id.profile);
        String userName = firebaseUser.getDisplayName();
        System.out.println(userName);

        if (userName != null) {
            TextView userText = (TextView) findViewById(R.id.userNameText1);
            userText.setText(userName);
        } else {
            TextView userText = (TextView) findViewById(R.id.userNameText1);
            userText.setText("User");
        }






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
            case R.id.donate:
//                if(imageUri==null){
                    Toast.makeText(this,"Please upload image ",Toast.LENGTH_LONG).show();
//                }else{
                i = new Intent(this, UploadMedicine.class);
                startActivity(i);
                break;
            case R.id.search:
               if (imageUri == null) {
                    Toast.makeText(this, "Please upload image ", Toast.LENGTH_LONG).show();
                } else {
                    i = new Intent(this, SearchMedicine.class);
                    startActivity(i);
                }
                break;
            case R.id.request:
                i = new Intent(this, RequestMedicineList.class);
                startActivity(i);
                break;
            case R.id.organization:
                i = new Intent(this, OrganizatonListPage.class);
                startActivity(i);
                break;
            case R.id.about:
                i = new Intent(this, AboutPage.class);
                startActivity(i);
                break;
            case R.id.profile:
                i = new Intent(this, PersonalInformation.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }


//    public void goRequestMedicineList(View v) {
//        Intent _intent = new Intent(this, RequestMedicineList.class);
//        startActivity(_intent);
//    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomePage.this, MainActivity.class));
        Toast.makeText(HomePage.this, "LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                Logout();
                break;
            }
            case R.id.reset:{
                resetPassword();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetPassword() {
      Intent intent=new Intent(HomePage.this, ResetPasswrd.class);
      startActivity(intent);

    }


}