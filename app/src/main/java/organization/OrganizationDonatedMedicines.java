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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import models.OrganizationDonatedMedicineClass;

public class OrganizationDonatedMedicines extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OrganizationDonatedMedicineAdapter donatedMedicineAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_donated_medicines);
        recyclerView = findViewById(R.id.orgDonatedMedicineListRecyclerView);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();

        System.out.println(userID);

        CollectionReference orgDonatedMedicinesRef = db
                .collection("organization"
                ).document(userID)
                .collection("donatedMedicine");
        Query requestListQuery = orgDonatedMedicinesRef.whereNotEqualTo("barcodeNumber", "");

        FirestoreRecyclerOptions<OrganizationDonatedMedicineClass> options = new FirestoreRecyclerOptions.Builder<OrganizationDonatedMedicineClass>()
                .setQuery(requestListQuery, OrganizationDonatedMedicineClass.class)
                .build();

        donatedMedicineAdapter = new OrganizationDonatedMedicineAdapter(options);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(donatedMedicineAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        donatedMedicineAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        donatedMedicineAdapter.stopListening();

    }
}