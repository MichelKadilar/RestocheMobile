package com.restoche;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragments.FragmentParameters;
import fragments.FragmentUserProfile;
import fragments.RestoFragment;

public class ActivityFragmentSwitcher extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    private int currentSelectedItemId;
    private BottomNavigationView bottomNavigationView;

    private FragmentManager fm;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_fragment_switcher);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        fm = getSupportFragmentManager();
        /*ft = fm.beginTransaction();
        ft.add(R.id.frame_fragment_container, new FragmentParameters());
        ft.commit();*/

        this.currentSelectedItemId = bottomNavigationView.getMenu().getItem(0).getItemId();
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int clickedItemId = item.getItemId();
        switch (clickedItemId) {
            case R.id.access_parameters -> { // not safe to use ids because not final/unique when builded
                fm.beginTransaction().replace(R.id.frame_fragment_container, new FragmentParameters()).commit();
                return true;
            }
            case R.id.access_profile -> {
                fm.beginTransaction().replace(R.id.frame_fragment_container, new FragmentUserProfile()).commit();
                return true;
            }
            case R.id.liste_resto -> {
                fm.beginTransaction().replace(R.id.frame_fragment_container, new RestoFragment()).commit();
                return true;
            }
            default -> {
                return true;
            }
        }
    }
}
