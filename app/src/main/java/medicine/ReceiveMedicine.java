package medicine;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmate.Loadingbar;
import com.example.pharmate.R;

import location.LocationActivity;

public class ReceiveMedicine extends AppCompatActivity {

    Button receive;
    CardView cardView;

    EditText medicineName, barcode, amount, date;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_medicine);

        medicineName=findViewById(R.id.signInEmailText);
        barcode=findViewById(R.id.editTextTextPersonName2);
        amount=findViewById(R.id.editTextTextPersonName3);
        date=findViewById(R.id.editTextTextPersonName4);
       receive=findViewById(R.id.receive);
        cardView=findViewById(R.id.cardview);
        final Loadingbar loadingbar = new Loadingbar(ReceiveMedicine.this);

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardView.setVisibility(View.GONE);

                    }
                }, 5000);
            }
        });
        Intent intent=getIntent();
        String name = intent.getStringExtra("nameOfMedicine");
        medicineName.setText(name);
        medicineName.setEnabled(false);
        String barcodenum = intent.getStringExtra("barcodeNumber");
        barcode.setText(barcodenum);
        barcode.setEnabled(false);
        String quantity = intent.getStringExtra("quantity");
        amount.setText(quantity);

        String expdate = intent.getStringExtra("expirationdate");
        date.setText(expdate);
        date.setEnabled(false);
        userID = intent.getStringExtra("userID");
        System.out.println(userID);
    }

    public void goReceiveOptionClick(View v) {
        Intent receiveOptionsIntent = new Intent(ReceiveMedicine.this, LocationActivity.class);
//        receiveOptionsIntent.putExtra("nameOfMedicine",name);
//        receiveOptionsIntent.putExtra("barcodeNumber", medicineClass.getBarcodeNumber());
//        receiveOptionsIntent.putExtra("quantity",medicineClass.getQuantity());
//        receiveOptionsIntent.putExtra("expirationdate",medicineClass.getExpirationdate());
//        receiveOptionsIntent.putExtra("userID",firebaseAuth.getCurrentUser().getUid());

        startActivity(receiveOptionsIntent);


    }
}