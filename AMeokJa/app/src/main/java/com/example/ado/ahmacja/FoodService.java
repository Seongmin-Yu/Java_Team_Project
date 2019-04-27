package com.example.ado.ahmacja;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodService {
    @GET("foods")
    Call<FoodRepo> getFoods(
            @Query("item") String keyword
    );

    @POST("likes")
    Call<FoodRepo> likes(
            @Body LikeRequest body
    );
}
