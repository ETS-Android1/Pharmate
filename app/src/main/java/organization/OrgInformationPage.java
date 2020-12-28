package organization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmate.Loadingbar;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import homepage.HomePage;
import models.OrganizationClass;

public class OrgInformationPage extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    Button OrgSubmit;
    CardView cardView;
    EditText OrgNameText, OrgContactText, OrgAddressText;
    Button updateInfo;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_information_page);

        OrgSubmit = findViewById(R.id.OrgSubmit);
        cardView = findViewById(R.id.cardview);
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

        OrgNameText = findViewById(R.id.OrgNameText);
        OrgContactText = findViewById(R.id.OrgContactText);
        OrgAddressText = findViewById(R.id.OrgAddressText);
        updateInfo = findViewById(R.id.OrgSubmit);

        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("organization").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                OrgNameText.setText(value.getString("organizationName"));
                OrgContactText.setText(value.getString("contact"));
                OrgAddressText.setText(value.getString("city"));

            }
        });

    }

    public void submitOrgInfoClick(View view) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String orgtext = OrgNameText.getText().toString();
        String orgaddresstext = OrgAddressText.getText().toString();
        String orgcontact = OrgContactText.getText().toString();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(orgtext)
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

        String email = firebaseUser.getEmail();
        String id = firebaseUser.getUid();
        OrganizationClass organizationClass = new OrganizationClass(orgaddresstext, email, orgtext, orgcontact, null);
        HashMap<String, Object> postUserData = new HashMap<>();

        postUserData.put("organizatonName", organizationClass.getOrganizationName());
        postUserData.put("contact", organizationClass.getContact());
        postUserData.put("city", organizationClass.getCity());
        postUserData.put("email", organizationClass.getEmail());

        firebaseFirestore.collection("organization").document(id).update(postUserData);


    }

    public void UpdateOrgInfo(View view) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userEmail = firebaseUser.getEmail();

        String orgName = OrgNameText.getText().toString();
        String orgAdress = OrgAddressText.getText().toString();
        String orgContact = OrgContactText.getText().toString();

        HashMap<String, Object> postData = new HashMap<>();
        postData.put("organizationName", orgName);
        postData.put("city", orgAdress);
        postData.put("contact", orgContact);

        firebaseFirestore.collection("organization").document(userId).update(postData);

        Intent intent = new Intent(OrgInformationPage.this, HomePage.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }
}