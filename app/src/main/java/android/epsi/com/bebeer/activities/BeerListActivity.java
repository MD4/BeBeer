package android.epsi.com.bebeer.activities;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.adapters.BeerListItemAdapter;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.services.ApiClient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Show list of beers, quick search
 */
public class BeerListActivity extends AppCompatActivity {

    private static final String TAG = "BeerListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.beer_list_toolbar);
        setUpToolbar(toolbar);

        ApiClient apiClient = new ApiClient();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.beer_list_recycler);
        setUpRecyclerView(mRecyclerView, apiClient);

    }

    /**
     * Init toolbar related things
     *
     * @param toolbar
     */
    private void setUpToolbar(Toolbar toolbar) {
        toolbar.setTitle(getResources().getString(R.string.beer_list_title));
        setSupportActionBar(toolbar);
    }

    /**
     * Init RecyclerView (~= ListView) related things
     * @param mRecyclerView
     * @param apiClient
     */
    private void setUpRecyclerView(final RecyclerView mRecyclerView, ApiClient apiClient) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setHasFixedSize(true);

        // API call
        apiClient.getBeers().enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Response<List<Beer>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    // Load data in view
                    BeerListItemAdapter adapter = new BeerListItemAdapter(response.body());
                    mRecyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "onResponse: code = " + response.code());
                    Toast.makeText(BeerListActivity.this, getResources().getString(R.string.beer_list_api_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: can't fetch beers", throwable);
                Toast.makeText(BeerListActivity.this, getResources().getString(R.string.beer_list_api_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
