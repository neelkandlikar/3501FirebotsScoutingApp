package com.neelk.robotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Competitions extends Fragment {

    private de.hdodenhof.circleimageview.CircleImageView competitionOnePic;
    private de.hdodenhof.circleimageview.CircleImageView competitionTwoPic;
    private TextView competitionOneTextView;
    private TextView competitionTwoTextView;


    public Competitions() {

    }


    public static Competitions newInstance() {
        Competitions fragment = new Competitions();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_competitions, container, false);

        competitionOnePic = view.findViewById(R.id.competitionOnePic);
        competitionOneTextView = view.findViewById(R.id.competitionOneTextView);
        competitionTwoPic = view.findViewById(R.id.competitionTwoPic);
        competitionTwoTextView = view.findViewById(R.id.competitionTwoTextView);

        competitionTwoTextView.setOnClickListener(competitionTwoOnClick);
        competitionTwoPic.setOnClickListener(competitionTwoOnClick);
        competitionOnePic.setOnClickListener(competitionOneOnClick);
        competitionOneTextView.setOnClickListener(competitionOneOnClick);

        return view;
    }

    private View.OnClickListener competitionOneOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(), Competition1.class));
        }
    };

    private View.OnClickListener competitionTwoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(), Competition2.class));
        }
    };

}
