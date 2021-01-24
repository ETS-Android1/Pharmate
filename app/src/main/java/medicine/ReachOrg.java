package medicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import homepage.HomePage;
import models.DonatedMedicines;
import models.MedicineClass;
import models.ReceivedMedicines;

public class ReachOrg extends AppCompatActivity implements OnMapReadyCallback {

    private final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public MarkerOptions markerOptions;
    public CameraPosition cameraMapPosition;
    public Double latitude;
    public Double longitude;
    public String orgid, nameorg, medicineName, barcodeNumber, receiverUserID, medicineReceiveQuantity;
    public Integer inventoryQuantity;
    EditText name, city, phone, email, medicinename, barcodenumber, quantitiy, orgId, userId;
    MapView mapView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DocumentReference orgMedReference, donateMedicineRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reach_org);
        Map<String, Object> updateMedicineMap;
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.googleMap);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        name = findViewById(R.id.OrgName);
        city = findViewById(R.id.OrgCity);
        phone = findViewById(R.id.OrgContact);
        email = findViewById(R.id.OrgMail);
        medicinename = findViewById(R.id.medicine);
        barcodenumber = findViewById(R.id.barcodenum);
        quantitiy = findViewById(R.id.quantity);
//        userId=findViewById(R.id.userId);
//        orgId=findViewById(R.id.orgid);


        Intent intent = getIntent();
        medicineName = intent.getStringExtra("medicineName");
        medicinename.setText("Medicine Name: " + medicineName);
        medicinename.setEnabled(false);
        barcodeNumber = intent.getStringExtra("barcodeNumber");
        barcodenumber.setText("Barcode: " + barcodeNumber);
        barcodenumber.setEnabled(false);
        receiverUserID = intent.getStringExtra("userID");
//        userId.setText(receiverUserID);
//        userId.setEnabled(false);
        orgid = intent.getStringExtra("organizationID");
