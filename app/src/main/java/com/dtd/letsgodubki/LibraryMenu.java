package com.dtd.letsgodubki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by Egor on 21.11.2014.
 */
public class LibraryMenu extends FragmentActivity {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 2;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    int position;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_pager);
        position = 0;

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setCurrentItem(position);



        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return LibraryFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String menuString = "";

            switch (position){
                case 0:
                    menuString="Встречи";
                    break;
                case 1:
                    menuString="Ищу";
                    break;
            }
            return menuString;
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
