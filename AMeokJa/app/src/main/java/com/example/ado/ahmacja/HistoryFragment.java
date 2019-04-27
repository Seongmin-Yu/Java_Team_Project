package com.example.ado.ahmacja;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class HistoryFragment extends Fragment{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    History history;
    private String _myTag;
    public void setMyTag(String value)
    {
        if("".equals(value))
            return;
        _myTag = value;
    }
    public HistoryFragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_history, container, false);
        TextView title = (TextView)layout.findViewById(R.id.title_hitory);
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(),  "Roboto-Medium.ttf");
        title.setTypeface(customFont);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_history);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        history = new History();
        ArrayList<HistoryInfo> historyInfoArrayList = history.readHistory();
        Collections.reverse(historyInfoArrayList);
        HistoryAdapter historyAdapter = new HistoryAdapter(historyInfoArrayList);
        mRecyclerView.setAdapter(historyAdapter);
        return layout;
    }
}
