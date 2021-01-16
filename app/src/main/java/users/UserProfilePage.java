package users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;

public class UserProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);
    }

    public void goToProfilePage(View v) {
        Intent intent = new Intent(this, PersonalInformation.class);
        startActivity(intent);
    }

    public void goReceivedMedicineList(View v) {
        Intent intent = new Intent(this, UserReceivedMedicines.class);
        startActivity(intent);
    }

    public void donatedMedicineList(View v) {
        Intent intent = new Intent(this, UserDonatedMedicines.class);
        startActivity(intent);
    }
}