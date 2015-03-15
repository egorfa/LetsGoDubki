package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class FirstActivity extends Activity {

    private static String URL = "https://letsgodubki-dtd.appspot.com/_ah/api/dubkiapi/v1/itemscount";

    TextView tv7;
    TextView tv91;
    TextView tv92;
    ImageView img7, img91, img92;

    private class ArrayDorm{
        private String Dorm7;
        private String Dorm91;
        private String Dorm92;

        private ArrayDorm(String dorm7, String dorm91, String dorm92) {
            Dorm7 = dorm7;
            Dorm91 = dorm91;
            Dorm92 = dorm92;
        }

        private ArrayDorm() {
        }

        public String getDorm7() {
            return Dorm7;
        }

        public void setDorm7(String dorm7) {
            Dorm7 = dorm7;
        }

        public String getDorm91() {
            return Dorm91;
        }

        public void setDorm91(String dorm91) {
            Dorm91 = dorm91;
        }

        public String getDorm92() {
            return Dorm92;
        }

        public void setDorm92(String dorm92) {
            Dorm92 = dorm92;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");
        setContentView(R.layout.activity_first);

        tv7 = (TextView) findViewById(R.id.tv7);
        tv91 = (TextView) findViewById(R.id.tv9_1);
        tv92 = (TextView) findViewById(R.id.tv9_2);
        img7 = (ImageView)findViewById(R.id.img7);
        img91 = (ImageView)findViewById(R.id.img91);
        img92 = (ImageView)findViewById(R.id.img92);

        ItemsCountTask itemsCountTask = new ItemsCountTask();
        if(isOnline()) {
            itemsCountTask.execute(URL);
        }
        else{
            Toast.makeText(this, "Отсутствует интернет-соединение", Toast.LENGTH_LONG).show();
        }


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
            startActivity(intent);
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
            overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        }

    };


    class ItemsCountTask extends AsyncTask<String, String, ArrayDorm> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected ArrayDorm doInBackground(String... args) {
            JSONParserObj news_jParser = new JSONParserObj();
            JSONObject json = news_jParser.getJSONFromUrl(args[0]);
            ArrayDorm Dorms = null;
            if (json!=null){
                try {
                    Dorms = new ArrayDorm();
                    if(json.has("dorm7")){
                    Dorms.setDorm7(json.getString("dorm7"));
                    }else{
                        Dorms.setDorm7("");
                    }
                    if(json.has("dorm91")){
                    Dorms.setDorm91(json.getString("dorm91"));
                    }else{
                        Dorms.setDorm91("");
                    }
                    if(json.has("dorm92")) {
                        Dorms.setDorm92(json.getString("dorm92"));
                    }else{
                        Dorms.setDorm92("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return Dorms;
        }

        @Override
        protected void onPostExecute(ArrayDorm dorms) {
            if(dorms!=null) {
                if (dorms.getDorm7().equals("0")) {
                    tv7.setVisibility(View.INVISIBLE);
                    img7.setVisibility(View.INVISIBLE);
                } else {
                    tv7.setText("+" + dorms.getDorm7());
                }
                if (dorms.getDorm91().equals("0")) {
                    tv91.setVisibility(View.INVISIBLE);
                    img91.setVisibility(View.INVISIBLE);
                } else {
                    tv91.setText("+" + dorms.getDorm91());
                }
                if (dorms.getDorm92().equals("0")) {
                    tv92.setVisibility(View.INVISIBLE);
                    img92.setVisibility(View.INVISIBLE);
                } else {
                    tv92.setText("+" + dorms.getDorm92());
                }
            }
            else{
                //tv7.setText("+" + "0");
                //tv91.setText("+" + "0");
                //tv92.setText("+" + "0");
                tv7.setVisibility(View.INVISIBLE);
                tv91.setVisibility(View.INVISIBLE);
                tv92.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Vibrator vibrator = (Vibrator) FirstActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        finish();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
