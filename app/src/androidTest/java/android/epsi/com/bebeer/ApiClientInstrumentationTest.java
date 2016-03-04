package android.epsi.com.bebeer;

import android.app.Instrumentation;
import android.content.Context;
import android.epsi.com.bebeer.activities.user.LoginActivity;
import android.epsi.com.bebeer.bean.Beer;
import android.epsi.com.bebeer.bean.Brewery;
import android.epsi.com.bebeer.bean.User;
import android.epsi.com.bebeer.services.remote.ApiClient;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import retrofit.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
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
    private static final String TAG = "ClientInstrumentation";
    private ApiClient mApiClient;
    private Context mContext;

    public ApiClientInstrumentationTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // Injecting the Instrumentation instance is required
        // for your test to run with AndroidJUnitRunner.
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        injectInstrumentation(instrumentation);
        mContext = instrumentation.getTargetContext();
        mContext.getSharedPreferences(AppConfig.PREFS_SCOPE, Context.MODE_PRIVATE).edit().clear().apply();
        mApiClient = new ApiClient(mContext);
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
    public void testAuth() throws IOException {

        TestUtils.logPrefs(mContext);
        Response<User> resp = TestUtils.getUserResponse(mApiClient, "test", "testtest");
        TestUtils.logPrefs(mContext);

        assertThat(
                "request should have succeed",
                resp.isSuccess(),
                is(true)
        );

        assertThat(
                "user 'test' should have been authenticated",
                resp.body().getUsername(),
                equalTo("test")
        );

        Response<User> execute = mApiClient.isAuthenticated().execute();
        assertThat(
                "isAuth is true",
                execute.isSuccess(),
                is(true)
        );
    }

    @Test
    public void testLogout() throws IOException {
        TestUtils.getUserResponse(mApiClient, "test", "testtest");

        assertThat(
                "user is authenticated",
                mApiClient.isAuthenticated().execute().isSuccess(),
                is(true)
        );

        Response<Void> execute = mApiClient.logout().execute();

        assertThat(
                "user should be logged out",
                execute.isSuccess(),
                is(true)
        );

        assertThat(
                "user is no longer authenticated",
                mApiClient.isAuthenticated().execute().isSuccess(),
                is(false)
        );
    }

    @Test
    public void testFailedAuth() throws IOException {
        Response<User> resp = TestUtils.getUserResponse(mApiClient, "wegsrh", "tykjyt");

        assertThat(
                "request should NOT have succeed",
                resp.isSuccess(),
                is(false)
        );

        assertThat(
                "user 'tykjyt' should NOT have been authenticated",
                resp.code(),
                equalTo(401)
        );
    }

    @Test
    public void testGetUser() throws IOException {
        Response<User> userResponse = TestUtils.getUserResponse(mApiClient, "FXHibon", "lolilol");

        User user = null;
        assertThat(
                "user not defined",
                user,
                is(nullValue())
        );

        user = userResponse.body();
        Log.i(TAG, "testGetUser: " + user.toString());
        assertThat(
                "user is defined",
                user,
                not(nullValue())
        );


        assertThat(
                "user got a mail address",
                user.getEmail(),
                equalTo("djxf44@gmail.com")
        );

        assertThat(
                "user got ratings",
                user.getRatings().size(),
                greaterThan(0)
        );
    }


    @Test
    public void testGetBeers() throws IOException {
        TestUtils.getUserResponse(mApiClient, "test", "testtest");

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
        TestUtils.getUserResponse(mApiClient, "test", "testtest");
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
        TestUtils.getUserResponse(mApiClient, "test", "testtest");
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
        TestUtils.getUserResponse(mApiClient, "test", "testtest");
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
        TestUtils.getUserResponse(mApiClient, "test", "testtest");
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
        TestUtils.getUserResponse(mApiClient, "test", "testtest");
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