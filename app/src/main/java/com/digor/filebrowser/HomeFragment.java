package com.digor.filebrowser;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static HomeFragment _instance;
    public static  HomeFragment Instance(Context context ){
        if(_instance == null){
            _instance = new HomeFragment(context);
        }
        return _instance;
    }

    public static  HomeFragment Instance(){
        if(_instance == null){
            _instance = new HomeFragment();
        }
        return _instance;
    }
    private Context _mainContext;

    private HomeFragment(Context context){
        _mainContext = context;
    }

    private HomeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Button btnPermissions = view.findViewById(R.id.checkPermissions);

        btnPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionsInfo pInfo = new PermissionsInfo();
                Toast toast = Toast.makeText(view.getContext(),"Permissions granted:  " + Boolean.toString(pInfo.IsOnPermissionsGranted()),Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
