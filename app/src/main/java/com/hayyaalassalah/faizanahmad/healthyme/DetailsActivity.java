package com.hayyaalassalah.faizanahmad.healthyme;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Faizan Ahmad on 11/27/2016.
 */
public class DetailsActivity extends AppCompatActivity {


    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //creating main page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MyDbHelper dbhelper = new MyDbHelper(this);
        SQLiteDatabase sql = dbhelper.getReadableDatabase();


        ArrayList<String> arraylist = new ArrayList<String>();
        String query = "SELECT * FROM DailySteps";
        Cursor cursor = sql.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int steps = cursor.getInt(cursor.getColumnIndex("Steps"));
            String sSteps = Integer.toString(steps);
            System.out.println(type+sSteps);
            arraylist.add(type+","+sSteps);
        }


        listview = (ListView) findViewById(R.id.listView);
        //String[] array = {"Faizan","Ahmad","Mohsin"};
        DetailsListAdapter adapter = new DetailsListAdapter(this,R.layout.activity_detail,arraylist);
        listview.setAdapter(adapter);

    }
}
