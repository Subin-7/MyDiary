package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // onCreate() : Activity (화면) 이 시작될 때 가장 먼저 실행되는 곳 (안드로이드 생명주기)


        // 딜레이를 발생시켜서 (1~2초) 그 이후에 MainActivity 로 이동 해야 함
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //메인 엑티비티로 이동하는 구간
                Intent mainIntent = new Intent(SplashActivity.this , MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1500); //딜레이 해주고 싶은 초 (1000밀리초 = 1초 )


    }
}