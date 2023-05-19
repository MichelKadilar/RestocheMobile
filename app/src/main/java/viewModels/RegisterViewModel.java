package viewModels;

import androidx.lifecycle.ViewModel;
import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;

    public RegisterViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(String email, String password, OnRegistrationCompleteListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        listener.onRegistrationComplete(true, user);
                    } else {
                        listener.onRegistrationComplete(false, null);
                    }
                });
    }

    public interface OnRegistrationCompleteListener {
        void onRegistrationComplete(boolean success, FirebaseUser user);
    }
}
