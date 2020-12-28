package medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pharmate.R;
import com.google.android.material.snackbar.Snackbar;

import homepage.HomePage;
import models.OrganizationClass;

public class ReachOrg extends AppCompatActivity {

    EditText name,city,phone,email,location;
    String userID;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_org);

        name=findViewById(R.id.OrgName);
        city=findViewById(R.id.OrgCity);
        phone=findViewById(R.id.OrgContact);
        email=findViewById(R.id.OrgMail);
        icon=findViewById(R.id.orgimage);

        Intent intent=getIntent();
        String nameorg = intent.getStringExtra("organizationName");
        name.setText(nameorg);
        name.setEnabled(false);
        String cityname = intent.getStringExtra("city");
        city.setText(cityname);
        city.setEnabled(false);
        String phonenum = intent.getStringExtra("contact");
        phone.setText(phonenum);
       phone.setEnabled(false);
        String mail = intent.getStringExtra("email");
        email.setText(mail);
        email.setEnabled(false);


        userID = intent.getStringExtra("userID");
        System.out.println(userID);

    }

    public void informClick(View view) {
        Intent intent=new Intent(ReachOrg.this, HomePage.class);
        Toast.makeText(this,"The organization has been informed",Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    public void send(View view) {
        String mail=email.getText().toString();
        Intent intent=new Intent(ReachOrg.this,SendEmail.class);
        intent.putExtra("email",mail);
        startActivity(intent);
    }

    public void callClick(View view) {
        String phoneNum=phone.getText().toString();
        Intent intent=new Intent(ReachOrg.this,CallPhone.class);
        intent.putExtra("contact",phoneNum);
        startActivity(intent);
    }
}