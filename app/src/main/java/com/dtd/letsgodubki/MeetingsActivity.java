package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

        View view = getActionBar().getCustomView();
        mTitle = (TextView) view.findViewById(R.id.actionbarTitle);


        Intent intent = getIntent();
        NumDorm = intent.getExtras().getString("Dormitory");
        String dorm = "";
        switch (NumDorm.toString()){
            case "7":
                dorm=NumDorm.toString();
                break;
            case "91":
                dorm = "9/1";
                break;
            case "92":
                dorm = "9/2";
                break;
        }

        mTitle.setText("Встречи в " + dorm);

        JazzyListView listViewMeetings;

        listViewMeetings = (JazzyListView) findViewById(R.id.LV1);
        listViewMeetings.setTransitionEffect(JazzyHelper.FADE);



        JSONParse newsTask = new JSONParse();
        newsTask.execute(TAG_URL1 + NumDorm + TAG_URL2);
        try{
            final ArrayList<MeetItem> array = newsTask.get();
            ListMeetingsAdapter adapter = new ListMeetingsAdapter(this, array);
            listViewMeetings.setAdapter(adapter);
            listViewMeetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                    Intent intent = new Intent(MeetingsActivity.this, MeetingDescriptionActivity.class);

                    intent.putExtra("Theme", array.get(position).header);
                    intent.putExtra("Time", array.get(position).starttime);
                    intent.putExtra("Category", array.get(position).category);
                    intent.putExtra("CurNumPeople", array.get(position).currentp);
                    intent.putExtra("NeedNumPeople", array.get(position).limitp);
                    intent.putExtra("Content", array.get(position).description);
                    intent.putExtra("Dormitory", array.get(position).dormitory);
                    intent.putExtra("Contacts", array.get(position).contacts);
                    intent.putExtra("ID", array.get(position).ID);
                    //intent.putExtra("flatNum", array.get(position).flat);

                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_down_up_enter, R.anim.activity_down_up_exit);
                    finish();
                }
            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    }

    class JSONParse extends AsyncTask<String, String, ArrayList<MeetItem>> {

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
        protected void onPostExecute(ArrayList<MeetItem> news_array) {
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
    public void onBackPressed(){
        finish();
    }
}