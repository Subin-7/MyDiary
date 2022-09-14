package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 상세보기 화면 or 작성하기 화면
 */

public class DiaryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvDate;   // 일시 설정 텍스트
    private EditText mEtTitle , mEtContent;  // 일기 제목 , 일기 내용
    private RadioGroup mRgWeather;

    private String mDetailMode = "";     //intent로 받아낸 게시글 모드
    private String mBeforeDate ="";     // intent로 받아낸 게시글 기존 작성일자
    private String mSelectedUserDate = ""; //선택 된 일시 값
    private int mSelectedWeatherType = -1; // 선택 된 날씨 값 (1~6)


    private DatabaseHelper mDatabaseHelper; // Database Util  객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        //데이터 베이스 객체 생성
        mDatabaseHelper = new DatabaseHelper(this);

        mTvDate = findViewById(R.id.tv_date);   //일시 설정 텍스트
        mEtTitle = findViewById(R.id.et_title); //제목 입력 필드
        mEtContent = findViewById(R.id.et_content); //내용 입력 필드
        mRgWeather = findViewById(R.id.rg_weather); //날씨 선택 라디오 그룹

        ImageView iv_back = findViewById(R.id.iv_back); //뒤로가기 버튼
        ImageView iv_check = findViewById(R.id.iv_check);   //작성 완료 버튼


        mTvDate.setOnClickListener(this);   // 클릭기능 부여
        iv_back.setOnClickListener(this);   // 클릭기능 부여
        iv_check.setOnClickListener(this);  // 클릭 기능 부여

        //기본으로 설정 된 날짜의 갓을 지정 (디바이스 현재 시간 기준)
        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(new Date());
        mTvDate.setText(mSelectedUserDate);



    }

    @Override
    public void onClick(View view) {
        // setOnClickListener가 붙어있는 뷰들은 클릭이 발생하면 모두 이곳을 수행하게 된다.
        switch (view.getId()) {
            case R.id.iv_back:
                //뒤로가기 버튼
                finish();   // 현재 엑티비티 종료
                break;

            case R.id.iv_check:
                //작성 완료 버튼


                //체크 값에 대해 아이디를 추출해내어 그 아이디 값을 WeatherType에 넣어준다.(라디오 그룹의 버튼 클릭 현재 상황 가지고 오기)
                mSelectedWeatherType = mRgWeather.indexOfChild(findViewById(mRgWeather.getCheckedRadioButtonId()));

                //입력 필드 작성란이 비어 있는지 체크
                if (mEtTitle.getText().length() == 0 || mEtContent.getText().length() == 0){
                    //error
                    Toast.makeText(this, "제목과 내용을 모두 입력하여 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 날씨 선택이 되어 있는지 체크
                if(mSelectedWeatherType == -1) {
                    //error
                    Toast.makeText(this, "날씨를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //데이터 저장
                String title = mEtTitle.getText().toString();   // 제목 입력 값
                String content = mEtContent.getText().toString();   //내용 입력 값
                String userDate = mSelectedUserDate;    // 사용자가 선택한 일시

                String writeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN).format(new Date()); //작성 완료 누른 시전의 일시

                // 데이터 베이스에 저장
                mDatabaseHelper.setInsertDiaryList(title, content, mSelectedWeatherType, userDate, writeDate);
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();


                finish(); //현재 액티비티 종료
                break;

            case R.id.tv_date:
                // 일시 설정 텍스트

                //달력을 띄워서 사용자에게 일시를 입력 받는다.

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // 달력에 성택 된 (년,월,일)을 가지고 와서 다시 캘린더 함수에 널어줘서 사용자가 선택한 요일을 알아낸다.
                        Calendar innerCal = Calendar.getInstance();
                        innerCal.set(Calendar.YEAR, year);
                        innerCal.set(Calendar.MONTH, month);
                        innerCal.set(Calendar.DAY_OF_MONTH, day);

                        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(innerCal.getTime());
                        mTvDate.setText(mSelectedUserDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show(); // 다이어로그 (팝업) 활성화

                break;
        }
    }
}