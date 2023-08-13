package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.restoche.ActivityMap;
import com.restoche.R;
import com.squareup.picasso.Picasso;

import models.Resto;


public class FragmentRestaurantProfile extends Fragment {

    private Button BTGoToMap;

    private TextView distanceTextView;

    public static FragmentRestaurantProfile newInstance(Resto resto) {
        FragmentRestaurantProfile fragment = new FragmentRestaurantProfile();
        Bundle args = new Bundle();
        args.putParcelable("resto", resto);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_profile, container, false);

        if (getArguments() != null) {
            Resto resto = getArguments().getParcelable("resto");
            if (resto != null) {
                TextView restaurantName = v.findViewById(R.id.TV_restaurant_name_profile);
                System.out.println("FIX RESTO NAME");
                System.out.println("Resto." + resto.getTitle());
                restaurantName.setText(resto.getTitle());

                distanceTextView = v.findViewById(R.id.textView11);
                setRandomDistance();

                // Load and set restaurant image
                ImageView restaurantImage = v.findViewById(R.id.IV_restaurant_image_profile);
                String imageUrl = resto.getImage();
                Picasso.get().load(imageUrl).into(restaurantImage); // Using Picasso to load the image

                TextView restaurantaddress = v.findViewById(R.id.TV_restaurant_profile_address);
                System.out.println("Resto." + resto.getLocalisation());
                restaurantaddress.setText(resto.getLocalisation());

                TextView restaurantSpec = v.findViewById(R.id.TV_restaurant_specialities_profile_list);
                System.out.println("Resto." + resto.getSpec());
                restaurantSpec.setText(resto.getSpec());
            }
        }

        BTGoToMap = v.findViewById(R.id.BT_access_map);
        BTGoToMap.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ActivityMap.class);
            startActivity(intent);
        });
        return v;
    }

    private void setRandomDistance() {
        double randomDistance = 0.1 + Math.random() * 15; // Génère une distance aléatoire entre 1000 et 41000
        distanceTextView.setText(String.format("%.1f KM", randomDistance));
    }
}

