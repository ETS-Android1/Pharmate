package organization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class OrganizatonListPage extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;

    EditText nameorganization;
    Button list;
    RecyclerView recyclerView;
    private CollectionReference organizationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizaton_list_page);

        nameorganization=findViewById(R.id.editTextTextPersonName16);
        list=findViewById(R.id.button9);
        organizationReference = db.collection("organization");
        recyclerView = findViewById(R.id.org_recycler_view);


    }


    }
