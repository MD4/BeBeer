package android.epsi.com.bebeer.services;

import android.epsi.com.bebeer.bean.Beer;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
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
        mApiClient = new ApiClient();
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
                is(20)
        );
    }

    @Test
    public void testGetBeer() throws IOException {
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
                "beer retrieved has the good id",
                resp.body().getId(),
                is(testId)
        );
    }
}