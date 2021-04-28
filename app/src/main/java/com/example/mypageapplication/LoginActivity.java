package com.example.mypageapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    TextView goToSignUp;
    Button login;
    ProgressDialog progressDialog;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                if (e.isEmpty() ||p.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "This field's can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    signIn(e,p);
                }
            }
        });
    }

    private void signIn(String e, String p) {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
            }else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Please verify email", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }


    private void init() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        goToSignUp = findViewById(R.id.goToSignUp);
        login = findViewById(R.id.login);
    }

}