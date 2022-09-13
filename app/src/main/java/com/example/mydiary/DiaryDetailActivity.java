package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DiaryDetailActivity extends AppCompatActivity {

    /**
     * 상세보기 화면 or 작성하기 화면
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);
    }
}