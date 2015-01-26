package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        btn9_1.setOnClickListener(handler);
        btn9_2.setOnClickListener(handler);
        btn7.setOnClickListener(handler);
    }

    View.OnClickListener handler = new View.OnClickListener(){
        public void onClick(View v){
            Intent i = new Intent(FirstActivity.this, MeetingsActivity.class);
            FirstActivity.this.startActivity(i);
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
