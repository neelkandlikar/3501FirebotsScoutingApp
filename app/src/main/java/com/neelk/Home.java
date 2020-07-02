package com.neelk.robotics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends Fragment {

    private de.hdodenhof.circleimageview.CircleImageView imageView1;
    private de.hdodenhof.circleimageview.CircleImageView imageView2;
    private de.hdodenhof.circleimageview.CircleImageView imageView3;
    private TextView score1;
    private TextView score2;
    private TextView score3;
    private TextView record;
    private static boolean alreadyLoaded = false;
    private Results results;

    public Home() {
    }


    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public TextView getRecord() {
        return record;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        imageView1 = view.findViewById(R.id.image1);
//        imageView2 = view.findViewById(R.id.image2);
//        imageView3 = view.findViewById(R.id.image3);
//        score1 = view.findViewById(R.id.textViewScore1);
//        score2 = view.findViewById(R.id.textViewScore2);
//        score3 = view.findViewById(R.id.textViewScore3);
//        record = view.findViewById(R.id.record);
//
//        if (!alreadyLoaded) {
//            GetEventCode scores = new GetEventCode(this, getActivity());
//            new Thread(scores).start();
//            alreadyLoaded = true;
//        } else {
//            results = GetScores.getResults();
//            results.populate(imageView1, score1, 0);
//            results.populate(imageView2, score2, 1);
//            results.populate(imageView3, score3, 2);
//            record.setText(results.getRecord());
//        }
        return view;
    }


    public CircleImageView getImageView1() {
        return imageView1;
    }

    public CircleImageView getImageView2() {
        return imageView2;
    }

    public CircleImageView getImageView3() {
        return imageView3;
    }

    public TextView getScore1() {
        return score1;
    }

    public TextView getScore2() {
        return score2;
    }

    public TextView getScore3() {
        return score3;
    }


    public static class Results {

        private ArrayList<String> scores;
        private ArrayList<Boolean> won;
        private String record;

        public Results(ArrayList<ArrayList> results, String record) {
            try {
                scores = results.get(0);
                won = results.get(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.record = record;
        }

        public Results(ArrayList<ArrayList> results) {
            try {
                scores = results.get(0);
                won = results.get(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void populate(de.hdodenhof.circleimageview.CircleImageView image, TextView text, int number) {
            if (won.get(number)) {
                image.setImageResource(R.drawable.win);
            } else {
                image.setImageResource(R.drawable.loss);
            }
            text.setText(scores.get(number));
        }

        public String getRecord() {
            return record;
        }
    }
}
