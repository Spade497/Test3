package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class lockstatus extends AppCompatActivity {
    ToggleButton lockbtn2;
    Button backbtn;
    Snackbar mysnackbar;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = db.getReference("Unlock");
    int val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockstatus);

        lockbtn2   = findViewById(R.id.toggleButton1);
        backbtn    = findViewById(R.id.backbtn2);

        lockbtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mysnackbar = Snackbar.make(findViewById(R.id.LinearLayout),"DOOR is now UNLOCK", BaseTransientBottomBar.LENGTH_SHORT);
                    mysnackbar.show();
                    val=0;
                    RTDB(val);
                }
                else{
                    mysnackbar = Snackbar.make(findViewById(R.id.LinearLayout),"DOOR is now LOCK", BaseTransientBottomBar.LENGTH_SHORT);
                    mysnackbar.show();
                    val=1;
                    RTDB(val);
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }

    public void RTDB(int val) {
        databaseReference.setValue(val);
    }
}