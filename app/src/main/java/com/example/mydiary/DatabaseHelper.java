package com.example.mydiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * 데이터 베이스 관리 유틸 클래스
 */
//SQLite = 안드로이드에서 지원하는 앱 내부 데이터베이스 시스템
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MyDiary.db";

    //생성자 (constructor)
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Database Create

        //테이블 생성
        //AUTOINCREMENT을 사용하면 자동으로 1씩 증가시킨다
        db.execSQL("CREATE TABLE IF NOT EXISTS DiaryInfo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "content TEXT NOT NULL, " +
                "weatherType INTEGER NOT NULL , " +
                "userDate TEXT NOT NULL, " +
                "writeDate TEXT NOT NULL )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * 다이어리 작성 데이터를 DB에 저장한다. (insert)
     */
    public void setInsertDiaryList(String _title, String _content , int _weatherType , String _userDate , String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DiaryInfo (title, content, weatherType, userDate, writeDate) VALUES('" + _title + "','"+ _content +"','"+ _weatherType +"','"+ _userDate+"','"+ _writeDate+"')");
    }

    /**
     * 다이어리 작성 데이터를 조회하여 가지고 옴.(select)
     */
    public ArrayList<DiaryModel> getDiaryListFromDB(){
        ArrayList<DiaryModel> lstDiary = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DiaryInfo ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                int weatherType = cursor.getInt(cursor.getColumnIndexOrThrow("weatherType"));
                String userDate = cursor.getString(cursor.getColumnIndexOrThrow("userDate"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                //create date class
                DiaryModel diaryModel = new DiaryModel();
                diaryModel.setId(id);
                diaryModel.setTitle(title);
                diaryModel.setContent(content);
                diaryModel.setWeatherType(weatherType);
                diaryModel.setUserDate(userDate);
                diaryModel.setWriteDate(writeDate);

                lstDiary.add(diaryModel);
            }
        }
        cursor.close();

        return lstDiary;
    }

    /**
     *  기존 작성 데이터를 수정할 수정한다. (update)
     */
    public void setUpdateDiaryList(String _title, String _content , int _weatherType , String _userDate , String _writeDate, String _beforeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DiaryInfo SET title = '" + _title + "', content = '"+ _content + "', weatherType = '"+ _weatherType + "' + userDate = '"+ _userDate + "' , writeDate = '"+ _writeDate + "' WHERE  writeDate = '" + _beforeDate + "' " );
    }

    /**
     * 기존 작성 데이터를 삭제한다. (delete)
     */
    public void setDeleteDiaryList(String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DiaryInfo WHERE writeDate = '" + _writeDate + "'");
    }

}
