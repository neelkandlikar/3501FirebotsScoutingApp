package com.neelk.robotics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class SendToDatabase {

    private Context context;
    private DatabaseReference databaseReference;

    //database
    public SendToDatabase(Context context) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void sendData(HashMap<String, Object> dataToSend) {
        String competitionLocation = (String) dataToSend.get(context.getString(R.string.competitionLocation));
        Log.e(competitionLocation, "e");
        String matchNumber = (String) dataToSend.get(context.getString(R.string.matchNumber));
        boolean isSuperScout = (boolean) dataToSend.get(context.getString(R.string.isSuperScout));
        if (!isSuperScout) {
            sendScoutData(competitionLocation, matchNumber, dataToSend);
        } else {
            sendSuperScoutData(competitionLocation, matchNumber, dataToSend);
        }
        Log.e("Mpa", dataToSend.toString());
        Log.e("match number pt 2", matchNumber);


    }

    private void sendSuperScoutData(String competitionLocation, String matchNumber, HashMap<String, Object> dataToSend) {
        Log.e("scannedData", dataToSend.toString());


        ArrayList<String> teams = (ArrayList<String>) dataToSend.get(context.getString(R.string.teamScouting));

        TreeMap<String, Object> team1Data = new TreeMap<>();
        TreeMap<String, Object> team2Data = new TreeMap<>();
        TreeMap<String, Object> team3Data = new TreeMap<>();

        addBasicInfo(team1Data, dataToSend);
        addBasicInfo(team2Data, dataToSend);
        addBasicInfo(team3Data, dataToSend);

        HashMap<String, TreeMap<String, Object>> treeMapHashMap = new HashMap<>();
        treeMapHashMap.put("team1Data", team1Data);
        treeMapHashMap.put("team2Data", team2Data);
        treeMapHashMap.put("team3Data", team3Data);


        populateTeamData(dataToSend, team1Data, team2Data, team3Data, teams);
//


        for (int i = 0; i < teams.size(); i++) {

            Log.e("LOOP", "IN LOOP");
            TreeMap<String, Object> currentTeamMap = treeMapHashMap.get("team" + (i + 1) + "Data");
            currentTeamMap.remove(context.getString(R.string.competitionLocation));
            currentTeamMap.remove(context.getString(R.string.matchNumber));
            currentTeamMap.remove(context.getString(R.string.teamScouting));
            currentTeamMap.remove(context.getString(R.string.isSuperScout));

            databaseReference.child(competitionLocation).child(teams.get(i)).child(matchNumber).updateChildren(treeMapHashMap.get("team" + (i + 1) + "Data"))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Data was Sent!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Error sending to Database", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void addBasicInfo(TreeMap<String, Object> treeMap, HashMap<String, Object> dataMap) {

        treeMap.put("Super Scout Name", dataMap.get("Super Scout Name"));
        treeMap.put(context.getString(R.string.competitionLocation), dataMap.get(context.getString(R.string.competitionLocation)));
        treeMap.put(context.getString(R.string.matchNumber), dataMap.get(context.getString(R.string.matchNumber)));
        treeMap.put(context.getString(R.string.teamScouting), dataMap.get(context.getString(R.string.teamScouting)));
    }

    private void sendScoutData(String competitionLocation, String matchNumber, HashMap<String, Object> dataToSend) {
        Log.e("LOOP", "SCOUT DATA");
        Log.e("scannedData", dataToSend.toString());

        String teamScouting = (String) dataToSend.get(context.getString(R.string.teamScouting));
        dataToSend.remove(context.getString(R.string.isSuperScout));


        databaseReference.child(competitionLocation).child(teamScouting).child(matchNumber).updateChildren(dataToSend)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Data was successfully sent!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error sending to Database", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void populateTeamData(HashMap<String, Object> dataToSend, TreeMap<String, Object> team1Data, TreeMap<String, Object> team2Data, TreeMap<String, Object> team3Data, ArrayList<String> teams) {
        for (String key : dataToSend.keySet()) {
            boolean failed = false;
            for (int i = 0; i < key.length(); i++) {
                if (Character.isDigit(key.charAt(i)) && !teams.get(0).contains(String.valueOf(key.charAt(i)))) {
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                team1Data.put(key, dataToSend.get(key));
            }
        }

        for (String key : dataToSend.keySet()) {
            boolean failed = false;
            for (int i = 0; i < key.length(); i++) {
                if (Character.isDigit(key.charAt(i)) && !teams.get(1).contains(String.valueOf(key.charAt(i)))) {
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                team2Data.put(key, dataToSend.get(key));
            }
        }

        for (String key : dataToSend.keySet()) {
            boolean failed = false;
            for (int i = 0; i < key.length(); i++) {
                if (Character.isDigit(key.charAt(i)) && !teams.get(2).contains(String.valueOf(key.charAt(i)))) {
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                team3Data.put(key, dataToSend.get(key));
            }
        }
    }
}
