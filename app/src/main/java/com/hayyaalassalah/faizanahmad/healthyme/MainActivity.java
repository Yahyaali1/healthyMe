package com.hayyaalassalah.faizanahmad.healthyme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private long date;
    private static final String tag="MIT";
    private TextView totalCount;
    private TextView totalRunningCount;
    private int totalStepsToday = 0;
    ProgressBar _progressBar;
    private TextView totalSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //creating main page

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("MySteps"));

        totalSteps = (TextView) findViewById(R.id.totalSteps);


        makeGraph();

        _progressBar = (ProgressBar)findViewById (R.id.circularProgressBar);
        _progressBar.setProgress(0);
        startService(new Intent(getBaseContext(), StepCounterService.class));


        totalCount = (TextView) findViewById(R.id.totalSteps);
        //totalRunningCount = (TextView) findViewById(R.id.totalrunning);
        SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = datef.format(new Date(date));
        TextView set_date = (TextView) findViewById(R.id.date);
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy");
        assert set_date != null;
        set_date.setText(format.format(ca.getTime()));  //setting today's date*/

    }   //this function is completely okay now


    public void makeGraph() {
        /* GRAPHING */
        Calendar calendar = Calendar.getInstance();
        int d1 = calendar.get(Calendar.DAY_OF_MONTH);
        int d2 = d1-1;
        int d3 = d1-2;
        int d4 = d1-3;
        int d5 = d1-4;
        int d6 = d1-5;
        int d7 = d1-6;


        GraphView graph = (GraphView) findViewById(R.id.graph);
        System.out.println("Dates" + d1 + d3 + d4);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d7, 120),
                new DataPoint(d6, 80),
                new DataPoint(d5, 52),
                new DataPoint(d4, 31),
                new DataPoint(d3, 28),
                new DataPoint(d2, 15),
                new DataPoint(d1, 113)
        });
        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.addSeries(series);
        series.setColor(Color.rgb(52,152,219));
        series.setAnimated(true);
        series.setBackgroundColor(Color.WHITE);
        series.setThickness(10);
        series.setTitle("Step count");
        series.setDrawAsPath(true);
        graph.clearSecondScale();
        graph.getGridLabelRenderer().setTextSize(28);



       /* series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb(44,62,80);
            }
        });*/

        //series.setSpacing(20);
        graph.getViewport().setScalable(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void walkfunction(View v)    //start walking activity page
    {
        Intent i = new Intent(this, StepsActivity.class);
        startActivityForResult(i, 0);
    }

    public void runningfunction(View V) //start RunningActivity activity page
    {
        Intent i = new Intent(this, RunningActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        MyDbHelper dbhelper = new MyDbHelper(getApplicationContext());
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        System.out.println("Result code is " + resultCode);
        System.out.println("Request code is " + requestCode);
        switch(requestCode){
            case 0: // Do your stuff here...
                if(resultCode == 1){
                    //THIS IS WALKING INTENT GIVING DATA BACK
                    String steps=data.getStringExtra("steps");
                    String time=data.getStringExtra("time");
                    String km=data.getStringExtra("kilometers");
                    System.out.println(steps);
                   // totalCount = (TextView) findViewById(R.id.totalSteps);
                   // totalCount.setText("Total running count is " + steps);

                    int stepsCount = Integer.parseInt(steps);
                    totalStepsToday = totalStepsToday + stepsCount;
                    _progressBar.setProgress(totalStepsToday);
                    float totalKilometers = Float.parseFloat(km);
                    int totalTime = Integer.parseInt(time);
                    SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
                    String currentDateandTime = datef.format(new Date(date));
                    //TextView set_date = (TextView) findViewById(R.id.date);
                    Calendar ca = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String date = format.format(ca.getTime()).toString();
                    System.out.println("insert into DailySteps values (" + stepsCount + "," + totalKilometers + "," + date + "," + totalTime + "," + "'walking'" +  ")");
                     db.execSQL("insert into DailySteps values (" + stepsCount + "," + totalKilometers + "," + date + "," + totalTime + "," + "'walking'" +  ")");
                    //Log.i(tag,result);
                }

                break;
            case 1: // Do your other stuff here...
                if (resultCode == 2) {
                    //THIS IS RUNNING INTENT GIVING DATA BACK
                    //Write your code if there's no result
                    String steps=data.getStringExtra("steps");
                    String time=data.getStringExtra("time");
                    String km=data.getStringExtra("kilometers");
                    System.out.println(steps);
                    //totalRunningCount = (TextView) findViewById(R.id.totalrunning);
                    //totalRunningCount.setText("Total running count is " + steps);

                    int stepsCount = Integer.parseInt(steps);
                    totalStepsToday = totalStepsToday + stepsCount;
                    _progressBar.setProgress(totalStepsToday);
                    float totalKilometers = Float.parseFloat(km);
                    int totalTime = Integer.parseInt(time);
                    SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
                    String currentDateandTime = datef.format(new Date(date));
                   // TextView set_date = (TextView) findViewById(R.id.date);
                    Calendar ca = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String date = format.format(ca.getTime()).toString();
                    System.out.println("insert into DailySteps values (" + stepsCount + "," + totalKilometers + "," + date + "," + totalTime + "," + "'running'" +  ")");
                    db.execSQL("insert into DailySteps values (" + stepsCount + "," + totalKilometers + "," + date + "," + totalTime + "," + "'running'" + ")");

                }
                 break;
        }

        if (requestCode == 1) {
        }
    }//onActivityResult

    public void DetailsActivity(View v)  //get all the graphs
    {
        Intent i = new Intent(this, DetailsActivity.class);
        startActivity(i);
    }

    public void share(View v)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "This is the text that will be shared.");
        startActivity(Intent.createChooser(sharingIntent,"Share using"));
    }

    public void startService(View v)
    {
        startService(new Intent(getBaseContext(), StepCounterService.class));
    }

    public void stopService(View v)
    {
        stopService(new Intent(getBaseContext(), StepCounterService.class));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String steps = intent.getStringExtra("steps");
            totalStepsToday = (int)Float.parseFloat(steps);
            _progressBar.setProgress(totalStepsToday);

            int percentage = (int)((((float)totalStepsToday)/8000f)*100);
            totalSteps.setText(Integer.toString(percentage)+"%");
            Toast.makeText(context, steps, Toast.LENGTH_LONG).show();
        }
    };

    public void otherActivity(View v)
    {
        Intent i = new Intent(this, ActivityOther.class);
        startActivity(i);
    }

}
