package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.twotoasters.jazzylistview.JazzyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Egor on 21.11.2014.
 */
public class MeetingsActivity extends Activity {

    private String TAG_URL1 = "https://letsgodubki-dtd.appspot.com/_ah/api/dubkiapi/v1/myitems?dormitory=";
    private String TAG_URL2 = "&order=starttime";

    private String TAG_LIMITP = "limitp";
    private String TAG_CONTACTS = "contacts";
    private String TAG_CURRENTP = "currentp";
    private String TAG_DORMITORY = "dormitory";
    private String TAG_HEADER = "header";
    private String TAG_STARTTIME = "starttime";
    private String TAG_ENDTIME = "endtime";
    private String TAG_DESCRIPTION = "description";
    private String TAG_CATEGORY = "category";
    private String TAG_ID = "id";


    TextView mTitle;
    CircularProgressBar bar;
    JazzyListView listViewMeetings;
    ArrayList<MeetItem> Array;

    String NumDorm;

    public class MeetItem {
        String category;
        Integer limitp;
        String contacts;
        Integer currentp;
        Integer dormitory;
        String header;
        String starttime;
        String endtime;
        String description;
        String ID;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meetings_list);

        bar = (CircularProgressBar) findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);

        View view = getActionBar().getCustomView();
        mTitle = (TextView) view.findViewById(R.id.actionbarTitle);
        //title = (TextView) view.findViewById(R.id.title);

        Intent intent = getIntent();
        NumDorm = intent.getExtras().getString("Dormitory");
        String dorm = "";
        switch (NumDorm){
            case "7":
                dorm=NumDorm;
                break;
            case "91":
                dorm = "9/1";
                break;
            case "92":
                dorm = "9/2";
                break;
        }

        mTitle.setText("Встречи в " + "«" + dorm + "»");

        listViewMeetings = (JazzyListView) findViewById(R.id.LV1);

        MeetingsList meetingsTask = new MeetingsList();
        meetingsTask.execute(TAG_URL1 + NumDorm + TAG_URL2);

            listViewMeetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                    Intent intent = new Intent(MeetingsActivity.this, MeetingDescriptionActivity.class);

                    intent.putExtra("Theme", Array.get(position).header);
                    intent.putExtra("Time", Array.get(position).starttime);
                    intent.putExtra("Category", Array.get(position).category);
                    intent.putExtra("CurNumPeople", Array.get(position).currentp);
                    intent.putExtra("NeedNumPeople", Array.get(position).limitp);
                    intent.putExtra("Content", Array.get(position).description);
                    intent.putExtra("Dormitory", Array.get(position).dormitory);
                    intent.putExtra("Contacts", Array.get(position).contacts);
                    intent.putExtra("ID", Array.get(position).ID);
                    //intent.putExtra("flatNum", array.get(position).flat);

                    Vibrator vibrator = (Vibrator) MeetingsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_down_up_enter, R.anim.activity_down_up_exit);
                    finish();
                }
            });
    }

    class MeetingsList extends AsyncTask<String, String, ArrayList<MeetItem>> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<MeetItem> doInBackground(String... args) {
            JSONParser news_jParser = new JSONParser();
            JSONArray json = news_jParser.getJSONFromUrl(args[0]);
            ArrayList<MeetItem> JSON_array = new ArrayList<MeetItem>();
            if (json!=null){
                try {
                    for (int i = 0; i < json.length(); i++) {
                        MeetItem buf = new MeetItem();
                        buf.limitp = Integer.valueOf(checkNull(json.getJSONObject(i), TAG_LIMITP));
                        buf.contacts = checkNull(json.getJSONObject(i), TAG_CONTACTS);
                        buf.currentp = Integer.valueOf(checkNull(json.getJSONObject(i), TAG_CURRENTP));
                        buf.dormitory = Integer.valueOf(checkNull(json.getJSONObject(i), TAG_DORMITORY));
                        buf.header = checkNull(json.getJSONObject(i), TAG_HEADER);
                        buf.starttime= checkNull(json.getJSONObject(i), TAG_STARTTIME);
                        buf.endtime = checkNull(json.getJSONObject(i), TAG_ENDTIME);
                        buf.description = checkNull(json.getJSONObject(i), TAG_DESCRIPTION);
                        buf.category = checkNull(json.getJSONObject(i), TAG_CATEGORY);
                        buf.ID = checkNull(json.getJSONObject(i), TAG_ID);
                        JSON_array.add(buf);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return JSON_array;
        }

        @Override
        protected void onPostExecute(ArrayList<MeetItem> array) {
            bar.setVisibility(View.INVISIBLE);
            Array = array;
            ListMeetingsAdapter adapter = new ListMeetingsAdapter(MeetingsActivity.this, array);
            listViewMeetings.setAdapter(adapter);

        }

    }

    private String checkNull(JSONObject json, String str_in){
        if (json.has(str_in)){
            try {
                return json.getString(str_in);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else return "";
        return "";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Vibrator vibrator = (Vibrator) MeetingsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }
}