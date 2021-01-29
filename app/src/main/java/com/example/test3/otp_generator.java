package com.example.test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class otp_generator extends AppCompatActivity {
    Button generate, returnbtn;
    TextView textView;
    Random random = new Random();
    int otpvalue;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = db.getReference("PIN");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generator);

        generate    = findViewById(R.id.generateOTP);
        returnbtn   = findViewById(R.id.backbtn4);
        textView    = findViewById(R.id.OTPvalue);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpvalue = random.nextInt(9000);
                otpvalue += 1000;
                textView.setText(String.valueOf(otpvalue));
                StoreToRTDB();
            }
        });

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    public void StoreToRTDB() {
        databaseReference.setValue(otpvalue);
    }
}