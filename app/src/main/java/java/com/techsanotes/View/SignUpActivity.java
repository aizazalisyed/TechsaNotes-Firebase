package java.com.techsanotes.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button signUpButton;
    ProgressBar progressBar;
    TextView signIpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progressBar);
        signIpTextView = findViewById(R.id.signIpTextView);


        signIpTextView.setOnClickListener(v -> switchToLoginActivity());

        signUpButton.setOnClickListener(v -> {

            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View focusedView = getCurrentFocus();
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);}

            if(emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){
                Toast.makeText(this, "Incomplete Credentials", Toast.LENGTH_SHORT).show();
            }
            else createAccount(emailEditText.getText().toString(),passwordEditText.getText().toString());
        });

    }

    private void createAccount(String email, String password){

        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    switchToLoginActivity();
                }
                else{
                    Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void switchToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}