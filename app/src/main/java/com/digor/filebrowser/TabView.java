package com.digor.filebrowser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return view;
    }

    private void InitializeData(){
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
        states.add(new State("1a", "1b", "1c", R.drawable.file_ic));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}