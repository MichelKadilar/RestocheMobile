package viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> _email = new MutableLiveData<>();
    private MutableLiveData<String> _password = new MutableLiveData<>();

    public LiveData<String> getEmail() {
        return _email;
    }

    public LiveData<String> getPassword() {
        return _password;
    }

    public void onEmailChanged(String email) {
        _email.setValue(email);
    }

    public void onPasswordChanged(String password) {
        _password.setValue(password);
    }

    public boolean validateInputs() {
        // Validez les entrées et renvoyez true si elles sont valides, sinon false
        return true; // Retournez la valeur correcte après avoir implémenté la logique de validation
    }

    public boolean authenticate() {
        // Authentifiez l'utilisateur et renvoyez true si la connexion est réussie, sinon false
        return true; // Retournez la valeur correcte après avoir implémenté la logique d'authentification
    }
}
