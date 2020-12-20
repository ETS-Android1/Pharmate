package medicine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;

public class ReceiveMedicine extends AppCompatActivity {
    EditText medicineName,barcode,amount,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_medicine);

        medicineName=findViewById(R.id.signInEmailText);
        barcode=findViewById(R.id.editTextTextPersonName2);
        amount=findViewById(R.id.editTextTextPersonName3);
        date=findViewById(R.id.editTextTextPersonName4);

        Intent intent=getIntent();
        String name=intent.getStringExtra("nameOfMedicine");
        medicineName.setText(name);
        String barcodenum=intent.getStringExtra("barcodeNumber");
        barcode.setText(barcodenum);
        String quantity=intent.getStringExtra("quantity");
        amount.setText(quantity);

        String expdate=intent.getStringExtra("expirationdate");
        date.setText(expdate);
    }
}