package android.epsi.com.bebeer.activities.profile;

import android.content.Intent;
import android.epsi.com.bebeer.AppConfig;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.account.LoginActivity;
import android.epsi.com.bebeer.activities.list.BeerListActivity;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Rating;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.image.ApiImageAccessor;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Handle beer profile view
 * TODO: design a prettier GUI
 */
public class BeerProfileActivity extends AppCompatActivity {

    public static final String ACTION_VIEW_INTENT_URL = AppConfig.API_BASE_URL + "beers/";
    private static final String TAG = "BeerProfileActivity";
    private ApiImageAccessor mApiImageAccessor;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.beer_profile_toolbar);
        setSupportActionBar(toolbar);

        ApiClient apiClient = new ApiClient(this);
        mApiImageAccessor = ApiImageAccessor.getInstance();
        setUpUser(apiClient);

    }

    private void setUpUser(final ApiClient apiClient) {
        apiClient.isAuthenticated().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    BeerProfileActivity.this.mUser = response.body();
                    Log.i(TAG, "onResponse: " + response.body().toString());
                    setUpBeerView(getIntent(), apiClient);
                } else {
                    Intent intent = new Intent(BeerProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    /**
     * @param shareBtn
     * @param beer
     */
    private void setUpShareBtn(ImageView shareBtn, final Beer beer) {
        Log.i(TAG, "setUpShareBtn() called with: " + "shareBtn = [" + shareBtn + "], beer = [" + beer + "]");
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, buildIntentUrl(beer));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    @NonNull
    private String buildIntentUrl(Beer beer) {
        return ACTION_VIEW_INTENT_URL + beer.getId();
    }

    /**
     * Set up view with given intent
     *
     * @param intent    Intent containing beer.id to look for
     * @param apiClient
     */
    private void setUpBeerView(Intent intent, ApiClient apiClient) {

        final String beerId = parseIntent(intent);

        if (beerId == null || beerId.equals("")) {
            Log.e(TAG, "setUpBeerView: no beer id found in extra");
            Toast.makeText(BeerProfileActivity.this, getResources().getString(R.string.beer_profile_no_id), Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the beer!
        apiClient.getBeer(beerId)
                .enqueue(new Callback<Beer>() {
                    @Override
                    public void onResponse(Response<Beer> response, Retrofit retrofit) {
                        Log.i(TAG, "onResponse: code = " + response.code());
                        if (response.isSuccess()) {
                            Beer beer = response.body();
                            bindBeerToView(beer);
                        } else {
                            Log.e(TAG, String.format("onResponse: can't get Beer(%s)", beerId));
                            Toast.makeText(BeerProfileActivity.this, R.string.beer_profile_error_api, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure: error during getBeer call", t);
                        Toast.makeText(BeerProfileActivity.this, R.string.beer_profile_error_api, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * Parse itent to retrieve beer id if found
     *
     * @param intent Intent of this activity
     * @return Beer id if found, else -1
     */
    private String parseIntent(Intent intent) {
        String beerId = intent.getStringExtra(BeerListActivity.EXTRA_BEER_ID);
        if (beerId == null && intent.getData() != null) {
            String segment = intent.getData().getLastPathSegment();
            try {
                beerId = String.valueOf(segment);
            } catch (NumberFormatException e) {
                beerId = null;
            }
        }
        return beerId;
    }

    /**
     * Map the given Beer into the view
     *
     * @param beer
     */
    private void bindBeerToView(Beer beer) {
        CollapsingToolbarLayout toolbarName = (CollapsingToolbarLayout) findViewById(R.id.beer_profile_toolbar_layout);
        TextView country = (TextView) findViewById(R.id.beer_profile_country);
        TextView brewery = (TextView) findViewById(R.id.beer_profile_brewery);
        TextView comment = (TextView) findViewById(R.id.beer_profile_comment);
        TextView fermentation = (TextView) findViewById(R.id.beer_profile_fermentation);
        TextView shortDesc = (TextView) findViewById(R.id.beer_profile_short_description);
        ImageView image = (ImageView) findViewById(R.id.beer_profile_beer_image);
        RatingBar rating = (RatingBar) findViewById(R.id.beer_profile_rating);

        mApiImageAccessor.displayImageToView(image, beer.getImage());

        toolbarName.setTitle(beer.getName());
        country.setText(beer.getCountry());
        brewery.setText(beer.getBrewery());
        comment.setText(beer.getComment());
        fermentation.setText(beer.getFermentation());
        shortDesc.setText(beer.getShortDescription());
        rating.setMax(10);

        List<Rating> ratings = mUser.getRatings();
        Rating rate = null;
        for (Rating rateTmp : ratings) {
            if (rateTmp.getBeerId().equals(beer.getId())) {
                rate = rateTmp;
                break;
            }
        }
        if (rate != null) {
            rating.setRating(rate.getRate());
        }

        ImageView shareBtn = (ImageView) findViewById(R.id.beer_profile_share_btn);
        setUpShareBtn(shareBtn, beer);
    }


}
