package com.example.ado.ahmacja;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class detailActivity extends AppCompatActivity {
    TextView foodname;
    TextView context;
    ImageView foodimage;
    TextView likecnt;
    ImageButton back;
    FoodlistInfo info;
    ImageButton map;
    //MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        info = (FoodlistInfo) getIntent().getSerializableExtra("detail");

        History history = new History();
        history.saveHistroy(info.getFoodname(), "food");
        //mainActivity.refresh();

        back = (ImageButton) findViewById(R.id.backbtn_detail);
        foodname = (TextView) findViewById(R.id.foodname_detail);
        context = (TextView) findViewById(R.id.foodex_detail);
        foodimage = (ImageView) findViewById(R.id.foodimage_detail);
        likecnt = (TextView) findViewById(R.id.likecnt_detail);
        map = (ImageButton) findViewById(R.id.map_detail);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detailActivity.this, DaumMapsActivity.class);
                intent.putExtra("foodname",info.getFoodname());
                startActivity(intent);
            }
        });

        foodname.setText(info.getFoodname());
        context.setText(info.getEx());
        likecnt.setText(info.getLikecnt());
        Glide.with(this).load(info.getFoodimage()).into(foodimage);
    }

    public void report(View v) {
        Intent intent = new Intent(detailActivity.this, reportActivity.class);
        startActivity(intent);
    }

    public void like(View v) {

        int nowCnt = Integer.parseInt(likecnt.getText().toString());
        nowCnt++;
        likes();
        likecnt.setText(Integer.toString(nowCnt));
    }

    void likes() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.200.68.66:2207/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FoodService foodService = retrofit.create(FoodService.class);
        Call<FoodRepo> call = foodService.likes(new LikeRequest(info.getSerialatinfo()));
        call.enqueue(new Callback<FoodRepo>() {
            @Override
            public void onResponse(Call<FoodRepo> call, Response<FoodRepo> response) {

            }

            @Override
            public void onFailure(Call<FoodRepo> call, Throwable t) {
                Log.d("asdf", t.toString());
            }
        });
    }
}
