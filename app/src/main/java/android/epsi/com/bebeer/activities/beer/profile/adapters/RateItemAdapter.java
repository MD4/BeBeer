package android.epsi.com.bebeer.activities.beer.profile.adapters;

import android.content.Context;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Last;
import android.epsi.com.bebeer.bean.Ratings;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fx on 18/02/16.
 */
public class RateItemAdapter extends RecyclerView.Adapter<RateItemViewHolder> {

    private static final String TAG = "BeerListItemAdapter";
    private final ApiClient mApiClient;

    /**
     * Data set
     */
    private final List<Last> mRates;

    /**
     * Context
     */
    private String mBeerId;

    /**
     * Simple constructor
     */
    public RateItemAdapter(Context context, Beer beer) {
        mBeerId = beer.getId();
        mApiClient = new ApiClient(context);
        mRates = new ArrayList<>();
        Ratings ratings = beer.getRatings();
        if (ratings != null) {
            mRates.addAll(ratings.getLast());
        }

        Collections.sort(mRates, new Comparator<Last>() {
            @Override
            public int compare(Last lhs, Last rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });

        setUpData();
    }

    /**
     * Fetch data, paginated
     *
     */
    private void setUpData() {
        Log.i(TAG, "setUpData()");
        Call<List<Last>> rates;

        rates = mApiClient.getRates(mBeerId);

        rates.enqueue(new Callback<List<Last>>() {
            @Override
            public void onResponse(Response<List<Last>> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    List<Last> body = response.body();
                    if (!body.isEmpty()) {
                        mRates.addAll(body.subList(mRates.size() - 1, body.size() - 1));
                        RateItemAdapter.this.notifyDataSetChanged();
                    }

                    Log.i(TAG, "onResponse: received " + body + ", rates size = " + mRates.size());
                } else {
                    Log.e(TAG, String.format("onResponse: error while fetching rates: code = %d", response.code()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onFailure: can't fetch rates :", t);
            }

        });
    }


    /**
     * Inflate a view component if necessary
     *
     * @param parent   Parent view
     * @param viewType Not used for the moment (see RecyclerView doc)
     * @return View holder
     */
    @Override
    public RateItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.beer_profile_comments_item, parent, false);
        return new RateItemViewHolder(view);
    }

    /**
     * Map a rate to an existing view holder
     *
     * @param holder   Existing view holder
     * @param position Item's position
     */
    @Override
    public void onBindViewHolder(RateItemViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder() called with: " + "holder = [" + holder + "], position = [" + position + "]");

        Last rate = this.mRates.get(position);
        // TODO bind view holder
        holder.getUsernameTv().setText(rate.getUsername());
        holder.getRateTv().setRating(rate.getRate());
        holder.getDateTv().setText(rate.getDate());

        DateTime dateTime = new DateTime(rate.getDate());
        holder.getDateTv().setText(DateUtils.getRelativeTimeSpanString(dateTime.getMillis()).toString());
    }

    @Override
    public int getItemCount() {
        return mRates.size();
    }
}
