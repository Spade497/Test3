package com.example.test3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class DeleteGuest extends AppCompatDialogFragment {
    EditText deleteGuest;
    private String data;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private DatabaseReference total;
    private ArrayList<String> list = new ArrayList<String>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        this.db = QRGenerator.db;
        this.databaseReference = QRGenerator.databaseReference;
        this.total = QRGenerator.total;
        this.list = QRGenerator.list;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.deleteguest, null);

        deleteGuest = view.findViewById(R.id.deleteNum);

        builder.setView(view)
                .setTitle("Delete Guest")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data = deleteGuest.getText().toString().trim();
                        DeleteGuestinFirestore();
                        DeleteGuestinRTDB(data);
                    }
                });

        return builder.create();
    }

    private void DeleteGuestinFirestore() {
        firebaseFirestore.collection("List_user").whereEqualTo("Phone", data).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        WriteBatch writeBatch = FirebaseFirestore.getInstance().batch();
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot: snapshotList){
                            writeBatch.delete(snapshot.getReference());
                        }
                        writeBatch.commit()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(data,"Deleted");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(data, "Error: "+e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(data,"Error: "+e);
                    }
                });
    }

    private void DeleteGuestinRTDB(String data) {

        list.remove(data);
        databaseReference.setValue(list);
        total.setValue(list.size());
    }

}