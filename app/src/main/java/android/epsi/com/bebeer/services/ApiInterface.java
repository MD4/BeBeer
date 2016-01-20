package android.epsi.com.bebeer.services;

import android.epsi.com.bebeer.bean.Beer;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by fx on 20/01/16.
 */
public interface ApiInterface {

    @GET
    Call<List<Beer>> getBeers();

    @GET
    Call<Beer> getBeer();
}
