package medicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import models.MedicineClass;

public class MedicineAdapter extends FirestoreRecyclerAdapter<MedicineClass,MedicineAdapter.MedicineHolder>{


    public MedicineAdapter(@NonNull FirestoreRecyclerOptions<MedicineClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MedicineHolder holder, int position, @NonNull MedicineClass model) {

        holder.textViewName.setText(model.getNameOfMedicine());
        holder.textViewBarcodeNumber.setText(model.getBarcodeNumber());
        holder.textViewAmount.setText(String.valueOf(model.getQuantity()));

    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicineitem,
                parent,false);
        return new MedicineHolder(view);
    }

    class MedicineHolder extends RecyclerView.ViewHolder{

    TextView textViewName;
    TextView textViewBarcodeNumber;
    TextView textViewAmount;


    public MedicineHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.text_view_title);
        textViewBarcodeNumber = itemView.findViewById(R.id.text_view_description);
        textViewAmount = itemView.findViewById(R.id.text_view_priority);

    }
}
}
