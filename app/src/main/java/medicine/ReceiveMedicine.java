package medicine;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pharmate.Loadingbar;
import com.example.pharmate.R;

public class ReceiveMedicine extends AppCompatActivity {
    Button receive;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_medicine);

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
    }
}