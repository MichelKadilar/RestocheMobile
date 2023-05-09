package com.restoche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialise login button
        loginButton = findViewById(R.id.button);
        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: handle login functionality
            }
        });
        // Initialize the "Register" TextView
        LinearLayout registerTextView = findViewById(R.id.s_inscrire);

        // Set an OnClickListener for the "Register" TextView
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the "Register" activity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}