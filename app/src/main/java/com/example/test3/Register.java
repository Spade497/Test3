package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText r_fullName, r_email, r_password, r_phone;
    Button r_button;
    TextView to_l_button;
    FirebaseAuth fAuth;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        r_fullName  = findViewById(R.id.fullName);
        r_email     = findViewById(R.id.email);
        r_password  = findViewById(R.id.password);
        r_phone     = findViewById(R.id.phone);
        r_button    = findViewById(R.id.submit);
        to_l_button = findViewById(R.id.gotoLog);
        bar         = findViewById(R.id.pb_r);
        fAuth       = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = r_email.getText().toString().trim();
                String password = r_password.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    r_email.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    r_password.setError("Password is Required.");
                    return;
                }
                if(password.length() < 8) {
                    r_password.setError("Password MUST be >= 8 characters");
                    return;
                }
                bar.setVisibility(View.VISIBLE);

                //register user into firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

        });

        to_l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}