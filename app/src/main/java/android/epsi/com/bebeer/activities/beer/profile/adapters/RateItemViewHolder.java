package android.epsi.com.bebeer.activities.beer.profile.adapters;

import android.content.Context;
import android.content.Intent;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.user.profile.UserProfileActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by fx on 18/02/16.
 */
public class RateItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView mUsernameTv;
    private final RatingBar mRateTv;
    private final TextView mDateTv;
    private final Context mActivity;

    public RateItemViewHolder(View itemView, Context activity) {
        super(itemView);
        mActivity = activity;
        mUsernameTv = (TextView) itemView.findViewById(R.id.beer_profile_comment_username);
        mRateTv = (RatingBar) itemView.findViewById(R.id.beer_profile_comment_rate);
        mDateTv = (TextView) itemView.findViewById(R.id.beer_profile_comment_date);
        itemView.setOnClickListener(this);
    }

    public TextView getUsernameTv() {
        return mUsernameTv;
    }

    public RatingBar getRateTv() {
        return mRateTv;
    }

    public TextView getDateTv() {
        return mDateTv;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_ID, mUsernameTv.getText().toString());
        mActivity.startActivity(intent);
    }
}
