package android.epsi.com.bebeer.services;

import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Brewery;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by fx on 20/01/16.
 */
@SmallTest
public class ApiClientTest {

    private ApiClient mApiClient;

    public ApiClientTest() {
    }

    @Before
    public void createClient() {
        mApiClient = new ApiClient(null);
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
        int testId = 1509;
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
                is(testId)
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