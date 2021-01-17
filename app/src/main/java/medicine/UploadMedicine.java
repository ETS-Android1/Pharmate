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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Data;
import models.DonatedMedicines;
import models.MedicineClass;
import models.OrganizationDonatedMedicineClass;
import models.Token;
import notifications.Client;
import notifications.MyResponse;
import notifications.NotificationSender;
import notifications.NotificationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_medicine);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference organizationReference = firebaseFirestore.collection("organization");
        organizationNames = new ArrayList<>();
        orgIDs = new ArrayList<>();
        notificationService = Client.getClient("https://fcm.googleapis.com/").create(NotificationService.class);

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


            updateMedicineInventory(nameText, userID, organizationID, barcodeNoText, quantityText, expirationDateText, orgMedicineMap);
            uploadMedicineToDB(nameText, userID, organizationID, quantityText, barcodeNoText, expirationDateText, medicineMap);


        }
    }


    private void uploadMedicineToDB(String nameText, String userID, String organizationID, Integer quantityText, String barcodeNoText, String expirationDateText, Map<String, Object> medicineMap) {


        DocumentReference documentReference = firebaseFirestore.collection("medicine").document(barcodeNoText);
        DocumentReference userDonatedMedicineRef = firebaseFirestore
                .collection("user")
                .document(userID)
                .collection("donatedMedicine")
                .document(barcodeNoText);
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
                                        alertView("The medicine is already on the inventory. Quantity will be updated.", "Donation Successful");
                                        Toast.makeText(UploadMedicine.this, "Medicine added to Inventory", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        userDonatedMedicineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    MedicineClass userDonatedMedicineClass = document.toObject(MedicineClass.class);
                                                    if (document.exists()) {
                                                        System.out.println("Ilac ayni user tarafindan daha once de bagislanmis, quantity arttiriliyor");
                                                        documentReference.update("quantity", userDonatedMedicineClass.getQuantity() + quantityText)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        System.out.println("User'in halihazirda bagisladigi ilaclarin sayisi guncellendi PART1");
                                                                    }
                                                                });
                                                    } else {
                                                        System.out.println("UserDonatedMedicineRef can not found");
                                                        System.out.println("Creating field in the User Collection ");
                                                        userDonatedMedicineRef.set(medicineMap);
                                                    }
                                                } else {
                                                    System.out.println("Task is unreachable");
                                                }
                                            }
                                        });

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
                                // ADDING MEDICINE TO THE USERS DONATED MEDICINE COLLECTION
                                String userToken = "flkmDq1_StWKAKLU0EHySj:APA91bGfljn5EZBcXe0lNAFZwa5wsrdAOXZhiI_fgl_Q7jGTAUROjgiasQG-NBKVttqlQq0H65S9JFDAEFMlfPAMNq76qFlymvlTBapdqhVuq6xOb6bKY3V2H4pV922Df_lGeZlSKqXC";
                                sendNotifications(userToken, "Deneme Title", "Deneme Body");

                                userDonatedMedicineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            MedicineClass userDonatedMedicineClass = document.toObject(MedicineClass.class);
                                            if (document.exists()) {
                                                System.out.println("Ilac ayni user tarafindan daha once de bagislanmis, quantity arttiriliyor");
                                                documentReference.update("quantity", userDonatedMedicineClass.getQuantity() + quantityText)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                System.out.println("User'in halihazirda bagisladigi ilaclarin sayisi guncellendi PART2");
                                                                alertView("The medicine is already on the inventory. Quantity will be updated.", "Donation Successful");

                                                            }
                                                        });
                                            } else {
                                                System.out.println("UserDonatedMedicineRef can not found");
                                                System.out.println("Creating field in the User Collection ");
                                                userDonatedMedicineRef.set(medicineMap);
                                            }
                                        } else {
                                            System.out.println("Task is unreachable");
                                        }
                                    }
                                });


//                                // Requested medicine'i check et, notification gonder.
//
//                                DocumentReference notificationRef = firebaseFirestore
//                                        .collection("user"
//                                        ).document(targetMedicineUserID)
//                                        .collection("requestedMedicine").document(barcodeNoText);
//                                notificationRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            DocumentSnapshot document = task.getResult();
//                                            RequestClass requestClass = document.toObject(RequestClass.class);
//                                            System.out.println("REQUEST CLASS " + requestClass.getBarcode());
//                                            if (document.exists()) {
//                                                System.out.println("Request var");
//                                                if (requestClass.getBarcode().equals(barcodeNoText)) {
//                                                    String requestedMedicine = requestClass.getMedicineName();
//                                                    Integer requestQuantity = requestClass.getQuantity();
//
//                                                    DocumentReference tokenRef = firebaseFirestore.collection("Tokens").document("HzLKlWtdmufJF5HJIcPt4hbdyJ02");
//                                                    tokenRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                            if (task.isSuccessful()) {
//                                                                DocumentSnapshot document = task.getResult();
//                                                                Token tokenClass = document.toObject(Token.class);
//                                                                String userToken = tokenClass.getToken();
//                                                                System.out.println("USER TOKEN IS" + userToken);
//                                                                sendNotifications(userToken, "Deneme Title", "Deneme Body");
//                                                            } else {
//                                                                System.out.println("TASK NOT SUCCESSFUL");
//                                                            }
//                                                        }
//                                                    });
//                                                } else {
//                                                    System.out.println("REQUEST CLASS BOS!");
//                                                }
//
//                                            }
//                                        }
//                                    }
//                                });
                                UpdateToken();
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

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        notificationService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(UploadMedicine.this, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }


    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        DocumentReference updateTokenRef = firebaseFirestore
                .collection("Tokens")
                .document(firebaseUser.getUid());
        updateTokenRef.set(token);

    }

    private void updateMedicineInventory(String medicineName, String userid, String organizationID, String barcode, Integer quantity, String expirationDate, Map<String, Object> organizationMedicines) {
        String userToken = "flkmDq1_StWKAKLU0EHySj:APA91bGfljn5EZBcXe0lNAFZwa5wsrdAOXZhiI_fgl_Q7jGTAUROjgiasQG-NBKVttqlQq0H65S9JFDAEFMlfPAMNq76qFlymvlTBapdqhVuq6xOb6bKY3V2H4pV922Df_lGeZlSKqXC";
        sendNotifications(userToken, "Deneme Title", "Deneme Body");
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

                        System.out.println("Ayni Ilac Daha Once de Bu Organizasyona Bagislandigi Icin Halihazirda var. Quantity arttiriliyor");
                        organizationReference.update("quantity", donatedMedicines.getQuantity() + quantity)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("Ilacin Organization Envanterindeki Quantity'si Arttiriliyor");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    } else {
                        OrganizationDonatedMedicineClass orgDonatedMed = new OrganizationDonatedMedicineClass(medicineName, barcode, organizationID, userid, expirationDate, quantity);
                        organizationMedicines.put("quantity", orgDonatedMed.getQuantity());
                        organizationMedicines.put("nameOfMedicine", orgDonatedMed.getNameOfMedicine());
                        organizationMedicines.put("barcodeNumber", orgDonatedMed.getBarcodeNumber());
                        organizationMedicines.put("expirationdate", orgDonatedMed.getExpirationdate());
                        organizationMedicines.put("donatedBy", orgDonatedMed.getDonatedBy());
                        organizationMedicines.put("lastDonationTo", orgDonatedMed.getlastDonationTo());


//                        DonatedMedicines donatedMedicinesToAdd = new DonatedMedicines(barcode, userid, quantity);


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
