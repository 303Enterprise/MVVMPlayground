package com.anikinkirill.mvitutorial.api;

import androidx.lifecycle.LiveData;

import com.anikinkirill.mvitutorial.model.User;
import com.anikinkirill.mvitutorial.util.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users/{id}")
    LiveData<ApiResponse<User>> authenticateUserById(@Path("id") int id);

}
