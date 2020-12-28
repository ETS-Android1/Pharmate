package medicine;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import models.MedicineClass;

public class UploadMedicine extends AppCompatActivity {
    private static final String TAG = "UploadMedicine";
    AwesomeValidation awesomeValidation;
    EditText barcodeNo, quantity, name;
    HashMap<String, Object> postMedicineData = new HashMap<>();
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView expirationDate;
    private DatePickerDialog.OnDateSetListener nOnDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_medicine);

        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

//        //defining textFields

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
    }

    public void uploadMedicineClick(View view) {

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
                                        }
                                    });


                        } else {

                            MedicineClass medicineClassToAdd = new MedicineClass(nameText, userID, null, quantityText, barcodeNoText, expirationDateText);

                            Map<String, Object> medicine = new HashMap<>();

                            medicine.put("nameOfMedicine", medicineClassToAdd.getNameOfMedicine());
                            medicine.put("barcodeNumber", medicineClassToAdd.getBarcodeNumber());
                            medicine.put("donatedBy", medicineClassToAdd.getDonatedBy());
                            medicine.put("donatedTo", medicineClassToAdd.getDonatedTo());
                            medicine.put("quantity", medicineClassToAdd.getQuantity());
                            medicine.put("expirationdate", medicineClassToAdd.getExpirationdate());


                            documentReference.set(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UploadMedicine.this, "Medicine added to firestore", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadMedicine.this, "Error !" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }
            });
        }
    }

}
