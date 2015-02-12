package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 123 on 04.02.2015.
 */
public class MeetingDescriptionActivity extends Activity{

    TextView title, time, peopleNumber, flat;
    ImageView imageTheme;
    Button btnGo;

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meeting_description);

        title = (TextView)findViewById(R.id.theme);
        time = (TextView)findViewById(R.id.dscrTime);
        peopleNumber = (TextView)findViewById(R.id.peopleNum);
        imageTheme = (ImageView)findViewById(R.id.imgType);
        flat = (TextView)findViewById(R.id.flatNum);
        btnGo = (Button)findViewById(R.id.btnGoJoin);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
            if(extras != null) {
                String Theme = extras.getString("Theme");
                String Time = extras.getString("Time");
                Integer ImageTheme = extras.getInt("ImageTheme");
                String CurNumPeople = extras.getString("CurNumPeople");
                String NeedNumPeople = extras.getString("NeedNumPeople");
                String FlatNum = extras.getString("flatNum");

                try {
                    title.setText(Theme);
                    time.setText(Time);
                    peopleNumber.setText(CurNumPeople + "/" + NeedNumPeople);
                    Drawable drawable = getResources().getDrawable(ImageTheme);
                    imageTheme.setImageDrawable(drawable);
                    flat.setText(FlatNum);
                }catch (Exception e){
                    Log.d("ERROR", e.getMessage());
                }
            }

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MeetingDescriptionActivity.this, MeetingsActivity.class);
                startActivity(intent1);
            }
        });
    }
}
