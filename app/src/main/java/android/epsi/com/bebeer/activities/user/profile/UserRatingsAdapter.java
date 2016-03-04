package android.epsi.com.bebeer.activities.user.profile;

import android.content.Context;
import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.bean.Rating;
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

    public UserRatingsAdapter(Context context, int resource, List<Rating> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.user_profile_rating_item, null);
        }

        Rating item = getItem(position);

        TextView beerId = (TextView) convertView.findViewById(R.id.user_profile_rating_item_beer_id);
        TextView date = (TextView) convertView.findViewById(R.id.user_profile_rating_item_date);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.user_profile_rating_item_rate);

        beerId.setText(item.getBeerId());
        date.setText(DateUtils.getRelativeTimeSpanString(item.getDate().getMillis()));
        ratingBar.setRating(item.getRate());

        return convertView;
    }
}
