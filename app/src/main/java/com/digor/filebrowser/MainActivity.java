package com.digor.filebrowser;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        if(bottomNavigationView != null){
            bottomNavigationView.setOnItemSelectedListener(this);
            bottomNavigationView.findViewById(R.id.home_menu_button).performClick();
        }

        int a = 10;
    }

    @Override
    public  boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId() - 1){
            case R.id.homeFragment:
                CommitFragment(HomeFragment.Instance());
                return true;
            case R.id.settingsFragment:
                CommitFragment(SettingsFragment.Instance());
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
}