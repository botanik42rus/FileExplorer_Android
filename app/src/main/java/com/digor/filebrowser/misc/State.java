package com.digor.filebrowser.misc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class State {
    public State(String name, String size, String dateChange, Drawable image, String path, String mimeType){
        nameObject = name;
        sizeObject = size;
        dateChangeObject = dateChange;
        imageObject = image;
        buttonPath =path;
        currentMimeType = mimeType;
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

    private Drawable imageObject;
    public Drawable getImageObject(){
        return imageObject;
    }
    public void setImageObject(Drawable image){
        imageObject = image;
    }

    private String buttonPath;
    public String getButtonPath(){return buttonPath;}
    public void setButtonPath(String path){ buttonPath = path; }

    private String currentMimeType;
    public String getCurrentMimeType(){ return currentMimeType;}
    public void setCurrentMimeType(String mime){currentMimeType = mime;}
}

