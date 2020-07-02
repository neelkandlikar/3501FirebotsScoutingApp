package com.neelk.robotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Name extends AppCompatActivity {

    private Button scoutButton;
    private Button superScoutButton;
    private String name;
    private EditText nameEditText;
    private EditText teamScoutingEditText;
    private EditText matchNumberEditText;
    private String teamScouting;
    private String matchNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        nameEditText = findViewById(R.id.nameEditText);
        teamScoutingEditText = findViewById(R.id.teamScoutingEditText);
        matchNumberEditText = findViewById(R.id.matchNumberEditText);
        scoutButton = findViewById(R.id.scoutButton);
        superScoutButton = findViewById(R.id.superScoutButton);
        scoutButton.setOnClickListener(scoutOnClick);
        superScoutButton.setOnClickListener(superScoutOnClick);


    }

    private View.OnClickListener scoutOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(String.valueOf(nameEditText.getText()).trim()) || TextUtils.isEmpty(String.valueOf(teamScoutingEditText.getText()).trim()) || TextUtils.isEmpty(String.valueOf(matchNumberEditText.getText()).trim())) {
                Toast.makeText(Name.this, "Please Fill In All Fields!", Toast.LENGTH_SHORT).show();

            } else {


                name = String.valueOf(nameEditText.getText()).trim().toLowerCase();
                teamScouting = String.valueOf(teamScoutingEditText.getText()).trim();
                matchNumber = String.valueOf(matchNumberEditText.getText()).trim();
                Intent intent = new Intent(Name.this, ScoutingTabAdapter.class);
                intent.putExtra(getString(R.string.competitionLocation), getIntent().getStringExtra(getString((R.string.competitionLocation))));
                intent.putExtra(getString(R.string.name), name);
                intent.putExtra(getString(R.string.teamScouting), teamScouting);
                intent.putExtra(getString(R.string.matchNumber), matchNumber);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener superScoutOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(String.valueOf(nameEditText.getText()).trim()) || TextUtils.isEmpty(String.valueOf(teamScoutingEditText.getText()).trim()) || TextUtils.isEmpty(String.valueOf(matchNumberEditText.getText()).trim())) {
                Toast.makeText(Name.this, "Please Fill In All Fields!", Toast.LENGTH_SHORT).show();
            } else {
                name = String.valueOf(nameEditText.getText()).trim().toLowerCase();
                String[] splitTeams;
                String teams = (String.valueOf(teamScoutingEditText.getText()).trim());
                if (teams.contains(",") && teams.contains(" ") || !teams.contains(",") && !teams.contains(" ")) {
                    Toast.makeText(Name.this, "Please enter the Teams Scouting seperated by a single space or comma", Toast.LENGTH_LONG).show();
                    return;
                } else if (teams.contains(",")) {
                    splitTeams = teams.split(",");
                    sendIntent(splitTeams);

                } else if (teams.contains(" ")) {
                    splitTeams = teams.split(" ");
                    sendIntent(splitTeams);

                }

            }

        }
    };

    private void sendIntent(String[] splitTeams) {
        ArrayList<String> teamsScouting = new ArrayList<>();
        teamsScouting.addAll(Arrays.asList(splitTeams));
        Log.e("teams Scouting", teamsScouting.toString());
        matchNumber = String.valueOf(matchNumberEditText.getText()).trim();
        Intent intent = new Intent(Name.this, SuperScoutTabAdapter.class);
        intent.putExtra(getString(R.string.competitionLocation), getIntent().getStringExtra(getString((R.string.competitionLocation))));
        intent.putExtra("Super Scout Name", name);
        intent.putExtra(getString(R.string.teamScouting), teamsScouting);
        intent.putExtra(getString(R.string.matchNumber), matchNumber);
        startActivity(intent);
    }

}
