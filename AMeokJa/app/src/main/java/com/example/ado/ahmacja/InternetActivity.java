package com.example.ado.ahmacja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class InternetActivity extends AppCompatActivity {
    WebView webView;
    ImageView backbtn_internet;
    TextView foodname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        String placeName = getIntent().getStringExtra("internet");
        History history = new History();
        history.saveHistroy(placeName, "store");
        webView = (WebView) findViewById(R.id.web);
        webView.loadUrl("https://m.search.naver.com/search.naver?query="+placeName);
        backbtn_internet = (ImageView) findViewById(R.id.back_btn_internet);
        backbtn_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        foodname = (TextView) findViewById(R.id.foodname_internet);
        foodname.setText(placeName);
    }
}
