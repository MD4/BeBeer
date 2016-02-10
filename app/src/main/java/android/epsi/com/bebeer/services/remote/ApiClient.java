package android.epsi.com.bebeer.services.remote;

import android.content.Context;
import android.epsi.com.bebeer.AppConfig;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Brewery;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.remote.convertors.DateTimeTypeConverter;
import android.epsi.com.bebeer.services.remote.interceptors.GetCookieInterceptor;
import android.epsi.com.bebeer.services.remote.interceptors.SetCookieInterceptor;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by fx on 20/01/16.
 * Web Service Client for our web API
 */
public class ApiClient {

    private static final String TAG = "ApiClient";

    /**
     * Singleton
     */
    private static ApiInterface mApi;

    /**
     * Current user, or null if none
     */
    private static Beer mUser = null;

    public ApiClient(Context ctx) {

        // Build our client if not already build
        if (mApi == null) {
            Log.i(TAG, String.format("ApiClient: Building client for %s", AppConfig.API_BASE_URL));
            OkHttpClient httpClient = new OkHttpClient();

            httpClient.interceptors().addAll(Arrays.asList(
                    new SetCookieInterceptor(ctx),
                    new GetCookieInterceptor(ctx)
            ));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.API_BASE_URL)
                    .addConverterFactory(
                            // Handle Date (un)serialize
                            GsonConverterFactory
                                    .create(
                                            new GsonBuilder()
                                                    .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                                                    .create()
                                    )
                    )
                    .client(httpClient)
                    .build();
            mApi = retrofit.create(ApiInterface.class);
        }
    }

    /**
     * List beers, paginated without search query
     * @param offset
     * @param count
     * @return
     */
    public Call<List<Beer>> getBeers(int offset, int count) {
        Log.i(TAG, "getBeers() called with: " + "offset = [" + offset + "], count = [" + count + "]");
        return mApi.getBeers(offset, count);
    }

    /**
     * List beers, paginated with search query
     *
     * @return promise-like object
     */
    public Call<List<Beer>> getBeers(int offset, int count, String name) {
        Log.i(TAG, "getBeers() called with: " + "offset = [" + offset + "], count = [" + count + "], name = [" + name + "]");
        return mApi.getBeers(offset, count, name);
    }

    /**
     * Find the beer for given id
     *
     * @param id Id to be looked up
     * @return promise-like object
     */
    public Call<Beer> getBeer(String id) {
        Log.i(TAG, "getBeer() called with: " + "id = [" + id + "]");
        return mApi.getBeer(id);
    }

    /**
     * Get breweries paginated
     *
     * @param offset
     * @param count
     * @return
     */
    public Call<List<Brewery>> getBreweries(int offset, int count) {
        return mApi.getBreweries(offset, count);
    }

    /**
     * Search for breweries, paginated
     * @param offset
     * @param count
     * @param query
     * @return
     */
    public Call<List<Brewery>> getBreweries(int offset, int count, String query) {
        return mApi.getBreweries(offset, count, query);
    }

    /**
     * Try to authenticate given user
     * Fx cqcq
     * @param user
     * @return
     */
    public Call<User> authenticate(User user) {
        return mApi.postAuth(user);
    }

    /**
     * Ask backend if user is authenticated or not
     *
     * @return
     */
    public Call<User> isAuthenticated() {
        return mApi.getAuth();
    }

    /**
     * Logout current user if any
     *
     * @return
     */
    public Call<Void> logout() {
        return mApi.deleteAuth();
    }

    /**
     * Search for given username
     *
     * @param username
     * @return
     */
    public Call<User> getUser(String username) {
        return mApi.getUser(username);
    }

    /**
     * Try to create given user
     *
     * @param user
     * @return
     */
    public Call<User> createUser(User user) {
        return mApi.postUser(user);
    }
}
