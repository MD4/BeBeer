package android.epsi.com.bebeer;

import android.content.Context;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import retrofit.Response;

/**
 * Created by fx on 10/02/16.
 */
public class TestUtils {

    private static final String TAG = "TestUtils";

    public static Response<User> getUserResponse(ApiClient mApiClient, String userName, String password) throws IOException {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        return mApiClient.authenticate(user).execute();
    }

    public static void logPrefs(Context context) {
        Set<String> stringSet = context.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE).getStringSet(AppConfig.COOKIES_KEY, new HashSet<String>());
        Log.d(TAG, "logPrefs: " + stringSet.toString());

    }
}
