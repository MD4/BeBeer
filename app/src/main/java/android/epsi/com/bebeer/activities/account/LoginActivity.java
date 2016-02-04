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
    private boolean signUp;
    private ApiClient api;

    private Button signInButton;
    private Button signUpButton;
    private Button backButton;

    private EditText emailFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        api = new ApiClient(this);

        signUp = false;

        final TextView userView = (TextView) findViewById(R.id.username);
        final EditText passwordView = (EditText) findViewById(R.id.password);
        final View loginFormView = findViewById(R.id.login_form);
        final View progressView = findViewById(R.id.login_progress);

        emailFormView = (EditText) findViewById(R.id.email);

        signInButton = (Button) findViewById(R.id.sign_in);
        signUpButton = (Button) findViewById(R.id.sign_up);
        backButton = (Button) findViewById(R.id.back);

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userView.getText().toString();
                String pwd = passwordView.getText().toString();
                attemptLogin(username, pwd);
            }
        });

        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!signUp) {
                    signUpView();
                } else {

                }
            }
        });

        backButton.setOnClickListener(new OnClickListener() {
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
        api.authenticate(user).enqueue(
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
        signUp = false;
        signInButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.GONE);
        emailFormView.setVisibility(View.GONE);
    }

    public void signUpView() {
        signUp = true;
        signInButton.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        emailFormView.setVisibility(View.VISIBLE);
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

