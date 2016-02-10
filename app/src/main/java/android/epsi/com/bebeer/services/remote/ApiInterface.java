package android.epsi.com.bebeer.services.remote;

import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Brewery;
import android.epsi.com.bebeer.bean.Rating;
import android.epsi.com.bebeer.bean.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
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
    Call<Beer> getBeer(@Path("id") String id);

    @POST("/beers/{id}/ratings")
    Call<Beer> rateBeer(@Path("id") String id, @Body Rating rate);

    @GET("/breweries")
    Call<List<Brewery>> getBreweries(@Query("offset") int offset,
                                     @Query("count") int count);

    @GET("/breweries")
    Call<List<Brewery>> getBreweries(@Query("offset") int offset,
                                     @Query("count") int count,
                                     @Query("name") String name);

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @POST("/users")
    Call<User> postUser(@Body User user);

    @POST("/auth")
    Call<User> postAuth(@Body User user);

    @GET("/auth")
    Call<User> getAuth();

    @DELETE("/auth")
    Call<Void> deleteAuth();

}
