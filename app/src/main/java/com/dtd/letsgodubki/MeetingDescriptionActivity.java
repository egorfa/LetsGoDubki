package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 123 on 04.02.2015.
 */
public class MeetingDescriptionActivity extends Activity{

    TextView title, time, peopleNumber, flat, content, vkId, dormitory, message, consist;
    ImageView imageTheme;
    Button btnGo;
    ProgressWheel wheel;

    Toast m_currentToast;

    String Id;

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/5haPbT1K.ttf");

        setContentView(R.layout.meeting_description);

        title =         (TextView)findViewById(R.id.theme);
        time =          (TextView)findViewById(R.id.dscrTime);
        peopleNumber =  (TextView)findViewById(R.id.peopleNum);
        imageTheme =    (ImageView)findViewById(R.id.imgType);
        flat =          (TextView)findViewById(R.id.flatNum);
        content =       (TextView) findViewById(R.id.vbros);
        dormitory =     (TextView) findViewById(R.id.number);
        vkId =          (TextView) findViewById(R.id.vkId);
        btnGo =         (Button)findViewById(R.id.btnGoJoin);
        wheel =         (ProgressWheel) findViewById(R.id.progress_wheel);
        message =         (TextView) findViewById(R.id.message);
        consist =       (TextView) findViewById(R.id.txt_consist);

        message.setVisibility(View.INVISIBLE);
        wheel.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
            if(extras != null) {
                String Theme = extras.getString("Theme");
                String Time = extras.getString("Time");
                String category = extras.getString("Category");
                Integer CurNumPeople = extras.getInt("CurNumPeople", 0);
                Integer NeedNumPeople = extras.getInt("NeedNumPeople", 0);
                Integer FlatNum = extras.getInt("flatNum", 0);
                Integer Dormitory = extras.getInt("Dormitory", 0);
                String Description = extras.getString("Content");
                String Contacts = extras.getString("Contacts");
                Id = extras.getString("ID");

                try {
                    title.setText(Theme);
                    if(!Time.equals("")){
                    String t = Time.substring(11, 16);
                    time.setText(t);}
                    peopleNumber.setText(CurNumPeople.toString() + "/" + NeedNumPeople.toString());
                    flat.setText(FlatNum.toString());
                    content.setText(Description);
                    dormitory.setText(Dormitory.toString());
                    vkId.setText(Contacts);


                    int resId = 0;
                    if(category.equals("drink")){
                        resId = R.drawable.drink;
                    }else if(category.equals("guitar")){
                        resId = R.drawable.guitar;
                    }else if(category.equals("films")){
                        resId = R.drawable.films;
                    }else if(category.equals("games")){
                        resId = R.drawable.games;
                    }

                    imageTheme.setImageResource(resId);



                }catch (Exception e){
                    Log.d("ERROR", e.getMessage());
                }
            }

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnGo.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.error_icon).getConstantState()) || btnGo.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.done_icon).getConstantState())){
                    LinearLayout ll = (LinearLayout) findViewById(R.id.ll2);
                    ll.setBackgroundColor(getResources().getColor(R.color.Blue));
                    btnGo.setBackground(getResources().getDrawable(R.drawable.go_btn));
                    consist.setVisibility(View.VISIBLE);
                    peopleNumber.setVisibility(View.VISIBLE);
                    message.setVisibility(View.INVISIBLE);
                }else {

                    SubscribeAsync subscribe = new SubscribeAsync();
                    subscribe.execute(Id);
                }
            }
        });

    }

    class SubscribeAsync extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute(){
            wheel.setVisibility(View.VISIBLE);
        }


        @Override
        protected Integer doInBackground(String... args) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("https://letsgodubki-dtd.appspot.com/_ah/api/dubkiapi/v1/itemsubscribe/" + args[0]);///456///091
            httpGet.setHeader("Content-Type", "application/json; charset=utf-8");////

            Integer result = null;
            try {
                HttpResponse response = httpclient.execute(httpGet);
                result = response.getStatusLine().getStatusCode();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            wheel.setVisibility(View.INVISIBLE);
            consist.setVisibility(View.INVISIBLE);
            peopleNumber.setVisibility(View.INVISIBLE);
            switch(result) {
                case 200:
                    btnGo.setBackground(getResources().getDrawable(R.drawable.done_icon));
                    message.setText("УСПЕХ");
                    message.setVisibility(View.VISIBLE);
                    showToast("Вы добавлены на встречу.");
                    break;
                default:
                    LinearLayout ll = (LinearLayout) findViewById(R.id.ll2);
                    ll.setBackgroundColor(getResources().getColor(R.color.Red));
                    btnGo.setBackground(getResources().getDrawable(R.drawable.error_icon));
                    message.setText("ОШИБКА");
                    message.setVisibility(View.VISIBLE);
                    if(result==503)
                    {
                        showToast("Извините, на эту встречу уже нет мест.");
                    }else{
                        showToast("Извините, запрос отклонён. Попробуйте позже.");
                    }
                    break;
            }
        }
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
