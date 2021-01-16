package users;

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

import models.UserDonatedMedicineClass;

public class UserDonatedMedicines extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DonatedMedicineAdapter donatedMedicineAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_donated_medicines);
        recyclerView = findViewById(R.id.donatedMedicineListRecyclerView);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();

        System.out.println(userID);

        CollectionReference userDonatedMedicinesRef = db
                .collection("user"
                ).document(userID)
                .collection("donatedMedicine");
        Query requestListQuery = userDonatedMedicinesRef.whereNotEqualTo("barcodeNumber", "");

        FirestoreRecyclerOptions<UserDonatedMedicineClass> options1 = new FirestoreRecyclerOptions.Builder<UserDonatedMedicineClass>()
                .setQuery(requestListQuery, UserDonatedMedicineClass.class)
                .build();

        donatedMedicineAdapter = new DonatedMedicineAdapter(options1);


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