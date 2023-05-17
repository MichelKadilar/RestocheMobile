package repositories;

import models.User;

public interface UserRepository {
    interface UserRepositoryCallback {
        void onSuccess(User user);
        void onError(Exception exception);
    }

    void register(String email, String password, UserRepositoryCallback callback);
    void login(String email, String password, UserRepositoryCallback callback);
}
