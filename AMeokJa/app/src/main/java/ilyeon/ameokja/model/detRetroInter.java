package ilyeon.ameokja.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface detRetroInter {
    @GET("foods/detail")
    Call<foodRetrofit> getDetail(
            @Query("serial") String serial
    );




}
