package fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restoche.ActivityFragmentSwitcher;
import com.restoche.MainActivity;
import com.restoche.R;

import models.User;
import viewModels.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private CheckBox rememberMeCheckBox;
    private LinearLayout registerTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        emailEditText = view.findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = view.findViewById(R.id.editTextTextPassword);
        loginButton = view.findViewById(R.id.button);
        rememberMeCheckBox = view.findViewById(R.id.checkBox);
        registerTextView = view.findViewById(R.id.s_inscrire);

        int nightModeFlags = getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Dark mode is active
                emailEditText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.hint_dark));
                passwordEditText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.hint_dark));
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                // Light mode is active
                emailEditText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.hint_light));
                passwordEditText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.hint_light));
                break;
        }

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.onEmailChanged(s.toString());
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.onPasswordChanged(s.toString());
            }
        });

        loginButton.setOnClickListener(view1 -> {
            if (loginViewModel.validateInputs()) {
                loginViewModel.authenticate(new LoginViewModel.OnAuthenticationCompleteListener() {
                    @Override
                    public void onAuthenticationComplete(boolean success, FirebaseUser firebaseUser) {
                        if (success) {
                            // Récupérer les données utilisateur à partir de l'UID
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                            mDatabase.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);

                                    if (user != null) {
                                        // Charger le RestoFragment avec l'objet user en extra
                                        Intent intent = new Intent(getActivity(), ActivityFragmentSwitcher.class);
                                        intent.putExtra("user", user); // Passez l'objet user en extra
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getContext(), "Erreur lors de la récupération des données utilisateur", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Affichez une erreur si quelque chose ne va pas
                                    Toast.makeText(getContext(), "Erreur: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // Échec de la connexion, affichez un message d'erreur
                            Toast.makeText(getContext(), "Échec de la connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                // Les entrées ne sont pas valides, affichez un message d'erreur
                Toast.makeText(getContext(), "Les entrées ne sont pas valides", Toast.LENGTH_SHORT).show();
            }
        });


        // Set an OnClickListener for the "Register" TextView
        registerTextView.setOnClickListener(view12 -> {
            // Load the RegisterFragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadRegisterFragment();
            }
        });


        // Set an OnClickListener for the "Register" TextView
        /*registerTextView.setOnClickListener(view12 -> {
            // Open the "Register" activity
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });*/
    }
}
