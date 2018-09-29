package com.example.seongmin.AMeokJa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    public void btn(View v) {
        Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_SHORT).show();
    }
}
