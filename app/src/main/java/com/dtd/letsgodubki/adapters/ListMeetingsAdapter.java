package com.dtd.letsgodubki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtd.letsgodubki.activities.MeetingsActivity.MeetItem;
import com.dtd.letsgodubki.R;

import java.util.List;

/**
 * Created by 123 on 19.01.2015.
 */
public class ListMeetingsAdapter extends BaseAdapter {
    Context context;

    protected List<MeetItem> listMeetings;
    LayoutInflater inflater;

    public ListMeetingsAdapter(Context context, List<MeetItem> listMeetings){
        this.listMeetings = listMeetings;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    public int getCount(){
        return listMeetings.size();
    }
    public MeetItem getItem(int position){
        return listMeetings.get(position);
    }
    public long getItemId(int position){
        //return listMeetings.get(position).dormitory;}
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.list_item, parent, false);
            holder.txtTime = (TextView)convertView.findViewById(R.id.time);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.title);
            holder.image = (ImageView)convertView.findViewById(R.id.image);
            holder.people = (TextView)convertView.findViewById(R.id.image2);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        MeetItem meeting = listMeetings.get(position);

        if(!meeting.starttime.equals("")) {

            String str = meeting.starttime.substring(11, 16);

            holder.txtTime.setText(str);
        }

        String title = meeting.header;
        if(title.length() > 10) {
            title = String.valueOf(title.substring(0, 10) + "...");
        }

        holder.txtTitle.setText(title);
        int resId = 0;
        if(meeting.category.equals("drink")){
            resId = R.drawable.drink;
        }else if(meeting.category.equals("guitar")){
            resId = R.drawable.guitar;
        }else if(meeting.category.equals("films")){
            resId = R.drawable.films;
        }else if(meeting.category.equals("games")){
            resId = R.drawable.games;
        }

        if(resId!=0) {
            holder.image.setImageResource(resId);
        }
        holder.people.setText(meeting.currentp + "/" + meeting.limitp);
       // holder.people.setTextAlignment();

        return convertView;
    }
    private class ViewHolder{
        TextView txtTime;
        TextView txtTitle;
        ImageView image;
        TextView people;
    }
}
