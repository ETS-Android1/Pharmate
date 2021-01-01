package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class Frag2 extends Fragment {
    public boolean isOrg;
    EditText mailSign, passwordSign;
    Button login;
    TextView forget;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2_layout, container, false);

        mailSign = view.findViewById(R.id.mailSign);
        passwordSign = view.findViewById(R.id.passwordSign);
        login = view.findViewById(R.id.login);
        progressBar = view.findViewById(R.id.frag2ProgressBar);
        progressBar.setVisibility(View.GONE);
        forget = view.findViewById(R.id.forgotpasswordd);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference orgRef = firebaseFirestore.collection("organization");
        List<String> orgMails = new ArrayList<>();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = mailSign.getText().toString();
                String password = passwordSign.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (checkOrganizationMail(mailSign.getText().toString(), orgMails, orgRef)) {
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
                            Toast.makeText(getActivity(), "Please Login From User Page", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    private boolean checkOrganizationMail(String orgMail, List<String> orgMails, CollectionReference ref) {
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("email");
                        orgMails.add(subject);
                        System.out.println(orgMails);
                        isOrg = orgMails.contains(orgMail);
                    }

                }
            }
        });
        return isOrg;
    }
}
