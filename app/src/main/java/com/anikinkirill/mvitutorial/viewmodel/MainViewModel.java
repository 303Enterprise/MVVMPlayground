package com.anikinkirill.mvitutorial.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.anikinkirill.mvitutorial.api.RetrofitBuilder;
import com.anikinkirill.mvitutorial.model.AuthResource;
import com.anikinkirill.mvitutorial.model.User;
import com.anikinkirill.mvitutorial.util.ApiResponse;

import static com.anikinkirill.mvitutorial.util.ApiResponse.*;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<AuthResource<User>> user = new MediatorLiveData<>();

    public void authenticateUserById(int userId) {
        user.setValue(AuthResource.loading((User) null));
        user.addSource(RetrofitBuilder.getApiService().authenticateUserById(userId), new Observer<ApiResponse<User>>() {
            @Override
            public void onChanged(ApiResponse<User> userApiResponse) {
                if(userApiResponse instanceof ApiSuccessResponse) {
                    user.postValue(AuthResource.authenticated(((ApiSuccessResponse<User>) userApiResponse).getBody()));
                }
                if(userApiResponse instanceof ApiErrorResponse) {
                    user.postValue(AuthResource.error(((ApiErrorResponse<User>) userApiResponse).getErrorMessage(), (User) null));
                }
                if(userApiResponse instanceof ApiEmptyResponse) {
                    user.postValue(AuthResource.error("Something went wrong...", new User()));
                }
            }
        });
    }

    public LiveData<AuthResource<User>> observeUser() {
        return user;
    }

}
