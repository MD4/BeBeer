package android.epsi.com.bebeer.activities.user.profile;

import android.content.Intent;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.beer.profile.BeerProfileActivity;
import android.epsi.com.bebeer.activities.user.profile.adapters.UserRatingsAdapter;
import android.epsi.com.bebeer.bean.Rating;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.image.ApiImageAccessor;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserProfileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_USER_ID = "username";
    private static final String TAG = "UserProfileActivity";
    private UserRatingsAdapter mUserRatingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        String usernameExtra = parseIntent(intent);

        fetchUser(usernameExtra);
    }

    private void fetchUser(final String username) {
        ApiClient apiClient = new ApiClient(this);
        if (username == null || username.equals("")) {
            apiClient.isAuthenticated().enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        bindUserToView(response.body());
                    } else {
                        Log.i(TAG, "onResponse: code = " + response.code());
                        Toast.makeText(UserProfileActivity.this, String.format("Can't fetch %s, sorry :(", username), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, String.format("onFailure: error while fetching user %s", username), t);
                }
            });
        } else {
            Log.i(TAG, String.format("fetchUser: %s", username));
            apiClient.getUser(username).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        bindUserToView(response.body());
                    } else {
                        Log.i(TAG, "onResponse: code = " + response.code());
                        Toast.makeText(UserProfileActivity.this, String.format("Can't fetch %s, sorry :(", username), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, String.format("onFailure: error while fetching user %s", username), t);
                }
            });
        }
    }

    private String parseIntent(Intent intent) {
        return intent.getStringExtra(EXTRA_USER_ID);
    }

    /**
     * Bind User to activity view
     *
     * @param user
     */
    private void bindUserToView(User user) {
        Log.i(TAG, "bindUserToView() called with: " + "user = [" + user + "]");
        TextView usernameView = (TextView) findViewById(R.id.user_profile_username);
        ImageView gravatarView = (ImageView) findViewById(R.id.user_profile_gravatar);
        ProgressBar loaderView = (ProgressBar) findViewById(R.id.user_profile_progress);
        ListView listView = (ListView) findViewById(R.id.user_profile_ratings);

        Collections.sort(user.getRatings(), new Comparator<Rating>() {
            @Override
            public int compare(Rating lhs, Rating rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });

        mUserRatingsAdapter = new UserRatingsAdapter(this, R.layout.user_profile_rating_item, user.getRatings());

        usernameView.setText(user.getUsername());
        ApiImageAccessor.createInstance(this);
        ApiImageAccessor.getInstance().displayImageToView(gravatarView, user.getGravatar());

        listView.setAdapter(mUserRatingsAdapter);

        listView.setOnItemClickListener(this);

        loaderView.setVisibility(View.GONE);
        gravatarView.setVisibility(View.VISIBLE);
        usernameView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String beerId = mUserRatingsAdapter.getItem(position).getBeerId();
        Intent intent = new Intent(this, BeerProfileActivity.class);
        intent.putExtra(BeerProfileActivity.EXTRA_BEER_ID, beerId);
        startActivity(intent);
    }
}
