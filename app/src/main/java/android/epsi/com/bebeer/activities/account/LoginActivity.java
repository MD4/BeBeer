package android.epsi.com.bebeer.activities.account;

import android.content.Intent;
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
import android.widget.Toast;

import java.io.IOException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // UI references.
    private boolean mSignUp;
    private ApiClient mApi;

    private Button mSignInButton;
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

        mEmailFormView = (EditText) findViewById(R.id.login_email);

        mSignInButton = (Button) findViewById(R.id.login_sign_in);
        Button mSignUpButton = (Button) findViewById(R.id.login_sign_up);
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
                Log.d(TAG, "onClick() called with: " + "view = [" + view + "]");
                if (!mSignUp) {
                    signUpView();
                } else {
                    String username = userView.getText().toString();
                    String pwd = passwordView.getText().toString();
                    String email = mEmailFormView.getText().toString();
                    attemptSignUp(username, pwd, email);
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
     * sign up user
     *
     * @param username the name of user
     * @param pwd      the user password
     * @param email    the user email
     */
    public void attemptSignUp(String username, String pwd, String email) {
        Log.d(TAG, "attemptSignUp() called with: " + "username = [" + username + "], email = [" + email + "], pwd = [" + pwd + "]");
        final User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(pwd);

        mApi.createUser(user).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "], retrofit = [" + retrofit + "]");
                        if (response.isSuccess()) {
                            Log.d(TAG, "success status" + response.code());
                            attemptLogin(user);
                        } else {
                            Log.d(TAG, "!success status" + response.code());
                            try {
                                Log.d(TAG, response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, "Inscription failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: " + "throwable = [" + throwable + "]");
                        Toast.makeText(LoginActivity.this, "Connection failed, server is down", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /**
     * log user
     *
     * @param user the user
     */
    public void attemptLogin(User user) {
        mApi.authenticate(user).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "], retrofit = [" + retrofit + "]");
                        if (response.isSuccess()) {
                            Intent intent = new Intent(LoginActivity.this, BeerListActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "status" + response.code());
                            try {
                                Log.d(TAG, response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: " + "throwable = [" + throwable + "]");
                        Toast.makeText(LoginActivity.this, "Connection failed, server is down", Toast.LENGTH_SHORT).show();
                    }
                }
        );
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
        attemptLogin(user);
    }

    /**
     * hide sign up field
     */
    public void signInView() {
        mSignUp = false;
        mSignInButton.setVisibility(View.VISIBLE);
        mBackButton.setVisibility(View.GONE);
        mEmailFormView.setVisibility(View.GONE);
    }

    /**
     * show sign up field
     */
    public void signUpView() {
        mSignUp = true;
        mSignInButton.setVisibility(View.GONE);
        mBackButton.setVisibility(View.VISIBLE);
        mEmailFormView.setVisibility(View.VISIBLE);
    }
}

