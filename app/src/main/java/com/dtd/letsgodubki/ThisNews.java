package com.dtd.letsgodubki;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;


public class ThisNews extends Activity {

    private class myWebChromeClient extends WebChromeClient
    {
        @Override
        public void onShowCustomView (View view, WebChromeClient.CustomViewCallback callback) {

            super.onShowCustomView(view, callback);
            Log.e("onShowCustomView", "i m onShowCustomView");
            if (view instanceof FrameLayout) {
                Log.e("Condin true : view instanceof FrameLayout",
                        "view instanceof FrameLayout");
                FrameLayout frame = (FrameLayout) view;
                if (frame.getFocusedChild() instanceof WebView) {
                    Log.e("True : frame.getFocusedChild()",
                            "True : frame.getFocusedChild()");
                    VideoView video = (VideoView) frame.getFocusedChild();
                    frame.removeView(frame.getFocusedChild());
                    setContentView(video);
                    video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            mp.stop();
                            Log.e("mp.stop()", "mp.stop()");
                            finish();
                            // setContentView(R.layout.loaddata_qrurl);
                        }
                    });
                    video.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                        @Override
                        public boolean onError(MediaPlayer mp, int what,
                                               int extra) {
                            Log.e("oNError", "onError");
                            Toast.makeText(getApplicationContext(),
                                    "Video Error...", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                    video.start();
                }
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
