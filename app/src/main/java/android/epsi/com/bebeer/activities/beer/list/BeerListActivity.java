package android.epsi.com.bebeer.activities.beer.list;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.beer.list.adapters.BeerListItemAdapter;
import android.epsi.com.bebeer.services.OnItemSelectedMenuListener;
import android.epsi.com.bebeer.services.image.ApiImageAccessor;
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
import android.widget.Spinner;

/**
 * Show list of beers, quick search
 */
public class BeerListActivity extends AppCompatActivity {

    private static final String TAG = "BeerListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);

        ApiImageAccessor.createInstance(this);

        Spinner spinnerMenu = (Spinner) findViewById(R.id.list_menu_spinner);
        setUpMenu(spinnerMenu);

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
     * Set up spinner menu
     *
     * @param menu
     */
    private void setUpMenu(Spinner menu) {
        menu.setOnItemSelectedListener(new OnItemSelectedMenuListener(this, "Beers"));
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
                } else {
                    search.requestFocus();
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

    @Override
    protected void onResume() {
        super.onResume();
        Spinner menu = (Spinner) findViewById(R.id.list_menu_spinner);
        menu.setSelection(0);
    }
}
