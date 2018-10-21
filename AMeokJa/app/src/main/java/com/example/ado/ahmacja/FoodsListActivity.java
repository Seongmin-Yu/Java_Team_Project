package com.example.ado.ahmacja;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodsListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView mRecyclerView_hash;
    RecyclerView mRecyclerView_food;
    RecyclerView.LayoutManager mLayoutManager;
    TextView clickedView;
    ArrayList<HashtagInfo> hashtagInfoArrayList;
    ArrayList<FoodlistInfo> foodlistInfoArrayList;
    FoodlistAdapter foodlistAdapter;
    HashtagAdapter hashtagAdapter;
    FoodlistInfo selectedfood;
    static final String URL = "http://35.200.68.66:2207/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.searchbar);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView sortname = (TextView)findViewById(R.id.sortbyname);
        TextView sortheart = (TextView)findViewById(R.id.sortbyheart);
        Typeface customFont = Typeface.createFromAsset(getAssets(),  "서울남산 장체M.ttf");
        sortname.setTypeface(customFont);
        sortname.setOnClickListener(this);
        sortheart.setTypeface(customFont);
        sortheart.setOnClickListener(this);
        clickedView = sortname;

        mRecyclerView_hash = (RecyclerView) findViewById(R.id.recycler_hashtag);
        mRecyclerView_food = (RecyclerView) findViewById(R.id.recycler_foodlist);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView_hash.setLayoutManager(horizontalLayoutManagaer);
        mRecyclerView_food.setLayoutManager(mLayoutManager);
        foodlistInfoArrayList = new ArrayList<>();
        hashtagInfoArrayList = (ArrayList<HashtagInfo>) getIntent().getSerializableExtra("hashtag");
        foods();
        hashtagAdapter = new HashtagAdapter(hashtagInfoArrayList);
        foodlistAdapter = new FoodlistAdapter(foodlistInfoArrayList);
        mRecyclerView_hash.setAdapter(hashtagAdapter);
        mRecyclerView_food.setAdapter(foodlistAdapter);

        mRecyclerView_food.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView_food, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedfood = foodlistInfoArrayList.get(position);
                Intent intent = new Intent(FoodsListActivity.this, detailActivity.class);
                intent.putExtra("detail", selectedfood);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortbyname:
                if(clickedView != null) {
                    clickedView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                clickedView = (TextView) v;
                v.setBackgroundColor(getResources().getColor(R.color.onclick));
                Collections.sort(foodlistInfoArrayList, new CompareNameDesc());
                foodlistAdapter.notifyDataSetChanged();
                break;
            case R.id.sortbyheart:
                if(clickedView != null) {
                    clickedView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                clickedView = (TextView) v;
                v.setBackgroundColor(getResources().getColor(R.color.onclick));
                Collections.sort(foodlistInfoArrayList, new CompareLikecntDesc());
                foodlistAdapter.notifyDataSetChanged();
                break;
        }
    }
    static class CompareLikecntDesc implements Comparator<FoodlistInfo> {
        @Override
        public int compare(FoodlistInfo o1, FoodlistInfo o2) {
            return o2.getLikecnt().compareTo(o1.getLikecnt());
        }
    }

    static class CompareNameDesc implements Comparator<FoodlistInfo> {
        @Override
        public int compare(FoodlistInfo o1, FoodlistInfo o2) {
            return o1.getFoodname().compareTo(o2.getFoodname());
        }
    }
    public void foods() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String keyword = hashtagInfoArrayList.get(0).text;
        FoodService foodService = retrofit.create(FoodService.class);
        Call<FoodRepo> call = foodService.getFoods(keyword);
        call.enqueue(new Callback<FoodRepo>() {
            @Override
            public void onResponse(Call<FoodRepo> call, Response<FoodRepo> response) {
                FoodRepo repo = response.body();
                for(int i = 0; i < repo.getFoods().size();i++) {
                FoodlistInfo food = new FoodlistInfo();
                food.setSerialatinfo(repo.getFoods().get(i).getSerial());
                food.setFoodname(repo.getFoods().get(i).getFoodname());
                food.setEx(repo.getFoods().get(i).getContext());
                food.setLikecnt(repo.getFoods().get(i).getLike());
                food.setFoodimage("https://s3.ap-northeast-2.amazonaws.com/amugja/"+repo.getFoods().get(i).getFoodname()+".jpg");
                foodlistInfoArrayList.add(food);
                foodlistAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FoodRepo> call, Throwable t) {
                Log.d("debugfor", t.toString());
            }
        });
    }
}

