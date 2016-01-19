package android.epsi.com.bebeer.adapters;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.beans.Beer;
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

    private List<Beer> mBeers;

    public BeerListItemAdapter(List<Beer> beers) {
        mBeers = beers;
    }

    @Override
    public BeerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.beer_list_beer_card, parent, false);
        return new BeerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeerItemViewHolder holder, int position) {
        TextView textView = (TextView) holder.mItemView.findViewById(R.id.beer_list_beer_card_label);
        textView.setText(mBeers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mBeers.size();
    }

    public class BeerItemViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;

        public BeerItemViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }
    }
}
