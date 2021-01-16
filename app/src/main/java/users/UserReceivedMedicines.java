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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import models.UserReceivedMedicineClass;

public class UserReceivedMedicines extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserReceivedMedicineAdapter receivedMedicineAdapter;
    RecyclerView recyclerView;
    private CollectionReference userReceivedMedicineRef;
    private DocumentReference userReceivedMedicinesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_received_medicines);
        recyclerView = findViewById(R.id.receivedMedicineListRecyclerView);
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
                .collection("receivedMedicine");
        Query requestListQuery = userDonatedMedicinesRef.whereNotEqualTo("barcodeNumber", "");

        FirestoreRecyclerOptions<UserReceivedMedicineClass> options1 = new FirestoreRecyclerOptions.Builder<UserReceivedMedicineClass>()
                .setQuery(requestListQuery, UserReceivedMedicineClass.class)
                .build();

        receivedMedicineAdapter = new UserReceivedMedicineAdapter(options1);


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