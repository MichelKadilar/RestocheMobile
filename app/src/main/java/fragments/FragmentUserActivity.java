package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restoche.R;
import com.bumptech.glide.Glide;  // Assurez-vous d'avoir cette dépendance si vous utilisez Glide
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import models.Resto;

public class FragmentUserActivity extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_activity, container, false);

        recyclerView = view.findViewById(R.id.RV_user_activity_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("restos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Resto> restaurantsList = new ArrayList<>();
                for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    Resto restaurant = restaurantSnapshot.getValue(Resto.class);
                    restaurantsList.add(restaurant);
                }

                // Mise à jour de l'adapter avec les nouvelles données
                adapter = new MyAdapter(restaurantsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FragmentUserActivity", "Erreur de chargement des données", databaseError.toException());
            }
        });
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<Resto> dataList;

        public MyAdapter(List<Resto> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_activity_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            Resto resto = dataList.get(position);


            int radius = 830; // rayon pour les coins arrondis
            int margin = 0;  // marge à utiliser si nécessaire
            RoundedCornersTransformation transformation = new RoundedCornersTransformation(radius, margin);

            Picasso.get()
                    .load(resto.getImage())
                    .transform(transformation)
                    .into(holder.imageView);


            holder.titleView.setText(resto.getTitle());
            holder.ratingBar.setRating(resto.getRatings());
            holder.descriptionView.setText(resto.getDescription());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleView;
            RatingBar ratingBar;
            TextView descriptionView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView4);
                titleView = itemView.findViewById(R.id.textView20);
                ratingBar = itemView.findViewById(R.id.ratingBar);
                descriptionView = itemView.findViewById(R.id.textView22);
            }
        }
    }
}
