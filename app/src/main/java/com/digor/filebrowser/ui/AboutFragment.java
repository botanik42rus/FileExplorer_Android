package com.digor.filebrowser.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digor.filebrowser.MainActivity;
import com.digor.filebrowser.R;
import com.digor.filebrowser.misc.IOnBackListner;

public class AboutFragment extends Fragment implements IOnBackListner {

    private static AboutFragment _instance;
    public static AboutFragment Instance(){
        if(_instance == null){
            _instance = new AboutFragment();
        }
        return _instance;
    }
    private AboutFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


    @Override
    public void onPause() {
        onBackListner = null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackListner = this;
    }

    @Override
    public void  onHiddenChanged(boolean hidden){
        onBackListner = !hidden ? this : null;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean getInitialState() {
        return true;
    }

    private IOnBackListner onBackListner;
    @Override
    public IOnBackListner getOnBackListnerObject() {
        return onBackListner;
    }
}