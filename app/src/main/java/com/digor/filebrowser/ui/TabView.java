package com.digor.filebrowser.ui;

import android.os.Build;
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
import com.digor.filebrowser.misc.FileExploreAfterAPI26;
import com.digor.filebrowser.misc.FileExplore_API_21_to_26;
import com.digor.filebrowser.misc.IFileExplore;
import com.digor.filebrowser.misc.State;
import com.digor.filebrowser.misc.StateAdapter;

import java.io.File;
import java.util.ArrayList;

public class TabView extends Fragment {


    ArrayList<State> states = new ArrayList<State>();
    RecyclerView recyclerView;
    IFileExplore FileExploreClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_view, container, false);

        try {
            FileExploreClass = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? new FileExploreAfterAPI26() : new FileExplore_API_21_to_26();
            states = (ArrayList<State>) FileExploreClass.InitializeData();

            recyclerView = view.findViewById(R.id.recycleViewLeft);
            StateAdapter adapter = new StateAdapter(inflater, states, FileExploreClass);
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e){
            Log.i("myLog", e.toString());
        }


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}