package com.codingblocks.oauthandroid;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS_NAME, MODE_PRIVATE);
        final String access_token = sharedPreferences.getString(MainActivity.SP_ACCESS_TOKEN_KEY, "");


        String url = "https://account.codingblocks.com/api/users/";
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        UserApi userApi = retrofit.create(UserApi.class);
        userApi.getMe("Bearer " + access_token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: " + response.body());
                setViews(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    private void setViews(User user) {
        TextView tvId = (TextView) findViewById(R.id.tvId);
        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        TextView tvFirstname = (TextView) findViewById(R.id.tvFirstname);
        TextView tvLastName = (TextView) findViewById(R.id.tvLastName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvCreatedAt = (TextView) findViewById(R.id.tvCreatedAt);
        TextView tvUpdatedAt = (TextView) findViewById(R.id.tvUpdatedAt);
        ImageView ivPhoto = (ImageView) findViewById(R.id.ivImageView);

        Log.d(TAG, "setViews: " + tvId);
        tvId.setText(String.valueOf(user.getId()));
        tvUsername.setText(user.getUsername());
        tvFirstname.setText(user.getFirstname());
        tvLastName.setText(user.getLastname());
        tvEmail.setText(user.getEmail());
        tvCreatedAt.setText(user.getCreatedAt());
        tvUpdatedAt.setText(user.getUpdatedAt());
        Picasso.with(this).load(user.getPhoto()).into(ivPhoto);
    }
}
