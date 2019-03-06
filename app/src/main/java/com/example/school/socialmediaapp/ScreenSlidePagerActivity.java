package com.example.school.socialmediaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

    public static final String DATABASE_REFERENCE ="GET_POST";

    private int[] LIST_OF_FRAGMENTS = new int[]{R.layout.profile_fragment,R.layout.feed_fragment,R.layout.search_fragment};

    private ScreenSlidePagerAdapter mPagerAdapter;
    public int i;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_screen_slide);

        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),LIST_OF_FRAGMENTS);
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

        ((ScreenSlidePageFragment)mPagerAdapter.getItem(0)).setOnStartListener(new OnViewCreateListener() {
            @Override
            public void onLoadListener(View v) {
                ((TextView)v.findViewById(R.id.username)).setText(mAuth.getCurrentUser().getDisplayName());
            }
        });

        ((ScreenSlidePageFragment)mPagerAdapter.getItem(2)).setOnStartListener(new OnViewCreateListener() {
            @Override
            public void onLoadListener(View v) {
                ((SearchView)v.findViewById(R.id.searchView)).setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        database.getReference("USERS").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<String> usernames = new ArrayList<>();
                                dataSnapshot.getChildren().forEach(d->usernames.add((String)d.getValue()));
                                for (String s : usernames) {
                                    CardView cardView = new CardView(ScreenSlidePagerActivity.this);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return false;

                    }
                });
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

    public void logOut(View v){
        mAuth.signOut();
        startActivity(new Intent(ScreenSlidePagerActivity.this,LoginActivity.class));
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<ScreenSlidePageFragment> fragmentList;

        public ScreenSlidePagerAdapter(FragmentManager fm, int[] a) {

            super(fm);
            fragmentList = new ArrayList<>();
            for(int i : a){
                fragmentList.add(new ScreenSlidePageFragment(i));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    public void postPage(View v){
        startActivity(new Intent(ScreenSlidePagerActivity.this,PostActivity.class));
    }


}
