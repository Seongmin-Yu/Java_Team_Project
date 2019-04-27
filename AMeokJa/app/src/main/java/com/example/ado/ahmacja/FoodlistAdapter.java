package com.example.ado.ahmacja;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodlistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView foodname;
        TextView foodex;
        TextView likecnt;
        ImageView food;
        View viewadd;


        MyViewHolder(View view){
            super(view);
            foodname = (TextView)view.findViewById(R.id.foodname_list);
            foodex = (TextView)view.findViewById(R.id.foodex_list);
            likecnt = (TextView)view.findViewById(R.id.likecnt_list);
            food = (ImageView)view.findViewById(R.id.foodimage_list);
            viewadd = view;
        }
    }

    private ArrayList<FoodlistInfo> FoodlistInfoArrayList;
    FoodlistAdapter(ArrayList<FoodlistInfo> FoodlistInfoArrayList){
        this.FoodlistInfoArrayList = FoodlistInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_low_foodlist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

//        myViewHolder.ivPicture.setImageResource(foodInfoArrayList.get(position).drawableId);
//        myViewHolder.tvPrice.setText(foodInfoArrayList.get(position).price);
        myViewHolder.food.setImageResource(R.drawable.sample);
        Glide.with(myViewHolder.viewadd).load(FoodlistInfoArrayList.get(position).foodimage).into(myViewHolder.food);
        myViewHolder.likecnt.setText(FoodlistInfoArrayList.get(position).likecnt);
        myViewHolder.foodname.setText(FoodlistInfoArrayList.get(position).foodname);
        myViewHolder.foodex.setText(FoodlistInfoArrayList.get(position).ex);
    }

    @Override
    public int getItemCount() {
        return FoodlistInfoArrayList.size();
    }
}

