package location;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import models.OrganizationClass;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {


    private String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public LatLng latlng;
    MapView mapView;
    public LatLng myLocations;
    Button btn;
    OrganizationClass organizationClass;
    private FirebaseAuth firebaseAuth;
    private GoogleMap mMap;
    private CollectionReference locationReference;
    private RecyclerView recyclerView;
    private OrgLocationOptionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = (MapView) findViewById(R.id.googleMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


        locationReference = firebaseFirestore.collection("organization");
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.map_options_recyclerview);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        System.out.println(latlng);
        String id = firebaseUser.getUid();
        System.out.println(id);
        setUpRecyclerView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition
                .Builder().target(new LatLng(37.42342342342342, -122.08395287867832))
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.42342342342342, -122.08395287867832)));
    }

    private void setUpRecyclerView() {
        Query getLocations = locationReference;
        FirestoreRecyclerOptions<OrganizationClass> options = new FirestoreRecyclerOptions.Builder<OrganizationClass>()
                .setQuery(getLocations, OrganizationClass.class)
                .build();
        adapter = new OrgLocationOptionsAdapter(options);
        adapter.setOnItemClickListener(new OrgLocationOptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                organizationClass = documentSnapshot.toObject(OrganizationClass.class);
                GeoPoint myLocation = organizationClass.getLocation();
                myLocations = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                System.out.println(organizationClass.getEmail());
                System.out.println(organizationClass.getCity());
                System.out.println(organizationClass.getLocation());
                System.out.println(organizationClass.getContact());
                System.out.println(organizationClass.getOrganizationName());

                String id = documentSnapshot.getId();
                Toast.makeText(LocationActivity.this, "Click Calisiyor" + position, Toast.LENGTH_LONG).show();

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
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
    }


    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}