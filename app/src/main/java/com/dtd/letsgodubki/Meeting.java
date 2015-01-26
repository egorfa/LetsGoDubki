package com.dtd.letsgodubki;

/**
 * Created by 123 on 19.01.2015.
 */
public class Meeting {
    private int drawableId;
    private String time;
    private String title;
    private int people;
    private String curNum;
    private String nedNum;

    public Meeting(int drawableId, String time, String title, int people, String curNum, String nedNum){
        this.drawableId = drawableId;
        this.time = time;
        this.title = title;
        this.people = people;
        this.curNum = curNum;
        this.nedNum = nedNum;
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
    public int getPeople(){ return people; }
    public String getCurNum() { return curNum; }
    public String getNedNum() { return  nedNum; }
    public void setDrawableId(){
        this.drawableId = drawableId;
    }
}
