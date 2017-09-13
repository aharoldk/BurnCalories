package com.aharoldk.burncalories.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aharoldk.burncalories.model.Home;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "burncalories.db";

    private static final String TABLE_NAME1 = "Bcuser";
    private static final String TABLE_NAME2 = "Bcfood";
    private static final String TABLE_NAME3 = "Bcactivity";

    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String TOTAL_CALORIES = "total_calories";
    private static final String TOTAL_WALK_OFF = "total_walk_off";
    private static final String TOTAL_RUN_OFF = "total_run_off";

    private static final String DISTANCES = "distances";
    private static final String CALORIES = "calories";
    private static final String STEPS = "steps";
    private static final String DATE_ACTIVITY = "date_activity";

    private static final String QUERY_TABLE1 = "CREATE TABLE "+TABLE_NAME1+" ("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +NAME+" VARCHAR(20) NULL)";

    private static final String QUERY_TABLE2 = "CREATE TABLE "+TABLE_NAME2+" ("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TOTAL_CALORIES+" DOUBLE NULL, "
            +TOTAL_WALK_OFF+" INTEGER NULL, "
            +TOTAL_RUN_OFF+" INTEGER NULL, "
            +DATE_ACTIVITY+" VARCHAR(10) NULL)";

    private static final String QUERY_TABLE3 = "CREATE TABLE "+TABLE_NAME3+" ("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DISTANCES+" DOUBLE NULL, "
            +CALORIES+" DOUBLE NULL, "
            +STEPS+" INTEGER NULL, "
            +DATE_ACTIVITY+" VARCHAR(10) NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY_TABLE1);
        sqLiteDatabase.execSQL(QUERY_TABLE2);
        sqLiteDatabase.execSQL(QUERY_TABLE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QUERY_TABLE1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QUERY_TABLE2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QUERY_TABLE3);

        onCreate(sqLiteDatabase);
    }

    public Boolean insertUser(String name){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);

        long result = sqLiteDatabase.insert(TABLE_NAME1, null, contentValues);

        return result != -1;
    }

    public boolean updateUser(String name){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);

        long result = sqLiteDatabase.update(TABLE_NAME1, contentValues, NAME+" = ?", new String[] {name});

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor selectUser(){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME1, null);

        return cursor;
    }

    public Boolean insertFood(Double calories, int walk, int run, String date){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TOTAL_CALORIES, calories);
        contentValues.put(TOTAL_WALK_OFF, walk);
        contentValues.put(TOTAL_RUN_OFF, run);
        contentValues.put(DATE_ACTIVITY, date);

        long result = sqLiteDatabase.insert(TABLE_NAME2, null, contentValues);

        return result != -1;
    }

    public List<Home> selectFood(){
        List<Home> list = new ArrayList<>();

        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME2, null);

        while (cursor.moveToNext()) {
            double total_calories = cursor.getDouble(1);
            int total_walk_off = cursor.getInt(2);
            int total_run_off = cursor.getInt(3);
            String date = cursor.getString(4);

            Home home = new Home(total_calories, total_walk_off, total_run_off, date);

            list.add(home);
        }

        return list;

    }

    public Boolean insertActivity(Double distance, double calories, int steps, String date){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DISTANCES, distance);
        contentValues.put(CALORIES, calories);
        contentValues.put(STEPS, steps);
        contentValues.put(DATE_ACTIVITY, date);

        long result = sqLiteDatabase.insert(TABLE_NAME3, null, contentValues);

        return result != -1;
    }

    public Cursor selectActivity(){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME3, null);

        return cursor;
    }

    public Cursor selectCalories(){
        sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME2, null);

        return cursor;
    }

}
