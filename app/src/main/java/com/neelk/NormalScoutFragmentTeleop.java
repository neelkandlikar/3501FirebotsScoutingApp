package com.neelk.robotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;


public class NormalScoutFragmentTeleop extends Fragment {


    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private Switch switch4;
    private TextView criteria1Value;
    private TextView criteria2Value;
    private TextView criteria3Value;
    private TextView criteria4Value;
    private Button submitButton;
    private Button criteria1ButtonMinus;
    private Button criteria2ButtonMinus;
    private Button criteria3ButtonMinus;
    private Button criteria4ButtonMinus;
    private Button criteria1ButtonPlus;
    private Button criteria2ButtonPlus;
    private Button criteria3ButtonPlus;
    private Button criteria4ButtonPlus;
    private int criteria1ValueInt = 0;
    private int criteria2ValueInt = 0;
    private int criteria3ValueInt = 0;
    private int criteria4ValueInt = 0;
    private static HashMap<String, Object> dataMap = new HashMap<>();


    public NormalScoutFragmentTeleop() {
    }


    public static NormalScoutFragmentTeleop newInstance() {
        NormalScoutFragmentTeleop fragment = new NormalScoutFragmentTeleop();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal_scout_teleop, container, false);

        initUi(view);


        return view;
    }


    private View.OnClickListener submitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            generateQrCode();

        }
    };

    private View.OnClickListener minusOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button1minus:
                    if (criteria1ValueInt > 0) {
                        criteria1ValueInt--;
                        criteria1Value.setText(String.valueOf(criteria1ValueInt));
                    }
                    break;

                case R.id.button2minus:
                    if (criteria2ValueInt > 0) {
                        criteria2ValueInt--;
                        criteria2Value.setText(String.valueOf(criteria2ValueInt));
                    }
                    break;

                case R.id.button3minus:
                    if (criteria3ValueInt > 0) {
                        criteria3ValueInt--;
                        criteria3Value.setText(String.valueOf(criteria3ValueInt));
                    }
                    break;

                case R.id.button4minus:
                    if (criteria4ValueInt > 0) {
                        criteria4ValueInt--;
                        criteria4Value.setText(String.valueOf(criteria4ValueInt));
                    }
                    break;
            }

        }
    };

    private View.OnClickListener plusOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button1plus:
                    criteria1ValueInt++;
                    criteria1Value.setText(String.valueOf(criteria1ValueInt));
                    break;

                case R.id.button2plus:
                    criteria2ValueInt++;
                    criteria2Value.setText(String.valueOf(criteria2ValueInt));
                    break;

                case R.id.button3plus:
                    criteria3ValueInt++;
                    criteria3Value.setText(String.valueOf(criteria3ValueInt));
                    break;

                case R.id.button4plus:
                    criteria4ValueInt++;
                    criteria4Value.setText(String.valueOf(criteria4ValueInt));
                    break;
            }
        }
    };

    private void initUi(View view) {

        switch1 = view.findViewById(R.id.switch1);
        switch2 = view.findViewById(R.id.switch2);
        switch3 = view.findViewById(R.id.switch3);
        switch4 = view.findViewById(R.id.switch4);


        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitOnClick);

        criteria1ButtonMinus = view.findViewById(R.id.button1minus);
        criteria1ButtonPlus = view.findViewById(R.id.button1plus);
        criteria2ButtonMinus = view.findViewById(R.id.button2minus);
        criteria2ButtonPlus = view.findViewById(R.id.button2plus);

        criteria1Value = view.findViewById(R.id.value1);
        criteria2Value = view.findViewById(R.id.value2);


        criteria1ButtonMinus.setOnClickListener(minusOnClick);
        criteria2ButtonMinus.setOnClickListener(minusOnClick);


        criteria1ButtonPlus.setOnClickListener(plusOnClick);
        criteria2ButtonPlus.setOnClickListener(plusOnClick);


    }

    private void generateQrCode() {
        Intent nameInfo = ScoutingTabAdapter.getNameInfo();


        dataMap.put(getString(R.string.competitionLocation), nameInfo.getStringExtra(getString(R.string.competitionLocation)));
        dataMap.put(getString(R.string.name), nameInfo.getStringExtra(getString(R.string.name)));
        dataMap.put(getString(R.string.teamScouting), nameInfo.getStringExtra(getString(R.string.teamScouting)));
        dataMap.put(getString(R.string.matchNumber), nameInfo.getStringExtra(getString(R.string.matchNumber)));
        dataMap.put(getString(R.string.isSuperScout), false);
        Log.e("Match Number", (String) dataMap.get(getString(R.string.matchNumber)));
        dataMap.putAll(SandstormScout.getSanstormData());
        dataMap.put(getString(R.string.hatches), criteria1ValueInt);
        dataMap.put(getString(R.string.cargo), criteria2ValueInt);
        dataMap.put(getString(R.string.normalScoutTeleopCriteria5), switch1.isChecked());
        dataMap.put(getString(R.string.normalScoutTeleopCriteria6), switch2.isChecked());
        dataMap.put(getString(R.string.normalScoutTeleopCriteria7), switch3.isChecked());
        dataMap.put(getString(R.string.normalScoutTeleopCriteria8), switch4.isChecked());

        Log.e("allData", dataMap.toString());

        String dataString = serialize(dataMap);
        Intent intent = new Intent(getActivity(), QrCodeGeneratorActivity.class);
        intent.putExtra("data", dataString);
        startActivity(intent);

    }

    public String serialize(Object toSerialize) {
        Gson gson = new Gson();
        Type listType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        return gson.toJson(toSerialize, listType);
    }

}
