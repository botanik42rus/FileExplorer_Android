package com.digor.filebrowser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DevInfoFragment extends Fragment {

    private static DevInfoFragment _instance;
    public static DevInfoFragment Instance(){
        if(_instance == null){
            _instance = new DevInfoFragment();
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
        return inflater.inflate(R.layout.fragment_dev_info, container, false);
    }
}