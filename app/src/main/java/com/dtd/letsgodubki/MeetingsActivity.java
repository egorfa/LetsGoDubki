package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Egor on 21.11.2014.
 */
public class MeetingsActivity extends Activity {

    Button addBtn;

    protected class JSON_element
    {
        String title;
        String creadted;
        String link;
        String img_link;
    }

    @Override
     protected void attachBaseContext(Context newBase){
         super.attachBaseContext(new CalligraphyContextWrapper(newBase));
        /*super.attachBaseContext(EdgeEffectOverride.createContextWrapper(newBase,
                newBase.getResources().getColor(R.color.ActionBar)));*/
        //EdgeEffectOverride.createContextThemeWrapper(newBase, R.drawable.actionbar_background, R.color.ActionBar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meetings_list);

        addBtn = (Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingsActivity.this, MeetingAdd.class);
                startActivity(intent);
            }
        });

        final ArrayList<Meeting> arrayMeetings;
        JazzyListView listViewMeetings;



        arrayMeetings = new ArrayList<Meeting>();

        Meeting mafia = new Meeting(R.drawable.drink, "15:01", "Вечером играем в мафию", R.drawable.circle, "5", "15", "507");
        Meeting drink1 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "8", "203");
        Meeting drink2 = new Meeting(R.drawable.games, "18:00", "Рыба1", R.drawable.circle, "1", "5", "444");
        Meeting drink3 = new Meeting(R.drawable.films, "19:50", "Рыба2", R.drawable.circle, "2", "5", "555");
        Meeting drink4 = new Meeting(R.drawable.guitar, "02:42", "Рыба3", R.drawable.circle, "3", "5", "666");
        Meeting drink5 = new Meeting(R.drawable.guitar, "13:31", "Рыба4", R.drawable.circle, "4", "5", "777");
        Meeting drink6 = new Meeting(R.drawable.drink, "13:55", "Рыба5", R.drawable.circle, "5", "5", "888");
        Meeting drink7 = new Meeting(R.drawable.games, "20:00", "Рыба6", R.drawable.circle, "6", "10", "999");
        Meeting drink8 = new Meeting(R.drawable.films, "10:10", "Рыба7", R.drawable.circle, "10", "10", "1000");
        Meeting drink9 = new Meeting(R.drawable.guitar, "13:42", "Рыба8", R.drawable.circle, "7", "10", "1010");
        Meeting drink10 = new Meeting(R.drawable.films, "13:42", "Рыба9", R.drawable.circle, "8", "10", "1111");
        Meeting drink11 = new Meeting(R.drawable.drink, "13:42", "Рыба10", R.drawable.circle, "9", "10", "1212");

        arrayMeetings.add(mafia);
        arrayMeetings.add(drink1 );
        arrayMeetings.add(drink2 );
        arrayMeetings.add(drink3 );
        arrayMeetings.add(drink4 );
        arrayMeetings.add(drink5 );
        arrayMeetings.add(drink6 );
        arrayMeetings.add(drink7 );
        arrayMeetings.add(drink8 );
        arrayMeetings.add(drink9 );
        arrayMeetings.add(drink10);
        arrayMeetings.add(drink11);

        listViewMeetings = (JazzyListView)findViewById(R.id.LV1);
        listViewMeetings.setTransitionEffect(JazzyHelper.FADE);
        ListMeetingsAdapter adapter = new ListMeetingsAdapter(this, arrayMeetings);
        listViewMeetings.setAdapter(adapter);

        listViewMeetings.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                Intent intent = new Intent(MeetingsActivity.this,MeetingDescriptionActivity.class);

                intent.putExtra("Theme", arrayMeetings.get(position).getTitle());
                intent.putExtra("Time", arrayMeetings.get(position).getTime());
                intent.putExtra("ImageTheme", arrayMeetings.get(position).getDrawableId());
                intent.putExtra("CurNumPeople", arrayMeetings.get(position).getCurNum());
                intent.putExtra("NeedNumPeople", arrayMeetings.get(position).getNedNum());
                intent.putExtra("flatNum", arrayMeetings.get(position).getflatNum());

                startActivity(intent);
            }
        });

    }
}