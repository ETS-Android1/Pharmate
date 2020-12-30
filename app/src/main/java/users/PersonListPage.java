package users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import medicine.MedicineAdapter;
import medicine.ReceiveMedicine;
import medicine.SearchMedicine;
import models.MedicineClass;
import models.OrganizationClass;
import models.UserClass;
import organization.OrganizationAdapter;

public class PersonListPage extends AppCompatActivity {
    EditText personName;
    Button list;
    RecyclerView recyclerView;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private PersonAdapter adapter;
    private CollectionReference personReference;

    public PersonListPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list_page);
        personReference = db.collection("user");
        personName=findViewById(R.id.persnName);
        list=findViewById(R.id.personlist);
        recyclerView=findViewById(R.id.person_recycler_view);
        setUpRecyclerView();

    }
    private void setUpRecyclerView() {

        Query denemQuery = personReference;

        Query query = personReference;
        System.out.println(personName.getText().toString().trim());

        if (personName.getText().toString().trim().equals(null) || personName.getText().toString().trim().equals("")) {

            FirestoreRecyclerOptions<UserClass> options1 = new FirestoreRecyclerOptions.Builder<UserClass>()
                    .setQuery(denemQuery, UserClass.class)
                    .build();
            adapter = new PersonAdapter(options1);
            adapter.setOnItemClickListener(new PersonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    UserClass userClass = documentSnapshot.toObject(UserClass.class);
                    String id = documentSnapshot.getId();
                    Intent intent = new Intent(PersonListPage.this, PersonDetailed.class);
                    intent.putExtra("name", userClass.getName());
                    intent.putExtra("surname", userClass.getSurname());
                    intent.putExtra("contact", userClass.getContact());
                    intent.putExtra("address", userClass.getAddress());

//                    intent.putExtra("userID",firebaseAuth.getCurrentUser().getUid());

                    startActivity(intent);
                    Toast.makeText(PersonListPage.this, "Position" + position, Toast.LENGTH_LONG).show();
                }
            });


        } else {

            FirestoreRecyclerOptions<UserClass> options2 = new FirestoreRecyclerOptions.Builder<UserClass>()
                    .setQuery(query, UserClass.class)
                    .build();
            adapter = new PersonAdapter(options2);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void setUpRecyclerViewSearch() {

        Query query = personReference.whereEqualTo("name","HzLKIWtdmufJF5HJIcPt4hbdyJ02").orderBy("name");
        System.out.println(personName.getText().toString().trim());
        FirestoreRecyclerOptions<UserClass> options2 = new FirestoreRecyclerOptions.Builder<UserClass>()
                .setQuery(query, UserClass.class)
                .build();
        adapter = new PersonAdapter(options2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        recyclerView
                .setAdapter(adapter);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Information");
        alert.setMessage("Person not found!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "kişi yok", Toast.LENGTH_LONG).show();
            }

        });
        alert.create().show();


    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    public void listPersonClick(View view) {
        if (personName.getText().toString().trim().equals(null) || personName.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "BOS ARAMA DA YAPMAZSIN", Toast.LENGTH_LONG).show();
        } else {
//            setUpRecyclerViewSearch();
            System.out.println("denemeeeee");
        }
    }
    }
