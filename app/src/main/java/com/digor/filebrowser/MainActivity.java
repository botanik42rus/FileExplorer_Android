package com.digor.filebrowser;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BottomAppBar bottomAppBar;
    public BottomNavigationView bottomNavigationView;
    public NavigationView appNavigationView;
    public AppCompatImageButton appMenuButton;
    public DrawerLayout mainLayout;
    public FloatingActionButton floatingActionButton;
    public AppCompatTextView tittleTextView;
    private String CurrentFragment;
    private String BackFragment;

    private enum AnimationDirection{
        NO_ANIMATION,
        FROM_LEFT_TO_RIGHT,
        FROM_RIGHT_TO_LEFT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBarSetup();
        LeftNavigationViewSetup();
        TopBarSetup();
        SetupFloatButton();
    }

    @Override
    public  boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.tabLeft:
                CommitFragment(TabLeft.Instance());
                break;
            case R.id.tabRight:
                CommitFragment(TabRight.Instance());
                break;
            case R.id.home_button:
                CommitFragment(HomeFragment.Instance());
                SkipSelectedBottomBar();
                break;
            case R.id.settings_button:
                CommitFragment(SettingsFragment.Instance());
                SkipSelectedBottomBar();
                HideBottomBar();
                return true;
            case R.id.info_button:
                CommitFragment(AboutFragment.Instance());
                SkipSelectedBottomBar();
                HideBottomBar();
                return true;
            case R.id.dev_button:
                CommitFragment(DevInfoFragment.Instance());
                SkipSelectedBottomBar();
                HideBottomBar();
                return true;
        }
        ShowBottomBar();
        return true;
    }

    protected  void CommitFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(fragmentTransaction != null && fragment != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();

            AnimationDirection currentDirection = IsAnimationDirection(fragment);
            if(currentDirection == AnimationDirection.FROM_LEFT_TO_RIGHT){
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
            }
            else if(currentDirection == AnimationDirection.FROM_RIGHT_TO_LEFT){
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            }

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
            if(mainLayout != null){
                mainLayout.closeDrawer(GravityCompat.START);
            }
            CurrentFragment = fragment.getClass().getSimpleName();
            tittleTextView.setText(fragment.getClass().getSimpleName());
        }
    }

    protected AnimationDirection IsAnimationDirection(Fragment fragment){
            String newFragment = fragment.getClass().getSimpleName();
            String tabLeft = TabLeft.Instance().getClass().getSimpleName();
            String tabRight = TabRight.Instance().getClass().getSimpleName();
            String home = HomeFragment.Instance().getClass().getSimpleName();

            if((newFragment != null && CurrentFragment != null)&&
                    ((CurrentFragment.equals(tabLeft) && (newFragment.equals(home) || newFragment.equals(tabRight))) ||
                            (CurrentFragment.equals(home) && newFragment.equals(tabRight)))){
                return AnimationDirection.FROM_RIGHT_TO_LEFT;
            }
            else if((newFragment != null && CurrentFragment != null)&&
                    (CurrentFragment.equals(tabRight)&&((newFragment.equals(home) || newFragment.equals(tabLeft))) ||
                            (CurrentFragment.equals(home) && newFragment.equals(tabLeft)))){
                return AnimationDirection.FROM_LEFT_TO_RIGHT;
            }

            return AnimationDirection.NO_ANIMATION;

    }

    protected void BottomNavigationBarSetup(){
        bottomAppBar = findViewById(R.id.BottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        if(bottomNavigationView != null){
            bottomNavigationView.setOnItemSelectedListener(this);
        }
    }

    protected void LeftNavigationViewSetup(){
        appNavigationView = findViewById(R.id.navigationView);
        if(appNavigationView != null){
            appNavigationView.inflateHeaderView(R.layout.navigation_head);
            appNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        }
    }

    protected void TopBarSetup(){
        appMenuButton = findViewById(R.id.topMenuButton);
        mainLayout = findViewById(R.id.mainContainer);
        tittleTextView = findViewById(R.id.tittleTextView);

        appMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    protected void SetupFloatButton(){
        floatingActionButton = findViewById(R.id.homeFloatingButton);
        if (floatingActionButton!=null){
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SkipSelectedBottomBar();
                    CommitFragment(HomeFragment.Instance());
                }
            });
        }
        floatingActionButton.performClick();
    }

    protected void SkipSelectedBottomBar(){
        if(bottomNavigationView != null){
            bottomNavigationView.findViewById(R.id.Placeholder).performClick();
        }
    }

    protected  void HideBottomBar(){
        if(bottomAppBar != null && floatingActionButton != null){
            bottomAppBar.performHide();
            floatingActionButton.hide();
        }
    }
     protected void ShowBottomBar(){
         if(bottomAppBar != null && floatingActionButton != null){
            bottomAppBar.performShow();
            floatingActionButton.show();
         }
     }
}