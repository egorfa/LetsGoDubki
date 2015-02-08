package com.dtd.letsgodubki;

/**
 * Created by 123 on 07.02.2015.
 */
public class DialogMenuItem {
    private int drawableId;
    private String title;

    public DialogMenuItem(int drawableId, String title){
        this.drawableId = drawableId;
        this.title = title;
    }

    public int getDrawableId() { return drawableId; }
    public String getTitle() { return title; }
}
