package com.example.myscore;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myscore.model.DatabaseHelper;
import com.example.myscore.model.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myscore.ui.main.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    DatabaseHelper myDb;

    // URL to get contacts JSON
    private static String oncomingUrl = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328";
    private static String pastUrl = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328";

    ArrayList<Event> oncomingEventList;
    ArrayList<Event> pastEventList;
    ArrayList<Event> favoriteEventList;

    SectionsPagerAdapter sectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        oncomingEventList = new ArrayList<>();
        pastEventList = new ArrayList<>();
        favoriteEventList = new ArrayList<>();

        myDb = new DatabaseHelper(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        new GetData().execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new GetFavorite().execute();
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStrOncoming = sh.makeServiceCall(oncomingUrl);
            String jsonStrPast = sh.makeServiceCall(pastUrl);

            Log.e(TAG, "Response from url: " + jsonStrOncoming);
            Log.e(TAG, "Response from url: " + jsonStrPast);

            // Process data
            if (jsonStrOncoming != null && jsonStrPast != null) {
                try {

                    JSONObject jsonObjOncoming = new JSONObject(jsonStrOncoming);
                    JSONObject jsonObjPast = new JSONObject(jsonStrPast);

                    // Getting JSON Array node
                    JSONArray oncomingEvents = jsonObjOncoming.getJSONArray("events");
                    JSONArray pastEvents = jsonObjPast.getJSONArray("events");

                    // looping through oncoming events
                    for (int i = 0; i < oncomingEvents.length(); i++) {
                        JSONObject c = oncomingEvents.getJSONObject(i);

                        String idEvent = c.getString("idEvent");
                        String strEvent = c.getString("strEvent");
                        String dateEvent = c.getString("dateEvent");
                        String strTime = c.getString("strTime");
                        String intAwayScore = c.getString("intAwayScore");
                        String intHomeScore = c.getString("intHomeScore");
                        String strHomeTeam = c.getString("strHomeTeam");
                        String strAwayTeam = c.getString("strAwayTeam");
                        String strVenue = c.getString("strVenue");
                        String strThumb = c.getString("strThumb");

                        // tmp hash map for single event
                        Event event = new Event();

                        // adding each child node to model

                        event.setIdEvent(idEvent);
                        event.setStrEvent(strEvent);
                        event.setDateEvent(dateEvent);
                        event.setStrTime(strTime);
                        event.setIntAwayScore(intAwayScore);
                        event.setIntHomeScore(intHomeScore);
                        event.setStrHomeTeam(strHomeTeam);
                        event.setStrAwayTeam(strAwayTeam);
                        event.setStrVenue(strVenue);
                        event.setStrThumb(strThumb);

                        // adding contact to contact list
                        oncomingEventList.add(event);
                    }

                    // looping through past events
                    for (int i = 0; i < pastEvents.length(); i++) {
                        JSONObject c = pastEvents.getJSONObject(i);

                        String idEvent = c.getString("idEvent");
                        String strEvent = c.getString("strEvent");
                        String dateEvent = c.getString("dateEvent");
                        String strTime = c.getString("strTime");
                        String intAwayScore = c.getString("intAwayScore");
                        String intHomeScore = c.getString("intHomeScore");
                        String strHomeTeam = c.getString("strHomeTeam");
                        String strAwayTeam = c.getString("strAwayTeam");
                        String strVenue = c.getString("strVenue");
                        String strThumb = c.getString("strThumb");

                        // tmp hash map for single event
                        Event event = new Event();

                        // adding each child node to model
                        event.setIdEvent(idEvent);
                        event.setStrEvent(strEvent);
                        event.setDateEvent(dateEvent);
                        event.setStrTime(strTime);
                        event.setIntAwayScore(intAwayScore);
                        event.setIntHomeScore(intHomeScore);
                        event.setStrHomeTeam(strHomeTeam);
                        event.setStrAwayTeam(strAwayTeam);
                        event.setStrVenue(strVenue);
                        event.setStrThumb(strThumb);

                        // adding contact to contact list
                        pastEventList.add(event);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            // do something here
            sectionsPagerAdapter.updateUpcomingFragment(oncomingEventList);
            sectionsPagerAdapter.updatePastFragment(pastEventList);
        }

    }

    private class GetFavorite extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            Cursor res = myDb.getAllData();
            if(res.getCount() == 0) { return null; }

            while (res.moveToNext() ) {

                // tmp hash map for single event
                Event event = new Event();

                // adding each child node to model
                event.setIdEvent(res.getString(1));
                event.setStrEvent(res.getString(2));
                event.setStrHomeTeam(res.getString(3));
                event.setStrAwayTeam(res.getString(4));
                event.setIntHomeScore(res.getString(5));
                event.setIntAwayScore(res.getString(6));
                event.setDateEvent(res.getString(7));
                event.setStrTime(res.getString(8));
                event.setStrVenue(res.getString(9));
                event.setStrThumb(res.getString(10));

                // adding contact to contact list
                favoriteEventList.add(event);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // update the list in favorite
        }

    }

}