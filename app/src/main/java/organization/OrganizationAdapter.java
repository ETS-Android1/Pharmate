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

import medicine.MedicineAdapter;
import models.MedicineClass;
import models.OrganizationClass;

public class OrganizationAdapter extends FirestoreRecyclerAdapter<OrganizationClass, OrganizationAdapter.OrganizationHolder> {
    private MedicineAdapter.OnItemClickListener listener;
    public OrganizationAdapter(@NonNull FirestoreRecyclerOptions<OrganizationClass> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull OrganizationAdapter.OrganizationHolder holder, int position, @NonNull OrganizationClass model) {

        holder.textViewManager.setText(model.getManager());
        holder.textViewCity.setText(model.getCity());
        holder.textViewEmail.setText(String.valueOf(model.getEmail()));

    }
    @NonNull
    @Override
    public OrganizationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orgitem,
                parent,false);
        return new OrganizationAdapter.OrganizationHolder(view);
    }

    class OrganizationHolder extends RecyclerView.ViewHolder {

        TextView textViewManager;
        TextView textViewCity;
        TextView textViewEmail;

        public OrganizationHolder(@NonNull View itemView) {
            super(itemView);
            textViewManager = itemView.findViewById(R.id.text_view_title);
            textViewCity = itemView.findViewById(R.id.text_view_description);
            textViewEmail = itemView.findViewById(R.id.text_view_priority);

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
