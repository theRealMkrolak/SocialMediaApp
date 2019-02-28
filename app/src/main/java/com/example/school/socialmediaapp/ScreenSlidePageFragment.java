package com.example.school.socialmediaapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment {
    ViewGroup rootView;
    public int cntrl = 0;

    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragment(int x){
        cntrl = x;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(cntrl, container, false);
        return rootView;

    }
}
