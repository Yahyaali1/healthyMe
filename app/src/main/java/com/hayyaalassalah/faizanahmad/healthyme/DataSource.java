package com.hayyaalassalah.faizanahmad.healthyme;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

    SQLiteDatabase database;
    MyDbHelper openHelper;

    DataSource(Context context)
    {
        openHelper = new MyDbHelper(context);
    }
    public void open()
    {
        database = openHelper.getWritableDatabase();
    }
    public void close(){
        openHelper.close();
    }


    public void create(String steps,String kilometers,String max_speed)
    {
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.TOTAL_STEPS,steps);
        values.put(MyDbHelper.TOTAL_KM,kilometers);
        values.put(MyDbHelper.MAX_SPEED,max_speed);
        database.insert(MyDbHelper.TABLE_NAME,null,values);
    }

    /*public List<String> get_words()
    {
        database = openHelper.getWritableDatabase();

        List<String> words = new ArrayList<String>();
        Cursor cursor = database.query(db.TABLE_NAME, word_column,null,null,null,null,null);
        Log.i(MainActivity.LOG_TAG,"Returned" + cursor.getCount() + "rows of words");
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                String temp ;
                temp = cursor.getString(cursor.getColumnIndex(db.WORDS));
                words.add(temp);
            }
        }
        return words;
    }
*/

}
