package users;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import homepage.HomePage;
import models.UserClass;

import static android.graphics.Color.TRANSPARENT;

public class PersonalInformation extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    AwesomeValidation awesomeValidation;


    private static final String TAG = "PersonalInformation";

    private DatePickerDialog.OnDateSetListener nOnDateSetListener;


    EditText name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
    String nameperson, surnameperson, turkisIdperson, contactperson, birthdateperson, addressperson;
    Button update;
    ImageView picture;
    public Uri imageUri;
    Bitmap selectedImage;

    // KULLANICININ BU FORMU DOLDURDUĞUNU UYGULAMA BOYUNCA KONTROL EDİLMESİ GEREKİYOR.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_page);
        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("user");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

//
//        //defining
        picture=findViewById(R.id.pictureId);
        name = findViewById(R.id.personNameText);
        userSurname = findViewById(R.id.personSurnameText);
        userTurkishID = findViewById(R.id.turkishIdText);
        userContact = findViewById(R.id.personContactText);
        userAddress = findViewById(R.id.personAddressText);
        userBirthDate = findViewById(R.id.birthDateText);
        update=findViewById(R.id.button3);
        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PersonalInformation.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        nOnDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
                dialog.show();
            }
        });
        nOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                userBirthDate.setText(date);
            }
        };

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            Log.d(TAG, "onCreate:"+user.getDisplayName());
            if (user.getDisplayName() != null) {
                name.setText(user.getDisplayName());
                name.setSelection(user.getDisplayName().length());
                userSurname.setText(user.getDisplayName());
                userSurname.setSelection(user.getDisplayName().length());
                userTurkishID.setText(user.getDisplayName());
                userTurkishID.setSelection(user.getDisplayName().length());
                userContact.setText(user.getDisplayName());
                userContact.setSelection(user.getDisplayName().length());
                userAddress.setText(user.getDisplayName());
                userAddress.setSelection(user.getDisplayName().length());
                userBirthDate.setText(user.getDisplayName());
                userBirthDate.setSelection(user.getDisplayName().length());


            }
        }
    }


    public void updatePersonInfoClick(View view) {
        if (imageUri != null) {

            //universal unique id
            UUID uuid = UUID.randomUUID();
            final String imageName = "images/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Download URL

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString();

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userEmail = firebaseUser.getEmail();

                            String Name = name.getText().toString();
                            String surName = userSurname.getText().toString();
                            String id = userTurkishID.getText().toString();
                            String contact = userContact.getText().toString();
                            String address = userAddress.getText().toString();
                            String birthdate = userBirthDate.getText().toString();


                            HashMap<String, Object> postData = new HashMap<>();
                            postData.put("name",Name);
                            postData.put("surname",surName);
                            postData.put("turkishId",id);
                            postData.put("contact",contact);
                            postData.put("address",address);
                            postData.put("birthDate",birthdate);


                            firebaseFirestore.collection("user").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Intent intent = new Intent(PersonalInformation.this, HomePage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PersonalInformation.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                }
                            });




                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PersonalInformation.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null ) {

            imageUri = data.getData();

            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageUri);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    picture.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    picture.setImageBitmap(selectedImage);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    public void selectedImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
    }
}












