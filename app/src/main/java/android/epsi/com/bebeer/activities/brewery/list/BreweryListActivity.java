package android.epsi.com.bebeer.activities.brewery.list;

import android.epsi.com.bebeer.R;
import android.epsi.com.bebeer.activities.brewery.list.adapters.BreweryListItemAdapter;
import android.epsi.com.bebeer.services.OnItemSelectedMenuListener;
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

public class BreweryListActivity extends AppCompatActivity {

    private static final String TAG = "breweryListActivity";
    private OnItemSelectedMenuListener mMenuListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.brewery_list_toolbar);
        setUpToolbar(toolbar);

        Spinner menu = (Spinner) findViewById(R.id.list_menu_spinner);
        setUpSpinnerMenu(menu);

        BreweryListItemAdapter breweryListItemAdapter = new BreweryListItemAdapter(this);

        EditText search = (EditText) findViewById(R.id.brewery_list_search_view);
        ImageView searchBtn = (ImageView) findViewById(R.id.brewery_list_search_clear);
        setUpSearchView(search, breweryListItemAdapter, searchBtn);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.brewery_list_recycler);
        setUpRecyclerView(recyclerView, breweryListItemAdapter);

    }

    /**
     * Set up menu spinner
     *
     * @param menu
     */
    private void setUpSpinnerMenu(Spinner menu) {
        menu.setSelection(1);
        mMenuListener = new OnItemSelectedMenuListener(this, "Breweries");
        menu.setOnItemSelectedListener(mMenuListener);
    }

    /**
     * Configure search field
     *
     * @param search
     * @param breweryListItemAdapter
     * @param searchBtn
     */
    private void setUpSearchView(final EditText search, final BreweryListItemAdapter breweryListItemAdapter, final ImageView searchBtn) {
        search.addTextChangedListener(breweryListItemAdapter);
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
     *
     * @param recyclerView
     * @param breweryListItemAdapter
     */
    private void setUpRecyclerView(final RecyclerView recyclerView, BreweryListItemAdapter breweryListItemAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(breweryListItemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Spinner menu = (Spinner) findViewById(R.id.list_menu_spinner);
        menu.setSelection(1);
    }

}
