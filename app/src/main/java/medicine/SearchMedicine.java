package medicine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import models.MedicineClass;

public class SearchMedicine extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;



    private MedicineAdapter adapter;

    EditText barcode,name;
    Button searchButton,request;
    RecyclerView recyclerView;
    private CollectionReference medicineReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);

        barcode = findViewById(R.id.editTextTextPersonName14);
        name=findViewById(R.id.editTextTextPersonName13);
//        searchButton = findViewById(R.id.button10);
        medicineReference = db.collection("medicine");
        recyclerView = findViewById(R.id.medicine_recycler_view);
        request =findViewById(R.id.request);
        request.setVisibility(View.INVISIBLE);

        name.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        barcode.setFilters(new  InputFilter[]{new InputFilter.LengthFilter(14)});
        setUpRecyclerView();

        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                Query query = medicineReference.whereEqualTo("barcodeNumber", barcode.getText().toString().trim()).orderBy("barcodeNumber");

                FirestoreRecyclerOptions<MedicineClass> options1 = new FirestoreRecyclerOptions.Builder<MedicineClass>()
                        .setQuery(query, MedicineClass.class)
                        .build();
                adapter.updateOptions(options1);


            }
        });


    }

            private void setUpRecyclerView() {

                Query denemQuery = medicineReference.orderBy("barcodeNumber");

                Query query = medicineReference.whereEqualTo("barcodeNumber", barcode.getText().toString().trim()).orderBy("barcodeNumber");
                System.out.println(barcode.getText().toString().trim());
                if (barcode.getText().toString().trim().equals(null) || barcode.getText().toString().trim().equals("")) {

                    FirestoreRecyclerOptions<MedicineClass> options1 = new FirestoreRecyclerOptions.Builder<MedicineClass>()
                            .setQuery(denemQuery, MedicineClass.class)
                            .build();
                    adapter = new MedicineAdapter(options1);
                    adapter.setOnItemClickListener(new MedicineAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                            MedicineClass medicineClass = documentSnapshot.toObject(MedicineClass.class);
                            String id = documentSnapshot.getId();
                            Intent intent = new Intent(SearchMedicine.this, ReceiveMedicine.class);
                            intent.putExtra("nameOfMedicine", medicineClass.getNameOfMedicine());
                            intent.putExtra("barcodeNumber", medicineClass.getBarcodeNumber());
                            intent.putExtra("quantity", medicineClass.getQuantity());
                            intent.putExtra("expirationdate", medicineClass.getExpirationdate());
//                    intent.putExtra("userID",firebaseAuth.getCurrentUser().getUid());

                            startActivity(intent);
                            Toast.makeText(SearchMedicine.this, "Position" + position, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {

                    FirestoreRecyclerOptions<MedicineClass> options2 = new FirestoreRecyclerOptions.Builder<MedicineClass>()
                            .setQuery(query, MedicineClass.class)
                            .build();
                    adapter = new MedicineAdapter(options2);
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            }

            private void setUpRecyclerViewSearch() {

                Query query = medicineReference.whereEqualTo("barcodeNumber", "08699565523578").orderBy("barcodeNumber");
                System.out.println(barcode.getText().toString().trim());
                FirestoreRecyclerOptions<MedicineClass> options2 = new FirestoreRecyclerOptions.Builder<MedicineClass>()
                        .setQuery(query, MedicineClass.class)
                        .build();
                adapter = new MedicineAdapter(options2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                request.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "ilaç yok", Toast.LENGTH_LONG).show();
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

            public void searchMedicineClick(View view) {

                if (barcode.getText().toString().trim().equals(null) || barcode.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "BOS ARAMA DA YAPMAZSIN", Toast.LENGTH_LONG).show();
                } else {
                    setUpRecyclerViewSearch();
                }
            }

            public void goRequest(View view) {
                Intent intent = new Intent(SearchMedicine.this, RequestMedicine.class);
                startActivity(intent);
            }
}