package location;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import medicine.ReachOrg;
import models.OrganizationClass;

public class LocationActivity extends AppCompatActivity {


    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public LatLng latlng;
    public String medicineName, barcodeNumber, receiverUserID, medicineReceiveQuantity;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private OrgLocationOptionsAdapter adapter;
    private CollectionReference locationReference, medicineInventoryRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        recyclerView = findViewById(R.id.map_options_recyclerview);
        setUpRecyclerView();

        Intent receiveMedicineIntent = getIntent();
        medicineName = receiveMedicineIntent.getStringExtra("nameOfMedicine");
        barcodeNumber = receiveMedicineIntent.getStringExtra("barcodeNumber");
        medicineReceiveQuantity = receiveMedicineIntent.getStringExtra("quantity");
        receiverUserID = receiveMedicineIntent.getStringExtra("userID");

    }

    private void setUpRecyclerView() {
        locationReference = firebaseFirestore.collection("organization");
        Query getLocations = locationReference;
        FirestoreRecyclerOptions<OrganizationClass> options = new FirestoreRecyclerOptions.Builder<OrganizationClass>()
                .setQuery(getLocations, OrganizationClass.class)
                .build();
        adapter = new OrgLocationOptionsAdapter(options);
        adapter.setOnItemClickListener(new OrgLocationOptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                OrganizationClass organizationClass = documentSnapshot.toObject(OrganizationClass.class);
                String id = documentSnapshot.getId();
                GeoPoint location = organizationClass.getLocation();
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                System.out.println("orgIDLocation" + organizationClass.getOrgID());
                receiverUserID = organizationClass.getOrgID();

                Intent intent = new Intent(LocationActivity.this, ReachOrg.class);
                intent.putExtra("organizationName", organizationClass.getOrganizationName());
                intent.putExtra("contact", organizationClass.getContact());
                intent.putExtra("email", organizationClass.getEmail());
                intent.putExtra("city", organizationClass.getCity());
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("medicineName", medicineName);
                intent.putExtra("barcodeNumber", barcodeNumber);
                intent.putExtra("quantity", medicineReceiveQuantity);
                intent.putExtra("userID", receiverUserID);
                intent.putExtra("organizationID", receiverUserID);
                startActivity(intent);


            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}