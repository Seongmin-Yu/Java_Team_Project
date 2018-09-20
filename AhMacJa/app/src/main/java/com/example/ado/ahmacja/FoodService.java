package com.example.ado.ahmacja;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodService {
    @GET("foods")
    Call<FoodRepo> getFoods(
            @Query("item") String keyword
    );
}
