package com.restoche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RestoActivity extends AppCompatActivity implements ListeRestoFragment.ItemSelected {
    TextView restODescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto);
     //   restODescription=findViewById(R.id.description_resto);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ListeRestoFragment listeRestoFragment = new ListeRestoFragment();
        fragmentTransaction.replace(R.id.list_rest_frag,(Fragment) listeRestoFragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(int id) {
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DetailFragment detailFragment = new DetailFragment();
        fragmentTransaction.replace(R.id.list_rest_frag,(Fragment) detailFragment);

        fragmentTransaction.commit();*/
        Intent intent = new Intent(RestoActivity.this, DetailActivity.class);
        startActivity(intent);

    }
}
