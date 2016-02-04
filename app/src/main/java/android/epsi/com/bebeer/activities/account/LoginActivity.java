package android.epsi.com.bebeer.activities.account;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.list.BeerListActivity;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = "LoginActivity";

    // UI references.
    private boolean mSignUp;
    private ApiClient mApi;

    private Button mSignInButton;
    private Button mSignUpButton;
    private Button mBackButton;

    private EditText mEmailFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApi = new ApiClient(this);

        mSignUp = false;

        final TextView userView = (TextView) findViewById(R.id.login_username);
        final EditText passwordView = (EditText) findViewById(R.id.login_password);
        final View loginFormView = findViewById(R.id.login_form);
        final View progressView = findViewById(R.id.login_progress);

        mEmailFormView = (EditText) findViewById(R.id.login_email);

        mSignInButton = (Button) findViewById(R.id.login_sign_in);
        mSignUpButton = (Button) findViewById(R.id.login_sign_up);
        mBackButton = (Button) findViewById(R.id.login_back);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userView.getText().toString();
                String pwd = passwordView.getText().toString();
                attemptLogin(username, pwd);
            }
        });

        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mSignUp) {
                    signUpView();
                } else {

                }
            }
        });

        mBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signInView();
            }
        });
    }

    /**
     * attemptLogin
     *
     * @param username name of user
     * @param pwd      password of user
     */
    public void attemptLogin(String username, String pwd) {
        Log.d(TAG, "attemptLogin() called with: " + "");
        User user = new User();
        user.setUsername(username);
        user.setPassword(pwd);
        mApi.authenticate(user).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "], retrofit = [" + retrofit + "]");
                        Intent intent = new Intent(LoginActivity.this, BeerListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: " + "throwable = [" + throwable + "]");

                    }
                }
        );
    }

    public void signInView() {
        mSignUp = false;
        mSignInButton.setVisibility(View.VISIBLE);
        mBackButton.setVisibility(View.GONE);
        mEmailFormView.setVisibility(View.GONE);
    }

    public void signUpView() {
        mSignUp = true;
        mSignInButton.setVisibility(View.GONE);
        mBackButton.setVisibility(View.VISIBLE);
        mEmailFormView.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

