package users;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
    AwesomeValidation awesomeValidation;


    private static final String TAG = "PersonalInformation";

    private DatePickerDialog.OnDateSetListener nOnDateSetListener;


    EditText  name, userSurname, userTurkishID, userContact, userAddress, userBirthDate;

    // KULLANICININ BU FORMU DOLDURDUĞUNU UYGULAMA BOYUNCA KONTROL EDİLMESİ GEREKİYOR.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_page);
        // Instance
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
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
        String regexusertype = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\d])(?=.*[~`!@#\\\\$%\\\\^&\\\\*\\\\(\\\\)\\\\-_\\\\+=\\\\{\\\\}\\\\[\\\\]\\\\|\\\\;:\\\"<>,./\\\\?]).{8,}";
        awesomeValidation.addValidation(PersonalInformation.this, R.id.userTypeText,"[a-zA-Z\\s]+", R.string.usernameerror);
        awesomeValidation.addValidation(PersonalInformation.this,R.id.turkishIdText,"[0-9]{11}+",R.string.iderror);
        awesomeValidation.addValidation(PersonalInformation.this, R.id.personNameText, "[a-zA-Z\\s]+", R.string.nameerror);
        awesomeValidation.addValidation(PersonalInformation.this, R.id.personSurnameText, "[a-zA-Z\\s]+", R.string.surnameerror);
        awesomeValidation.addValidation(PersonalInformation.this, R.id.personContactText, RegexTemplate.TELEPHONE, R.string.mobileerror);
    }


    public void submitPersonInfoClick(View v) {

        if (awesomeValidation.validate()) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            String nameText = name.getText().toString();
            String userSurnameText = userSurname.getText().toString();
            String userTurkishIDText = userTurkishID.getText().toString();
            String userAddressText = userAddress.getText().toString();
            String userContactText = userContact.getText().toString();
            String userBirthDayText = userBirthDate.getText().toString();


            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nameText + userSurnameText)
                    .build();

            firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = null;
                                UserClass userClass = document.toObject(UserClass.class);
                                System.out.println("Task Successful");
                            }
                        }
                    });

            UserClass userClassToAdd = new UserClass(nameText, userSurnameText, userTurkishIDText, userAddressText, userContactText, userBirthDayText, null);
            String userID = firebaseUser.getUid();

            HashMap<String, Object> postUserData = new HashMap<>();
            postUserData.put("name", userClassToAdd.getName());
            postUserData.put("surname", userClassToAdd.getSurname());
            postUserData.put("turkishId", userClassToAdd.getTurkishId());
            postUserData.put("contact", userClassToAdd.getContact());
            postUserData.put("address", userClassToAdd.getAddress());
            postUserData.put("birthDate", userClassToAdd.getBirthdate());
            firebaseFirestore.collection("user").document(userID).set(postUserData);
        }
    }
}






