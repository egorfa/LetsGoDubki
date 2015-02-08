package com.dtd.letsgodubki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

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


    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meeting_add);

        addImage = (Button)findViewById(R.id.imgTypeAdd);
        //android.widget.ListView mListView = new ListView(this);
        //ArrayList<String> values = new ArrayList<String>();
        //values.add("ТУСОВКА");

        ArrayList<DialogMenuItem> arrayDialogMenuItems = new ArrayList<DialogMenuItem>();

        DialogMenuItem tusovaka = new DialogMenuItem(R.drawable.drink, "ТУСОВКА");
        DialogMenuItem music = new DialogMenuItem(R.drawable.guitar, "МУЗЫКА");
        DialogMenuItem games = new DialogMenuItem(R.drawable.games, "ИГРЫ");
        DialogMenuItem cinema = new DialogMenuItem(R.drawable.films, "КИНО");
        arrayDialogMenuItems.add(tusovaka);
        arrayDialogMenuItems.add(music);
        arrayDialogMenuItems.add(games);
        arrayDialogMenuItems.add(cinema);

        final ListView lvDialogMenu = new ListView(this);
        final ListDialogMenuAdapter adapter = new ListDialogMenuAdapter(this, arrayDialogMenuItems);
        lvDialogMenu.setAdapter(adapter);
        lvDialogMenu.setDivider(getResources().getDrawable(R.drawable.divider));
        lvDialogMenu.setDividerHeight(2);


        addImage.setOnClickListener(new View.OnClickListener() {
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
                        });*/
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
        });
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
}
