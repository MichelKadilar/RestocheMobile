package viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> _email = new MutableLiveData<>();
    private MutableLiveData<String> _password = new MutableLiveData<>();

    private FirebaseAuth firebaseAuth;

    public LoginViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("fr"); // Pour le français
    }

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
        String email = _email.getValue();
        String password = _password.getValue();

        return email != null && !email.trim().isEmpty() && password != null && !password.trim().isEmpty();
    }

    public void authenticate(OnAuthenticationCompleteListener listener) {
        String email = _email.getValue();
        String password = _password.getValue();

        if (email != null && password != null) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            listener.onAuthenticationComplete(true, user);
                        } else {
                            listener.onAuthenticationComplete(false, null);
                        }
                    });
        } else {
            listener.onAuthenticationComplete(false, null);
        }
    }

    public interface OnAuthenticationCompleteListener {
        void onAuthenticationComplete(boolean success, FirebaseUser user);
    }
}
