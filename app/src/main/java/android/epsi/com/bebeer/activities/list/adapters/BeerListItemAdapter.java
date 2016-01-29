package android.epsi.com.bebeer.activities.list.adapters;

import android.app.Activity;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 19/01/16.
 * Adapter for the recycler view (list)
 * Handle pagination for beer lis
 */
public class BeerListItemAdapter extends RecyclerView.Adapter<BeerItemViewHolder> implements TextWatcher, View.OnClickListener {

    private static final String TAG = "BeerListItemAdapter";
    private final ApiClient mApiClient;
    private final int mCount;

    /**
     * Data set
     */
    private final List<Beer> mBeers;

    /**
     * Context
     */
    private final Activity mActivity;
    private final Handler mSearchHandler;
    private String mSearch;
    private Runnable mSearchRunnable;

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
        mSearchHandler = new Handler();

        // Starting from 0, loading 20 items each request
        setUpData(0, mCount, null, null);
    }

    /**
     * Fetch data, paginated
     *
     * @param offset
     * @param count
     * @param holder
     */
    private void setUpData(final int offset, int count, String search, final BeerItemViewHolder holder) {
        Log.i(TAG, "setUpData() called with: " + "offset = [" + offset + "], count = [" + count + "], search = [" + search + "], holder = [" + holder + "]");
        Call<List<Beer>> beers;
        if (search != null && !search.equals("")) {
            beers = mApiClient.getBeers(offset, count, search);
        } else {
            beers = mApiClient.getBeers(offset, count);
        }

        beers.enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Response<List<Beer>> response, Retrofit retrofit) {

                if (holder != null) {
                    holder.getLoader().setVisibility(View.GONE);
                }

                if (response.isSuccess()) {
                    if (!response.body().isEmpty()) {
                        mBeers.addAll(response.body());
                        BeerListItemAdapter.this.notifyDataSetChanged();
                    }

                    Log.i(TAG, "onResponse: received " + response.body() + ", beers size = " + mBeers.size());
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
        Log.i(TAG, "onBindViewHolder() called with: " + "holder = [" + holder + "], position = [" + position + "]");
        if (position == (mBeers.size() - 1)) {
            holder.getLoader().setVisibility(View.VISIBLE);
            setUpData(position + 1, mCount, mSearch, holder);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, int start, int before, int count) {
        Log.i(TAG, "onTextChanged() called with: " + "s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");

        if (mSearchRunnable != null) {
            mSearchHandler.removeCallbacks(mSearchRunnable);
        }

        mSearchRunnable = new Runnable() {

            @Override
            public void run() {
                mSearch = s.toString();
                mBeers.clear();
                notifyDataSetChanged();
                setUpData(0, 20, s.toString(), null);
                mSearchRunnable = null;

            }
        };
        mSearchHandler.postDelayed(mSearchRunnable, 350);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * Reset search query
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        mSearch = null;
        mBeers.clear();
        notifyDataSetChanged();
        setUpData(0, 20, null, null);
    }
}
