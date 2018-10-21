package com.example.ado.ahmacja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class reportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    public void back(View v) {
        finish();
    }

    public void btn(View v) {
        Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
