package organization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;

public class OrganizationProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_profile_page);
    }

    public void goToOrgProfilePage(View v) {
        Intent intent = new Intent(this, OrgInformationPage.class);
        startActivity(intent);
    }

    public void goOrgReceivedMedicineList(View v) {
        Intent intent = new Intent(this, OrganizationReceivedMedicines.class);
        startActivity(intent);
    }

    public void goOrgDonatedMedicineList(View v) {
        Intent intent = new Intent(this, OrganizationDonatedMedicines.class);
        startActivity(intent);
    }
}