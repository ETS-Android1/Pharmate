package com.example.pharmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MedicineAdapter extends FirestoreRecyclerAdapter<Medicine,MedicineAdapter.MedicineHolder>{


    public MedicineAdapter(@NonNull FirestoreRecyclerOptions<Medicine> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MedicineHolder holder, int position, @NonNull Medicine model) {
        holder.textViewName.setText(model.getName());
        holder.textViewBarcodeNumber.setText(model.getBarcodeNumber());
        holder.textViewAmount.setText(model.getAmount());
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicineitem,
                parent,false);
        return new MedicineHolder(v);
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
