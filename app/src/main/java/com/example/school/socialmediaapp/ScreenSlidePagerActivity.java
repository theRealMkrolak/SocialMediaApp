package com.example.school.socialmediaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;
    public BottomNavigationView navigation;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public ViewPager mPager;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    mPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_search:
                    mPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
    private PagerAdapter mPagerAdapter;
    public int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();





        setContentView(R.layout.activity_screen_slide);

        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        i = 1;
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPager.getCurrentItem() == 0) {
                    navigation.setSelectedItemId(R.id.navigation_profile);
                } else if (mPager.getCurrentItem() == 1) {
                    navigation.setSelectedItemId(R.id.navigation_dashboard);
                } else if (mPager.getCurrentItem() == 2) {
                    navigation.setSelectedItemId(R.id.navigation_search);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new ScreenSlidePageFragment(R.layout.fragment_screen_slide_page);
            }
            else if(position==1) {
                return new ScreenSlidePageFragment(R.layout.fragment_screen_slide_page2);
            }
            else if(position==2) {
                return new ScreenSlidePageFragment(R.layout.fragment_screen_slide_page3);
            }
            else{
                return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
