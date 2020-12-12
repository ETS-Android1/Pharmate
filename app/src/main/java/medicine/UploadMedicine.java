package medicine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

import models.MedicineClass;

public class UploadMedicine extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    EditText barcodeNo,quantity, name,expirationDate;
    HashMap<String, Object> postMedicineData = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_medicine);

        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

//        //defining textFields

        name = (EditText) findViewById(R.id.nameOfMedicineText);
        barcodeNo = (EditText) findViewById(R.id.barcodeNumberText);
        quantity = (EditText) findViewById(R.id.amountText);
        expirationDate = (EditText) findViewById(R.id.expirationDateText);



    }

        public void uploadMedicineClick(View view) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            String userID = firebaseUser.getUid();
            String displayName = firebaseUser.getDisplayName();
            String nameText = name.getText().toString();
            String barcodeNoText = barcodeNo.getText().toString();
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

                            MedicineClass medicineClassToAdd = new MedicineClass(nameText, userID, null, quantityText, barcodeNoText);

                            Map<String, Object> medicine = new HashMap<>();

                            medicine.put("nameOfMedicine", medicineClassToAdd.getNameOfMedicine());
                            medicine.put("barcodeNumber", medicineClassToAdd.getBarcodeNumber());
                            medicine.put("donatedBy", medicineClassToAdd.getDonatedBy());
                            medicine.put("donatedTo", medicineClassToAdd.getDonatedTo());
                            medicine.put("quantity", medicineClassToAdd.getQuantity());


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
