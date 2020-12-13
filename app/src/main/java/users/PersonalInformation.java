package users;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import models.UserClass;

public class PersonalInformation extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    EditText userType, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;

    // KULLANICININ BU FORMU DOLDURDUĞUNU UYGULAMA BOYUNCA KONTROL EDİLMESİ GEREKİYOR.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_page);
        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

//        //defining textFields
        userType = findViewById(R.id.userTypeText);
        name = findViewById(R.id.personNameText);
        userSurname = findViewById(R.id.personSurnameText);
        userTurkishID = findViewById(R.id.turkishIdText);
        userContact = findViewById(R.id.personContactText);
        userAddress = findViewById(R.id.personAddressText);
        userBirthDate = findViewById(R.id.birthDateText);


    }

    public void submitPersonInfoClick(View v) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String nameText = name.getText().toString();
        String userSurnameText = userSurname.getText().toString();
        String userTurkishIDText = userTurkishID.getText().toString();
        String userAddressText = userAddress.getText().toString();
        String userContactText = userContact.getText().toString();
        String userTypeText = userType.getText().toString();
        String userBirthDayText = userBirthDate.getText().toString();


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameText+userSurnameText)
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = null;
                            UserClass userClass = document.toObject(UserClass.class);
                            System.out.println("Task Successful");
                        }
                    }
                });

        String userEmail = firebaseUser.getEmail();
        String userID = firebaseUser.getUid();

        UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userEmail, userTurkishIDText, userAddressText, userContactText, userBirthDayText, userTypeText);

        HashMap<String, Object> postUserData = new HashMap<>();


        postUserData.put("type", userClassToAdd.getType());
        postUserData.put("name", userClassToAdd.getName());
        postUserData.put("surname", userClassToAdd.getSurname());
        postUserData.put("turkishId", userClassToAdd.getTurkishId());
        postUserData.put("contact", userClassToAdd.getContact());
        postUserData.put("address", userClassToAdd.getAddress());
        postUserData.put("birthDate", userClassToAdd.getBirthdate());
        firebaseFirestore.collection("userType").document(userID).set(postUserData);
    }
    }






