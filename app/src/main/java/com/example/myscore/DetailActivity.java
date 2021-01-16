package com.example.myscore;

import android.content.Intent;
import android.os.Bundle;

import com.example.myscore.model.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    ImageView myCover;
    TextView myAwayScore, myHomeScore, myAwayName, myHomeName, myDate, myVenue;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);

        Intent intent = getIntent();
        String idEvent = intent.getStringExtra("idEvent");
        String eventName = intent.getStringExtra("strEventName");
        String coverUrl = intent.getStringExtra("StrThumb");
        String awayScore = intent.getStringExtra("IntAwayScore");
        String homeScore = intent.getStringExtra("IntHomeScore");
        String awayName = intent.getStringExtra("StrAwayTeam");
        String homeName = intent.getStringExtra("StrHomeTeam");
        String date = intent.getStringExtra("DateEvent");
        String time = "00:00";
        String venue = intent.getStringExtra("StrVenue");
        String saved = intent.getStringExtra("saved");

        myCover = (ImageView) findViewById(R.id.image_cover);
        myAwayScore = (TextView) findViewById(R.id.away_score);
        myHomeScore = (TextView) findViewById(R.id.home_score);
        myAwayName = (TextView) findViewById(R.id.away_name);
        myHomeName = (TextView) findViewById(R.id.home_name);
        myDate = (TextView) findViewById(R.id.tanggal);
        myVenue = (TextView) findViewById(R.id.venue);

        Picasso.get().load(coverUrl).into(myCover);

        myAwayScore.setText(awayScore);
        myHomeScore.setText(homeScore);
        myAwayName.setText(awayName);
        myHomeName.setText(homeName);
        myDate.setText(date);
        myVenue.setText(venue);

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab_2 = findViewById(R.id.fab_2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isInserted = myDb.insertData(idEvent, eventName, awayName, homeName, homeScore, awayScore, date, time, venue, coverUrl);

                if(isInserted == true) {
                    Toast.makeText(DetailActivity.this, "Berhasil menambahkan ke favorit", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailActivity.this,"Sudah ditambahkan ke favorit",Toast.LENGTH_LONG).show();
                }
            }
        });

        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteData(idEvent);
                Toast.makeText(DetailActivity.this, "Berhasil menghapus dari favorit", Toast.LENGTH_LONG).show();
            }
        });

        if (saved.equals("true")) {
            fab.setVisibility(View.INVISIBLE);
            fab_2.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab_2.setVisibility(View.INVISIBLE);
        }
    }
}