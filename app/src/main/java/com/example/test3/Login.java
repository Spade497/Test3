package com.example.test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {
    EditText l_email, l_password;
    Button l_button;
    TextView to_r_button;
    FirebaseAuth fAuth;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        l_email     = findViewById(R.id.email);
        l_password  = findViewById(R.id.password);
        l_button    = findViewById(R.id.submit);
        bar         = findViewById(R.id.pb_l);
        fAuth       = FirebaseAuth.getInstance();
        to_r_button = findViewById(R.id.gotoReg);

        bar.setMax(100);
        bar.setScaleY(2f);

        l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = l_email.getText().toString().trim();
                String password = l_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    l_email.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    l_password.setError("Password is Required.");
                    return;
                }
                if(password.length() < 8) {
                    l_password.setError("Wrong Password");
                    return;
                }
                bar.setVisibility(View.VISIBLE);

                //get user info from firebase
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });

        to_r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }

}