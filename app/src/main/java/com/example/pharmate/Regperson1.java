package com.example.pharmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Regperson1 extends Fragment {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    EditText userType, name, userSurname,userContact, userAddress;
    Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.regperson1,container,false);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

//
//        //defining textFields
        userType =view.findViewById(R.id.userTypeText);
        name =view.findViewById(R.id.personNameText);
        userSurname =view.findViewById(R.id.personSurnameText);
        userContact =view.findViewById(R.id.personContactText);
        userAddress =view.findViewById(R.id.personAddressText);
        next=view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                String userSurnameText = userSurname.getText().toString();
                String userAddressText = userAddress.getText().toString();
                String userContactText = userContact.getText().toString();
                String userTypeText = userType.getText().toString();

                Bundle bundle=new Bundle();
                bundle.putString("name",nameText);
                bundle.putString("userSurname",userSurnameText);
                bundle.putString("userAddress",userAddressText);
                bundle.putString("userContact",userContactText);
                bundle.putString("userType",userTypeText);

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                Regperson2 regperson2=new Regperson2();
                regperson2.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container_view_tag,regperson2);
                fragmentTransaction.commit();


            }
        });



        return view;
    }
}