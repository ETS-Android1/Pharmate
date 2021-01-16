package users;

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

import models.UserDonatedMedicineClass;

public class DonatedMedicineAdapter extends FirestoreRecyclerAdapter<UserDonatedMedicineClass, DonatedMedicineAdapter.UserDonatedMedicineHolder> {
    private OnItemClickListener listener;

    public DonatedMedicineAdapter(@NonNull FirestoreRecyclerOptions<UserDonatedMedicineClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserDonatedMedicineHolder holder, int position, @NonNull UserDonatedMedicineClass model) {

        holder.textViewRequestName.setText(model.getNameOfMedicine());
        holder.textViewBarcodeNumber.setText(model.getBarcodeNumber());
        holder.textViewQuantity.setText(String.valueOf(model.getQuantity()));
        System.out.println(model.getQuantity());

    }

    @NonNull
    @Override
    public UserDonatedMedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donated_medicine_list_item,
                parent, false);
        return new UserDonatedMedicineHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class UserDonatedMedicineHolder extends RecyclerView.ViewHolder {

        TextView textViewRequestName;
        TextView textViewBarcodeNumber;
        TextView textViewQuantity;


        public UserDonatedMedicineHolder(@NonNull View itemView) {
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
