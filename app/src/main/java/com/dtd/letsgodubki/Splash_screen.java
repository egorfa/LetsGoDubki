package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Egor on 16.01.2015.
 */
public class Splash_screen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        WebView wv = (WebView) findViewById(R.id.wv);
        wv.loadUrl("file:///android_asset/splash.html");


                Thread logoTimer = new Thread()
        {
            public void run()
            {
                try {

                    sleep(3600);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Splash_screen.this, FirstActivity.class));
                finish();
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
