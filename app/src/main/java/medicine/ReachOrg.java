package medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import homepage.HomePage;

public class ReachOrg extends AppCompatActivity implements OnMapReadyCallback {

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public Double latitude;
    public Double longitude;
    EditText name, city, phone, email;
    String userID;
    ImageView icon;
    MapView mapView;
    LatLng orgLocation;
    private FirebaseAuth firebaseAuth;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_org);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.googleMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        firebaseAuth = FirebaseAuth.getInstance();


        name = findViewById(R.id.OrgName);
        city = findViewById(R.id.OrgCity);
        phone = findViewById(R.id.OrgContact);
        email = findViewById(R.id.OrgMail);

        Intent intent = getIntent();
        String nameorg = intent.getStringExtra("organizationName");
        name.setText(nameorg);
        name.setEnabled(false);
        String cityname = intent.getStringExtra("city");
        city.setText(cityname);
        city.setEnabled(false);
        String phonenum = intent.getStringExtra("contact");
        phone.setText(phonenum);
        phone.setEnabled(false);
        String mail = intent.getStringExtra("email");
        email.setText(mail);
        email.setEnabled(false);
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        System.out.println("Lat" + latitude);
        System.out.println("Long" + longitude);

    }

    public void informClick(View view) {
        updateMedicineInventory();
        Intent intent = new Intent(ReachOrg.this, HomePage.class);
        Toast.makeText(this, "The organization has been informed", Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    private void updateMedicineInventory() {
    }

    public void send(View view) {
        String mail = email.getText().toString();
        Intent intent = new Intent(ReachOrg.this, SendEmail.class);
        intent.putExtra("email", mail);
        startActivity(intent);
    }

    public void callClick(View view) {
        String phoneNum = phone.getText().toString();
        Intent intent = new Intent(ReachOrg.this, CallPhone.class);
        intent.putExtra("contact", phoneNum);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraPosition cameraPosition = new CameraPosition
                .Builder().target(new LatLng(latitude, longitude))
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(longitude, -longitude)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

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