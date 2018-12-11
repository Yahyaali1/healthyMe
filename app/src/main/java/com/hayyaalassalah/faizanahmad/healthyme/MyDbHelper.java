package com.hayyaalassalah.faizanahmad.healthyme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper{

    private static final String LOGTAG = "EXPLORECA";

    private static final String DATABASE_NAME = "healthrecord.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_NAME ="DailySteps";
    public static final String TOTAL_STEPS = "Steps";
    public static final String TOTAL_KM = "Km";
    public static final String MAX_SPEED = "Speed";
    public static final String DATE = "Date";
    public static final String TIMESPENT = "TimeSpent";
    public static final String TYPE = "type";

    public static final String TABLE_NAME_2 ="DailyActivities";
    public static final String ACTIVITY_NAME ="activityName";
    public static final String ACTIVITY_TIME ="duration";
    public static final String ACTIVITY_DATE ="date";
    public static final String ACTIVITY_COUNT ="count";

    public static final String TABLE_NAME_3 ="steps";
    public static final String ACTIVITY_COUNT_2 ="count";
    public static final String ACTIVITY_DATE_2 ="date";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    TOTAL_STEPS + " INTEGER, " +
                    TOTAL_KM + " FLOAT, " +
                    DATE + " TEXT," +
                    TIMESPENT + " INTEGER," +
                    TYPE + " TEXT" +
                    ")";

    private static final String TABLE_CREATE_2 =
            "CREATE TABLE " + TABLE_NAME_2 + " (" +
                    ACTIVITY_NAME + " TEXT, " +
                    ACTIVITY_TIME + " INTEGER, " +
                    ACTIVITY_DATE + " TEXT," +
                    ACTIVITY_COUNT + " INTEGER" +
                    ")";

    private static final String TABLE_CREATE_3 =
            "CREATE TABLE " + TABLE_NAME_3 + " (" +
                    ACTIVITY_COUNT_2 + " INTEGER, " +
                    ACTIVITY_DATE_2 + " TEXT" +
                    ")";

    public MyDbHelper(Context context) {
        super(context,DATABASE_NAME ,null ,DATABASE_VERSION);
        System.out.println(TABLE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(TABLE_CREATE);

        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_2);
        db.execSQL(TABLE_CREATE_3);
        Log.i(LOGTAG,"Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println(TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        Log.i(LOGTAG,"Table dropped");
        onCreate(db);
    }
}
