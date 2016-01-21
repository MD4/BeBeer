package android.epsi.com.bebeer.activities.list.adapters;

import android.app.Activity;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.services.ApiClient;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 19/01/16.
 * Adapter for the recycler view (list)
 * Handle pagination for beer lis
 */
public class BeerListItemAdapter extends RecyclerView.Adapter<BeerItemViewHolder> {

    private final ApiClient mApiClient;
    private static final String TAG = "BeerListItemAdapter";

    private int mCount;

    /**
     * Data set
     */
    private List<Beer> mBeers;

    /**
     * Context
     */
    private Activity mActivity;

    /**
     * Simple constructor
     *
     * @param activity Context
     */
    public BeerListItemAdapter(Activity activity) {
        mApiClient = new ApiClient();
        mActivity = activity;
        mBeers = new ArrayList<>();
        mCount = 20;

        // Starting from 0, loading 20 items each request
        setUpData(0, mCount, null);
    }

    /**
     * Fetch data, paginated
     *
     * @param offset
     * @param count
     * @param holder
     */
    private void setUpData(final int offset, int count, final BeerItemViewHolder holder) {
        Log.i(TAG, "setUpData() called with: " + "offset = [" + offset + "], count = [" + count + "]");
        mApiClient.getBeers(offset, count)
                .enqueue(new Callback<List<Beer>>() {
                    @Override
                    public void onResponse(Response<List<Beer>> response, Retrofit retrofit) {

                        if (holder != null) {
                            holder.getLoader().setVisibility(View.GONE);
                        }

                        if (response.isSuccess()) {
                            mBeers.addAll(response.body());
                            Log.i(TAG, "onResponse: beers size = " + mBeers.size());
                            BeerListItemAdapter.this.notifyDataSetChanged();
                        } else {
                            Log.e(TAG, String.format("onResponse: error while fetching beers: code = %d", response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure: can't fetch beers :(", t);
                        if (holder != null) {
                            holder.getLoader().setVisibility(View.GONE);
                        }
                    }

                });
    }


    /**
     * Inflate a view component if necesary
     *
     * @param parent   Parent view
     * @param viewType Not used for the moment (see RecyclerView doc)
     * @return View holder
     */
    @Override
    public BeerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.beer_list_beer_card, parent, false);
        return new BeerItemViewHolder(view, mActivity);
    }

    /**
     * Map a beer to an existing view holder
     *
     * @param holder   Existing view holder
     * @param position Item's position
     */
    @Override
    public void onBindViewHolder(BeerItemViewHolder holder, int position) {
        if (position == (mBeers.size() - 1)) {
            holder.getLoader().setVisibility(View.VISIBLE);
            setUpData(position + 1, mCount, holder);
        }
        Beer beer = this.mBeers.get(position);
        holder.setBeer(beer);

        holder.getName().setText(beer.getName());
        holder.getBrewery().setText(beer.getBrewery());
        holder.getCountry().setText(beer.getCountry());
    }

    @Override
    public int getItemCount() {
        return mBeers.size();
    }

}
