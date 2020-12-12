package organization;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class OrgInformationPage extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    EditText  OrgNameText,OrgContactText,OrgAddressText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_information_page);
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
        String id = firebaseUser.getUid();
        HashMap<String, Object> postUserData = new HashMap<>();
        postUserData.put("manager",orgtext);
        postUserData.put("province",orgcontact);
        postUserData.put("city",orgaddresstext);
        firebaseFirestore.collection("organization").document(id).set(postUserData);



    }
}