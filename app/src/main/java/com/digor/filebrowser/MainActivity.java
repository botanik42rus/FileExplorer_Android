package com.digor.filebrowser;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomNavigationView bottomNavigationView;
    public NavigationView appNavigationView;
    public AppCompatImageButton appMenuButton;
    public DrawerLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBarSetup();
        RightNavigationViewSetup();
        ButtonMenuBackSetup();
    }

    @Override
    public  boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.tabLeft:
                CommitFragment(TabLeft.Instance());
                return true;
            case R.id.tabRight:
                CommitFragment(TabRight.Instance());
                return true;
        }
        return true;
    }

    protected  void CommitFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(fragmentTransaction != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment itemFragment : fragments) {
                if (itemFragment.getClass().getSimpleName() != fragment.getClass().getSimpleName()) {
                    fragmentTransaction.hide(itemFragment);
                }
            }

            for (Fragment itemFragment : fragments) {
                if (itemFragment.getClass().getSimpleName() == fragment.getClass().getSimpleName()) {
                    fragmentTransaction.show(itemFragment);
                    fragmentTransaction.commit();
                    return;
                }
            }
            fragmentTransaction.add(R.id.FragmentContainer, fragment);
            fragmentTransaction.commit();
        }
    }

    protected void BottomNavigationBarSetup(){
        bottomNavigationView = findViewById(R.id.bottomNavView);
        if(bottomNavigationView != null){
            bottomNavigationView.setOnItemSelectedListener(this);
            bottomNavigationView.findViewById(R.id.tabLeft).performClick();
        }
    }

    protected void RightNavigationViewSetup(){
        appNavigationView = findViewById(R.id.navigationView);
        if(appNavigationView != null){
            appNavigationView.inflateHeaderView(R.layout.navigation_head);
        }
    }

    protected void ButtonMenuBackSetup(){
        appMenuButton = findViewById(R.id.topMenuButton);
        mainLayout = findViewById(R.id.mainContainer);
        appMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}