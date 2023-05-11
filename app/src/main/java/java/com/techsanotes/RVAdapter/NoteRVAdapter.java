package java.com.techsanotes.RVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.com.techsanotes.Model.NoteModel;
import java.com.techsanotes.R;
import java.util.ArrayList;

public class NoteRVAdapter extends FirestoreRecyclerAdapter<NoteModel, NoteRVAdapter.ViewHolder> {


    public NoteRVAdapter(@NonNull FirestoreRecyclerOptions<NoteModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull NoteModel model) {

        holder.text.setText(model.getNote());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_recycler_view_list_items, parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
        }
    }
}
