package android.epsi.com.bebeer.activities.account;

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

public class UserProfile extends AppCompatActivity {

    private static final String TAG = "UserProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        ApiClient mApiClient = new ApiClient(this);
        mApiClient.isAuthenticated().enqueue(new Callback<User>() {

            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    bindUserToView(response.body());
                } else {
                    Log.d(TAG, String.format("onResponse: response.code = %d", response.code()));

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(UserProfile.this, "Error while fetching user's data", Toast.LENGTH_SHORT).show();
            }
        });
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
