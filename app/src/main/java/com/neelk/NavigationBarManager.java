package com.neelk.robotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Type;
import java.util.HashMap;

public class NavigationBarManager extends AppCompatActivity {

    private android.support.v4.app.Fragment fragment;
    private BottomNavigationView mBottomNavigationView;
    private FragmentTransaction toHomeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar_manager);

        toHomeFragment = getSupportFragmentManager().beginTransaction();
        toHomeFragment.replace(R.id.constraint_layout, Home.newInstance());
        toHomeFragment.addToBackStack(null);
        toHomeFragment.commit();

        mBottomNavigationView = findViewById(R.id.navigation);


        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });


    }

    public void selectFragment(MenuItem item) {
        fragment = null;
        FragmentTransaction ft;
        Intent intent;
        switch (item.getItemId()) {

            case R.id.menu_home:
                fragment = Home.newInstance();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.constraint_layout, fragment, "Home");
                ft.addToBackStack(null);
                ft.commit();
                break;


            case R.id.menu_competititions:
                fragment = Competitions.newInstance();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.constraint_layout, fragment, "Competitions");
                ft.addToBackStack(null);
                ft.commit();
                break;


            case R.id.menu_scanner:
                fragment = Scanner.newInstance();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.constraint_layout, fragment, "Scanner");
                ft.addToBackStack(null);
                ft.commit();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("LOOP", "ON RESULT");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            // Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            HashMap<String, Object> dataMap = deserialize(result.getContents());
            if (dataMap != null) {
                SendToDatabase database = new SendToDatabase(this);
                database.sendData(dataMap);
            }
        } else Log.e("Null", "Result is Null");


    }

    public HashMap<String, Object> deserialize(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> dataMap = gson.fromJson(data, listType);
        return dataMap;
    }

}

