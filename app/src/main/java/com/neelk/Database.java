package com.neelk.robotics;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Database {


    private Activity activity;
    private final String NAME = "Name";


    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public Database(Activity activity) {
        this.activity = activity;
    }


    public void storeData(Map<String, Object> map, String competition) {
        String documentName = map.get(NAME) + "" + (int) (System.currentTimeMillis() / 1000);
        System.out.println(competition);
        database.collection(competition).document(documentName).set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Saving Data Failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void storeDataMove(Map<String, Object> map, String competition) {
        String documentName = map.get(NAME) + "" + (int) (System.currentTimeMillis() / 1000);
        System.out.println(competition);
        database.collection(competition).document(documentName).set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show();
                        activity.startActivity(new Intent(activity, Competitions.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Saving Data Failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
