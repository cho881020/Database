package kr.co.tjeit.database.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2017-09-11.
 */

public class StudentDBManager {


    //데이터베이스도 하나의 파일. (In excel : ~~.xlsx)
    static final String DB_STUDENT = "student.db";
//    학생 정보가 저장되는 표 Table (excel : Sheet)
    static final String TABLE_STUDENT = "Students";

//    DB 구조가 변경될때, 이를 버젼화 하여 기록하면 앱을 업데이트 해도 큰 문제 발생 X
    static final int DB_VERSION = 1;

    Context mContext;

//    데이터베이스의 관리자의 경우엔 동시에 여러개의 객체가 생존해서 좋을게 없음.
//    데이터베이스 관리 시스템은 단 하나의 객체만 존재하도록 보장.
//     => 이러한 기법을 "Singletone" 패턴이라고 함.
//     => 프로그래밍의 디자인패턴. (코드 설계 디자인)
    private static StudentDBManager mDbManager = null;
    private SQLiteDatabase mDB = null;


    public static StudentDBManager getInstance(Context context) {
        if (mDbManager == null) {
            mDbManager = new StudentDBManager(context);
        }

        return mDbManager;
    }

//    StudentDBManager 생성자.

    private StudentDBManager(Context context) {
        mContext = context;

//        mDB는 새로만들거나, 기존의데이터베이스 체계를 받아옴.
        mDB = mContext.openOrCreateDatabase(DB_STUDENT, Context.MODE_PRIVATE, null);

//        DB내부에 저장되 실제 표 (테이블) 만드는 과정.
        mDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STUDENT +
                    "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "stdNum TEXT, " +
                    "name TEXT, " +
                    "department TEXT, " +
                    "grade INTEGER" +
                    ");");
    }

    public long insert(ContentValues addRowValue) {
        return mDB.insert(TABLE_STUDENT, null, addRowValue);
    }

    public Cursor query(String[] columns,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderBy) {

        return mDB.query(TABLE_STUDENT,
                columns, selection, selectionArgs, groupBy, having, orderBy);

    }

//    영문과 4학년 학생의 이름? 쿼리

//    메쏘드명 : 0번문제의 쿼리
    public Cursor getQuery00() {

        return mDB.rawQuery("SELECT name FROM " + TABLE_STUDENT +
                " WHERE department='영문' AND grade = 4;", null);

    }

}
