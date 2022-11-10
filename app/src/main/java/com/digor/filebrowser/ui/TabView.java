package com.digor.filebrowser.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digor.filebrowser.MainActivity;
import com.digor.filebrowser.R;
import com.digor.filebrowser.misc.State;
import com.digor.filebrowser.misc.StateAdapter;

import java.io.File;
import java.util.ArrayList;

public class TabView extends Fragment {


    ArrayList<State> states = new ArrayList<State>();
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_view, container, false);

        InitializeData();
        recyclerView = view.findViewById(R.id.recycleViewLeft);
        StateAdapter adapter = new StateAdapter(inflater, states);
        recyclerView.setAdapter(adapter);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i){
                    case KeyEvent.KEYCODE_ENTER:
                        String path = new File(states.get(0).getButtonPath()).getParent();
                        adapter.FolderNavigation(path);
                        return true;
                }
                return false;
            }
        });

        return view;
    }

    private void InitializeData(){

        File[] appsDir = ContextCompat.getExternalFilesDirs(MainActivity.mainContext, null);
        ArrayList<File> extRootPath = new ArrayList<File>();
        for (File file1 : appsDir) {
            extRootPath.add(file1.getParentFile().getParentFile().getParentFile().getParentFile());
        }

        for(File filesToStorage : extRootPath){
            for (String paths : filesToStorage.list()){
            }
            states.add(new State(filesToStorage.getName(),"","0.0.2000 00:00",R.drawable.ic_launcher_foreground, filesToStorage.getPath()));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}