package com.digor.filebrowser.misc;

public interface IOnBackListner {
    public void onBackPressed();
    public boolean getInitialState();
    public IOnBackListner getOnBackListnerObject();
}
