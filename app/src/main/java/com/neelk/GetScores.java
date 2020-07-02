package com.neelk.robotics;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

import com.neelk.robotics.Home.Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class GetScores {

    private final String TEAM_KEY = "3501";
    private final String YEAR = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    private final String API_KEY = "TuQVh8HXaPEiCo8QN3sMVyqR36rTNdaBJrHWcqzpm50H2UButmudk6hl6d27PoGz";
    private final int SUCCESS = 200;
    private Home home;
    private Activity activity;
    private static Results results;
    private ArrayList<EventInformation> data;
    private int tried = 0;


    public GetScores(ArrayList<EventInformation> data, Home home, Activity activity) {
        this.home = home;
        this.activity = activity;
        this.data = data;
    }


    public void getScores() {


        try {
            StringBuilder builder = new StringBuilder();
            URL newsURL = new URL("https://www.thebluealliance.com/api/v3/team/frc3501/event/" + data.get(data.size() - 1 - tried).getEventCode() + "/matches/simple");
            HttpsURLConnection connection = (HttpsURLConnection) newsURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("X-TBA-Auth-Key", API_KEY);
            int responseCode = connection.getResponseCode();
            if (responseCode == SUCCESS) {
                InputStream responseBody = connection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");
                try (BufferedReader in = new BufferedReader(responseBodyReader)) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        builder.append(line); // + "\r\n"(no need, json has no line breaks!)
                    }
                    in.close();
                    final String scores = builder.toString();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Results results = null;
                            try {
                                results = new Home.Results(parseScoresJson(scores, "frc3501"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            assert results != null;
                            results.populate(home.getImageView1(), home.getScore1(), 0);
                            results.populate(home.getImageView2(), home.getScore2(), 1);
                            results.populate(home.getImageView3(), home.getScore3(), 2);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("Error", Integer.toString(responseCode));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList> parseScoresJson(String json, String teamToCheck) throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray jResponse = new JSONArray(json);
        String record = "Record at " + data.get(data.size() - 1 - tried).getName() + ": " + getRecord(jResponse);
        home.getRecord().setText(record);
        System.out.println(record);
        HashMap<Integer, JSONObject> map = new HashMap<Integer, JSONObject>();
        int[] times = new int[jResponse.length()];
        if (times.length == 0) {
            Log.e("Error", "Running getScoresAgain");
            Log.e("jresponse", jResponse.toString());
            tried++;
            getScores();
        }
        Log.e("jresponse", jResponse.toString());
        for (int i = 0; i < jResponse.length(); i++) {
            JSONObject currentElement = (JSONObject) jResponse.get(i);
            int eventTime = currentElement.getInt("time");
            map.put(eventTime, currentElement);
            times[i] = eventTime;
        }
        Arrays.sort(times);
        ArrayList<ArrayList> results = new ArrayList<>(2);
        ArrayList<String> scores = new ArrayList<>();
        ArrayList<Boolean> won = new ArrayList<>();
        String gameResult = "Loss";
        String firebotsAlliance = "Red Alliance (Firebots): ";
        String otherAlliance = "Blue Alliance: ";
        for (int i = 0; i < 3; i++) {
            JSONObject element = map.get(times[times.length - 1 - i]);
            JSONObject alliances = element.getJSONObject("alliances");
            JSONObject blueAlliance = alliances.getJSONObject("blue");
            JSONObject redAlliance = alliances.getJSONObject("red");
            int blueScore = blueAlliance.getInt("score");
            int redScore = redAlliance.getInt("score");
            int firebotsScore = redScore;
            int otherScore = blueScore;
            JSONArray blueTeams = blueAlliance.getJSONArray("team_keys");
            boolean inBlueAlliance = false;
            boolean teamWon = false;
            for (int j = 0; j < blueTeams.length(); j++) {
                if (blueTeams.getString(j).equals(teamToCheck)) {
                    inBlueAlliance = true;
                    firebotsAlliance = "Blue Alliance (Firebots): ";
                    otherAlliance = "Red Alliance: ";
                    firebotsScore = blueScore;
                    otherScore = redScore;
                }
            }
            // if ((inBlueAlliance) && (blueScore > redScore) || ((!inBlueAlliance) && (redScore > blueScore))) {
            if (firebotsScore > otherScore) {
                teamWon = true;
                gameResult = "Win!";
                System.out.println(firebotsScore + "," + otherScore);
            }
            won.add(teamWon);

            scores.add(gameResult + "\n" + data.get(data.size() - 1 - tried).getName() + "\n" + data.get(data.size() - 1 - tried).getLocation() + "\n" + firebotsAlliance + firebotsScore + "\n" + otherAlliance + otherScore);

        }
        results.add(scores);
        results.add(won);
        GetScores.results = new Results(results, record);
        return results;
    }

    public static Results getResults() {
        return results;
    }

    public String getRecord(JSONArray response) throws JSONException {
        int won = 0;
        int totalPlayed = response.length();
        for (int i = 0; i < response.length(); i++) {
            JSONObject currentObject = response.getJSONObject(i);
            JSONObject alliances = currentObject.getJSONObject("alliances");
            JSONObject blueAlliance = alliances.getJSONObject("blue");
            JSONObject redAlliance = alliances.getJSONObject("red");
            int blueScore = blueAlliance.getInt("score");
            int redScore = redAlliance.getInt("score");
            JSONArray blueTeams = blueAlliance.getJSONArray("team_keys");
            boolean inBlueAlliance = false;
            for (int j = 0; j < blueTeams.length(); j++) {
                if (blueTeams.getString(j).equals("frc3501")) {
                    inBlueAlliance = true;
                }
            }
            if ((inBlueAlliance && blueScore > redScore) || (!inBlueAlliance && redScore > blueScore)) {
                won++;
            }

        }
        return won + "-" + (totalPlayed - won);
    }


}

