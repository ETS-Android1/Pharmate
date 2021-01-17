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

import models.OrganizationDonatedMedicineClass;


public class OrganizationDonatedMedicineAdapter extends FirestoreRecyclerAdapter<OrganizationDonatedMedicineClass, OrganizationDonatedMedicineAdapter.OrganizationDonatedMedicineHolder> {
    private OrganizationDonatedMedicineAdapter.OnItemClickListener listener;

    public OrganizationDonatedMedicineAdapter(@NonNull FirestoreRecyclerOptions<OrganizationDonatedMedicineClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrganizationDonatedMedicineAdapter.OrganizationDonatedMedicineHolder holder, int position, @NonNull OrganizationDonatedMedicineClass model) {

        holder.textViewRequestName.setText(model.getNameOfMedicine());
        holder.textViewBarcodeNumber.setText(model.getBarcodeNumber());
        holder.textViewQuantity.setText(String.valueOf(model.getQuantity()));


    }

    @NonNull
    @Override
    public OrganizationDonatedMedicineAdapter.OrganizationDonatedMedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donated_medicine_list_item,
                parent, false);
        return new OrganizationDonatedMedicineAdapter.OrganizationDonatedMedicineHolder(view);
    }

    public void setOnItemClickListener(OrganizationDonatedMedicineAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class OrganizationDonatedMedicineHolder extends RecyclerView.ViewHolder {

        TextView textViewRequestName;
        TextView textViewBarcodeNumber;
        TextView textViewQuantity;


        public OrganizationDonatedMedicineHolder(@NonNull View itemView) {
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
