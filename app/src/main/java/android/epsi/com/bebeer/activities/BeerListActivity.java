package android.epsi.com.bebeer.activities;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.adapters.BeerListItemAdapter;
import android.epsi.com.bebeer.bean.Beer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class BeerListActivity extends AppCompatActivity {

    private static final String TAG = "BeerListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.beer_list_toolbar);
        setUpToolbar(toolbar);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.beer_list_recycler);
        setUpRecyclerView(mRecyclerView);
    }

    private void setUpToolbar(Toolbar toolbar) {
        toolbar.setTitle(getResources().getString(R.string.beer_list_title));
        setSupportActionBar(toolbar);
    }

    private void setUpRecyclerView(RecyclerView mRecyclerView) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.Adapter adapter;

        adapter = new BeerListItemAdapter(new ArrayList<Beer>());
        mRecyclerView.setAdapter(adapter);
    }

}
