package com.dtd.letsgodubki.MVP.viewImpl;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.dtd.letsgodubki.ArrayDorm;
import com.dtd.letsgodubki.MVP.view.FirstActivityView;
import com.dtd.letsgodubki.MVP.view.viewholders.FirstActivityViewHolder;
import com.dtd.letsgodubki.R;
import com.dtd.letsgodubki.activities.FirstActivity;
import com.dtd.letsgodubki.activities.MeetingsActivity;

/**
 * Created by Egor on 14/09/15.
 */
public class FirstActivityViewImpl implements FirstActivityView, View.OnClickListener {

    private final FirstActivityViewHolder viewHolder;
    private Context mContext;

    public FirstActivityViewImpl(Context context, FirstActivityViewHolder viewHolder) {
        mContext = context;
        this.viewHolder = viewHolder;
    }

    @Override
    public void showResponse(ArrayDorm result) {
        if(!result.hasError()) {
            String buf = result.getDorm7();
            if (!buf.equals("0")) viewHolder.tv7.setText(result.getDorm7());
            buf = result.getDorm91();
            if (!buf.equals("0")) viewHolder.tv91.setText(result.getDorm91());
            buf = result.getDorm92();
            if (!buf.equals("0")) viewHolder.tv92.setText(result.getDorm92());
        }else
            Toast.makeText(mContext,"Похоже какие-то неполадки с интернетом, попробуйте позднее", Toast.LENGTH_SHORT)
                    .show();
    }

    @Override
    public void setClickListeners() {
        viewHolder.btn7.setOnClickListener(this);
        viewHolder.btn91.setOnClickListener(this);
        viewHolder.btn92.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        Intent intent = new Intent(mContext, MeetingsActivity.class);
        switch(view.getId()){
            case R.id.btn7:
                intent.putExtra("Dormitory", "7");
                break;
            case R.id.btn9_1:
                intent.putExtra("Dormitory", "91");
                break;
            case R.id.btn9_2:
                intent.putExtra("Dormitory", "92");
                break;
        }
        mContext.startActivity(intent);
        ((FirstActivity)mContext).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
