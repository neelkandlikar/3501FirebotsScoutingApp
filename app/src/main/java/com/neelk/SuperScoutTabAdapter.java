package com.neelk.robotics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class SuperScoutTabAdapter extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;
    private SuperScoutSectionsPagerAdapter adapter;
    private static Intent intent;
    private SuperScoutTeam team1;
    private SuperScoutTeam team2;
    private SuperScoutTeam team3;
    private int currentPositionViewPager;
    private static HashMap<String, Object> team1Data;
    private static HashMap<String, Object> team2Data;


    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_tab_adapter);


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        intent = getIntent();
        ArrayList<String> teamsScouting = intent.getStringArrayListExtra(getString(R.string.teamScouting));

        team1 = SuperScoutTeam.newInstance(false, teamsScouting.get(0));
        team2 = SuperScoutTeam.newInstance(false, teamsScouting.get(1));
        team3 = SuperScoutTeam.newInstance(true, teamsScouting.get(2));

        team1Data = new HashMap<>();
        team2Data = new HashMap<>();


        adapter = new SuperScoutSectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(team1, "Team " + teamsScouting.get(0));
        adapter.addFragment(team2, "Team " + teamsScouting.get(1));
        adapter.addFragment(team3, "Team " + teamsScouting.get(2));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        currentPositionViewPager = 0;

    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if (getCurrentPositionViewPager() == 0) {
                team1Data.clear();
                team1Data.putAll(team1.populateData());
            }
            if (getCurrentPositionViewPager() == 1) {
                team2Data.clear();
                team2Data.putAll(team2.populateData());
            }

            currentPositionViewPager = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SuperScoutSectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> fragmentNames;

        public SuperScoutSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentNames = new ArrayList<>();
            fragments = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentNames.add(title);
        }
    }

    public static Intent getNameInfo() {
        return intent;
    }


    private int getCurrentPositionViewPager() {
        return currentPositionViewPager;
    }

    public static void generateQr(HashMap<String, Object> team3data, Context context) {
        HashMap<String, Object> fullData = new HashMap<>();

        fullData.put("Super Scout Name", intent.getStringExtra("Super Scout Name"));
        fullData.put((context.getString(R.string.matchNumber)), intent.getStringExtra(context.getString(R.string.matchNumber)));
        fullData.put((context.getString(R.string.competitionLocation)), intent.getStringExtra(context.getString(R.string.competitionLocation)));
        fullData.put((context.getString(R.string.teamScouting)), intent.getStringArrayListExtra(context.getString(R.string.teamScouting)));
        fullData.put(context.getString(R.string.isSuperScout), true);


        fullData.putAll(team1Data);
        fullData.putAll(team2Data);
        fullData.putAll(team3data);
        Log.e("all Data", fullData.toString());

        for (int i = 0; i < 10; i++) {
            Toast.makeText(context, fullData.toString(), Toast.LENGTH_LONG).show();
        }

        String dataString = serialize(fullData);
        Intent intent = new Intent(context, QrCodeGeneratorActivity.class);
        intent.putExtra("data", dataString);
        context.startActivity(intent);


    }

    public static String serialize(Object toSerialize) {
        Gson gson = new Gson();
        Type listType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        return gson.toJson(toSerialize, listType);
    }

}
