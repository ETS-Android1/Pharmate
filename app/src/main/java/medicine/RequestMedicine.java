package medicine;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        DocumentReference documentReference = firebaseFirestore
                .collection("user")
                .document(userID)
                .collection("requestedMedicine")
                .document(barcode);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    RequestClass requestClass = document.toObject(RequestClass.class);
                    if (document.exists()) {
                        System.out.println("Dosya var");
                        documentReference.update("quantity", requestClass.getQuantity() + amountText)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("Quantity has been updated");
                                    }
                                });


                    } else {

                        RequestClass requestClassToAdd = new RequestClass(medicinename, barcode, amountText);

                        Map<String, Object> medicine = new HashMap<>();

                        medicine.put("medicineName", requestClassToAdd.getMedicineName());
                        medicine.put("barcode", requestClassToAdd.getBarcode());
                        medicine.put("quantity", requestClassToAdd.getQuantity());


                        documentReference.set(medicine).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RequestMedicine.this);
                                alert.setTitle("Information");
                                alert.setMessage(requestClassToAdd.getMedicineName() + " will be requested. You will be notified when it's available on the inventory.");
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(RequestMedicine.this, "Medicine added to firestore", Toast.LENGTH_SHORT).show();
                                    }

                                });
                                alert.create().show();

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