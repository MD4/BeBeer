package android.epsi.com.bebeer.activities.brewery.list.adapters;

import android.app.Activity;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Brewery;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * View holder: views wrapper for a brewery item
 */
public class BreweryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "BreweryItemViewHolder";
    /**
     * brewery item view labels
     */
    private final TextView mName;
    private final TextView mCountry;

    /**
     * Remember the brewery we are displaying (usefull for event handling)
     */
    private Brewery mBrewery;

    private final Activity mActivity;
    private final ProgressBar mLoader;

    public BreweryItemViewHolder(View itemView, Activity activity) {
        super(itemView);
        mActivity = activity;
        /**
         * Look for card view's nested view
         */
        mName = (TextView) itemView.findViewById(R.id.brewery_list_item_name);
        mCountry = (TextView) itemView.findViewById(R.id.brewery_list_item_country);
        mLoader = (ProgressBar) itemView.findViewById(R.id.brewery_list_item_loader);

        // Register event
        itemView.setOnClickListener(this);
    }

    /**
     * On brewery item listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: " + "v = [" + v + "]");
    }

    public TextView getName() {
        return mName;
    }

    public TextView getCountry() {
        return mCountry;
    }

    public void setBeer(Brewery brewery) {
        this.mBrewery = brewery;
    }

    public ProgressBar getLoader() {
        return mLoader;
    }
}
