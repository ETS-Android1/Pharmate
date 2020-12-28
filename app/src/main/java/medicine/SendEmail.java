package medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pharmate.R;

public class SendEmail extends AppCompatActivity {
    private EditText mEditTextTo;
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);
        Button buttonSend = findViewById(R.id.button_send);

        Intent intent=getIntent();
        String mail = intent.getStringExtra("email");
        mEditTextTo.setText(mail);
        mEditTextTo.setEnabled(false);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:"+ mEditTextTo.getText().toString()));
                String subject = mEditTextSubject.getText().toString();
                String message = mEditTextMessage.getText().toString();
                intent.putExtra(Intent.EXTRA_SUBJECT, mEditTextSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, mEditTextMessage.getText().toString());
                startActivity(intent);
            }
        });
    }


    }
