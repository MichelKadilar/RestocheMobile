package com.restoche;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestoActivity extends Fragment implements ListeRestoFragment.ItemSelected {

    RecyclerView recyclerView;
    List<Resto> restoList = new ArrayList<>();
    RequestQueue requestQueue;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = VolleySingleton.getmInstance(getActivity()).getRequestQueue();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("restos");
        mDatabase.addValueEventListener(postListener);
        fetchResto();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_liste_resto, container, false);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void writeNewResto(String titre, String localisation, String image, float rating) {
        Resto resto = new Resto(titre, localisation, image, rating);

        mDatabase.child(titre + localisation).setValue(resto);
    }


    ValueEventListener postListener = new ValueEventListener() {


        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                for (DataSnapshot restoSnapshot : dataSnapshot.getChildren()) {
                    Resto resto = restoSnapshot.getValue(Resto.class);
                    Toast.makeText(getActivity(), resto.getTitle(), Toast.LENGTH_SHORT).show();
                    restoList.add(resto);
                }
            } else {
                Toast.makeText(getActivity(), "Le restaurant n'existe pas", Toast.LENGTH_LONG).show();
            }
            RestoAdapter adapter = new RestoAdapter(getActivity(), restoList);
            recyclerView.setAdapter(adapter);
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
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);

    }

    private void fetchResto() {
        String url = "https://the-fork-the-spoon.p.rapidapi.com/restaurants/v2/list?queryPlaceValueCityId=381418&pageSize=1&pageNumber=1";
        String apiKey = "d40e116fb1mshb9509405b320cadp1815f4jsn794214bdf4fa";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    //    List<Resto> restoList = new ArrayList<>();

                    try {
                        JSONArray dataArray = response.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject jsonObject = dataArray.getJSONObject(i);
                            String title = jsonObject.getString("name");
                            String localisation = jsonObject.getString("slug");
                            String image = jsonObject.getString("mainPhotoSrc");
                            float rating = (float) jsonObject.getInt("priceRange");

                            Resto resto = new Resto(title, localisation, image, rating);
                            restoList.add(resto);
                        }

                        // Mettre à jour l'interface utilisateur avec la liste des restos
                        RestoAdapter adapter = new RestoAdapter(getActivity(), restoList);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                error -> {
                    Toast.makeText(getActivity(), "Erreur lors de la récupération des données.", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", apiKey);
                headers.put("X-RapidAPI-Host", "the-fork-the-spoon.p.rapidapi.com");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
