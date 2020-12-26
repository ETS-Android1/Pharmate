package medicine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import models.RequestClass;

public class RequestMedicineList extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RequestMedicineListAdapter requestMedicineListAdapter;
    RecyclerView recyclerView;
    private CollectionReference requestListReference = db.collection("requestedMedicine");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicinerequest_list);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println(userID);
        Query requestQuery = requestListReference.whereEqualTo("requestedBy", "OmZYn4AFUQfB6VgkQn0E8nN9r7t2").orderBy("requestedBy");

        FirestoreRecyclerOptions<RequestClass> options = new FirestoreRecyclerOptions.Builder<RequestClass>()
                .setQuery(requestQuery, RequestClass.class)
                .build();


        requestMedicineListAdapter = new RequestMedicineListAdapter(options);

        recyclerView = findViewById(R.id.request_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(requestMedicineListAdapter);

        requestMedicineListAdapter.setOnItemClickListener(new RequestMedicineListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                RequestClass requestClass = documentSnapshot.toObject(RequestClass.class);
                String id = documentSnapshot.getId();
            }
        });


    }

}
