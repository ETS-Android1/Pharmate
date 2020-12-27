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

import models.OrganizationClass;

public class OrganizationAdapter extends FirestoreRecyclerAdapter<OrganizationClass, OrganizationAdapter.OrganizationHolder> {
    public OrganizationAdapter(@NonNull FirestoreRecyclerOptions<OrganizationClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrganizationAdapter.OrganizationHolder holder, int position, @NonNull OrganizationClass model) {

        holder.textViewManager.setText(model.getOrganizationName());
        System.out.println("OrgName" + model.getOrganizationName());
        holder.textViewCity.setText(model.getCity());
        System.out.println("City" + model.getCity());

    }

    @NonNull
    @Override
    public OrganizationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orgitem,
                parent, false);
        return new OrganizationAdapter.OrganizationHolder(view);
    }

    class OrganizationHolder extends RecyclerView.ViewHolder {

        TextView textViewManager;
        TextView textViewCity;

        public OrganizationHolder(@NonNull View itemView) {
            super(itemView);
            textViewManager = itemView.findViewById(R.id.text_view_title);
            textViewCity = itemView.findViewById(R.id.text_view_description);


        }

    }

}
