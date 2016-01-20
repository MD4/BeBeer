package android.epsi.com.bebeer.services;


import android.epsi.com.bebeer.activities.BeerListActivity;
import android.epsi.com.bebeer.bean.Beer;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by fx on 20/01/16.
 * ApiClient test
 * See http://developer.android.com/tools/testing-support-library/index.html#AndroidJUnitRunner
 */
@RunWith(AndroidJUnit4.class)
public class ApiClientTest extends ActivityInstrumentationTestCase2<BeerListActivity> {

    private BeerListActivity mActivity;
    private ApiClient mApiClient;

    public ApiClientTest() {
        super(BeerListActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // We need a context to create ApiClient
//        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @Before
    public void createClient() {
        mApiClient = new ApiClient(mActivity.getApplicationContext());
    }

    @Test
    public void testCreateApiClient() {
        assertThat(
                "client should be instantiated",
                mApiClient,
                not(nullValue())
        );
    }

    @Test
    public void testGetBeers() throws IOException {

        Response<List<Beer>> resp = mApiClient.getBeers().execute();

        assertThat(
                "request should have succeed",
                resp.isSuccess(),
                is(true)
        );

        assertThat(
                "beer list should not be empty",
                resp.body(),
                not(nullValue())
        );

        assertThat(
                "beer list count should be > 0",
                resp.body().size(),
                Matchers.greaterThan(0)
        );

    }
}