package android.epsi.com.bebeer;

import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.remote.ApiClient;

import java.io.IOException;

import retrofit.Response;

/**
 * Created by fx on 10/02/16.
 */
public class TestUtils {

    public static Response<User> getUserResponse(ApiClient mApiClient, String userName, String password) throws IOException {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        return mApiClient.authenticate(user).execute();
    }
}
