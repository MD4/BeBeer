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

import java.io.IOException;
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

    private int mOffset;
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
        mOffset = 0;
        mCount = 20;

        setUpData(mOffset, mCount);
    }

    /**
     * Fetch data
     *
     * @param offset
     * @param count
     */
    private void setUpData(int offset, int count) {
        Log.i(TAG, "setUpData() called with: " + "offset = [" + offset + "], count = [" + count + "]");
        mApiClient.getBeers(offset, count)
                .enqueue(new Callback<List<Beer>>() {
                    @Override
                    public void onResponse(Response<List<Beer>> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            mBeers.addAll(response.body());
                            BeerListItemAdapter.this.notifyDataSetChanged();
                        } else {
                            try {
                                Log.e(TAG, String.format("onResponse: error while fetching beers: %d, %s", response.code(), response.errorBody().string()));
                            } catch (IOException e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure: can't fetch beers :(", t);
                    }
                });
    }


    /**
     * Inflate a view component if necesary
     * @param parent Parent view
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
     * @param holder Existing view holder
     * @param position Item's position
     */
    @Override
    public void onBindViewHolder(BeerItemViewHolder holder, int position) {
        if (position == (mBeers.size() - 1)) {
            setUpData(position - 1, mCount);
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
