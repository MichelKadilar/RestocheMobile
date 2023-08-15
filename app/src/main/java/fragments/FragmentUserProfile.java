package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.restoche.R;

import java.util.ArrayList;
import java.util.List;

import models.User;

public class FragmentUserProfile extends Fragment {

    private User user;

    // Méthode pour créer une nouvelle instance du FragmentUserProfile avec un objet User en tant qu'argument
    public static FragmentUserProfile newInstance(User user) {
        FragmentUserProfile fragment = new FragmentUserProfile();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    // Lors de la création du Fragment, récupérez l'objet User depuis le Bundle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }
    }

    // Lors de la création de la vue du Fragment, affichez le FragmentUserInfo et retournez la vue principale
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return view;
    }

    // Affichez le FragmentUserInfo avec l'objet User comme argument
    public void showUserInfoFragment() {
        FragmentUserInfo userInfoFragment = FragmentUserInfo.newInstance(user);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_user_info, userInfoFragment)
                .commit();
    }
}

