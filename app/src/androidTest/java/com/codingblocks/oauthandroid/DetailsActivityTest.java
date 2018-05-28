package com.codingblocks.oauthandroid;

import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;


public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<DetailsActivity> detailsActivityActivityTestRule =
            new ActivityTestRule<>(DetailsActivity.class);

    private DetailsActivity detailActivity = null;
    SharedPreferences sharedPreferences;

    @Before
    public void setUp() {

        detailActivity = detailsActivityActivityTestRule.getActivity();
    }

    @Test
    public void beforelogoutwithcorrectaccess_token() {
        sharedPreferences = detailActivity.getSharedPreferences(MainActivity.SHARED_PREFS_NAME, MODE_PRIVATE);
        final String access_token = sharedPreferences.getString(MainActivity.SP_ACCESS_TOKEN_KEY, "");

        String url = "https://account.codingblocks.com/api/users/";
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<User> call = userApi.getMe("Bearer " + access_token);
        try {
            Response<User> response = call.execute();
            assertTrue(response.isSuccessful());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void beforelogoutwithincorrectaccess_token() {
        sharedPreferences = detailActivity.getSharedPreferences(MainActivity.SHARED_PREFS_NAME, MODE_PRIVATE);
        String access_token = sharedPreferences.getString(MainActivity.SP_ACCESS_TOKEN_KEY, "");

        String url = "https://account.codingblocks.com/api/users/";
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        UserApi userApi = retrofit.create(UserApi.class);
        access_token = access_token + "abc";
        Call<User> call = userApi.getMe("Bearer " + access_token);
        try {
            Response<User> response = call.execute();
            assertTrue(!response.isSuccessful());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logoutworks() {
        onView(withId(R.id.btnLogout)).check(matches(withText("Logout"))).perform(click());

    }

    @After
    public void tearDown() {

        detailActivity = null;
    }

}