package location;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmate.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import models.UserClass;

public class UserLocationOptionsAdapter extends FirestoreRecyclerAdapter<UserClass, UserLocationOptionsAdapter.UserLocationHolder> {
    public UserLocationOptionsAdapter(@NonNull FirestoreRecyclerOptions<UserClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserLocationOptionsAdapter.UserLocationHolder holder, int position, @NonNull UserClass model) {

//        holder.textViewManager.setText(model.getOrganizationName());
//        System.out.println("OrgName" + model.getOrganizationName());
//        holder.textViewCity.setText(model.getCity());
//        System.out.println("City" + model.getCity());

    }

    @NonNull
    @Override
    public UserLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_options,
                parent, false);
        return new UserLocationOptionsAdapter.UserLocationHolder(view);
    }

    class UserLocationHolder extends RecyclerView.ViewHolder {

        TextView textViewManager;
        TextView textViewCity;

        public UserLocationHolder(@NonNull View itemView) {
            super(itemView);
            textViewManager = itemView.findViewById(R.id.text_view_title);
            textViewCity = itemView.findViewById(R.id.text_view_description);


        }

    }

}