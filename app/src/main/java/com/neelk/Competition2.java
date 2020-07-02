package com.neelk.robotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

public class Competition2 extends AppCompatActivity {

    private GoogleMap mMap;
    private double LAT;
    private double LNG;
    private String competitionTwoLocation;
    private Button scoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition2);

        LAT = Double.parseDouble(getString(R.string.competitionTwoLat));
        LNG = Double.parseDouble(getString(R.string.competitionTwoLng));
        scoutButton = findViewById(R.id.scoutButton);
        scoutButton.setOnClickListener(scoutOnClick);
        competitionTwoLocation = this.getString(R.string.competitionTwoLocation);

//        if (this.mMap == null) {
//            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            mapFrag.getMapAsync(this);
//
//        }
//    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        LatLng compLoc = new LatLng(LAT, LNG);
//        mMap.addMarker(new MarkerOptions().position(compLoc).title(competitionTwoLocation + " Competition"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(compLoc));
//        CameraPosition camera_position = new CameraPosition.Builder().target(new LatLng(LAT, LNG)).zoom(10).build();
//        CameraUpdate updateCamera = CameraUpdateFactory.newCameraPosition(camera_position);
//        googleMap.animateCamera(updateCamera);
//    }

    }

    private View.OnClickListener scoutOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Competition2.this, Name.class);
            intent.putExtra(getString(R.string.competitionLocation), competitionTwoLocation);
            startActivity(intent);
        }
    };

}
