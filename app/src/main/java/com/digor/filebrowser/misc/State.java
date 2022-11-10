package com.digor.filebrowser.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class State {
    public State(String name, String size, String dateChange, int image, String path){
        nameObject = name;
        sizeObject = size;
        dateChangeObject = dateChange;
        imageObject = image;
        buttonPath =path;
    }

    private String nameObject;
    public String getNameObject(){
        return nameObject;
    }
    public void setNameObject(String name){
        nameObject = name;
    }

    private String sizeObject;
    public String getSizeObject(){
        return sizeObject;
    }
    public void setSizeObject(String size){
        sizeObject = size;
    }

    private String dateChangeObject;
    public String getDateChangeObject(){
        return dateChangeObject;
    }
    public void setDateChangeObject(String dateChange){
        dateChangeObject = dateChange;
    }

    private int imageObject;
    public int getImageObject(){
        return imageObject;
    }
    public void setImageObject(int image){
        imageObject = image;
    }

    private String buttonPath;
    public String getButtonPath(){return buttonPath;}
    public void setButtonPath(String path){ buttonPath = path; }
}

