package java.com.techsanotes.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.com.techsanotes.R;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    TextView signUpTextView;
    Button signInButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpTextView = findViewById(R.id.signUpTextView);
        signInButton = findViewById(R.id.signInButton);
        progressBar = findViewById(R.id.progressBar);

        signUpTextView.setOnClickListener(v -> switchToSignUpActivity());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Incomplete Credentials", Toast.LENGTH_SHORT).show();
                }
                else logInUser(emailEditText.getText().toString(),passwordEditText.getText().toString());
            }
        });
    }



    private void logInUser(String email, String password){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    switchToMainActivity();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Authentication failed. Please try again.")
                            .setTitle("Login Failed")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    private void switchToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void switchToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity(); // close all activities of the app
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}