package android.epsi.com.bebeer.services;

import android.content.Context;
import android.content.Intent;
import android.epsi.com.bebeer.AppConfig;
import android.epsi.com.bebeer.activities.beer.list.BeerListActivity;
import android.epsi.com.bebeer.activities.brewery.list.BreweryListActivity;
import android.epsi.com.bebeer.activities.user.login.LoginActivity;
import android.epsi.com.bebeer.activities.user.profile.UserProfileActivity;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 04/03/16.
 * Handle menu change
 */
public class OnItemSelectedMenuListener implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "OnItemSelectedMenu";

    final private Context mContext;
    private String mCurrentState;

    private static OnItemSelectedMenuListener mInstance;

    /**
     * @param context Context
     * @param from    State calling this listener
     */
    public OnItemSelectedMenuListener(Context context, String from) {
        mContext = context;
        mCurrentState = from;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (view == null) return;
        TextView tv = (TextView) view;
        Intent intent = null;

        if (tv.getText().equals("Beers") && !mCurrentState.equals(tv.getText())) {
            intent = new Intent(mContext, BeerListActivity.class);
            mCurrentState = "Beers";

        } else if (tv.getText().equals("Breweries") && !mCurrentState.equals(tv.getText())) {
            intent = new Intent(mContext, BreweryListActivity.class);
            mCurrentState = "Breweries";

        } else if (tv.getText().equals("Profile") && !mCurrentState.equals(tv.getText())) {
            intent = new Intent(mContext, UserProfileActivity.class);
            mCurrentState = "Profile";

        } else if (tv.getText().equals("Disconnect") && !mCurrentState.equals(tv.getText())) {
            new ApiClient(mContext).logout().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    mCurrentState = "Disconnect";
                    mContext.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE).edit().clear().apply();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }

        if (intent != null) {
            mContext.startActivity(intent);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setCurrent(String current) {
        this.mCurrentState = current;
    }
}
