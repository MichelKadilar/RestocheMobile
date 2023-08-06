package fragments;

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
import com.restoche.MainActivity;
import com.restoche.R;

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
                    public void onAuthenticationComplete(boolean success, FirebaseUser user) {
                        if (success) {
                            // Connexion réussie, passez à l'activité suivante ou au fragment suivant
                            Toast.makeText(getContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();

                            // Charger le RestoFragment
                            RestoFragment restoFragment = new RestoFragment();
                            FragmentTransaction restoTransaction = getFragmentManager().beginTransaction();
                            restoTransaction.replace(R.id.fragment_container, restoFragment);
                            restoTransaction.commit();

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
