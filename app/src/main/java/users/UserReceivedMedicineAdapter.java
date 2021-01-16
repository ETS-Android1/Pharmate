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

import models.UserReceivedMedicineClass;

public class UserReceivedMedicineAdapter extends FirestoreRecyclerAdapter<UserReceivedMedicineClass, UserReceivedMedicineAdapter.UserReceivedMedicineHolder> {
    private OnItemClickListener listener;

    public UserReceivedMedicineAdapter(@NonNull FirestoreRecyclerOptions<UserReceivedMedicineClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserReceivedMedicineAdapter.UserReceivedMedicineHolder holder, int position, @NonNull UserReceivedMedicineClass model) {

        holder.textViewRequestName.setText(model.getNameOfMedicine());
        holder.textViewBarcodeNumber.setText(model.getBarcodeNumber());
        holder.textViewQuantity.setText(String.valueOf(model.getQuantity()));


    }

    @NonNull
    @Override
    public UserReceivedMedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donated_medicine_list_item,
                parent, false);
        return new UserReceivedMedicineAdapter.UserReceivedMedicineHolder(view);
    }

    class UserReceivedMedicineHolder extends RecyclerView.ViewHolder {

        TextView textViewRequestName;
        TextView textViewBarcodeNumber;
        TextView textViewQuantity;


        public UserReceivedMedicineHolder(@NonNull View itemView) {
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

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
