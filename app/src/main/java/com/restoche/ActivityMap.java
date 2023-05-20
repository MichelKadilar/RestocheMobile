package com.restoche;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMap extends AppCompatActivity {

    private LinearLayout goBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        goBack = findViewById(R.id.LL_go_back);
        goBack.setOnClickListener(view -> {
            finish();
        });
    }
}
