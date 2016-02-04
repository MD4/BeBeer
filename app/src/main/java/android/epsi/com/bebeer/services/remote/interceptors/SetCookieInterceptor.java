package android.epsi.com.bebeer.services.remote.interceptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.epsi.com.bebeer.AppConfig;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fx on 04/02/16.
 */
public class SetCookieInterceptor implements Interceptor {

    private static final String TAG = "SetCookieInterceptor";

    public static final String HEADER = "Set-Cookie";

    private Context mContext;

    public SetCookieInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers(HEADER).isEmpty()) {
            Set<String> cookies = new HashSet<>();
            for (String cookie : originalResponse.headers(HEADER)) {
                cookies.add(cookie);
                Log.v(TAG, String.format("Setting cookie: %s", cookie));
            }
            SharedPreferences.Editor simpleCalendar = mContext.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE).edit();
            simpleCalendar.putStringSet(AppConfig.COOKIES_KEY, cookies).apply();
        }

        return originalResponse;
    }

}
