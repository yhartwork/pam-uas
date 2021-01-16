package com.example.myscore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myscore.model.DatabaseHelper;
import com.example.myscore.model.Event;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.ListItemClickListener {

    RecyclerView recyclerView;
    DatabaseHelper myDb;
    ArrayList<Event> favoriteEventList;
    FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.list);

        favoriteEventList = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(this, favoriteEventList );

        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myDb = new DatabaseHelper(this);

        new GetFavorite().execute();
    }

    @Override
    public void onListItemClick(int position) {

    }

    public void updateData(ArrayList<Event> newData) {
        favoriteAdapter.updateData(newData);
        favoriteAdapter.notifyDataSetChanged();
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
            updateData(favoriteEventList);
        }

    }

}