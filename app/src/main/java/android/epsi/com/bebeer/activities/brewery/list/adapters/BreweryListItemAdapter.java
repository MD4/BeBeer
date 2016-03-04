package android.epsi.com.bebeer.activities.brewery.list.adapters;

import android.app.Activity;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Brewery;
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


public class BreweryListItemAdapter extends RecyclerView.Adapter<BreweryItemViewHolder> implements TextWatcher, View.OnClickListener {

    private static final String TAG = "BeerListItemAdapter";
    private final ApiClient mApiClient;
    private final int mCount;

    /**
     * Data set
     */
    private final List<Brewery> mBreweries;

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
    public BreweryListItemAdapter(Activity activity) {
        mApiClient = new ApiClient(activity.getApplicationContext());
        mActivity = activity;
        mBreweries = new ArrayList<>();
        mCount = 20;
        mSearchHandler = new Handler();

        // Starting from 0, loading 20 items each0 request
        setUpData(0, mCount);
    }

    /**
     * Fetch data, paginated
     *
     * @param offset
     * @param count
     * @param holder
     */
    private void setUpData(final int offset, int count, String search, final BreweryItemViewHolder holder) {
        Call<List<Brewery>> breweries;
        if (search != null && !search.equals("")) {
            breweries = mApiClient.getBreweries(offset, count, search);
        } else {
            breweries = mApiClient.getBreweries(offset, count);
        }

        breweries.enqueue(new Callback<List<Brewery>>() {
            @Override
            public void onResponse(Response<List<Brewery>> response, Retrofit retrofit) {

                if (holder != null) {
                    holder.getLoader().setVisibility(View.GONE);
                }

                if (response.isSuccess()) {
                    if (!response.body().isEmpty()) {
                        mBreweries.addAll(response.body());
                        BreweryListItemAdapter.this.notifyDataSetChanged();
                    }

                    Log.i(TAG, "onResponse: received " + response.body() + ", breweries size = " + mBreweries.size());
                } else {
                    Log.e(TAG, String.format("onResponse: error while fetching breweries: code = %d", response.code()));
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
     * overload with default values
     *
     * @param offset
     * @param count
     */
    private void setUpData(final int offset, final int count) {
        setUpData(0, mCount, null, null);
        ;
    }


    /**
     * Inflate a view component if necesary
     *
     * @param parent   Parent view
     * @param viewType Not used for the moment (see RecyclerView doc)
     * @return View holder
     */
    @Override
    public BreweryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.brewery_list_item, parent, false);
        return new BreweryItemViewHolder(view, mActivity);
    }

    /**
     * Map a beer to an existing view holder
     *
     * @param holder   Existing view holder
     * @param position Item's position
     */
    @Override
    public void onBindViewHolder(BreweryItemViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder() called with: " + "holder = [" + holder + "], position = [" + position + "]");
        if (position == (mBreweries.size() - 1)) {
            holder.getLoader().setVisibility(View.VISIBLE);
            setUpData(position + 1, mCount, mSearch, holder);
        }
        Brewery brewery = this.mBreweries.get(position);
        holder.setBeer(brewery);

        holder.getName().setText(brewery.getName());
        holder.getCountry().setText(brewery.getCountry());
    }

    @Override
    public int getItemCount() {
        return mBreweries.size();
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
                mBreweries.clear();
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
        mBreweries.clear();
        notifyDataSetChanged();
        setUpData(0, 20, null, null);
    }

    @Override
    public void onViewRecycled(BreweryItemViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
