package android.epsi.com.bebeer.services;

import android.epsi.com.bebeer.AppConfig;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.dto.BeerRequest;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

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

    public ApiClient() {

        // Build our client if not already build
        if (mApi == null) {
            Log.i(TAG, String.format("ApiClient: Building client for %s", AppConfig.API_BASE_URL));
            OkHttpClient httpClient = new OkHttpClient();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            mApi = retrofit.create(ApiInterface.class);
        }
    }

    /**
     * List beers with default params
     *
     * @return promise-like object
     */
    public Call<List<Beer>> getBeers() {
        Log.i(TAG, "getBeers() called");
        return mApi.getBeers();
    }

    /**
     * List beers matching request params
     *
     * @param beerRequest Wrapped params
     * @return promise-like object
     */
    public Call<List<Beer>> getBeers(BeerRequest beerRequest) {
        Log.i(TAG, "getBeers() called with: " + "beerRequest = [" + beerRequest + "]");
        return mApi.getBeers(beerRequest.getOffset(), beerRequest.getCount(), beerRequest.getRequestByName());
    }

    /**
     * Find the beer for given id
     *
     * @param id Id to be looked up
     * @return promise-like object
     */
    public Call<Beer> getBeer(int id) {
        Log.i(TAG, "getBeer() called with: " + "id = [" + id + "]");
        return mApi.getBeer(id);
    }

}