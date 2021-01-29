package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GuestList extends AppCompatActivity {
    Button returnbtn;
    ImageButton deletebtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Ref = db.collection("List_user");
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerList);
        returnbtn = findViewById(R.id.backbtn3);
        deletebtn = findViewById(R.id.deletebtn);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //Query
        Query query = Ref.orderBy("Date", Query.Direction.DESCENDING);

        //Recycler Option
        FirestoreRecyclerOptions<List_guest> options = new FirestoreRecyclerOptions.Builder<List_guest>()
                .setQuery(query, List_guest.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<List_guest, List_guestHolder>(options) {
            @NonNull
            @Override
            public List_guestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                return new List_guestHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull List_guestHolder holder, int position, @NonNull List_guest model) {
                holder.textView.setText(String.valueOf(model.getPhone()));
                holder.textView2.setText(String.valueOf(model.getDate()));
            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    //View Holder
    public class List_guestHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public List_guestHolder(@NonNull View itemView) {
            super(itemView);
            textView    = itemView.findViewById(R.id.textView_pnum);
            textView2   = itemView.findViewById(R.id.textView_time);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void openDialog() {
        DeleteGuest deleteGuest = new DeleteGuest();
        deleteGuest.show(getSupportFragmentManager(), "Delete Guest num");
    }

}