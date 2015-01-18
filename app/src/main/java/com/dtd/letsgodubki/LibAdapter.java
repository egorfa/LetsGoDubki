package com.dtd.letsgodubki;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Egor on 21.11.2014.
 */
public class LibAdapter extends ArrayAdapter<String> {

    ImageLoader imageLoader;

    private ArrayList<String> images_array;
    private ArrayList<String> news_array;
    private final Context context;

    public LibAdapter(Context context, ArrayList<String> images_array, ArrayList<String> news_array){
        super(context, R.layout.activity_library_pager, news_array);
        this.context = context;
        this.images_array = images_array;
        this.news_array =   news_array;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        if (convertView == null) {
            if(images_array!=null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
            else convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news_noimages,parent, false);
        }

        final ProgressBar pb = (ProgressBar) convertView.findViewById(R.id.progressBar);

        final ImageView img = (ImageView) convertView.findViewById(R.id.img_news);
        TextView tv = (TextView) convertView.findViewById(R.id.text_news);

        if(images_array!=null)
        imageLoader.displayImage(images_array.get(position), img, options, new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String ImageUri, View view){
                pb.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason){
                pb.setVisibility(View.GONE);
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){
                pb.setVisibility(View.GONE);
                img.bringToFront();
            }
        });
        tv.setText(news_array.get(position));

        return convertView;
    }

}
