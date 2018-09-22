package com.example.ado.ahmacja;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView date;
        TextView time;

        MyViewHolder(View view){
            super(view);
            icon = view.findViewById(R.id.history_icon);
            title = view.findViewById(R.id.history_text);
            date = view.findViewById(R.id.history_date);
            time = view.findViewById(R.id.history_time);
        }
    }

    private ArrayList<HistoryInfo> HistoryInfoArrayList;
    HistoryAdapter(ArrayList<HistoryInfo> HistoryInfoArrayList){
        this.HistoryInfoArrayList = HistoryInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_low_history, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

//        myViewHolder.ivPicture.setImageResource(foodInfoArrayList.get(position).drawableId);
//        myViewHolder.tvPrice.setText(foodInfoArrayList.get(position).price);
        myViewHolder.icon.setImageResource(HistoryInfoArrayList.get(position).kind);
        myViewHolder.title.setText(HistoryInfoArrayList.get(position).text);
        myViewHolder.date.setText(HistoryInfoArrayList.get(position).date);
        myViewHolder.time.setText(HistoryInfoArrayList.get(position).time);
    }

    @Override
    public int getItemCount() {
        return HistoryInfoArrayList.size();
    }
}

