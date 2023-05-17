package repositories;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import models.User;

public class FirebaseUserRepository implements UserRepository {
    private FirebaseAuth mAuth;

    public FirebaseUserRepository() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void register(String email, String password, final UserRepositoryCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(email, password);
                            callback.onSuccess(user);
                        } else {
                            callback.onError(task.getException());
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password, final UserRepositoryCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(email, password);
                            callback.onSuccess(user);
                        } else {
                            callback.onError(task.getException());
                        }
                    }
                });
    }
}
