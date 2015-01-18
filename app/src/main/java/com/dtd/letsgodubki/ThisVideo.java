package com.dtd.letsgodubki;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;


public class ThisVideo extends Activity {

    String url;
    FrameLayout mContentView;
    WebView mWebView;
    FrameLayout mCustomViewContainer;
    View mCustomView;
    WebChromeClient.CustomViewCallback mCustomViewCallback;

    private class myWebChromeClient extends WebChromeClient
    {
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
        {
            //super.onShowCustomView(view, callback);
            // if a view already exists then immediately terminate the new one
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            finish();
            /*if (mCustomView != null)
            {
                callback.onCustomViewHidden();
                return;
            }
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // Add the custom view to its container.
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            mCustomViewContainer.addView(view, params);
            mCustomView = view;
            mCustomViewCallback = callback;

            // hide main browser view
            mContentView.setVisibility(View.GONE);

            // Finally show the custom view container.
            mCustomViewContainer.setVisibility(View.VISIBLE);
            mCustomViewContainer.bringToFront();
        }

        @Override
        public void onHideCustomView()
        {
            if (mCustomView == null)
                return;

            // Hide the custom view.
            mCustomViewCallback.onCustomViewHidden();
            mCustomView.setVisibility(View.GONE);
            // Remove the custom view from its container.
            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();

            // Show the content view.
            mContentView.setVisibility(View.VISIBLE);*/
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
