package com.example.test3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LogBook extends AppCompatActivity {
    Button returnbtn;
    RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = db.getReference().child("Record");
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        returnbtn = findViewById(R.id.backbtn5);
        recyclerView = findViewById(R.id.recyclerLogBook);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
/*
        textView = findViewById(R.id.textView7);
        Query query = databaseReference.orderByChild("1");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_record = snapshot.child("1").getValue(String.class);
                textView.setText(String.valueOf(user_record));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {       }
        });*/

        //Query query = databaseReference.orderByKey().equalTo("1");
        FirebaseRecyclerOptions<Records> options = new FirebaseRecyclerOptions.Builder<Records>()
                .setQuery(databaseReference, Records.class)
                .build();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FirebaseRecyclerAdapter<Records, myviewholder>(options) {
            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
                return new myviewholder(view2);
            }

            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Records model) {
                holder.t1.setText(String.valueOf(model.getUser()));
                holder.t2.setText(String.valueOf(model.getStatus()));
                holder.t3.setText(String.valueOf(model.getTime()));
            }
        };
        recyclerView.setAdapter(adapter);

    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.textView_user);
            t2 = itemView.findViewById(R.id.textView_status);
            t3 = itemView.findViewById(R.id.textView_timestamp);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}