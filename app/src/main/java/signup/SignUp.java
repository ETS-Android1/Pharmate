package signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.pharmate.MainActivity;
import com.example.pharmate.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import location.LocationTracker;
import models.UserClass;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";
    AwesomeValidation awesomeValidation;
    EditText emailText, passwordText, confirmPasswordText, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
    Button signUpClickButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private DatePickerDialog.OnDateSetListener nOnDateSetListener;
    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        passwordText = findViewById(R.id.userSignUpPasswordText);
        confirmPasswordText = findViewById(R.id.userSignUpConfirmPasswordText);
        name = findViewById(R.id.userNameText);
        emailText = findViewById(R.id.userSignUpEmailText);
        userSurname = findViewById(R.id.userSurnameText);
        userTurkishID = findViewById(R.id.userTurkishIdText);
        userContact = findViewById(R.id.userContactText);
        userAddress = findViewById(R.id.userAddressText);
        userBirthDate = findViewById(R.id.userBirthDateText);
        signUpClickButton = findViewById(R.id.signUpUserClickButton);


        awesomeValidation.addValidation(SignUp.this, R.id.userNameText, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(SignUp.this, R.id.userSurnameText, "[a-zA-Z\\s]+", R.string.surnameerror);
        awesomeValidation.addValidation(SignUp.this, R.id.userSignUpPasswordText, "[a-zA-Z\\d\\!@#.\\$%&\\*]{8,}", R.string.passworderror);
        awesomeValidation.addValidation(SignUp.this, R.id.userSignUpConfirmPasswordText, "[a-zA-Z\\d\\!@#.\\$%&\\*]{8,}", R.string.passworderror);
        awesomeValidation.addValidation(SignUp.this, R.id.userSignUpEmailText, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(SignUp.this, R.id.userTurkishIdText, "^((?!(0))[0-9]{11})$+", R.string.iderror);
        awesomeValidation.addValidation(SignUp.this, R.id.userContactText, RegexTemplate.TELEPHONE, R.string.mobileerror);
        awesomeValidation.addValidation(SignUp.this, R.id.userBirthDateText, new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {
                // check if the age is >= 18
                try {
                    Calendar calendarBirthday = Calendar.getInstance();
                    Calendar calendarToday = Calendar.getInstance();
                    calendarBirthday.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input));
                    int yearOfToday = calendarToday.get(Calendar.YEAR);
                    int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
                    if (yearOfToday - yearOfBirthday > 18) {
                        return true;
                    } else if (yearOfToday - yearOfBirthday == 18) {
                        int monthOfToday = calendarToday.get(Calendar.MONTH);
                        int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                        if (monthOfToday > monthOfBirthday) {
                            return true;
                        } else if (monthOfToday == monthOfBirthday) {
                            if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                                return true;
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }, R.string.dateerror);


    }


    public void signUpUserClick(View view) {
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

                                                String id = firebaseUser.getUid();
                                                LatLng userLocation = getLocation();
                                                GeoPoint geoPoint = new GeoPoint(userLocation.latitude, userLocation.longitude);
                                                UserClass userClassToAdd = new UserClass(nameText, userSurnameText, email, userTurkishIDText, userContactText, userAddressText, userBirthDayText, "", geoPoint);


                                                HashMap<String, Object> postUserData = new HashMap<>();


                                                postUserData.put("name", userClassToAdd.getName());
                                                postUserData.put("surname", userClassToAdd.getSurname());
                                                postUserData.put("userEmail", userClassToAdd.getEmail());
                                                postUserData.put("turkishId", userClassToAdd.getTurkishId());
                                                postUserData.put("contact", userClassToAdd.getContact());
                                                postUserData.put("address", userClassToAdd.getAddress());
                                                postUserData.put("birthDate", userClassToAdd.getBirthdate());
                                                postUserData.put("location", userClassToAdd.getLocation());
                                                postUserData.put("photoURL", userClassToAdd.getPhotoURL());

                                                firebaseFirestore.collection("user").document(id).set(postUserData);
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

    public LatLng getLocation() {
        locationTracker = new LocationTracker(SignUp.this);
        LatLng location;
        if (locationTracker.canGetLocation()) {
            location = new LatLng(locationTracker.getLatitude(), locationTracker.getLongitude());
            System.out.println(location);
            return location;
        } else {
            locationTracker.showSettingsAlert();
            return null;
        }
    }


}