package com.neelk.robotics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuperScoutTeam extends Fragment {

    private RatingBar defenseRating;
    private RatingBar driverAbility;
    private RatingBar robotAbility;
    private Switch groundPickup;
    private LinearLayout linearLayout;
    private static HashMap<String, Object> data;
    private Bundle arguments;


    public SuperScoutTeam() {
    }

    public static SuperScoutTeam newInstance(boolean submitButton, String team) {

        Bundle bundle = new Bundle();
        bundle.putBoolean("submitButton", submitButton);
        bundle.putString("team", team);

        SuperScoutTeam superScoutTeam = new SuperScoutTeam();
        superScoutTeam.setArguments(bundle);

        return superScoutTeam;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_super_scout_team, container, false);

        arguments = this.getArguments();


        defenseRating = view.findViewById(R.id.defenseRating);
        driverAbility = view.findViewById(R.id.driverAbilityRating);
        robotAbility = view.findViewById(R.id.robotAbilityRating);
        groundPickup = view.findViewById(R.id.groundPickupSwitch);
        linearLayout = view.findViewById(R.id.linearLayoutSuperScoutTeam);

        if (arguments != null && arguments.getBoolean("submitButton")) {

            Button submitButton = new Button(getContext());
            submitButton.setText(getContext().getString(R.string.submit));
            submitButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            submitButton.setId(R.id.submitButtonSuperScoutTeam);
            submitButton.setBackgroundResource(R.drawable.round_button);
            submitButton.setTextColor(getResources().getColor(R.color.black));

            submitButton.setOnClickListener(submitOnClick);
            linearLayout.addView(submitButton);
        }

        data = new HashMap<>();

        return view;
    }


    public HashMap<String, Object> populateData() {
        data.clear();
        String team = arguments.getString("team");
        data.put(getString(R.string.defense) + team, (double) defenseRating.getRating());
        data.put(getString(R.string.robotAbility) + team, (double) robotAbility.getRating());
        data.put(getString(R.string.driverAbility) + team, (double) driverAbility.getRating());
        data.put(getString(R.string.groundPickup) + team, groundPickup.isChecked());

        return data;

    }

    private View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String team = arguments.getString("team");
            data.put(getString(R.string.defense) + team, (double) defenseRating.getRating());
            data.put(getString(R.string.robotAbility) + team, (double) robotAbility.getRating());
            data.put(getString(R.string.driverAbility) + team, (double) driverAbility.getRating());
            data.put(getString(R.string.groundPickup) + team, groundPickup.isChecked());

            SuperScoutTabAdapter.generateQr(data, getContext());

        }
    };


}
