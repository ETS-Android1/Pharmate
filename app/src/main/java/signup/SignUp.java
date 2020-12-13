package signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.pharmate.MainActivity;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;

import models.UserClass;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    AwesomeValidation awesomeValidation;

    private static final String TAG = "SignUp";

    private DatePickerDialog.OnDateSetListener nOnDateSetListener;

    EditText emailText, passwordText, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
    Button signUpClickButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.userSignUpEmailText);
        passwordText = findViewById(R.id.userSignUpPasswordText);
        name = findViewById(R.id.personNameText);
        userSurname = findViewById(R.id.personSurnameText);
        userTurkishID = findViewById(R.id.turkishIdText);
        userContact = findViewById(R.id.personContactText);
        userAddress = findViewById(R.id.personAddressText);
        userBirthDate = findViewById(R.id.birthDateText);
        signUpClickButton=findViewById(R.id.signUpUserClickButton);

        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUp.this,
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
                userBirthDate.setText(date);
            }
        };

        awesomeValidation.addValidation(SignUp.this,R.id.turkishIdText,"[1-9]{11}+",R.string.iderror);
        awesomeValidation.addValidation(SignUp.this, R.id.signUpEmailText, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(SignUp.this, R.id.personNameText, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(SignUp.this, R.id.personSurnameText, "[a-zA-Z\\s]+", R.string.surnameerror);
        awesomeValidation.addValidation(SignUp.this, R.id.personContactText, RegexTemplate.TELEPHONE, R.string.mobileerror);



        // girilen inputları kontrol edicez.
    }
    public void signUpUserClick (View view) {
        if (awesomeValidation.validate()) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            String nameText = name.getText().toString();
            String userSurnameText = userSurname.getText().toString();
            String userTurkishIDText = userTurkishID.getText().toString();
            String userAddressText = userAddress.getText().toString();
            String userContactText = userContact.getText().toString();
            String userBirthDayText = userBirthDate.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            // bir ust satirda kullanicidan aldigimiz sifreyi asagidaki firebase'e gondermeden once

            // validation islemlerini yapacagiz


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(nameText + userSurnameText)
                                                        .build();

                                                firebaseUser.updateProfile(profileUpdates)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    System.out.println("Task Successful");
                                                                }
                                                            }
                                                        });

                                                UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userTurkishIDText, userAddressText, userContactText, userBirthDayText);
                                                String id = firebaseUser.getUid();

                                                HashMap<String, Object> postUserData = new HashMap<>();


                                                postUserData.put("name", userClassToAdd.getName());
                                                postUserData.put("surname", userClassToAdd.getSurname());
                                                postUserData.put("turkishId", userClassToAdd.getTurkishId());
                                                postUserData.put("contact", userClassToAdd.getContact());
                                                postUserData.put("address", userClassToAdd.getAddress());
                                                postUserData.put("birthDate", userClassToAdd.getBirthdate());
                                                firebaseFirestore.collection("userType").document(id).set(postUserData);
                                                Toast.makeText(SignUp.this, "please check your email", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(SignUp.this, task.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }
    }

}