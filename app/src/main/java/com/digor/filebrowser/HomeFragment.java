package com.digor.filebrowser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static HomeFragment _instance;
    public static  HomeFragment Instance(){
        if(_instance == null){
            _instance = new HomeFragment();
        }
        return _instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        _instance = new HomeFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.home_fragment, container, false);
    }
}
