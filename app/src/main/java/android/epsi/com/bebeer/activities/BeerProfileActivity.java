package android.epsi.com.bebeer.activities;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.beans.Beer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class BeerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        Beer beer = new Beer();

        beer.setName("Krönembourg");
        beer.setComment("This beer might not be the best beer ever, although it does the taff!");

        toolbar.setTitle(beer.getName());
        TextView commentTv = (TextView) findViewById(R.id.content_beer_comment);
        setSupportActionBar(toolbar);
        commentTv.setText(beer.getComment());
    }


}
