package medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmate.R;

import location.LocationActivity;

public class ReceiveMedicine extends AppCompatActivity {
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
//
        startActivity(receiveOptionsIntent);

    }
}