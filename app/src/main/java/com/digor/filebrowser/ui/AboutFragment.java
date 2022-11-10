package com.digor.filebrowser.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digor.filebrowser.R;

public class AboutFragment extends Fragment {

    private static AboutFragment _instance;
    public static AboutFragment Instance(){
        if(_instance == null){
            _instance = new AboutFragment();
        }
        return _instance;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}