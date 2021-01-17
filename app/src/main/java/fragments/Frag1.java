package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pharmate.ForgetPassword;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import homepage.HomePage;

public class Frag1 extends Fragment {
    public boolean isUser;
    EditText emailText, passwordText;
    Button button2;
    TextView forget;
    ImageView imageView;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference userRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1_layout, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference userRef = firebaseFirestore.collection("user");
        emailText = view.findViewById(R.id.editTextTextPersonName15);
        passwordText = view.findViewById(R.id.signInPasswordText);
        imageView = view.findViewById(R.id.imageView3);
        button2 = view.findViewById(R.id.button2);
        progressBar = view.findViewById(R.id.frag1ProgressBar);
        progressBar.setVisibility(View.GONE);
        forget = view.findViewById(R.id.forgotpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        List<String> userMails = new ArrayList<>();
        // Getting User Email Addresses From Firestore and
        // Check if the user logins from the correct screen
        checkUserMail("", userMails, userRef);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (checkUserMail(emailText.getText().toString(), userMails, userRef)) {
                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent _intent = new Intent(getActivity(), HomePage.class);
                                startActivity(_intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), "Please Verify Your Email Address", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        } else {
                            Toast.makeText(getActivity(), "OOPS! Something Went Wrong :( Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(_intent);
                getActivity().finish();

            }
        });

        return view;


    }

    private boolean checkUserMail(String userMail, List<String> userMails, CollectionReference ref) {
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("userEmail");
                        userMails.add(subject);
                        System.out.println(userMails);
                        isUser = userMails.contains(userMail);
                    }

                }
            }
        });
        return isUser;
    }
}

