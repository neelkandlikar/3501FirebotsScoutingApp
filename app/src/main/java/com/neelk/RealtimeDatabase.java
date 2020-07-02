package com.neelk.robotics;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class RealtimeDatabase {

    private Activity activity;
    private DatabaseReference reference;
    private String competiton;
    private boolean success = false;


    public RealtimeDatabase(Activity activity, String competition) {
        this.activity = activity;
        this.competiton = competition;
        reference = FirebaseDatabase.getInstance().getReference("Competitions");

    }

    public void storeData(Map<String, Object> map) {
        String teamScouting = (String) (activity.getString(R.string.team)) + " " + map.get(activity.getString(R.string.teamScouting));
        String matchNumber = (String) activity.getString(R.string.matchNumber) + " " + map.get(activity.getString(R.string.matchNumber));
        reference.child(competiton).child(teamScouting).child(matchNumber).setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        success = true;
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

    public boolean isSuccess() {
        return success;
    }
}
