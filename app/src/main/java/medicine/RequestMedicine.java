package medicine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import models.RequestClass;

public class RequestMedicine extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    EditText medicineName,barcodenum,amount;
    Button requesting;
    HashMap<String, Object> postMedicineData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_medicine);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        medicineName=findViewById(R.id.medicinename);
        barcodenum=findViewById(R.id.barcode);
        amount=findViewById(R.id.amount);
        requesting=findViewById(R.id.requesting);

    }

    public void goTorequest(View view) {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        String medicinename = medicineName.getText().toString();
        String barcode = barcodenum.getText().toString();
        Integer amountText = Integer.parseInt(String.valueOf(amount.getText()));

        DocumentReference documentReference = firebaseFirestore.collection("requestedMedicine").document(barcode);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    RequestClass requestClass = document.toObject(RequestClass.class);
                    if (document.exists()) {
                        System.out.println("Dosya var");
                        documentReference.update("quantity", requestClass.getQuantity() +amountText )
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("Quantity has been updated");
                                    }
                                });


                    } else {

                        RequestClass requestClassToAdd = new RequestClass(medicinename, userID, null, amountText, barcode);

                        Map<String, Object> medicine = new HashMap<>();

                        medicine.put("medicinename", requestClassToAdd.getMedicinename());
                        medicine.put("barcode", requestClassToAdd.getBarcode());
                        medicine.put("quantity", requestClassToAdd.getQuantity());


                        documentReference.set(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RequestMedicine.this, "Medicine added to firestore", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RequestMedicine.this, "Error !" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }




}