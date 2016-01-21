package android.epsi.com.bebeer.activities.list;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.list.adapters.BeerListItemAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Show list of beers, quick search
 * TODO Add search field, and improve design
 */
public class BeerListActivity extends AppCompatActivity {

    private static final String TAG = "BeerListActivity";

    /**
     * Use for passing param to another activity
     */
    public static final String EXTRA_BEER_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.beer_list_toolbar);
        setUpToolbar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.beer_list_recycler);
        setUpRecyclerView(recyclerView);

    }

    /**
     * Init toolbar related things
     *
     * @param toolbar
     */
    private void setUpToolbar(Toolbar toolbar) {
        toolbar.setTitle(getResources().getString(R.string.beer_list_title));
        setSupportActionBar(toolbar);
    }

    /**
     * Init RecyclerView (~= ListView) related things
     * @param recyclerView
     */
    private void setUpRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new BeerListItemAdapter(this));
    }

}
