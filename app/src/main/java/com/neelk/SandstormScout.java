package com.neelk.robotics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;


public class SandstormScout extends Fragment {

    private static Switch criteria1Switch;
    private static Switch criteria2Switch;
    private TextView criteria3Value;
    private TextView criteria4Value;
    private TextView criteria5Value;
    private TextView criteria6Value;
    private Button criteria3Plus;
    private Button criteria4Plus;
    private Button criteria5Plus;
    private Button criteria6Plus;
    private Button criteria3Minus;
    private Button criteria4Minus;
    private Button criteria5Minus;
    private Button criteria6Minus;
    private static int criteria3ValueInt = 0;
    private static int criteria4ValueInt = 0;
    private static int criteria5ValueInt = 0;
    private static int criteria6ValueInt = 0;
    private static HashMap<String, Object> dataMap;


    public SandstormScout() {
        // Required empty public constructor
    }

    public static SandstormScout newInstance() {
        SandstormScout fragment = new SandstormScout();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandstorm_scout, container, false);
        initUI(view);

        dataMap = new HashMap<>();

        return view;
    }

    private View.OnClickListener minusOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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

                case R.id.button5minus:
                    if (criteria5ValueInt > 0) {
                        criteria5ValueInt--;
                        criteria5Value.setText(String.valueOf(criteria5ValueInt));
                    }
                    break;

            }
        }
    };


    private View.OnClickListener plusOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button3plus:
                    criteria3ValueInt++;
                    criteria3Value.setText(String.valueOf(criteria3ValueInt));
                    break;

                case R.id.button4plus:
                    criteria4ValueInt++;
                    criteria4Value.setText(String.valueOf(criteria4ValueInt));
                    break;

                case R.id.button5plus:
                    criteria5ValueInt++;
                    criteria5Value.setText(String.valueOf(criteria5ValueInt));
                    break;


            }

        }
    };

    private void initUI(View view) {
        criteria1Switch = view.findViewById(R.id.switch1Sandstorm);
        criteria2Switch = view.findViewById(R.id.switch2Sandstorm);

        criteria3Value = view.findViewById(R.id.value3);
        criteria4Value = view.findViewById(R.id.value4);
        criteria5Value = view.findViewById(R.id.value5);

        criteria3Minus = view.findViewById(R.id.button3minus);
        criteria4Minus = view.findViewById(R.id.button4minus);
        criteria5Minus = view.findViewById(R.id.button5minus);


        criteria3Plus = view.findViewById(R.id.button3plus);
        criteria4Plus = view.findViewById(R.id.button4plus);
        criteria5Plus = view.findViewById(R.id.button5plus);

        criteria3Plus.setOnClickListener(plusOnClick);
        criteria4Plus.setOnClickListener(plusOnClick);


        criteria3Minus.setOnClickListener(minusOnClick);
        criteria4Minus.setOnClickListener(minusOnClick);


    }

    public static void populateSandstormData(Context context) {


        dataMap.clear();
        dataMap.put(context.getString(R.string.normalScoutSandstormCriteria1), criteria1Switch.isChecked());
        dataMap.put(context.getString(R.string.normalScoutSandstormCriteria2), criteria1Switch.isChecked());
        dataMap.put(context.getString(R.string.sandstormHatches), criteria3ValueInt);
        dataMap.put(context.getString(R.string.sandstormCargo), criteria4ValueInt);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Ran on Destroy", "Ran Method");
        populateSandstormData(getContext());
    }

    public static HashMap<String, Object> getSanstormData() {
        return dataMap;
    }
}
