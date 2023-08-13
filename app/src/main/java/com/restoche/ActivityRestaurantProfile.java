package com.restoche;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;

import fragments.FragmentRestaurantProfile;
import models.Resto;

public class ActivityRestaurantProfile extends AppCompatActivity {

    private Resto resto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

        if (getIntent().hasExtra("resto")) {
            Resto resto = getIntent().getParcelableExtra("resto");

            FragmentRestaurantProfile fragment = FragmentRestaurantProfile.newInstance(resto);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.exampleFragment, fragment)
                    .commit();
        }
    }

}
