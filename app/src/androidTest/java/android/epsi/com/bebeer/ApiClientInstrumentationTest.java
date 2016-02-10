package android.epsi.com.bebeer;

import android.epsi.com.bebeer.activities.account.LoginActivity;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Brewery;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by fx on 20/01/16.
 */
@RunWith(AndroidJUnit4.class)
public class ApiClientInstrumentationTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private ApiClient mApiClient;

    public ApiClientInstrumentationTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    @Before
    public void createClient() {
        mApiClient = new ApiClient(InstrumentationRegistry.getInstrumentation().getContext());
    }

    @Test
    public void testCreateApiClient() {
        assertThat(
                "client should have been instantiated",
                mApiClient,
                not(nullValue())
        );
    }

    @Test
    public void testGetBeers() throws IOException {

        Response<List<Beer>> resp = mApiClient.getBeers(0, 20).execute();
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
                is(20)
        );
    }

    @Test
    public void testGetBeersCounted() throws IOException {

        Response<List<Beer>> resp = mApiClient.getBeers(0, 30).execute();
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
                is(30)
        );
    }

    @Test
    public void testGetBeerWithSearch() throws IOException {
        String testId = "1509";
        Response<Beer> resp = mApiClient.getBeer(testId).execute();

        assertThat(
                "request should have succeed",
                resp.isSuccess(),
                is(true)
        );

        assertThat(
                "beer should not be empty",
                resp.body(),
                not(nullValue())
        );

        assertThat(
                "beer retrieved has the right id",
                resp.body().getId(),
                equalTo(testId)
        );
    }

    @Test
    public void testGetBreweries() throws IOException {
        Response<List<Brewery>> response = mApiClient.getBreweries(0, 20).execute();

        assertThat(
                "Request should have succeed",
                response.isSuccess(),
                is(true)
        );

        assertThat(
                "Body is not null",
                response.body(),
                not(nullValue())
        );

        assertThat(
                "There is 20 breweries",
                response.body().size(),
                is(20)
        );
    }

    @Test
    public void testGetBreweriesCounted() throws IOException {
        Response<List<Brewery>> response = mApiClient.getBreweries(0, 30).execute();

        assertThat(
                "Request should have succeed",
                response.isSuccess(),
                is(true)
        );

        assertThat(
                "Body is not null",
                response.body(),
                not(nullValue())
        );

        assertThat(
                "There is 30 breweries",
                response.body().size(),
                is(30)
        );
    }

    @Test
    public void testGetBreweriesSearch() throws IOException {
        String query = "Bracki";
        Response<List<Brewery>> response = mApiClient.getBreweries(0, 20, query).execute();

        assertThat(
                "Request should have succeed",
                response.isSuccess(),
                is(true)
        );

        assertThat(
                "Body is not null",
                response.body(),
                not(nullValue())
        );

        assertThat(
                "There is 1 matching result",
                response.body().size(),
                is(1)
        );

        assertThat(
                "Retrieved brewery has the correct name",
                response.body().get(0).getName(),
                startsWith(query)
        );
    }
}