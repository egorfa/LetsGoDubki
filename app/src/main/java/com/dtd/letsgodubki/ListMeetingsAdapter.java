package com.dtd.letsgodubki;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 123 on 19.01.2015.
 */
public class ListMeetingsAdapter extends BaseAdapter {
    Context context;

    protected List<Meeting> listMeetings;
    LayoutInflater inflater;

    public ListMeetingsAdapter(Context context, List<Meeting> listMeetings){
        this.listMeetings = listMeetings;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    public int getCount(){
        return listMeetings.size();
    }
    public Meeting getItem(int position){
        return listMeetings.get(position);
    }
    public long getItemId(int position){
        return listMeetings.get(position).getDrawableId();
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.list_item, parent, false);
            holder.txtTime = (TextView)convertView.findViewById(R.id.time);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.title);
            holder.image = (ImageView)convertView.findViewById(R.id.image);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        Meeting meeting = listMeetings.get(position);
        holder.txtTime.setText(meeting.getTime());
        holder.txtTitle.setText(meeting.getTitle());
        holder.image.setImageResource(meeting.getDrawableId());

        return convertView;
    }
    private class ViewHolder{
        TextView txtTime;
        TextView txtTitle;
        ImageView image;
    }
}
