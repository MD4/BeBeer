package android.epsi.com.bebeer.activities.user.profile;

import android.content.Intent;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.image.ApiImageAccessor;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "username";
    private static final String TAG = "UserProfileActivity";

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
        TextView usernameView = (TextView) findViewById(R.id.user_profile_username);
        TextView emailView = (TextView) findViewById(R.id.user_profile_email);
        ImageView gravatarView = (ImageView) findViewById(R.id.user_profile_gravatar);
        ProgressBar loaderView = (ProgressBar) findViewById(R.id.user_profile_progress);
        ListView listView = (ListView) findViewById(R.id.user_profile_ratings);

//        ArrayAdapter<Rating> arrayAdapter = new ArrayAdapter<Rating>(t)
// TODO finish it
        usernameView.setText(user.getUsername());
        emailView.setText(user.getEmail());
        ApiImageAccessor.createInstance(this);
        ApiImageAccessor.getInstance().displayImageToView(gravatarView, user.getGravatar());

        loaderView.setVisibility(View.GONE);
        gravatarView.setVisibility(View.VISIBLE);
        usernameView.setVisibility(View.VISIBLE);
        emailView.setVisibility(View.VISIBLE);
    }

}
