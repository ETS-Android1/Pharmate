package signup;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import fragments.Choose;
import models.UserClass;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    EditText emailText, passwordText,userType, name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseFirestore = FirebaseFirestore.getInstance();

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

        // girilen inputları kontrol edicez.
    }

    public void signUpUserClick(View view) {
        System.out.println("button pressed");
        String nameText = name.getText().toString();
        String userSurnameText = userSurname.getText().toString();
        String userTurkishIDText = userTurkishID.getText().toString();
        String userAddressText = userAddress.getText().toString();
        String userContactText = userContact.getText().toString();
        String userTypeText = userType.getText().toString();
        String userBirthDayText = userBirthDate.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        // bir ust satirda kullanicidan aldigimiz sifreyi asagidaki firebase'e gondermeden once
        // validation islemlerini yapacagiz
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseAuth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(nameText+userSurnameText)
                                                    .build();
                                            firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                System.out.println("Task Successful");
                                                            }
                                                        }
                                                    });
                                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                            String userEmail = firebaseUser.getEmail();
                                            UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userEmail, userTurkishIDText, userAddressText, userContactText, userBirthDayText, null);

                                            String userID = firebaseUser.getUid();
                                            HashMap<String, Object> postUserData = new HashMap<>();
                                            postUserData.put("name", userClassToAdd.getName());
                                            postUserData.put("surname", userClassToAdd.getSurname());
                                            postUserData.put("turkishId", userClassToAdd.getTurkishId());
                                            postUserData.put("contact", userClassToAdd.getContact());
                                            postUserData.put("address", userClassToAdd.getAddress());
                                            postUserData.put("birthdate", userClassToAdd.getBirthdate());
                                            firebaseFirestore.collection("user").document(userID).set(postUserData);

                                            Toast.makeText(SignUp.this,"please check your email", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(SignUp.this, Choose.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(SignUp.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show(); }

                                    }
                                });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
//
//
//        firebaseAuth.createUserWithEmailAndPassword(email,password)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                        firebaseUser.sendEmailVerification()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful()){
//                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                                    .setDisplayName(nameText+userSurnameText)
//                                                    .build();
//
//                                            Toast.makeText(SignUp.this,"please check your email", Toast.LENGTH_LONG).show();
//                                            Intent intent = new Intent(SignUp.this, MainActivity.class);
//                                            startActivity(intent);
//
//                                            firebaseUser.updateProfile(profileUpdates)
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            if (task.isSuccessful()) {
//                                                                DocumentSnapshot document = null;
//                                                                UserClass userClass = document.toObject(UserClass.class);
//                                                                System.out.println("Task Successful");
//                                                            }
//                                                        }
//                                                    });
//
//                                        }else{
//                                            Toast.makeText(SignUp.this, task.getException().getMessage(),
//                                                    Toast.LENGTH_LONG).show(); }
//
//                                    }
//                                });
//
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(SignUp.this, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
//            }
//        }).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                    UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userTurkishIDText,userAddressText,userContactText,userBirthDayText,null);
//                    String userEmail = firebaseUser.getEmail();
//                    String userID = firebaseUser.getUid();
//                    HashMap<String, Object> postUserData = new HashMap<>();
//
//                    postUserData.put("name", userClassToAdd.getName());
//                    postUserData.put("surname", userClassToAdd.getSurname());
//                    postUserData.put("turkishId", userClassToAdd.getTurkishId());
//                    postUserData.put("contact", userClassToAdd.getContact());
//                    postUserData.put("address",userClassToAdd.getAddress());
//                    postUserData.put("birthDate", userClassToAdd.getBirthdate());
//
//                    firebaseFirestore.collection("user").document(userID).set(postUserData);
//                } else {
//
//                    System.out.println("Task Failed");
//                }
//
//                // ...
//            }
//        })
//        ;

    }

}