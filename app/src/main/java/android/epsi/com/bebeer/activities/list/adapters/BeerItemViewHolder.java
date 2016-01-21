package android.epsi.com.bebeer.activities.list.adapters;

import android.app.Activity;
import android.content.Intent;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.list.BeerListActivity;
import android.epsi.com.bebeer.activities.profile.BeerProfileActivity;
import android.epsi.com.bebeer.bean.Beer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * View holder: views wrapper for a beer card
 */
public class BeerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "BeerItemViewHolder";
    /**
     * Beer card view labels
     */
    private TextView mName;
    private TextView mBrewery;
    private TextView mCountry;

    /**
     * Remember the beer we are displaying (usefull for event handling)
     */
    private Beer mBeer;

    private Activity mActivity;
    private ProgressBar mLoader;

    public BeerItemViewHolder(View itemView, Activity activity) {
        super(itemView);
        mActivity = activity;
        /**
         * Look for card view's nested view
         */
        mName = (TextView) itemView.findViewById(R.id.beer_list_beer_card_name);
        mBrewery = (TextView) itemView.findViewById(R.id.beer_list_beer_card_brewery);
        mCountry = (TextView) itemView.findViewById(R.id.beer_list_beer_card_country);
        mLoader = (ProgressBar) itemView.findViewById(R.id.beer_profile_card_loader);

        // Register event
        itemView.setOnClickListener(this);
    }

    /**
     * On beer card listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: id = " + mBeer.getId());

        // Launch BeerProfile activity with "beer.id" as a parameter
        Intent intent = new Intent(mActivity, BeerProfileActivity.class);
        intent.putExtra(BeerListActivity.EXTRA_BEER_ID, mBeer.getId());
        mActivity.startActivity(intent);
    }

    public TextView getName() {
        return mName;
    }

    public TextView getBrewery() {
        return mBrewery;
    }

    public TextView getCountry() {
        return mCountry;
    }

    public void setBeer(Beer beer) {
        this.mBeer = beer;
    }

    public ProgressBar getLoader() {
        return mLoader;
    }
}
