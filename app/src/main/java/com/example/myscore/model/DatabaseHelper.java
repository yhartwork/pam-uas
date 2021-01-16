package com.example.myscore.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "Soccer.db";
    public static final String TABLE_NAME = "score_table";
    private static final int DATABASE_VERSION = 1;

    public static final String COL_1 = "id";
    public static final String COL_2 = "idEvent";
    public static final String COL_3 = "strEvent";
    public static final String COL_4 = "strHomeTeam";
    public static final String COL_5 = "strAwayTeam";
    public static final String COL_6 = "intHomeScore";
    public static final String COL_7 = "intAwayScore";
    public static final String COL_8 = "dateEvent";
    public static final String COL_9 = "strTime";
    public static final String COL_10 = "strVenue";
    public static final String COL_11 = "strThumb";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table score_table (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idEvent TEXT  NULL, " +
                "strEvent TEXT  NULL, " +
                "strHomeTeam TEXT  NULL," +
                "strAwayTeam TEXT  NULL," +
                "intHomeScore TEXT  NULL," +
                "intAwayScore TEXT  NULL," +
                "dateEvent TEXT  NULL," +
                "strTime TEXT  NULL," +
                "strVenue TEXT  NULL," +
                "strThumb TEXT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String idEvent, String strEvent, String strHomeTeam, String strAwayTeam,
                              String intHomeScore, String intAwayScore, String dateEvent, String strTime, String strVenue,
                              String strThumb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,idEvent);
        contentValues.put(COL_3,strEvent);
        contentValues.put(COL_4,strHomeTeam);
        contentValues.put(COL_5,strAwayTeam);
        contentValues.put(COL_6,intHomeScore);
        contentValues.put(COL_7,intAwayScore);
        contentValues.put(COL_8,dateEvent);
        contentValues.put(COL_9,strTime);
        contentValues.put(COL_10,strVenue);
        contentValues.put(COL_11,strThumb);

        Cursor currentEntry = getSingleData(idEvent);

        if (currentEntry.getCount() == 0) {
            long result = db.insert(TABLE_NAME, null, contentValues);
            if(result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }

    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from score_table", null);
        return res;
    }

    public Cursor getSingleData(String idEvent) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from score_table where idEvent = " + idEvent, null);
        return res;
    }


    public boolean updateData(String id, String idEvent, String strEvent, String strHomeTeam, String strAwayTeam,
                              String intHomeScore, String intAwayScore, String dateEvent, String strTime, String strVenue,
                              String strThumb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,idEvent);
        contentValues.put(COL_3,strEvent);
        contentValues.put(COL_4,strHomeTeam);
        contentValues.put(COL_5,strAwayTeam);
        contentValues.put(COL_6,intHomeScore);
        contentValues.put(COL_7,intAwayScore);
        contentValues.put(COL_8,dateEvent);
        contentValues.put(COL_9,strTime);
        contentValues.put(COL_10,strVenue);
        contentValues.put(COL_11,strThumb);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }


    public int deleteData (String idEvent) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "idEvent = ?", new String[] {idEvent});
    }

}