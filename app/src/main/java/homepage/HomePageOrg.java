package homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;

import medicine.RequestMedicineList;
import medicine.SearchMedicine;
import medicine.UploadMedicine;
import organization.OrgInformationPage;
import organization.OrganizatonListPage;

public class HomePageOrg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_org);
    }

    public void goDonateMedicineClick(View view) {
        Intent _intent = new Intent(this, UploadMedicine.class);
        startActivity(_intent);
    }

    public void goOrganizationInformationPageClick(View view) {
        Intent _intent = new Intent(this, OrgInformationPage.class);
        startActivity(_intent);
    }

    public void goOrganizationClick(View view) {
        Intent _intent = new Intent(this, OrganizatonListPage.class);
        startActivity(_intent);
    }

    public void goSearchMedicineClick(View view) {
        Intent _intent = new Intent(this, SearchMedicine.class);
        startActivity(_intent);
    }

    public void goAboutPageClick(View view) {
    }

    public void goRequestedMedicinePageClick(View v) {
        Intent _intent = new Intent(this, RequestMedicineList.class);
        startActivity(_intent);
    }

}