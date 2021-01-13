package medicine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pharmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import location.LocationActivity;

import static users.PersonalInformation.imageUri;

public class ReceiveMedicine extends AppCompatActivity {
    EditText medicineName, barcode, amount, date;
    String userID, name, receiveBarcodenum, recQuantity;
    Button confirm;
    ImageView prescription;
    public static Uri prscrptnUri;
    Bitmap choosenImage;
    String userId;


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_medicine);

        medicineName = findViewById(R.id.signInEmailText);
        barcode = findViewById(R.id.editTextTextPersonName2);
        amount = findViewById(R.id.editTextTextPersonName3);
        date = findViewById(R.id.editTextTextPersonName4);
        confirm = findViewById(R.id.button5);
        prescription=findViewById(R.id.imageprescription);
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();


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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            prscrptnUri = data.getData();

            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), prscrptnUri);
                    choosenImage = ImageDecoder.decodeBitmap(source);
                    prescription.setImageBitmap(choosenImage);
                } else {
                    choosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), prscrptnUri);
                    prescription.setImageBitmap(choosenImage);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void selectedImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }
    public void selectPrescription(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }

}