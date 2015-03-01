package com.dtd.letsgodubki;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 123 on 06.02.2015.
 */
public class MeetingAdd extends Activity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /*final int MENU_DRAWABLE_DRINK = 1;
    final int MENU_DRAWABLE_GUITAR = 2;
    final int MENU_DRAWABLE_GAMES = 3;
    final int MENU_DRAWABLE_FILMS = 4;*/

    EditText title;
    EditText startTime;
    EditText description;
    EditText contacts;
    EditText limit;
    EditText hostel;
    EditText currentPeople;


    ImageButton addImage;
    Dialog dialog;
    final int DIALOG=1;

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    TimePickerDialog TPD;
    DatePickerDialog DPD;

    Toast m_currentToast=null;


    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meeting_add);

        calendar = Calendar.getInstance();

        TPD = TimePickerDialog.newInstance(MeetingAdd.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        DPD = DatePickerDialog.newInstance(MeetingAdd.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        title = (EditText) findViewById(R.id.headingText);
        startTime = (EditText) findViewById(R.id.startText);
        description = (EditText) findViewById(R.id.commentsText);
        contacts = (EditText) findViewById(R.id.contactsText);
        limit = (EditText) findViewById(R.id.needPeopleText);
        hostel = (EditText) findViewById(R.id.hostelText);
        currentPeople = (EditText) findViewById(R.id.curPeopleText);

        addImage = (ImageButton) findViewById(R.id.imgTypeAdd);
        addImage.setImageResource(R.drawable.button_plus);
        //android.widget.ListView mListView = new ListView(this);
        //ArrayList<String> values = new ArrayList<String>();
        //values.add("ТУСОВКА");


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG);
            }
        });

        startTime.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    DPD.show(getFragmentManager(), "datePicker");
                }
                return false;
            }
        });


        ImageButton addMeet = (ImageButton) findViewById(R.id.btn_go);

        addMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(validEditText()) {
                response_add respTask = new response_add();
                String category = null;
                if (addImage.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.drink).getConstantState())) {
                    category = "drink";
                } else if (addImage.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.guitar).getConstantState())) {
                    category = "guitar";
                } else if (addImage.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.films).getConstantState())) {
                    category = "films";
                } else if (addImage.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.games).getConstantState())) {
                    category = "games";
                }
                    String str = startTime.getText().toString();
                    String startT = str.substring(6,10) + "-" + str.substring(3,5) + "-" + str.substring(0,2) + "T" + str.substring(11,13) + ":" + str.substring(14,16)+ ":" + "00.000000";

                    Calendar mcurrentTime = Calendar.getInstance();
                    mcurrentTime.set(Integer.valueOf(str.substring(6,10)),Integer.valueOf(str.substring(3,5)),Integer.valueOf(str.substring(0,2)));
                    int monthDays = mcurrentTime.getActualMaximum(Calendar.DAY_OF_MONTH);
                    Integer hourP = Integer.valueOf(str.substring(11,13))+10;
                    Integer dayP = Integer.valueOf(str.substring(0,2));
                    Integer monthP = Integer.valueOf(str.substring(3,5));
                    Integer yearP = Integer.valueOf(str.substring(6,10));
                    if(hourP>=24)
                    {
                        hourP = hourP - 24;
                        dayP = dayP+1;
                        if(dayP>monthDays)
                        {
                            dayP = dayP - monthDays;
                            if(monthP==12){
                                monthP=1;
                                yearP += 1;
                            }else monthP += 1;
                        }
                    }

                    String  str_month = monthP.toString(), str_day = dayP.toString();
                    if(monthP<10) str_month ="0"+String.valueOf(monthP);
                    if(dayP<10) str_day = "0"+String.valueOf(dayP);

                    String endT = yearP.toString()+ "-" + str_month + "-" + str_day + "T" + hourP.toString() + startT.substring(13, startT.length());

                respTask.execute(startT, endT, title.getText().toString(), description.getText().toString(), category, contacts.getText().toString(), currentPeople.getText().toString(), limit.getText().toString(), hostel.getText().toString());
                /*}else{
                    showToast("Не все поля заполнены");
                }*/
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(year, monthOfYear, dayOfMonth);

        startTime.setText(sdf.format(myCalendar.getTime()).substring(0,10));

        TPD.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Calendar myCalendar = Calendar.getInstance();
        Date date = new Date();
        date.setHours(hourOfDay);
        date.setMinutes(minute);
        myCalendar.setTime(date);

        startTime.setText(startTime.getText() + " " + sdf.format(myCalendar.getTime()));

    }


    private boolean validEditText()
    {
        if(title.getText().toString().equals("")) return false;
        if(startTime.getText().toString().equals("")) return false;
        if(description.getText().toString().equals("")) return false;
        if(contacts.getText().toString().equals("")) return false;
        if(limit.getText().toString().equals("")) return false;
        if(hostel.getText().toString().equals("")) return false;
        if(currentPeople.getText().toString().equals("")) return false;
        if(addImage.getBackground() == getResources().getDrawable(R.drawable.button_plus)) return false;
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //adb.setTitle("Custom dialog");
        // создаем view из dialog.xml
        FrameLayout view = (FrameLayout) getLayoutInflater()
                .inflate(R.layout.dialog_type, null);
        ListView lv = (ListView) view.findViewById(R.id.lvTypes);

        ArrayList<DialogMenuItem> arrayDialogMenuItems = new ArrayList<DialogMenuItem>();

        DialogMenuItem tusovka = new DialogMenuItem(R.drawable.drink, "ТУСОВКА");
        DialogMenuItem music = new DialogMenuItem(R.drawable.guitar, "МУЗЫКА");
        DialogMenuItem games = new DialogMenuItem(R.drawable.games, "ИГРЫ");
        DialogMenuItem cinema = new DialogMenuItem(R.drawable.films, "КИНО");
        arrayDialogMenuItems.add(tusovka);
        arrayDialogMenuItems.add(music);
        arrayDialogMenuItems.add(games);
        arrayDialogMenuItems.add(cinema);

        ListDialogMenuAdapter adapter = new ListDialogMenuAdapter(this, arrayDialogMenuItems);
        lv.setAdapter(adapter);


        // устанавливаем ее, как содержимое тела диалога
        adb.setView(view);
        // находим TexView для отображения кол-ва
        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, final Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG) {
            ListView lvTypes = (ListView) dialog.getWindow().findViewById(R.id.lvTypes);
            lvTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(i){
                        case 0:
                            addImage.setImageResource(R.drawable.drink);
                            break;
                        case 1:
                            addImage.setImageResource(R.drawable.guitar);
                            break;
                        case 2:
                            addImage.setImageResource(R.drawable.games);
                            break;
                        case 3:
                            addImage.setImageResource(R.drawable.films);
                    }
                    dialog.dismiss();
                }
            });

        }
    }


    private class response_add extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(MeetingAdd.this,R.style.CustomDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog);
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params){
            //String str = null;
            Integer result;
            //str = EntityUtils.toString(postDataEnqueue(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]).getEntity());
            result = postDataEnqueue(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]).getStatusLine().getStatusCode();

            return result;
        }

        @Override
        protected void onPostExecute(Integer result){
            super.onPostExecute(result);
            if (result==200){
                showToast("Встреча успешно добавлена.");
            } else{
                showToast("Ошибка, попробуйте позже");
            }
            dialog.dismiss();
        }
    }


    public HttpResponse postDataEnqueue(String startTime, String endTime, String header, String description, String category, String contacts, String currentp, String limit, String dormitory){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://letsgodubki-dtd.appspot.com/_ah/api/dubkiapi/v1/item");///456///091
        httppost.setHeader("Content-Type", "application/json; charset=utf-8");////
        HttpResponse response = null;

        try {

            JSONObject json = new JSONObject();
            try {
                json.put("category", category);
                json.put("contacts", contacts);
                json.put("description", description);
                json.put("dormitory", dormitory);
                json.put("currentp", currentp);
                json.put("endtime",endTime);
                json.put("header", header);
                json.put("limitp", limit);
                json.put("starttime",startTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String str = json.toString();
            StringEntity se = new StringEntity(str, HTTP.UTF_8);

            // Выполняем HTTP Post Request
            httppost.setEntity(se);
            response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        return response;
    }


    void showToast(String text)
    {
        if(m_currentToast != null)
        {
            m_currentToast.cancel();
        }
        m_currentToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        m_currentToast.setGravity(Gravity.CENTER,0,0);
        m_currentToast.show();

    }

}
