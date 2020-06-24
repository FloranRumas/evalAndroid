package com.example.evalandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Cinema.db";
    public static final String TABLE_NAME = "Cinema_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "Titre";
    public static final String COL_3 = "Date";
    public static final String COL_4 = "NoteScenario";
    public static final String COL_5 = "NoteRealisation";
    public static final String COL_6 = "NoteMusique";
    public static final String COL_7 = "Critique";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITRE TEXT, DATE TEXT, NOTESCENARIO TEXT, NOTEREALISATION TEXT, NOTEMUSIQUE TEXT, CRITIQUE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }

    public boolean insertData(String Titre, String Date, String NoteScenario, String NoteRealisation, String NoteMusique, String Critique){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, Titre);
        contentValues.put(COL_3, String.valueOf(Date));
        contentValues.put(COL_4, NoteScenario);
        contentValues.put(COL_5, NoteRealisation);
        contentValues.put(COL_6, NoteMusique);
        contentValues.put(COL_7, Critique);
        long res = db.insert(TABLE_NAME, null, contentValues);
        if (res == -1 ){
            return false ;
        }else{
            return true ;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return  res;
    }
}
