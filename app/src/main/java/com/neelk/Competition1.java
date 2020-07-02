package com.neelk.robotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

public class Competition1 extends AppCompatActivity {

    private GoogleMap mMap;
    private double LAT;
    private double LNG;
    private String competitionOneLocation;
    private Button scoutingOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition1);

        LAT = Double.parseDouble(getString(R.string.competitionOneLat));
        LNG = Double.parseDouble(getString(R.string.competitionOneLng));
        competitionOneLocation = this.getString(R.string.competitionOneLocation);
        scoutingOne = findViewById(R.id.scoutButton);
        scoutingOne.setOnClickListener(scoutingOneOnClick);

//        if (this.mMap == null) {
//            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            mapFrag.getMapAsync(this);
//
//        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        LatLng compLoc = new LatLng(LAT, LNG);
//        mMap.addMarker(new MarkerOptions().position(compLoc).title(competitionOneLocation + " Competition"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(compLoc));
//        CameraPosition camera_position = new CameraPosition.Builder().target(new LatLng(LAT, LNG)).zoom(10).build();
//        CameraUpdate updateCamera = CameraUpdateFactory.newCameraPosition(camera_position);
//        googleMap.animateCamera(updateCamera);
//
//    }

    private View.OnClickListener scoutingOneOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Competition1.this, Name.class);
            intent.putExtra(getString(R.string.competitionLocation), competitionOneLocation);
            startActivity(intent);
        }
    };

}
