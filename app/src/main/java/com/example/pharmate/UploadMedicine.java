//package com.example.pharmate;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.util.HashMap;
//
//public class UploadMedicine extends AppCompatActivity {
//    private FirebaseStorage firebaseStorage;
//    private StorageReference storageReference;
//    private FirebaseFirestore firebaseFirestore;
//    private FirebaseAuth firebaseAuth;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_medicine);

//        // Instance
//        firebaseStorage = FirebaseStorage.getInstance();
//        storageReference = firebaseStorage.getReference();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//
//        EditText barcodeNo, name, amount, expirationDate, specialPrescription;
//        Button submitButton;
//
//
//        //defining textFields
//        userType = (EditText) findViewById(R.id.userTypeText);
//        name = (EditText) findViewById(R.id.personNameText);
//        userSurname = (EditText) findViewById(R.id.personSurnameText);
//        userTurkishID = (EditText) findViewById(R.id.personTurkishIDText);
//        userContact = (EditText) findViewById(R.id.personContactText);
//        userAddress = (EditText) findViewById(R.id.personAddressText);
//        submitButton = (Button) findViewById(R.id.personInfoSubmitButton);
//
//        if (firebaseUser  != null) {
//            String userEmail = firebaseUser.getEmail();
//            String userID = firebaseUser.getUid();
//
//            HashMap<String, Object> postData = new HashMap<>();
//            postData.put("type", userType);
//            postData.put("userEmail", userEmail);
//            postData.put("name", name);
//            postData.put("surname", userSurname);
//            postData.put("turkishId", userTurkishID);
//            postData.put("contact", userContact);
//            postData.put("address", userAddress);
//            firebaseFirestore.collection("userType").document(userID).set(postData);
//        }
//    }
//}