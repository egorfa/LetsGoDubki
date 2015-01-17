package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Egor on 16.01.2015.
 */
public class Splash_screen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try {
                    sleep(5000);
                    startActivity(new Intent(Splash_screen.this, FirstActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        logoTimer.start();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
