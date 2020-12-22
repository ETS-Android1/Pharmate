package users;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;

import models.UserClass;

import static android.graphics.Color.TRANSPARENT;

public class PersonalInformation extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    AwesomeValidation awesomeValidation;


    private static final String TAG = "PersonalInformation";

    private DatePickerDialog.OnDateSetListener nOnDateSetListener;


    EditText name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;
    String nameperson, surnameperson, turkisIdperson, contactperson, birthdateperson, addressperson;
    Button update;

    // KULLANICININ BU FORMU DOLDURDUĞUNU UYGULAMA BOYUNCA KONTROL EDİLMESİ GEREKİYOR.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_page);
        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("user");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

//
//        //defining textFields
        name = findViewById(R.id.personNameText);
        userSurname = findViewById(R.id.personSurnameText);
        userTurkishID = findViewById(R.id.turkishIdText);
        userContact = findViewById(R.id.personContactText);
        userAddress = findViewById(R.id.personAddressText);
        userBirthDate = findViewById(R.id.birthDateText);
        update=findViewById(R.id.button3);
        userBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PersonalInformation.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        nOnDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
                dialog.show();
            }
        });
        nOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                userBirthDate.setText(date);
            }
        };

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            Log.d(TAG, "onCreate:"+user.getDisplayName());
            if (user.getDisplayName() != null) {
                name.setText(user.getDisplayName());
                name.setSelection(user.getDisplayName().length());
                userSurname.setText(user.getDisplayName());
                userSurname.setSelection(user.getDisplayName().length());
                userTurkishID.setText(user.getDisplayName());
                userTurkishID.setSelection(user.getDisplayName().length());
                userContact.setText(user.getDisplayName());
                userContact.setSelection(user.getDisplayName().length());
                userAddress.setText(user.getDisplayName());
                userAddress.setSelection(user.getDisplayName().length());
                userBirthDate.setText(user.getDisplayName());
                userBirthDate.setSelection(user.getDisplayName().length());


            }
        }
    }



}












