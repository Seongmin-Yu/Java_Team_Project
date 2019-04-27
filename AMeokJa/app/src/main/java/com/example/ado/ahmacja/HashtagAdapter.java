package com.example.ado.ahmacja;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HashtagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        MyViewHolder(View view){
            super(view);
            text = view.findViewById(R.id.hashtag_text);
        }
    }

    private ArrayList<HashtagInfo> HashtagInfoArrayList;
    HashtagAdapter(ArrayList<HashtagInfo> HashtagInfoArrayList){
        this.HashtagInfoArrayList = HashtagInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_hashtag, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

//        myViewHolder.ivPicture.setImageResource(foodInfoArrayList.get(position).drawableId);
//        myViewHolder.tvPrice.setText(foodInfoArrayList.get(position).price);
        myViewHolder.text.setText(HashtagInfoArrayList.get(position).text);
    }

    @Override
    public int getItemCount() {
        return HashtagInfoArrayList.size();
    }
}
