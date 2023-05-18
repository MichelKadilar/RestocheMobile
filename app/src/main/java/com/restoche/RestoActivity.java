package com.restoche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestoActivity extends AppCompatActivity implements ListeRestoFragment.ItemSelected {
    TextView restODescription;
    RecyclerView recyclerView;
    Resto resto1,resto2,resto3,resto4;
    List<Resto> restoList=new ArrayList<>();
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        setContentView(R.layout.activity_resto);
       // fetchResto();
        resto1=new Resto("Chez adjo","Adidogome");
        resto2=new Resto("Chez adjo","Adidogome");
        resto3=new Resto("Chez adjo","Adidogome");
        restoList= Arrays.asList(resto1,resto2,resto3);
        recyclerView=findViewById(R.id.listResto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RestoAdapter adapter=new RestoAdapter(RestoActivity.this,restoList);
       recyclerView.setAdapter(adapter);



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

    private void fetchResto() {



        String url = "https://www.json-generator.com/api/json/get/cfsXpFGwwO?indent=2";
      //  url= "https://api.content.tripadvisor.com/api/v1/location/locationId/details?language=en&currency=USD";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String overview = jsonObject.getString("overview");
                                String poster = jsonObject.getString("poster");
                                Double rating = jsonObject.getDouble("rating");

                                Resto resto = new Resto(title , poster );
                                restoList.add(resto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            RestoAdapter adapter = new RestoAdapter(RestoActivity.this , restoList);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestoActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
