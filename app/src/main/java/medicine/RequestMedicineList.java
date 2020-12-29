package medicine;

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

import models.RequestClass;

public class RequestMedicineList extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RequestMedicineListAdapter requestMedicineListAdapter;
    RecyclerView recyclerView;
    private CollectionReference requestListReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicinerequest_list);
        recyclerView = findViewById(R.id.medicine_request_recyclerview);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        requestListReference = db.collection("requestedMedicine");
        Query requestListQuery = requestListReference;

        FirestoreRecyclerOptions<RequestClass> options1 = new FirestoreRecyclerOptions.Builder<RequestClass>()
                .setQuery(requestListQuery, RequestClass.class)
                .build();

        requestMedicineListAdapter = new RequestMedicineListAdapter(options1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(requestMedicineListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestMedicineListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestMedicineListAdapter.stopListening();

    }

}
