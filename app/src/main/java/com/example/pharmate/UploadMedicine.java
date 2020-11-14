package com.example.pharmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class UploadMedicine extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    EditText barcodeNo, name, amount, expirationDate, specialPrescription;
    HashMap<String, Object> postMedicineData = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_medicine);

        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

//        //defining textFields

        name = (EditText) findViewById(R.id.nameOfMedicineText);
        barcodeNo = (EditText) findViewById(R.id.barcodeNumberText);
        amount = (EditText) findViewById(R.id.amountText);
        expirationDate = (EditText) findViewById(R.id.expirationDateText);
        specialPrescription = (EditText) findViewById(R.id.prescriptionNoText);
    }

        public void uploadMedicineClick(View view){
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            String userID = firebaseUser.getUid();
            String displayName = firebaseUser.getDisplayName();
            String nameText = name.getText().toString();
            String barcodeNoText = barcodeNo.getText().toString();
            String amountText = amount.getText().toString();
            String expirationDateText = expirationDate.getText().toString();
            String specialPrescriptionText = specialPrescription.getText().toString();
            System.out.println("button pressed");
            System.out.println(displayName);
            String medicineName = name.getText().toString();

            postMedicineData.put("nameOfMedicine", nameText);
            postMedicineData.put("barcodeNumber", barcodeNoText);
            postMedicineData.put("amount", amountText);
            postMedicineData.put("expirationDate", expirationDateText);
            postMedicineData.put("specialPrescription", specialPrescriptionText);
            postMedicineData.put("donatedBy", userID);
            postMedicineData.put("donatedTo", "organizationName");

            firebaseFirestore.collection("medicine").
                    document(medicineName).
                    collection(barcodeNoText).document(displayName).set(postMedicineData);


        }
    }
