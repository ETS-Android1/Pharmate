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

import models.UserClass;


public class PersonAdapter extends FirestoreRecyclerAdapter<UserClass, PersonAdapter.PersonHolder> {

    private OnItemClickListener listener;

    public PersonAdapter(@NonNull FirestoreRecyclerOptions<UserClass> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull PersonAdapter.PersonHolder holder, int position, @NonNull UserClass model) {

        holder.textViewName.setText(model.getName());
        System.out.println("Name" + model.getName());
        holder.textViewSurname.setText(model.getSurname());
        System.out.println("Surname" + model.getSurname());

    }
    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personitem,
                parent, false);
        return new PersonAdapter.PersonHolder(view);
    }

    class PersonHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewSurname;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_title1);
            textViewSurname = itemView.findViewById(R.id.text_view_description2);

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

