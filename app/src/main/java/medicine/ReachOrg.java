package medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pharmate.R;

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
}