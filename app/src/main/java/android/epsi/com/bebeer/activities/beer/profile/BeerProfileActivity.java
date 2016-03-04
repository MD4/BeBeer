package android.epsi.com.bebeer.activities.beer.profile;

import android.content.Intent;
import android.epsi.com.bebeer.AppConfig;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.beer.profile.adapters.RateItemAdapter;
import android.epsi.com.bebeer.activities.brewery.profile.BreweryProfilActivity;
import android.epsi.com.bebeer.activities.user.login.LoginActivity;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Rating;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.image.ApiImageAccessor;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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
    /**
     * Use for passing param to another activity
     */
    public static final String EXTRA_BEER_ID = "id";
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
        String beerId = intent.getStringExtra(EXTRA_BEER_ID);
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
    private void bindBeerToView(final Beer beer) {
        CollapsingToolbarLayout toolbarName = (CollapsingToolbarLayout) findViewById(R.id.beer_profile_toolbar_layout);
        TextView country = (TextView) findViewById(R.id.beer_profile_country);
        TextView brewery = (TextView) findViewById(R.id.beer_profile_brewery);
        TextView comment = (TextView) findViewById(R.id.beer_profile_comment);
        TextView fermentation = (TextView) findViewById(R.id.beer_profile_fermentation);
        TextView shortDesc = (TextView) findViewById(R.id.beer_profile_short_description);

        RatingBar gradeTaste = (RatingBar) findViewById(R.id.beer_profile_taste);
        RatingBar gradeThirsty = (RatingBar) findViewById(R.id.beer_profile_thirsty);
        RatingBar gradeBitterness = (RatingBar) findViewById(R.id.beer_profile_bitterness);

        ImageView image = (ImageView) findViewById(R.id.beer_profile_beer_image);
        final RatingBar myRating = (RatingBar) findViewById(R.id.beer_profile_my_rating);
        final RatingBar rating = (RatingBar) findViewById(R.id.beer_profile_rating);

        mApiImageAccessor.displayImageToView(image, beer.getImage());

        toolbarName.setTitle(beer.getName());
        country.setText(beer.getCountry());
        brewery.setText(beer.getBrewery());
        comment.setText(beer.getComment());
        fermentation.setText(beer.getFermentation());
        shortDesc.setText(beer.getShortDescription());

        gradeTaste.setMax(10);
        gradeThirsty.setMax(4);
        gradeThirsty.setNumStars(2);
        gradeBitterness.setMax(4);
        gradeBitterness.setNumStars(2);

        gradeTaste.setRating(beer.getGrades().getTaste().intValue());
        gradeThirsty.setRating(beer.getGrades().getThirsty().intValue());
        gradeBitterness.setRating(beer.getGrades().getBitterness().intValue());

        myRating.setMax(10);
        myRating.setStepSize(1);


        rating.setMax(10);
        rating.setStepSize(1);

        if (beer.getRatings() != null) {
            rating.setRating(beer.getRatings().getAverage().floatValue());
        }

        brewery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeerProfileActivity.this, BreweryProfilActivity.class);
                intent.putExtra(BreweryProfilActivity.EXTRA_BREWERY_NAME, beer.getBrewery());
                startActivity(intent);
            }
        });


        final ApiClient apiClient = new ApiClient(BeerProfileActivity.this);

        // Add rate bar listener
        myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float ratingValue, boolean fromUser) {
                if (fromUser) {
                    myRating.setEnabled(false);
                    apiClient.rateBeer(beer.getId(), (int) ratingValue).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Response<Void> response, Retrofit retrofit) {
                            myRating.setEnabled(true);
                            if (!response.isSuccess()) {
                                try {
                                    Log.d(TAG, "onResponse: " + response.errorBody().string());
                                } catch (IOException ignored) {
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            myRating.setEnabled(true);
                            Log.e(TAG, "onFailure: ", t);
                        }
                    });
                    Log.i(TAG, "onRatingChanged: rating = " + ratingValue);
                }
            }
        });

        List<Rating> ratings = mUser.getRatings();
        Rating rate = null;
        for (Rating rateTmp : ratings) {
            if (rateTmp.getBeerId().equals(beer.getId())) {
                rate = rateTmp;
                break;
            }
        }
        if (rate != null) {
            myRating.setRating(rate.getRate());
        }


        ImageView shareBtn = (ImageView) findViewById(R.id.beer_profile_share_btn);
        setUpShareBtn(shareBtn, beer);
        findViewById(R.id.beer_profile_container).setVisibility(View.VISIBLE);
        setUpCommentsList(beer);
    }

    /**
     * Init comments/rate list
     *
     * @param beer
     */
    private void setUpCommentsList(final Beer beer) {
        Log.i(TAG, "setUpCommentsList() called with: " + "beer = [" + beer + "]");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.beer_profile_comments_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);

        RecyclerView.Adapter rateItemAdapter = new RateItemAdapter(this, beer);
        recyclerView.setAdapter(rateItemAdapter);
    }


}
