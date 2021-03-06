package com.example.watcher.data.login;

import androidx.annotation.NonNull;

import com.example.watcher.api.UserNetService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Singleton
public class LoginRepository {

    private final UserNetService userNetService;

    private LoggedInUser user = null;

    private MyCallback mCallback;

    @Inject
    LoginRepository(UserNetService userNetService) {
        this.userNetService = userNetService;
    }

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        userNetService.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public void login(String account, String password) {
        Call<Boolean> call = userNetService.login(account, password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())) {
                    mCallback.OnLogin();
                } else {
                    mCallback.OnFail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable throwable) {
                mCallback.OnFail();
            }
        });
    }


    public interface MyCallback {
        void OnLogin();

        void OnFail();
    }
}