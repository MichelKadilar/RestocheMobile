package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.restoche.MainActivity;
import com.restoche.R;
import com.restoche.RegisterActivity;

import viewModels.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private Button registerButton;
    private TextView loginTextView;
    private RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = view.findViewById(R.id.editTextTextPassword);
        editTextPasswordConfirm = view.findViewById(R.id.editTextTextPasswordConfirm);
        registerButton = view.findViewById(R.id.button);
        loginTextView = view.findViewById(R.id.textView3);

        // Set an OnClickListener for the register button
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Utilisez le RegisterViewModel pour gérer l'inscription
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String passwordConfirm = editTextPasswordConfirm.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
                    // Affichez un message indiquant que les champs ne doivent pas être vides
                    Toast.makeText(getContext(), "les champs ne doivent pas être vides", Toast.LENGTH_SHORT).show();

                } else if (!password.equals(passwordConfirm)) {
                    // Affichez un message indiquant que les mots de passe doivent correspondre
                    Toast.makeText(getContext(), "les mots de passe doivent correspondre", Toast.LENGTH_SHORT).show();
                } else {
                    registerViewModel.registerUser(email, password, new RegisterViewModel.OnRegistrationCompleteListener() {
                        @Override
                        public void onRegistrationComplete(boolean success, FirebaseUser user) {
                            if (success) {
                                // L'utilisateur a été enregistré avec succès
                                Toast.makeText(getContext(), "Inscription réussie", Toast.LENGTH_SHORT).show();
                                // Redirigez vers une autre activité ou un autre fragment
                            } else {
                                Toast.makeText(getContext(), "Les entrées ne sont pas valides", Toast.LENGTH_SHORT).show();
                                // L'inscription a échoué, affichez un message d'erreur
                            }
                        }
                    });
                }
            }
        });

        // Set an OnClickListener for the login TextView
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the "Login" activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
