package com.digor.filebrowser.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digor.filebrowser.MainActivity;
import com.digor.filebrowser.R;
import com.digor.filebrowser.misc.FileExploreAfterAPI26;
import com.digor.filebrowser.misc.FileExplore_API_21_to_26;
import com.digor.filebrowser.misc.IFileExplore;
import com.digor.filebrowser.misc.IOnBackListner;
import com.digor.filebrowser.misc.State;
import com.digor.filebrowser.misc.StateAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class TabView extends Fragment implements IOnBackListner {


    ArrayList<State> states = new ArrayList<State>();
    RecyclerView recyclerView;
    IFileExplore FileExploreClass;
    boolean IsLinearLayoutManager;
    LayoutInflater currentInflater;
    StateAdapter mAdapter;
    AppCompatImageButton changeLayoutButton;
    public AppCompatImageButton returnButton;
    TabView currentTabView;
    String Location;
    IOnBackListner onBackListnerObject;


    public TabView(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_view, container, false);

        currentTabView = this;
        InitializeRecycleView(view, inflater);
        InitializeButtons(view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        onBackListnerObject = null;
        super.onPause();
    }


    // Overriding onResume() method
    @Override
    public void onResume() {
        super.onResume();
        onBackListnerObject = this;
    }
    private void InitializeRecycleView(View view, LayoutInflater inflater){
        try {
            FileExploreClass = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? new FileExploreAfterAPI26() : new FileExplore_API_21_to_26();
            states = (ArrayList<State>) FileExploreClass.InitializeData();
            IsLinearLayoutManager = true;
            currentInflater = inflater;

            recyclerView = view.findViewById(R.id.fileRecycleView);
            mAdapter = new StateAdapter(currentInflater, states, FileExploreClass, IsLinearLayoutManager, this);
            recyclerView.setAdapter(mAdapter);
        }
        catch (Exception e){
            Log.i("myLog", e.toString());
        }
    }

    private void InitializeButtons(View view){
        changeLayoutButton = view.findViewById(R.id.change_layout);
        changeLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsLinearLayoutManager = !IsLinearLayoutManager;
                changeLayoutButton.setImageDrawable(IsLinearLayoutManager ?
                        MainActivity.mainContext.getDrawable(R.drawable.list_ic) :
                        MainActivity.mainContext.getDrawable(R.drawable.grid_ic));

                recyclerView.setLayoutManager( IsLinearLayoutManager ? new LinearLayoutManager(recyclerView.getContext()) : new GridLayoutManager(recyclerView.getContext(),3));
                mAdapter = new StateAdapter(currentInflater, states, FileExploreClass, IsLinearLayoutManager, currentTabView);
                recyclerView.setAdapter(mAdapter);
                recyclerView.scrollToPosition(0);
            }
        });

        returnButton = view.findViewById(R.id.return_button);
        returnButton.setVisibility(View.GONE);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!FileExploreClass.getIsInitial())
                    mAdapter.openPath(FileExploreClass.getParentPath());
            }
        });
    }

    @Override
    public void  onHiddenChanged(boolean hidden){
        onBackListnerObject = !hidden ? this : null;
    }


    @Override
    public void onBackPressed() {
        if(!FileExploreClass.getIsInitial())
            mAdapter.openPath(FileExploreClass.getParentPath());

    }


    @Override
    public boolean getInitialState() {
        return FileExploreClass.getIsInitial();
    }

    @Override
    public IOnBackListner getOnBackListnerObject() {
        return onBackListnerObject;
    }
}