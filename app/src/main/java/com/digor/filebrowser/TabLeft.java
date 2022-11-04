package com.digor.filebrowser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabLeft extends Fragment {

    private static TabLeft _instance;
    public static TabLeft Instance(){
        if(_instance == null){
            _instance = new TabLeft();
        }
        return _instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        _instance = new TabLeft();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_left, container, false);
    }
}