package android.epsi.com.bebeer.activities.brewery.profile;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.beer.list.adapters.BeerListItemAdapter;
import android.epsi.com.bebeer.bean.Brewery;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class BreweryProfilActivity extends AppCompatActivity {

    public static final String EXTRA_BREWERY_NAME = "name";
    private final String TAG = "BreweryProfilActivity";
    private String breweryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_profil);

        breweryName = getIntent().getStringExtra(EXTRA_BREWERY_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_brewery_profil_toolbar);
        setSupportActionBar(toolbar);

        ApiClient apiClient = new ApiClient(this);
        setUpBrewery(apiClient);
        setUpBeerList();
    }

    private void setUpBeerList() {
        Log.d(TAG, "setUpBeerList() called with: " + "");
        BeerListItemAdapter beerListItemAdapter = new BeerListItemAdapter(this);
        beerListItemAdapter.setBreweryName(EXTRA_BREWERY_NAME);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.brewery_profil_content_beers);
        setUpRecyclerView(recyclerView, beerListItemAdapter);
    }

    /**
     * Init RecyclerView (~= ListView) related things
     *
     * @param recyclerView
     * @param beerListItemAdapter
     */
    private void setUpRecyclerView(final RecyclerView recyclerView, BeerListItemAdapter beerListItemAdapter) {
        Log.d(TAG, "setUpRecyclerView() called with: " + "recyclerView = [" + recyclerView + "], beerListItemAdapter = [" + beerListItemAdapter + "]");
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(beerListItemAdapter);
    }

    private void setUpBrewery(ApiClient apiClient) {
        Log.d(TAG, "setUpBrewery() called with: " + "apiClient = [" + apiClient + "]");
        apiClient.getBrewery(breweryName).enqueue(new Callback<Brewery>() {
            @Override
            public void onResponse(Response<Brewery> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Brewery brewery = response.body();
                    bindBreweryToView(brewery);
                } else {
                    Log.d(TAG, "error: " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure() called with: " + "t = [" + t + "]");

            }
        });
    }

    private void bindBreweryToView(Brewery brewery) {
        Log.d(TAG, "bindBreweryToView() called with: " + "brewery = [" + brewery + "]");
        TextView name = (TextView) findViewById(R.id.brewery_profil_content_name);
        TextView country = (TextView) findViewById(R.id.brewery_profil_content_country);

        name.setText(brewery.getName());
        country.setText(brewery.getCountry());
    }

}
