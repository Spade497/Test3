package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton QRG_btn, lock_btn, List_btn, OTPG_btn, logbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRG_btn     = findViewById(R.id.gotoQRG);
        lock_btn    = findViewById(R.id.gotoLock);
        List_btn    = findViewById(R.id.gotoGlist);
        OTPG_btn    = findViewById(R.id.gotoOTPG);
        logbook     = findViewById(R.id.gotoLogBook);

        QRG_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QRGenerator.class));
                finish();
            }
        });

        lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),lockstatus.class));
                finish();
            }
        });

        List_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GuestList.class));
                finish();
            }
        });

        OTPG_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),otp_generator.class));
                finish();
            }
        });

        logbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogBook.class));
                finish();
            }
        });

    }

}