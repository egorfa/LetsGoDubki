package com.dtd.letsgodubki;

/**
 * Created by 123 on 19.01.2015.
 */
public class Meeting {
    private int drawableId;
    private String time;
    private String title;

    public Meeting(int drawableId, String time, String title){
        this.drawableId = drawableId;
        this.time = time;
        this.title = title;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTime(){
        return time;
    }
    public String getTitle(){
        return title;
    }
    public int getDrawableId(){
        return drawableId;
    }
    public void setDrawableId(){
        this.drawableId = drawableId;
    }
}
