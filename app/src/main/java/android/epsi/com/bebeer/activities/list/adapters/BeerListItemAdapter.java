package android.epsi.com.bebeer.activities.list.adapters;

import android.app.Activity;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Beer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by fx on 19/01/16.
 * Adapter for the recycler view (list)
 */
public class BeerListItemAdapter extends RecyclerView.Adapter<BeerItemViewHolder> {

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
     * @param beers Data
     * @param activity Context
     */
    public BeerListItemAdapter(List<Beer> beers, Activity activity) {
        mBeers = beers;
        mActivity = activity;
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
