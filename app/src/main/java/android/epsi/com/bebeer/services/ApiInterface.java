package android.epsi.com.bebeer.services;

import android.epsi.com.bebeer.bean.Beer;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by fx on 20/01/16.
 * Represents end-point for our api
 */
interface ApiInterface {

    @GET("/beers")
    Call<List<Beer>> getBeers(@Query("offset") int offset,
                              @Query("count") int count);

    @GET("/beers")
    Call<List<Beer>> getBeers(@Query("offset") int offset,
                              @Query("count") int count,
                              @Query("name") String name);

    @GET("/beers/{id}")
    Call<Beer> getBeer(@Path("id") int id);
}
