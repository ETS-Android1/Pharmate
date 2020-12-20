package organization;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmate.Loadingbar;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import medicine.UploadMedicine;
import models.OrganizationClass;

public class OrgInformationPage extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    Button OrgSubmit;
    CardView cardView;
    EditText  OrgNameText,OrgContactText,OrgAddressText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_information_page);

        OrgSubmit=findViewById(R.id.OrgSubmit);
        cardView=findViewById(R.id.cardview);
        final Loadingbar loadingbar = new Loadingbar(OrgInformationPage.this);

        OrgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardView.setVisibility(View.GONE);

                    }
                }, 5000);
            }
        });
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        OrgNameText=findViewById(R.id.OrgNameText);
        OrgContactText=findViewById(R.id.OrgContactText);
        OrgAddressText=findViewById(R.id.OrgAddressText);

    }

    public void submitOrgInfoClick(View view) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String orgtext = OrgNameText.getText().toString();
        String orgaddresstext = OrgAddressText.getText().toString();
        String orgcontact = OrgContactText.getText().toString();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(orgtext+orgaddresstext)
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Task Successful");
                        }
                    }
                });
        OrganizationClass organizationClassToAdd = new OrganizationClass(orgtext, orgaddresstext, orgcontact);
        String id = firebaseUser.getUid();
        HashMap<String, Object> postUserData = new HashMap<>();
        postUserData.put("manager",organizationClassToAdd.getManager());
        postUserData.put("province",organizationClassToAdd.getProvince());
        postUserData.put("city",organizationClassToAdd.getCity());
        firebaseFirestore.collection("organization").document(id).set(postUserData);



    }
}