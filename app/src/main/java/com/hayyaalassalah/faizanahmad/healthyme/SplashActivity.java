package com.hayyaalassalah.faizanahmad.healthyme;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Window;

import java.security.acl.Permission;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 2;
    private static int SPLASH_TIME_OUT = 1000;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{mPermission}, REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == MockPackageManager.PERMISSION_GRANTED ) {

                // Success Stuff here

            }
            else{
                // Failure Stuff
            }
        }
    }
}
