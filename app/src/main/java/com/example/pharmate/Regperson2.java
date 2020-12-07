package com.example.pharmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.ProgressDialog.show;

public class Regperson2  extends Fragment {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    EditText userTurkishID,userBirthDate,emailtext,passwordText;
    Button button3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.regperson2,container,false);

        Bundle bundle = new Bundle();
        bundle = getArguments();
        bundle.getString("name");
        bundle.getString("userSurname");
        bundle.getString("userAddress");
        bundle.getString("userContact");
        bundle.getString("userType");




        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userTurkishID=view.findViewById(R.id.turkishIdText);
        userBirthDate=view.findViewById(R.id.birthDateText);
        emailtext=view.findViewById(R.id.signUpEmailText);
        passwordText=view.findViewById(R.id.signUpPasswordText);
        button3=view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userBirthDayText = userBirthDate.getText().toString();
                String email = emailtext.getText().toString();
                String password = passwordText.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                firebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getActivity(),"please check your email", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getActivity(), Frag1.class);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(getActivity(), task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }
}

