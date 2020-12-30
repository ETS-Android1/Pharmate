package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pharmate.ForgetPassword;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import homepage.HomePage;

public class Frag1 extends Fragment {
    private FirebaseAuth firebaseAuth;
    EditText emailText, passwordText;
    Button button2;
    TextView forget;
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.frag1_layout,container,false);

emailText=view.findViewById(R.id.editTextTextPersonName15);
passwordText=view.findViewById(R.id.signInPasswordText);
imageView=view.findViewById(R.id.imageView3);
button2=view.findViewById(R.id.button2);
       forget=view.findViewById(R.id.forgotpassword);
firebaseAuth = FirebaseAuth.getInstance();

button2.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
       // AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
       // alert.setTitle("Information");
       // alert.setMessage("Are you sure you want to login?");
       // alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
         //   @Override
         //   public void onClick(DialogInterface dialog, int i) {
         //       Toast.makeText(getActivity(), "Login Canceled", Toast.LENGTH_SHORT).show();
        //    }
       // });
        //alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
          //  @Override
          //  public void onClick(DialogInterface dialogInterface, int i) {
               // if(firebaseAuth.getCurrentUser().isEmailVerified()){
                          //    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                          //   Intent _intent = new Intent(getActivity(), HomePage.class);
                          // startActivity(_intent);
                        //  }else{
                         //  Toast.makeText(getActivity(), "please verify your email address", Toast.LENGTH_SHORT).show();
                       // }

                    //  }
                    //}).addOnFailureListener(new OnFailureListener() {
                    //    @Override
                    //  public void onFailure(@NonNull Exception e) {
                    //      Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    //   }
                    //  });
          //  }

       // });

       // alert.create().show();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Information");
                alert.setMessage("Are you sure you want to login?");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(getActivity(), "Login Canceled", Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent _intent = new Intent(getActivity(), HomePage.class);
                            startActivity(_intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(), "please verify your email address", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                alert.create().show();

            }



        }).addOnFailureListener(new OnFailureListener() {
            @Override
           public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
});
       forget.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View view) {
               Intent _intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(_intent);
                getActivity().finish();

           }
        });

        return view;


        // Getting current user
        // if exists user will directly access for homepage
        // else login register page
        //  FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //if (firebaseUser != null) {
        // Intent _intent = new Intent(SignIn.this, HomePage.class);
        //  startActivity(_intent);
        //finish();
        //   System.out.println(firebaseUser.getUid());

        //}

    }


    }

