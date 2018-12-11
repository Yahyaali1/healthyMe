package com.hayyaalassalah.faizanahmad.healthyme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class StepsActivity extends AppCompatActivity implements SensorEventListener {

    private String temp;
    private SensorManager sensorManager;
    Sensor countSensor;
    private TextView count;
    boolean activity_running;
    Timer timer = new Timer();
    Thread t;
    int startValue = 0;
    int finalValue = 0;
    int tempValue = 0;
    private TextView km;
    boolean start = false;
    public static final int WALKING_RESULT = 1;
    private TextView myTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        km = (TextView) findViewById(R.id.km);
        TextView steps = (TextView) findViewById(R.id.stepsCount);
        steps.setText("0");
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            System.out.println("availabe");
        }
        else
            System.out.println("Not availabe");


        count =(TextView) findViewById(R.id.stepsCount);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume(){
        super.onResume();
        activity_running = true;
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        myTime = (TextView) findViewById(R.id.timer);
        t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                int currentTime = Integer.parseInt(myTime.getText().toString());
                                myTime.setText(Integer.toString(currentTime+1));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

        if(countSensor!=null)
        {
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(this,"Count sensor not available!",Toast.LENGTH_LONG).show();
        }
    }
    public void onStop(View view)
    {
        t.interrupt();
        this.onPause();
    }

    public void start(View view)
    {
        startValue = startValue + tempValue;
        this.onResume();
    }

    @Override
    protected void onPause(){
        System.out.println("Pausing");
        timer.cancel();
        t.interrupt();
        activity_running = false;
        super.onPause();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println(startValue);
        System.out.println(finalValue);

        if(start == false) {
            count.setText("0");
            temp = "0";
            startValue = (int) Float.parseFloat(String.valueOf(event.values[0]));
            start = true;
        }
        else {
            if (activity_running) {
                finalValue = (int) Float.parseFloat(String.valueOf(event.values[0]));
                int setValue = finalValue - startValue;
                count.setText(Integer.toString(setValue));

                float steps = (float) setValue;
                float kilometers = steps / 1250f;
                float totalTime = Float.parseFloat(myTime.getText().toString());

                km.setText(Float.toString(kilometers));
                temp = Integer.toString(setValue);
            } else {
                System.out.println("Entering else");
                int newfinalValue = (int) Float.parseFloat(String.valueOf(event.values[0]));
                tempValue = (newfinalValue - finalValue);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
    public void myfunction(View view){

        SensorManager sensorManager2 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager2.unregisterListener(this,countSensor);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("steps",count.getText());
        returnIntent.putExtra("time",myTime.getText());
        returnIntent.putExtra("kilometers",km.getText());
        System.out.println("Sending" + count.getText());
        setResult(WALKING_RESULT, returnIntent);
        finish();
    }
}
