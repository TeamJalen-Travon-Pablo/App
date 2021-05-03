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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText fullName, email, password;
    TextView goToLogin;
    Button signUp;
    ProgressDialog progressDialog;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = fullName.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                if (f.isEmpty() || e.isEmpty() || p.isEmpty()) {
                    Toast.makeText(MainActivity.this, "This field's can't be empty", Toast.LENGTH_SHORT).show();
                } else if (p.length() < 6) {
                    Toast.makeText(MainActivity.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(f, e, p);
                }
            }
        });
    }

    private void init() {
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        goToLogin = findViewById(R.id.goToLogin);
        signUp = findViewById(R.id.signUp);
    }

    private void createAccount(String f, String e, String p) {
        progressDialog=new ProgressDialog(MainActivity.this); 
        progressDialog.setTitle("Creating New Account");
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        Task<AuthResult> authResultTask = auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Verify
                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Please verify email", Toast.LENGTH_SHORT).show();
                                saveData(f, e, p);
                            } else {
                                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }


                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            private void saveData(String f, String e, String p) {
                user = auth.getCurrentUser();
                HashMap<String, Object> map = new HashMap<>();
                map.put("full_name", f)
                map.put("email", e);
                map.put("password", p);
                map.put("uid", user.getUid());
                reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            Toast.makeText(MainActivity.this, "Account created!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}