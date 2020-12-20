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

import java.util.HashMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        passwordText =(EditText) findViewById(R.id.userSignUpPasswordText);
        confirmPasswordText = (EditText)findViewById(R.id.userSignUpConfirmPasswordText);
        name = (EditText)findViewById(R.id.personNameText);
        userSurname = (EditText)findViewById(R.id.personSurnameText);
        userTurkishID =(EditText) findViewById(R.id.turkishIdText);
        userContact = (EditText)findViewById(R.id.personContactText);
        userAddress =(EditText) findViewById(R.id.personAddressText);
        userBirthDate = (EditText)findViewById(R.id.birthDateText);
        signUpClickButton = findViewById(R.id.signUpUserClickButton);
        



//
//        userBirthDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(SignUp.this,
//                        android.R.style.Theme_Holo_Dialog_MinWidth,
//                        nOnDateSetListener,
//                        year, month, day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });
//        nOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//                String date = month + "/" + day + "/" + year;
//                userBirthDate.setText(date);
//            }
//        };

    }
    private Boolean validateName() {
        String val = name.getText().toString();

        if (val.isEmpty()) {
            name.setError("Field cannot be empty");
            return false;
        }
        else {
            name.setError(null);
            return true;
        }
    }
    private Boolean validateSurname() {
        String val = userSurname.getText().toString();

        if (val.isEmpty()) {
            userSurname.setError("Field cannot be empty");
            return false;
        }
        else {
            userSurname.setError(null);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = emailText.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            emailText.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            emailText.setError("Invalid email address");
            return false;
        } else {
            emailText.setError(null);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = userContact.getText().toString();

        if (val.isEmpty()) {
            userContact.setError("Field cannot be empty");
            return false;
        } else {
            userContact.setError(null);
           // userContact.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = passwordText.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            passwordText.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            passwordText.setError("Password is too weak");
            return false;
        } else {
            passwordText.setError(null);
         //  passwordText.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateturkishId() {
        String val = userTurkishID.getText().toString();
        String noWhiteSpace = "[a-zA-Z\\s]+";

        if (val.isEmpty()) {
            userTurkishID.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 12) {
            userTurkishID.setError("Username too long");
            return false;
        } else if(val.length()<11){
            userTurkishID.setError("Username too short");
            return false;
        }
        else if (!val.matches(noWhiteSpace)) {
            userTurkishID.setError("White Spaces are not allowed");
            return false;
        } else {
            userTurkishID.setError(null);
           // userTurkishID.setErrorEnabled(false);
            return true;
        }
    }

    public void signUpUserClick(View view) {
        if (!validateName() | !validateSurname()| !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateturkishId()) {
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

                                                UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userTurkishIDText, userAddressText, userContactText, userBirthDayText, null);
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