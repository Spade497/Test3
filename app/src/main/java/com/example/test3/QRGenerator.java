package com.example.test3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRGenerator extends AppCompatActivity {
    EditText qrvalue;
    Button generate, returnbtn;
    ImageView qrImg;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private String data;
    static FirebaseDatabase db = FirebaseDatabase.getInstance();
    static DatabaseReference databaseReference = db.getReference("List_user");
    static DatabaseReference total = FirebaseDatabase.getInstance().getReference("Total");
    static ArrayList<String> list = new ArrayList<String>();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String date = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_generator);

        qrvalue = findViewById(R.id.qrValue);
        generate = findViewById(R.id.generateQR);
        returnbtn = findViewById(R.id.backbtn);
        qrImg = findViewById(R.id.qrImage);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = qrvalue.getText().toString().trim();

                firebaseFirestore.collection("List_user").whereEqualTo("Phone", data).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().isEmpty()) {
                                    GenerateQR();
                                    StoreToFirestore();
                                    StoreToRTDB();
                                } else {
                                    GenerateQR();
                                    Toast.makeText(getApplicationContext(), "Guest exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

    public void GenerateQR() {
        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 500);
        Bitmap qrBits = qrgEncoder.getBitmap();
        qrImg.setImageBitmap(qrBits);
    }

    public void StoreToFirestore() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("Phone", data);
        userMap.put("Date", date);
        firebaseFirestore.collection("List_user").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Guest Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void StoreToRTDB() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    list.add((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        list.add(data);
        databaseReference.setValue(list);
        total.setValue(list.size());
    }

}
