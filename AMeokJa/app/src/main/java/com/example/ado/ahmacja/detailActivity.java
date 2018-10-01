package com.example.ado.ahmacja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detailActivity extends AppCompatActivity {
    TextView foodname;
    TextView context;
    ImageView foodimage;
    TextView likecnt;
    ImageButton back;
    FoodlistInfo info;
    ImageButton map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        info = (FoodlistInfo) getIntent().getSerializableExtra("detail");

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
}
