package com.neelk.robotics;

import android.app.Activity;
import android.util.Log;

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
import java.util.Calendar;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;

public class GetEventCode implements Runnable {

    private String jsonAsString;
    private String YEAR = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    private String API_KEY;
    private final int SUCCESS = 200;
    private final String END_DATE = "end_date";
    private final String KEY = "key";
    private Home home;
    private Activity activity;
    private String eventLocation;
    private String eventName;
    private int lat;
    private int lng;
    private ArrayList<EventInformation> eventInfo;

    public GetEventCode(Home home, Activity activity) {
        this.home = home;
        this.activity = activity;
        API_KEY = this.activity.getString(R.string.blueAllianceApiKey);
        eventInfo = new ArrayList<>();

    }

    @Override
    public void run() {

        try {
            StringBuilder builder = new StringBuilder();

            URL scoresURL = new URL("https://www.thebluealliance.com/api/v3/team/frc3501/events/" + "2018");
            HttpsURLConnection connection = (HttpsURLConnection) scoresURL.openConnection();
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
                    jsonAsString = builder.toString();
                    getEventInfo(jsonAsString);
                    getScores();
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

    public void getEventInfo(String json) throws JSONException {
        JSONArray jResponse = new JSONArray(json);
        for (int i = 0; i < jResponse.length(); i++) {
            JSONObject currentElement = (JSONObject) jResponse.get(i);
            String eventCode = (String) currentElement.get(KEY);
            Date date = new com.neelk.robotics.Date((String) currentElement.get(END_DATE));
            String name = currentElement.getString("name");
            String locationName = currentElement.getString("location_name");
            int lat = currentElement.getInt("lat");
            int lng = currentElement.getInt("lng");
            eventInfo.add(new EventInformation(eventCode, name, locationName, date, lat, lng));
            /*
            eventCodes[i] = (String) currentElement.get(KEY);
            dates[i] = new com.neelk.robotics.Date((String) currentElement.get(END_DATE));
            eventNames[i] = currentElement.getString("name");
            eventLocations[i] = currentElement.getString("location_name");
            lat[i] = Integer.toString(currentElement.getInt("lat"));
            lng[i] = Integer.toString(currentElement.getInt("lng"));
            */
        }
        Collections.sort(eventInfo);
        Log.e("eventInfo", String.valueOf(eventInfo));

        /*
        int indexOfLatest = 0;
        for (int i = 0; i < dates.length; i++) {
            com.neelk.robotics.Date currentDate = dates[i];
            String currentEventCode = eventCodes[i];
            if (currentDate.isAfter(latestDate)) {
                latestDate = currentDate;
                latestEventCode = currentEventCode;
                indexOfLatest = i;
            }
            eventLocation = eventLocations[indexOfLatest];
            eventName = eventNames[indexOfLatest];
            this.lat = lat[indexOfLatest];
            this.lng = lng[indexOfLatest];
        }

        return latestEventCode;
    }
         */

    }

    public String getJsonAsString() {
        return jsonAsString;
    }

    public void getScores() {
        GetScores scores = new GetScores(eventInfo, home, activity);
        scores.getScores();
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public int getLat() {
        return lat;
    }

    public int getLng() {
        return lng;
    }

}
