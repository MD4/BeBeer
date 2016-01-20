package android.epsi.com.bebeer.adapters;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Beer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fx on 19/01/16.
 * Adapter for the recycler view (list)
 */
public class BeerListItemAdapter extends RecyclerView.Adapter<BeerListItemAdapter.BeerItemViewHolder> {

    /**
     * Data set
     */
    private List<Beer> mBeers;

    /**
     * Simple constructor
     *
     * @param beers
     */
    public BeerListItemAdapter(List<Beer> beers) {
        mBeers = beers;
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
        return new BeerItemViewHolder(view);
    }

    /**
     * Map a beer to an existing view holder
     * @param holder Existing view holder
     * @param position Item's position
     */
    @Override
    public void onBindViewHolder(BeerItemViewHolder holder, int position) {
        Beer beer = this.mBeers.get(position);
        holder.nameTv.setText(beer.getName());
        holder.breweryTv.setText(beer.getBrewery());
        holder.countryTv.setText(beer.getCountry());
    }

    @Override
    public int getItemCount() {
        return mBeers.size();
    }

    /**
     * View holder: views wrapper for a beer card
     */
    public class BeerItemViewHolder extends RecyclerView.ViewHolder {

        /**
         * Beer card view labels
         */
        private TextView nameTv;
        private TextView breweryTv;
        private TextView countryTv;

        public BeerItemViewHolder(View itemView) {
            super(itemView);
            /**
             * Look for card view's nested view
             */
            nameTv = (TextView) itemView.findViewById(R.id.beer_list_beer_card_name);
            breweryTv = (TextView) itemView.findViewById(R.id.beer_list_beer_card_brewery);
            countryTv = (TextView) itemView.findViewById(R.id.beer_list_beer_card_country);
        }
    }
}
