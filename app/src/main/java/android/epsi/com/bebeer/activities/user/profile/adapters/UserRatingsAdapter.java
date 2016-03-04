package android.epsi.com.bebeer.activities.user.profile.adapters;

import android.content.Context;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Rating;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fx on 04/03/16.
 */
public class UserRatingsAdapter extends ArrayAdapter<Rating> {

    private final ApiClient mApiClient;

    public UserRatingsAdapter(Context context, int resource, List<Rating> objects) {
        super(context, resource, objects);
        mApiClient = new ApiClient(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.user_profile_rating_item, null);
        }
        Rating item = getItem(position);
        final TextView beerId = (TextView) convertView.findViewById(R.id.user_profile_rating_item_beer_id);
        final TextView date = (TextView) convertView.findViewById(R.id.user_profile_rating_item_date);
        final RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.user_profile_rating_item_rate);

        // TODO it create problems
//        mApiClient.getBeer(item.getBeerId()).enqueue(new Callback<Beer>() {
//            @Override
//            public void onResponse(Response<Beer> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    beerId.setText(response.body().getName());
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });

        beerId.setText(item.getBeerId());
        date.setText(DateUtils.getRelativeTimeSpanString(item.getDate().getMillis()));
        ratingBar.setRating(item.getRate());

        return convertView;
    }
}
