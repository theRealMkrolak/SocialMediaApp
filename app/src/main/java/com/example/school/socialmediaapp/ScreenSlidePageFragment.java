package com.example.school.socialmediaapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment{
    ViewGroup rootView;
    public int cntrl = 0;
    private  OnViewCreateListener onStartListener;

    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragment(int x){
        cntrl = x;
        onStartListener = new OnViewCreateListener(){
            @Override
            public void onLoadListener(View v) {

            }

        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(cntrl, container, false);

        return rootView;

    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        onStartListener.onLoadListener(view);

    }

    public void setOnStartListener(OnViewCreateListener onStart){
        onStartListener = onStart;
    }
}
