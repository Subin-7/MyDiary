package com.example.mydiary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {
    ArrayList<DiaryModel> mLstDiary;        // 다이어리 데이터들을 들고 있는 자료형 배열
    Context mContext;

    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰가 최초로 생송이 될 때 호출이 되는 곳


        mContext = parent.getContext();
        View holder = LayoutInflater.from(mContext).inflate(R.layout.list_item_diary,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        // 생성 된 아이템 뷰가 실제 연동이 되어지는 곳

        //날씨의 경우의 수 작성
        int weatherType = mLstDiary.get(position).getWeatherType();
        switch (weatherType) {
            case 0: //맑음
                holder.iv_weather.setImageResource(R.drawable.img_sun);
                break;
            case 1: //흐림뒤갬
                holder.iv_weather.setImageResource(R.drawable.img_cloudy);
                break;
            case 2: //흐림
                holder.iv_weather.setImageResource(R.drawable.img_cloud);
                break;
            case 3: //매우흐림
                holder.iv_weather.setImageResource(R.drawable.img_bad_cloud);
                break;
            case 4: //비
                holder.iv_weather.setImageResource(R.drawable.img_rainy);
                break;
            case 5: //눈
                holder.iv_weather.setImageResource(R.drawable.img_snowy);
                break;
        }

        //날씨 제목, 날짜
        String title = mLstDiary.get(position).getTitle();
        String userDate = mLstDiary.get(position).getUserDate();

        holder.tv_title.setText(title);
        holder.tv_user_date.setText(userDate);

    }

    @Override
    public int getItemCount() {
        //아이템 뷰의 총 갯수를 관리하는 곳
        return mLstDiary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_weather;
        TextView tv_title, tv_user_date;


        // 하나의 아이템 뷰
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_weather = itemView.findViewById(R.id.iv_weather);        // 날씨 이미지
            tv_title = itemView.findViewById(R.id.tv_title);            // 다이어리 제목
            tv_user_date = itemView.findViewById(R.id.tv_user_date);    // 사용자 지정 날짜

            // 일반 클릭 (상세보기)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 현재 클릭이 된 위치 (배열 개념 이어서 첫 시작이 0부터 세는 기준)
                    int currentPosition = getAdapterPosition();

                    //현재 클릭 된 리스트 아이템 정보를 가지는 변수
                    DiaryModel diaryModel = mLstDiary.get(currentPosition);

                    //화면 이동 및 다이어리 데이터 다음 엑티비티로 전달
                    Intent diaryDetailIntent = new Intent(mContext, DiaryDetailActivity.class);
                    diaryDetailIntent.putExtra( "diaryModel", diaryModel);  // 다이어리 데이터 넘기기
                    diaryDetailIntent.putExtra("mode", "detail");   //상세보기 모드로 설정
                    mContext.startActivity(diaryDetailIntent);
                }
            });

            //선택지 옵션 팝업 (수정, 삭제)
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // 현재 클릭이 된 위치 (배열 개념 이어서 첫 시작이 0부터 세는 기준)
                    int currentPosition = getAdapterPosition();

                    //현재 클릭 된 리스트 아이템 정보를 가지는 변수
                    DiaryModel diaryModel = mLstDiary.get(currentPosition);

                    //버튼 선택지 배열
                    String[] strChoiceArray = {"수정 하기", "삭제 하기"};

                    return false;
                }
            });
        }
    }

    public void setSampleList(ArrayList<DiaryModel> lstDiary){

        mLstDiary = lstDiary;
    }
}