//        orgId.setText(orgid);
//        orgId.setEnabled(false);
        medicineReceiveQuantity = intent.getStringExtra("quantity");
        quantitiy.setText("Quantity: " + medicineReceiveQuantity);
        quantitiy.setEnabled(false);
        nameorg = intent.getStringExtra("organizationName");
        name.setText(nameorg);
        name.setEnabled(false);
        String cityname = intent.getStringExtra("city");
        city.setText(cityname);
        city.setEnabled(false);
        String phonenum = intent.getStringExtra("contact");
        phone.setText("Contact: " + phonenum);
        phone.setEnabled(false);
        String mail = intent.getStringExtra("email");
        email.setText("Email: " + mail);
        email.setEnabled(false);
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);
        System.out.println("Lat" + latitude);
        System.out.println("Long" + longitude);
        cameraMapPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude))
                .zoom(15)
                .build();
        markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude));


    }

    public void informClick(View view) {

        try {
            System.out.println("id" + orgid);
            System.out.println("barcode" + barcodeNumber);
            System.out.println("name" + nameorg);
            System.out.println("quantity" + medicineReceiveQuantity);
            DocumentReference orgMedReference = firebaseFirestore
                    .collection("organization"
                    ).document(orgid)
                    .collection("receivedMedicine").document(barcodeNumber);
            orgMedReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        ReceivedMedicines receivedMedicines = document.toObject(ReceivedMedicines.class);
                        if (document.exists()) {
                            System.out.println("Istenilen Ilac Envanterde Mevcut");
                            if (receivedMedicines.getQuantity() - Integer.parseInt(medicineReceiveQuantity) >= 0) {
                                orgMedReference.update("quantity", receivedMedicines.getQuantity() - Integer.parseInt(medicineReceiveQuantity))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                System.out.println("Ilac Basariyla Bagislandi ve Envanter Guncellendi");

                                                DocumentReference donatedMedicineRef = firebaseFirestore
                                                        .collection("organization")
                                                        .document(orgid)
                                                        .collection("donatedMedicine").document(barcodeNumber);

                                                donatedMedicineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot donatedMedicineSnap = task.getResult();
                                                            DonatedMedicines donatedToUsers = donatedMedicineSnap.toObject(DonatedMedicines.class);
                                                            if (donatedMedicineSnap.exists()) {
                                                                System.out.println("Bu Ilac Daha Once de Bagislanmis. Bagis miktari kadar sayi arttiriliyor.");

                                                                donatedMedicineRef.update("quantity", donatedToUsers.getQuantity() + Integer.parseInt(medicineReceiveQuantity));
                                                            } else {
                                                                System.out.println("Bu ilac ilk defa bagislaniyor. Veritabanina ekleniyor");
                                                                HashMap<String, Object> receiveMedicineMap = new HashMap<>();
                                                                ReceivedMedicines receivedMedicines = new ReceivedMedicines(receiverUserID, barcodeNumber, Integer.parseInt(medicineReceiveQuantity));
                                                                receiveMedicineMap.put("quantity", receivedMedicines.getQuantity());
                                                                receiveMedicineMap.put("lastDonatedBy", receivedMedicines.getUserID());


                                                                donatedMedicineRef.set(receiveMedicineMap).addOnSuccessListener(new OnSuccessListener<Void>() {

                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Intent intent = new Intent(ReachOrg.this, HomePage.class);
                                                                        DocumentReference medicineUpdateRef = firebaseFirestore.collection("medicine").document(barcodeNumber);
                                                                        medicineUpdateRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    DocumentSnapshot document = task.getResult();
                                                                                    MedicineClass medicineClass = document.toObject(MedicineClass.class);
                                                                                    if (document.exists()) {
                                                                                        System.out.println("Ilac Medicine Listesinde Var");
                                                                                        System.out.println("Ilac ismi" + medicineClass.getNameOfMedicine());
                                                                                        System.out.println("Ilac Quantity" + medicineClass.getQuantity());
                                                                                        System.out.println(Integer.parseInt(medicineReceiveQuantity));
                                                                                        medicineUpdateRef.update("quantity", medicineClass.getQuantity() - Integer.parseInt(medicineReceiveQuantity))
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        Intent intent = new Intent(ReachOrg.this, HomePage.class);
                                                                                                        alertView("Donation Successful. Organization will reach you as soon as possible.", "Donation Successful", intent);
                                                                                                        System.out.println("Ilac Medicine Listesinden de Azaltildi");
                                                                                                    }
                                                                                                });
                                                                                    } else {
                                                                                        System.out.println("ELSE");
                                                                                    }

                                                                                }
                                                                            }
                                                                        });

                                                                    }

                                                                }).addOnFailureListener(new OnFailureListener() {

                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(ReachOrg.this, "Error !" + e.getMessage(), Toast.LENGTH_LONG).show();
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                });

                                            }
                                        });
                            } else {
                                Intent intent = new Intent(ReachOrg.this, HomePage.class);
                                alertView("Insufficient Stocks. Please try again from a different organization", "Donation Failed", intent);
                            }
                        } else {
                            Intent intent = new Intent(ReachOrg.this, HomePage.class);
                            alertView("Insufficient Stocks. Please try again from a different organization", "Donation Failed", intent);
                            Toast.makeText(ReachOrg.this, "Insufficient Stocks", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            Intent intent = new Intent(ReachOrg.this, HomePage.class);
//            alertView("Your medicine is being prepared. For more information please contact with Organization.", "Donation Successful", intent);

        } catch (Exception e) {
            Toast.makeText(ReachOrg.this, "Error !" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void updateInventory(DocumentReference orgMedReference, DocumentReference donatedMedicineRef, String receiverID, String organizationID, String barcodeNumber, Integer quantity, Map<String, Object> receiveMedicineMap) {


    }

    public void send(View view, String medicineName, Integer quantity) {
        String message = "The Organization has been informed. They will reach you as soon as they can "
                + "for your request of " + "Medicine Name/n" +
                medicineName + " " + quantity;
        String subject = "Pharmate Medicine Request";

        String mail = email.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW
                , Uri.parse("mailto:" + mail));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
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
        CameraPosition cameraPosition = cameraMapPosition;
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(markerOptions);
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

    private void alertView(String alertMessage, String messageType, Intent intent) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReachOrg.this);
        dialog.setTitle(messageType)
                .setMessage(alertMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }

}