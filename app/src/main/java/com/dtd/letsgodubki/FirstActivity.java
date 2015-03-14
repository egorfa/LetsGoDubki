package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class FirstActivity extends Activity {


    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");
        setContentView(R.layout.activity_first);



        Button btn9_1 = (Button)findViewById(R.id.btn9_1);
        Button btn9_2 = (Button)findViewById(R.id.btn9_2);
        Button btn7 = (Button)findViewById(R.id.btn7);
        ImageButton btnAdd = (ImageButton) getActionBar().getCustomView().findViewById(R.id.addBtn);

        btn9_1.setOnClickListener(clicker);
        btn9_2.setOnClickListener(clicker);
        btn7.setOnClickListener(clicker);
        btnAdd.setOnClickListener(addMeet);
    }

    View.OnClickListener clicker = new View.OnClickListener(){
        public void onClick(View v){
            Vibrator vibrator = (Vibrator) FirstActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            Intent intent = new Intent(FirstActivity.this, MeetingsActivity.class);
            switch (v.getId()) {
                case R.id.btn7:
                    intent.putExtra("Dormitory", "7");
                    break;
                case R.id.btn9_1:
                    intent.putExtra("Dormitory", "91");
                    break;
                case R.id.btn9_2:
                    intent.putExtra("Dormitory", "92");
            }
            FirstActivity.this.startActivity(intent);
            overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        }
    };

    View.OnClickListener addMeet = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Vibrator vibrator = (Vibrator) FirstActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            Intent intent = new Intent(FirstActivity.this, MeetingAdd.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_down_up_enter,R.anim.activity_down_up_exit);
        }

    };

}
