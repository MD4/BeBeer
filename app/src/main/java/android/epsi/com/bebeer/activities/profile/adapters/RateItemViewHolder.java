package android.epsi.com.bebeer.activities.profile.adapters;

import android.epsi.com.bebeer.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fx on 18/02/16.
 */
public class RateItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mUsernameTv;
    private final TextView mRateTv;
    private final TextView mDateTv;

    public RateItemViewHolder(View itemView) {
        super(itemView);

        mUsernameTv = (TextView) itemView.findViewById(R.id.beer_profile_comment_username);
        mRateTv = (TextView) itemView.findViewById(R.id.beer_profile_comment_rate);
        mDateTv = (TextView) itemView.findViewById(R.id.beer_profile_comment_date);
    }

    public TextView getUsernameTv() {
        return mUsernameTv;
    }

    public TextView getRateTv() {
        return mRateTv;
    }

    public TextView getDateTv() {
        return mDateTv;
    }
}
