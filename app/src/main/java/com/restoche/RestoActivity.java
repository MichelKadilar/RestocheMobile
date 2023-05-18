package com.restoche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestoActivity extends AppCompatActivity implements ListeRestoFragment.ItemSelected {
    TextView restODescription;
    RecyclerView recyclerView;
    Resto resto,resto1,resto2,resto3;
    List<Resto> restoList=new ArrayList<>();
    RequestQueue requestQueue;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("restos");
        mDatabase.addValueEventListener(postListener);

        setContentView(R.layout.activity_resto);
       // fetchResto();
        resto1=new Resto("Chez adjo","Adidogome","");
        resto2=new Resto("Chez adjo","Adidogome","");
        resto3=new Resto("Chez adjo","Adidogome","");
        restoList= Arrays.asList(resto1,resto2,resto3);
        recyclerView=findViewById(R.id.listResto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RestoAdapter adapter=new RestoAdapter(RestoActivity.this,restoList);
       recyclerView.setAdapter(adapter);



    }
    public void writeNewResto(String titre, String localisation, String image) {
        Resto resto = new Resto(titre, localisation,image);

        mDatabase.child(titre+localisation).setValue(resto);
    }


    ValueEventListener postListener = new ValueEventListener() {


        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                for (DataSnapshot restoSnapshot : dataSnapshot.getChildren()) {
                    Resto resto = restoSnapshot.getValue(Resto.class);
                    Toast.makeText(RestoActivity.this, resto.getTitle(), Toast.LENGTH_SHORT).show();
                  //  restoList.add(resto);
                }

                // Faites ce que vous voulez avec l'objet resto récupéré
            } else {
                Toast.makeText(RestoActivity.this, "Le restaurant n'existe pas", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };




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

                                Resto resto = new Resto(title , poster,overview );
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
