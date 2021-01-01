package medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;

import location.LocationActivity;

public class ReceiveMedicine extends AppCompatActivity {
    EditText medicineName, barcode, amount, date;
    String userID, name, receiveBarcodenum, recQuantity;
    Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_medicine);

        medicineName = findViewById(R.id.signInEmailText);
        barcode = findViewById(R.id.editTextTextPersonName2);
        amount = findViewById(R.id.editTextTextPersonName3);
        date = findViewById(R.id.editTextTextPersonName4);
        confirm = findViewById(R.id.button5);

        Intent intent = getIntent();
        name = intent.getStringExtra("nameOfMedicine");
        medicineName.setText(name);
        medicineName.setEnabled(false);
        receiveBarcodenum = intent.getStringExtra("barcodeNumber");
        barcode.setText(receiveBarcodenum);
        barcode.setEnabled(false);


        String expdate = intent.getStringExtra("expirationdate");
        date.setText(expdate);
        date.setEnabled(false);
        userID = intent.getStringExtra("userID");
        System.out.println(userID);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recQuantity = amount.getText().toString();
                Intent receiveOptionsIntent = new Intent(ReceiveMedicine.this, LocationActivity.class);
                receiveOptionsIntent.putExtra("nameOfMedicine", name);
                receiveOptionsIntent.putExtra("barcodeNumber", receiveBarcodenum);
                receiveOptionsIntent.putExtra("quantity", recQuantity);
                receiveOptionsIntent.putExtra("userID", userID);
                startActivity(receiveOptionsIntent);
            }
        });
    }
}