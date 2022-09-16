package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRvDiary;          //리사이클러 뷰 (리스트 뷰)
    DiaryListAdapter mAdapter;      //리사이클러 뷰 와 연동할 어댑터
    ArrayList<DiaryModel> mLstDiary; //리스트에 표현한 다이어리 데이터들 (배열)
    DatabaseHelper mDatabaseHelper; //데이터 베이스 헬퍼 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //액티비티가 시작할 때 최초 1회만 호출
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터 베이스 객체의 초기화
        mDatabaseHelper = new DatabaseHelper(this);

        mLstDiary =new ArrayList<>();

        mRvDiary = findViewById(R.id.rv_diary);

        mAdapter = new DiaryListAdapter(); //리사이클러 뷰 어댑터 인스턴스 생성

        //다이어리 샘플 아이템 2개 생성
//        DiaryModel item = new DiaryModel();
//        item.setId(0);
//        item.setTitle("오늘은 추석연휴 마지막 날 ");
//        item.setContent("추석 마지막 날입니다. 가족과 함께 영화를 보고 왔습니다. 영화 제목은 공조 2. 현빈은 잘생겼다.");
//        item.setUserDate("2022/09/12 Mon");
//        item.setWriteDate("2022/09/12 Mon");
//        item.setWeatherType(0);
//        mLstDiary.add(item);
//
//        DiaryModel item2 = new DiaryModel();
//        item2.setId(0);
//        item2.setTitle("오늘은 화요일입니다 ");
//        item2.setContent("추석 연휴가 끝났으니 열심히 일해 봅시다.");
//        item2.setUserDate("2022/09/13 Tue");
//        item.setWriteDate("2022/09/13 Tue");
//        item2.setWeatherType(3);
//        mLstDiary.add(item2);


        mRvDiary.setAdapter(mAdapter);

        //엑티비티 (화면)이 실행 될 때 가장 먼저 호출 되는 곳
        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 작성하기 버튼을 누를때 호출 되는 곳

                //작성하기 화면으로 이동
                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                startActivity(intent);

            }
        });

        //get load list
        //setLoadRecentList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 엑티비티의 재개

        //get load list
        setLoadRecentList();

    }

    private void setLoadRecentList(){
        //최근 데이터 베이스 정보를 가지고 와서 리사이클러뷰에 갱신해준다

        //이전에 배열 리스트에 저장된 데이터가 있으면 비워버림
        if (!mLstDiary.isEmpty()) {
            mLstDiary.clear();
        }

        mLstDiary = mDatabaseHelper.getDiaryListFromDB();   //데이터 베이스로부터 저장되어 있는 DB를 확인하여 가지고 옴
        mAdapter.setListInit(mLstDiary);

    }

}