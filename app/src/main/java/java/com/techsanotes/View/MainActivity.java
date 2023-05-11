package java.com.techsanotes.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.com.techsanotes.Model.NoteModel;
import java.com.techsanotes.R;
import java.com.techsanotes.RVAdapter.NoteRVAdapter;
import java.com.techsanotes.Utility.Utility;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    NoteRVAdapter noteRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        setupRecyclerView();

        fab.setOnClickListener(view -> showDialog());
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add_note);
        dialog.setTitle("Add Note");

        TextView btnCancel = dialog.findViewById(R.id.cancelTextView);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);
        final EditText editTextNote = dialog.findViewById(R.id.editTextNote);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editTextNote.getText().toString();
                NoteModel noteModel = new NoteModel();
                noteModel.setNote(content);
                saveNoteToFireBase(noteModel);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void saveNoteToFireBase(NoteModel note){

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReference().document();

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Note Successfully added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupRecyclerView(){

        Query query  = Utility.getCollectionReference();
        FirestoreRecyclerOptions<NoteModel> options = new FirestoreRecyclerOptions.Builder<NoteModel>()
                .setQuery(query, NoteModel.class).build();

        noteRVAdapter = new NoteRVAdapter(options);
        recyclerView.setAdapter(noteRVAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteRVAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteRVAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}