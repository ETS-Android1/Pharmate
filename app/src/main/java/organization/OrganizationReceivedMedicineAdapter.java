package organization;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import models.OrganizationReceivedMedicineClass;

public class OrganizationReceivedMedicineAdapter extends FirestoreRecyclerAdapter<OrganizationReceivedMedicineClass, OrganizationReceivedMedicineAdapter.OrganizationReceivedMedicineHolder> {
    private OnItemClickListener listener;

    public OrganizationReceivedMedicineAdapter(@NonNull FirestoreRecyclerOptions<OrganizationReceivedMedicineClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrganizationReceivedMedicineAdapter.OrganizationReceivedMedicineHolder holder, int position, @NonNull OrganizationReceivedMedicineClass model) {

        holder.textViewRequestName.setText(model.getNameOfMedicine());
        holder.textViewBarcodeNumber.setText(model.getBarcodeNumber());
        holder.textViewQuantity.setText(String.valueOf(model.getQuantity()));


    }

    @NonNull
    @Override
    public OrganizationReceivedMedicineAdapter.OrganizationReceivedMedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donated_medicine_list_item,
                parent, false);
        return new OrganizationReceivedMedicineAdapter.OrganizationReceivedMedicineHolder(view);
    }

    public void setOnItemClickListener(OrganizationReceivedMedicineAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class OrganizationReceivedMedicineHolder extends RecyclerView.ViewHolder {

        TextView textViewRequestName;
        TextView textViewBarcodeNumber;
        TextView textViewQuantity;


        public OrganizationReceivedMedicineHolder(@NonNull View itemView) {
            super(itemView);
            textViewRequestName = itemView.findViewById(R.id.request_title);
            textViewBarcodeNumber = itemView.findViewById(R.id.request_description);
            textViewQuantity = itemView.findViewById(R.id.request_quantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
}
