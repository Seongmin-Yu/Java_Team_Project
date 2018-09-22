package com.example.ado.ahmacja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SerchActivity extends AppCompatActivity {
    RecyclerView mRecyclerView_hash;
    LinearLayoutManager horizontalLayoutManagaer
            = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    HashtagSearchAdapter hashtagAdapter;
    ArrayList<HashtagInfo> hashtagInfoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);

        mRecyclerView_hash = (RecyclerView) findViewById(R.id.recycler_hashtag_search);
        mRecyclerView_hash.addOnItemTouchListener( new RecyclerItemClickListener(this, mRecyclerView_hash ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                // do whatever

            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
                hashtagInfoArrayList.remove(position);
                hashtagAdapter.notifyDataSetChanged();
            }
        }));
        mRecyclerView_hash.setLayoutManager(horizontalLayoutManagaer);
        hashtagInfoArrayList= new ArrayList<>();
        if(getIntent().hasExtra("hashtag2")) {
            hashtagInfoArrayList = (ArrayList<HashtagInfo>) getIntent().getSerializableExtra("hashtag2");
        }
        hashtagAdapter = new HashtagSearchAdapter(hashtagInfoArrayList);
        mRecyclerView_hash.setAdapter(hashtagAdapter);
        EditText editText = (EditText) findViewById(R.id.editText_search);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EditText edit = (EditText) v;
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hashtagInfoArrayList.add(new HashtagInfo(edit.getText().toString()));
                    hashtagAdapter.notifyDataSetChanged();
                    edit.setText("");
                    return true;
                }
                return false;
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.search_icon);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hashtagInfoArrayList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "해시테그를 추가해주세요", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(SerchActivity.this, FoodsListActivity.class);
                    intent.putExtra("hashtag", hashtagInfoArrayList);
                    startActivity(intent);
                }
            }
        });
    }
}
