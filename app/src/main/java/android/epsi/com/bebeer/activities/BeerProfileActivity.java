package android.epsi.com.bebeer.activities;

import android.epsi.com.bebeer.R;
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


        TextView commentTv = (TextView) findViewById(R.id.content_beer_comment);
        setSupportActionBar(toolbar);
    }


}
