package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Egor on 21.11.2014.
 */
public class MeetingsActivity extends Activity {

    @Override
     protected void attachBaseContext(Context newBase){
        // super.attachBaseContext(new CalligraphyContextWrapper(newBase));
        super.attachBaseContext(EdgeEffectOverride.createContextWrapper(newBase,
                newBase.getResources().getColor(R.color.ActionBar)));
        //EdgeEffectOverride.createContextThemeWrapper(newBase, R.drawable.actionbar_background, R.color.ActionBar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meetings_list);

        ArrayList<Meeting> arrayMeetings;
        ListView listViewMeetings;

        arrayMeetings = new ArrayList<Meeting>();

        Meeting mafia = new Meeting(R.drawable.mafia, "15:01", "Вечером играем в мафию", R.drawable.circle, "5", "15");
        Meeting drink1 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink2 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink3 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink4 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink5 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink6 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink7 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink8 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink9 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink10 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");
        Meeting drink11 = new Meeting(R.drawable.drink, "13:42", "Кальян, девочки, чувачки", R.drawable.circle, "0", "5");

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

        listViewMeetings = (ListView)findViewById(R.id.LV1);
        ListMeetingsAdapter adapter = new ListMeetingsAdapter(this, arrayMeetings);
        listViewMeetings.setAdapter(adapter);

    }
}