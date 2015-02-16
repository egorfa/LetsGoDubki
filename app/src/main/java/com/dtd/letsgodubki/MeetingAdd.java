package com.dtd.letsgodubki;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 123 on 06.02.2015.
 */
public class MeetingAdd extends Activity {

    /*final int MENU_DRAWABLE_DRINK = 1;
    final int MENU_DRAWABLE_GUITAR = 2;
    final int MENU_DRAWABLE_GAMES = 3;
    final int MENU_DRAWABLE_FILMS = 4;*/

    Button addImage;
    Dialog dialog;
    final int DIALOG=1;


    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meeting_add);

        final EditText title = (EditText) findViewById(R.id.headingText);
        final EditText startTime = (EditText) findViewById(R.id.startText);
        final EditText description = (EditText) findViewById(R.id.commentsText);
        final EditText contacts = (EditText) findViewById(R.id.contactsText);
        final EditText limit = (EditText) findViewById(R.id.needPeopleText);

        addImage = (Button)findViewById(R.id.imgTypeAdd);
        //android.widget.ListView mListView = new ListView(this);
        //ArrayList<String> values = new ArrayList<String>();
        //values.add("ТУСОВКА");

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG);
            }
        });

        ImageButton addMeet = (ImageButton) findViewById(R.id.btn_go);

        addMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                response_add respTask = new response_add();
                respTask.execute(startTime.getText().toString(), "null", title.getText().toString(), description.getText().toString(), "null", contacts.getText().toString(), "null", limit.getText().toString());
                String str;
                try {
                    str = respTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });






        /*addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(MeetingAdd.this);
                /*final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        MeetingAdd.this,
                        android.R.layout.simple_selectable_list_item);

                builderSingle.setAdapter(arrayAdapter,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                //builderSingle.setView(null).setMessage(null);

                lvDialogMenu.setEdgeEffectColor(getResources().getColor(R.color.Red));
                if (lvDialogMenu.getParent() == null) {
                    builderSingle.setView(lvDialogMenu);
                } else {
                    //lvDialogMenu.getParent().removeView;
                    lvDialogMenu.getParent();

                    builderSingle.setView(lvDialogMenu);
                }
                //builderSingle.setView(lvDialogMenu);
                final AlertDialog alertDialog = builderSingle.create();
                alertDialog.show();

                lvDialogMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        try {
                            switch (position) {
                                case 0:
                                    addImage.setBackground(getResources().getDrawable(R.drawable.drink));
                                    break;
                                case 1:
                                    addImage.setBackground(getResources().getDrawable(R.drawable.guitar));
                                    break;
                                case 2:
                                    addImage.setBackground(getResources().getDrawable(R.drawable.games));
                                    break;
                                case 3:
                                    addImage.setBackground(getResources().getDrawable(R.drawable.films));
                            }
                        }catch (Exception e){
                            Log.d("QWQWQWQWQW", e.getMessage());
                        }
                        //alertDialog.cancel();
                        alertDialog.dismiss();
                        //alertDialog.setView(null);
                        //builderSingle.setView(null);
                    }
                });
            }
        });*/






    }

    /*public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_DRAWABLE_DRINK, 0 ,"ТУСОВКА");
        menu.add(0, MENU_DRAWABLE_GUITAR, 0 ,"МУЗЫКА");
        menu.add(0, MENU_DRAWABLE_GAMES, 0 ,"ИГРЫ");
        menu.add(0, MENU_DRAWABLE_FILMS, 0 ,"КИНО");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_DRAWABLE_DRINK:
                addImage.setBackground(getResources().getDrawable(R.drawable.drink));
                break;
            case MENU_DRAWABLE_GUITAR:
                addImage.setBackground(getResources().getDrawable(R.drawable.guitar));
                break;
            case MENU_DRAWABLE_GAMES:
                addImage.setBackground(getResources().getDrawable(R.drawable.games));
                break;
            case MENU_DRAWABLE_FILMS:
                addImage.setBackground(getResources().getDrawable(R.drawable.films));
                break;
        }

        return super.onContextItemSelected(item);
    }*/


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
                            addImage.setBackground(getResources().getDrawable(R.drawable.drink));
                            break;
                        case 1:
                            addImage.setBackground(getResources().getDrawable(R.drawable.guitar));
                            break;
                        case 2:
                            addImage.setBackground(getResources().getDrawable(R.drawable.games));
                            break;
                        case 3:
                            addImage.setBackground(getResources().getDrawable(R.drawable.films));
                    }
                    dialog.dismiss();
                }
            });

        }
    }


    private class response_add extends AsyncTask<String, Void, String> {


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
        protected String doInBackground(String... params){
            String str = null;
            try {
                str = EntityUtils.toString(postDataEnqueue(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]).getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        protected void onPostExecute(String str){
            super.onPostExecute(str);
            dialog.dismiss();
        }
    }


    public HttpResponse postDataEnqueue(String startTime, String endTime, String header, String description, String category, String contacts, String currentp, String limit){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://letsgodubki-dtd.appspot.com/_ah/api/dubkiapi/v1/item");///456///091
        HttpResponse response = null;

        try {
            //Добавляем свои данные
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("starttime", startTime));
            nameValuePairs.add(new BasicNameValuePair("endtime", endTime));
            nameValuePairs.add(new BasicNameValuePair("header", header));
            nameValuePairs.add(new BasicNameValuePair("description", description));
            nameValuePairs.add(new BasicNameValuePair("category", category));
            nameValuePairs.add(new BasicNameValuePair("contacts", contacts));
            nameValuePairs.add(new BasicNameValuePair("currentp", currentp));
            nameValuePairs.add(new BasicNameValuePair("limit", limit));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            /*"starttime": "",
                    "description": "",
                    "contacts": "",
                    "limit": "",
                    "currentp": "",
                    "category": "",
                    "endtime": "",
                    "header": ""
            */

            // Выполняем HTTP Post Request
            response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        return response;
    }


}
