package organization;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import models.OrganizationReceivedMedicineClass;

public class OrganizationReceivedMedicines extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OrganizationReceivedMedicineAdapter receivedMedicineAdapter;
    RecyclerView recyclerView;
    private CollectionReference orgReceivedMedicineRef;
    private DocumentReference orgReceivedMedicinesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_received_medicines);
        recyclerView = findViewById(R.id.orgReceivedMedicineListRecyclerView);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();

        System.out.println(userID);

        CollectionReference orgReceivedMedicineRef = db
                .collection("organization"
                ).document(userID)
                .collection("receivedMedicine");
        Query requestListQuery = orgReceivedMedicineRef.whereNotEqualTo("barcodeNumber", "");

        FirestoreRecyclerOptions<OrganizationReceivedMedicineClass> options = new FirestoreRecyclerOptions.Builder<OrganizationReceivedMedicineClass>()
                .setQuery(requestListQuery, OrganizationReceivedMedicineClass.class)
                .build();

        receivedMedicineAdapter = new OrganizationReceivedMedicineAdapter(options);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(receivedMedicineAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receivedMedicineAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        receivedMedicineAdapter.stopListening();

    }
}