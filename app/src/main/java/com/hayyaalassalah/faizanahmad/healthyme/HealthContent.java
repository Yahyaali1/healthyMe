package com.hayyaalassalah.faizanahmad.healthyme;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by jamsh on 11/15/2016.
 */
public class HealthContent extends ContentProvider{

    private SQLiteDatabase database;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI("com.hayyaalassalah.faizanahmad.healthyme.HealthContent","DailySteps",1);
    }


    @Override
    public boolean onCreate() {

        SQLiteOpenHelper openHelper = new MyDbHelper(getContext());
        database = openHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (matcher.match(uri) == 1) {
            return database.query(MyDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            //return db.query("Notes",projection,selection,selectionArgs,null,null,sortOrder);}
            // Cursor cursor = database.query(db.TABLE_NAME, meaning_column, "Word = ?", args, null,null,null);
        }
        String[] cols = {"_ID"};
        return new MatrixCursor(cols);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
