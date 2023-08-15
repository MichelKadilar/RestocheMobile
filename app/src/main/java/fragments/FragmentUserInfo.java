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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import models.Resto;
import models.User;

public class FragmentUserInfo extends Fragment {

    private User user;
    private TextView tvFirstname, tvLastname, tvAge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupérez l'objet User depuis le Bundle
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        View v = inflater.inflate(R.layout.fragment_restaurant_profile, container, false);

        // Références aux TextViews
        tvFirstname = view.findViewById(R.id.TV_firstname);
        tvLastname = view.findViewById(R.id.TV_lastname);
        tvAge = view.findViewById(R.id.TV_age);

        // Remplacez le texte des TextViews par les propriétés de l'objet User
        if (user != null) {
            tvFirstname.setText(user.getPseudo());
            tvLastname.setText(user.getPrenom());

            // Pour la date de naissance, vous pouvez utiliser une fonction pour calculer l'âge
            String ageText = getAgeFromBirthday(user.getBirthday()) + " ans - " + user.getBirthday();
            tvAge.setText(ageText);
        }
        ImageView profilImage = view.findViewById(R.id.IV_user_profile_img);
        String imageUrl = user.getProfil();
        Picasso.get().load(imageUrl).into(profilImage);

        return view;
    }

    private int getAgeFromBirthday(String birthday) {
        // Assumant que le format de birthday est "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date birthDate;
        try {
            birthDate = sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();

        birth.setTime(birthDate);

        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }
}
