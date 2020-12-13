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

import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Range;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.pharmate.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


import fragments.Choose;
import fragments.Frag1;
import medicine.UploadMedicine;

import models.UserClass;
import users.PersonalInformation;

public class SignUp extends AppCompatActivity {

    EditText emailText, passwordText, userType, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;
   private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    AwesomeValidation awesomeValidation;

    private static final String TAG = "SignUp";

    private DatePickerDialog.OnDateSetListener nOnDateSetListener;

    EditText emailText, passwordText,userType, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
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
        passwordText = findViewById(R.id.userSignUpConfirmPasswordText);
//        userType = findViewById(R.id.userTypeText);
        name = findViewById(R.id.userNameText);
        userSurname = findViewById(R.id.userSurnameText);
        userTurkishID = findViewById(R.id.userTurkishIdText);
        userContact = findViewById(R.id.userContactText);
        userAddress = findViewById(R.id.userAddressText);
        userBirthDate = findViewById(R.id.userBirthDateText);

        String regexpassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\d])(?=.*[~`!@#\\\\$%\\\\^&\\\\*\\\\(\\\\)\\\\-_\\\\+=\\\\{\\\\}\\\\[\\\\]\\\\|\\\\;:\\\"<>,./\\\\?]).{8,}";
        awesomeValidation.addValidation(SignUp.this, R.id.signUpPasswordText,"[a-z][A-Z][1-9]{6,}+" , R.string.passworderror);
        awesomeValidation.addValidation(SignUp.this, R.id.signUpPasswordText2, R.id.signInPasswordText, R.string.passworderror);
        awesomeValidation.addValidation(SignUp.this,R.id.turkishIdText,"[1-9]{11}+",R.string.iderror);
        awesomeValidation.addValidation(SignUp.this, R.id.signUpEmailText, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(SignUp.this, R.id.personNameText, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(SignUp.this, R.id.personSurnameText, "[a-zA-Z\\s]+", R.string.surnameerror);
        awesomeValidation.addValidation(SignUp.this, R.id.personContactText, RegexTemplate.TELEPHONE, R.string.mobileerror);

    }

    public void signUpUserClick(View view) {
        System.out.println("button pressed");
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

                                            firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                System.out.println("Task Successful");
                                                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                                                String userEmail = firebaseUser.getEmail();
                                                                UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userEmail, userTurkishIDText, userAddressText, userContactText, userBirthDayText, null);
                                                                String userID = firebaseUser.getUid();
                                                                HashMap<String, Object> postUserData = new HashMap<>();
                                                                postUserData.put("name", userClassToAdd.getName());
                                                                postUserData.put("surname", userClassToAdd.getSurname());
                                                                postUserData.put("email", userClassToAdd.getEmail());
                                                                postUserData.put("turkishId", userClassToAdd.getTurkishId());
                                                                postUserData.put("contact", userClassToAdd.getContact());
                                                                postUserData.put("address", userClassToAdd.getAddress());
                                                                postUserData.put("birthdate", userClassToAdd.getBirthdate());
                                                                firebaseFirestore.collection("user").document(userID).set(postUserData);
                                                            }
                                                        }
                                                    });
                                            Toast.makeText(SignUp.this, "please check your email", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(SignUp.this, Choose.class);
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