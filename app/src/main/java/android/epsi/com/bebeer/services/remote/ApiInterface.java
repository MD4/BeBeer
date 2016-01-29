package android.epsi.com.bebeer.services.remote;

import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Brewery;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by fx on 20/01/16.
 * List backend end-point
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

    @GET("/breweries")
    Call<List<Brewery>> getBreweries(@Query("offset") int offset,
                                     @Query("count") int count);

    @GET("/breweries")
    Call<List<Brewery>> getBreweries(@Query("offset") int offset,
                                     @Query("count") int count,
                                     @Query("name") String name);
}
