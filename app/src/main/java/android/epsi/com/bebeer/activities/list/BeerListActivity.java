package android.epsi.com.bebeer.activities.list;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.list.adapters.BeerListItemAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

        BeerListItemAdapter beerListItemAdapter = new BeerListItemAdapter(this);

        EditText search = (EditText) findViewById(R.id.beer_list_search_view);
        ImageView searchBtn = (ImageView) findViewById(R.id.beer_list_search_clear);
        setUpSearchView(search, beerListItemAdapter, searchBtn);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.beer_list_recycler);
        setUpRecyclerView(recyclerView, beerListItemAdapter);

    }

    /**
     * Configure search field
     *  @param search
     * @param beerListItemAdapter
     * @param searchBtn
     */
    private void setUpSearchView(final EditText search, final BeerListItemAdapter beerListItemAdapter, final ImageView searchBtn) {
        search.addTextChangedListener(beerListItemAdapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search.getText().length() > 0) {
                    search.setText("");
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchBtn.setImageResource(R.drawable.ic_clear_white_24dp);
                } else {
                    searchBtn.setImageResource(R.drawable.ic_search_white_24dp);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
     * @param beerListItemAdapter
     */
    private void setUpRecyclerView(final RecyclerView recyclerView, BeerListItemAdapter beerListItemAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(beerListItemAdapter);
    }
}
