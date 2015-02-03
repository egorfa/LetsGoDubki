package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button btn9_1 = (Button)findViewById(R.id.btn9_1);
        Button btn9_2 = (Button)findViewById(R.id.btn9_2);
        Button btn7 = (Button)findViewById(R.id.btn7);

        btn9_1.setOnClickListener(clicker);
        btn9_2.setOnClickListener(clicker);
        btn7.setOnClickListener(clicker);
    }

    View.OnClickListener clicker = new View.OnClickListener(){
        public void onClick(View v){
            Intent i = new Intent(FirstActivity.this, MeetingsActivity.class);
            switch (v.getId()) {
                //case R.id.btn7:
                //i.putExtra("dormitory", )
            }
            FirstActivity.this.startActivity(i);
        }
    };

}
