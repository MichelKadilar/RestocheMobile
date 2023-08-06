package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.restoche.ActivityMap;
import com.restoche.R;


public class FragmentRestaurantProfile extends Fragment {

    private Button BTGoToMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_profile, container, false);
        BTGoToMap = v.findViewById(R.id.BT_access_map);
        BTGoToMap.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ActivityMap.class);
            startActivity(intent);
        });
        return v;
    }
}
