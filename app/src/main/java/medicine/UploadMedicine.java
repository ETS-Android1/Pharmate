package medicine;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.DonatedMedicines;
import models.MedicineClass;

public class UploadMedicine extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "UploadMedicine";
    public String organizationID;
    public String organizationName;
    public List<String> organizationNames;
    public List<String> orgIDs;
    AwesomeValidation awesomeValidation;
    EditText barcodeNo, quantity, name;
    ProgressBar progressBar;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DocumentReference documentReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView expirationDate;
    private DatePickerDialog.OnDateSetListener nOnDateSetListener;
    private Spinner spinner;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_medicine);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference organizationReference = firebaseFirestore.collection("organization");
        organizationNames = new ArrayList<>();
        orgIDs = new ArrayList<>();

        // ID's
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        uploadButton = findViewById(R.id.uploadMedicineButton);


        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //defining textFields

        name = findViewById(R.id.nameOfMedicineText);
        barcodeNo = findViewById(R.id.barcodeNumberText);
        quantity = findViewById(R.id.amountText);
        expirationDate = findViewById(R.id.ExpirationDateText);


        expirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UploadMedicine.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        nOnDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        nOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                expirationDate.setText(date);
            }
        };
        awesomeValidation.addValidation(UploadMedicine.this, R.id.nameOfMedicineText, "[a-zA-Z\\s]+", R.string.medicinenameerror);
        awesomeValidation.addValidation(UploadMedicine.this, R.id.barcodeNumberText, "[0-9]+", R.string.barcoderror);
        awesomeValidation.addValidation(UploadMedicine.this, R.id.amountText, "[0-9]+", R.string.amounterror);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UploadMedicine.this,
                android.R.layout.simple_spinner_item, organizationNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        organizationReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String names = document.getString("organizationName");
                        String ids = document.getString("orgID");
                        organizationNames.add(names);
                        orgIDs.add(ids);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        for (int i = 0; i < organizationNames.size(); i++) {
            if (position == i) {
                organizationName = organizationNames.get(i);
                organizationID = orgIDs.get(i);
                System.out.println(organizationName);
                System.out.println(organizationID);
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    public void uploadMedicineClick(View view) {
        uploadButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if (awesomeValidation.validate()) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            String userID = firebaseUser.getUid();
            String displayName = firebaseUser.getDisplayName();
            String nameText = name.getText().toString();
            if (TextUtils.isEmpty(nameText)) {
                Toast.makeText(UploadMedicine.this, "not empty", Toast.LENGTH_SHORT).show();
                return;
            }
            String barcodeNoText = barcodeNo.getText().toString();
            String expirationDateText = expirationDate.getText().toString();
            Integer quantityText = Integer.parseInt(String.valueOf(quantity.getText()));
            System.out.println("button pressed");
            System.out.println(displayName);
            String medicineName = name.getText().toString();

            Map<String, Object> orgMedicineMap = new HashMap<>();
            Map<String, Object> medicineMap = new HashMap<>();


            updateMedicineInventory(userID, organizationID, barcodeNoText, quantityText, orgMedicineMap);
            uploadMedicineToDB(nameText, userID, organizationID, quantityText, barcodeNoText, expirationDateText, medicineMap);

        }
    }

    private void uploadMedicineToDB(String nameText, String userID, String organizationID, Integer quantityText, String barcodeNoText, String expirationDateText, Map<String, Object> medicineMap) {
        DocumentReference documentReference = firebaseFirestore.collection("medicine").document(barcodeNoText);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    MedicineClass medicineClass = document.toObject(MedicineClass.class);
                    if (document.exists()) {
                        System.out.println("Dosya var");
                        documentReference.update("quantity", medicineClass.getQuantity() + quantityText)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("Quantity has been updated");
                                        progressBar.setVisibility(View.GONE);
                                        alertView("Your Medicine Has Been Successfully Added to Inventory Of Organization.", "Donation Failed");
                                        Toast.makeText(UploadMedicine.this, "Medicine added to Inventory", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    } else {

                        fixStrings(nameText);
                        fixStrings(barcodeNoText);
                        MedicineClass medicineClassToAdd = new MedicineClass(nameText, userID, organizationID, quantityText, barcodeNoText, expirationDateText);

                        medicineMap.put("nameOfMedicine", medicineClassToAdd.getNameOfMedicine());
                        medicineMap.put("barcodeNumber", medicineClassToAdd.getBarcodeNumber());
                        medicineMap.put("donatedBy", medicineClassToAdd.getDonatedBy());
                        medicineMap.put("lastDonationTo", medicineClassToAdd.getDonatedTo());
                        medicineMap.put("quantity", medicineClassToAdd.getQuantity());
                        medicineMap.put("expirationdate", medicineClassToAdd.getExpirationdate());

                        documentReference.set(medicineMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                alertView("OOPS! Something Went Wrong. Please Try Again Later.", "Error!!");
                            }
                        });
                    }

                }
            }
        });
    }

    private void updateMedicineInventory(String userid, String organizationID, String barcode, Integer quantity, Map<String, Object> organizationMedicines) {
        DocumentReference organizationReference = firebaseFirestore
                .collection("organization"
                ).document(organizationID)
                .collection("receivedMedicine").document(barcode);
        organizationReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    DonatedMedicines donatedMedicines = document.toObject(DonatedMedicines.class);
                    if (document.exists()) {

                        System.out.println("Dosya var");
                        organizationReference.update("quantity", donatedMedicines.getQuantity() + quantity)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("Quantity has been updated");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    } else {

                        DonatedMedicines donatedMedicinesToAdd = new DonatedMedicines(barcode, userid, quantity);
                        organizationMedicines.put("quantity", donatedMedicinesToAdd.getQuantity());
                        organizationMedicines.put("lastDonatedBy", donatedMedicinesToAdd.getUserID());
                        organizationReference.set(organizationMedicines).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                alertView("Your Medicine Has Been Successfully Added to Inventory Of Organization.", "Donation Successful");
                                Toast.makeText(UploadMedicine.this, "Medicine added to Inventory", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }).addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UploadMedicine.this, "Error !" + e.getMessage(), Toast.LENGTH_LONG).show();
                                alertView("OOPS! Something Went Wrong. Please Try Again Later.", "Error!!");
                            }
                        });
                    }
                }
            }
        });

    }

    private void alertView(String alertMessage, String messageType) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(UploadMedicine.this);
        dialog.setTitle(messageType)
                .setMessage(alertMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }

    private void fixStrings(String a) {
        a.trim().toUpperCase();
    }
}
