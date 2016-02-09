package android.epsi.com.bebeer.services.remote.interceptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.epsi.com.bebeer.AppConfig;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by fx on 29/10/2015.
 * Add cookie for each request
 */
public class GetCookieInterceptor implements Interceptor {

    private static final String TAG = "AddCookieInterceptor";

    public static final String HEADER = "Cookie";

    private Context mContext;

    public GetCookieInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        SharedPreferences prefs = mContext.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE);
        Set<String> preferences = prefs.getStringSet(AppConfig.COOKIES_KEY, new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader(HEADER, cookie);
            Log.v(TAG, String.format("Get cookie %s", cookie));
        }
        return chain.proceed(builder.build());
    }
}
