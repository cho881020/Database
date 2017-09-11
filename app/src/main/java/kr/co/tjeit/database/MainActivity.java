package kr.co.tjeit.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import kr.co.tjeit.database.util.StudentDBManager;

public class MainActivity extends BaseActivity {

    private android.widget.Button insertBtn;
    private android.widget.Button updateBtn;
    private android.widget.Button deleteBtn;
    private android.widget.Button queryBtn;
    private android.widget.EditText contentEdt;

    public StudentDBManager mDBManager;
    private EditText stdNumEdt;
    private EditText nameEdt;
    private EditText departmentEdt;
    private EditText gradeEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setupEvents();
        setValues();


    }

    @Override
    public void setupEvents() {
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ContentValues를 다루는 법 : HashMap과 거의 동일.
//                키 / 값
                ContentValues cv = new ContentValues();
                cv.put("stdNum", stdNumEdt.getText().toString());
                cv.put("name", nameEdt.getText().toString());
                cv.put("department", departmentEdt.getText().toString());
                cv.put("grade", Integer.parseInt(gradeEdt.getText().toString()));

                long insertedRowId = mDBManager.insert(cv);

                Toast.makeText(mContext, "학생 추가 : " + insertedRowId, Toast.LENGTH_SHORT).show();

            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                필요한 데이터를 받아다가 출력.

                String[] columns = {"id", "stdNum", "name", "department", "grade"};

                Cursor c = mDBManager.query(columns, null, null, null, null, null);

                if (c != null) {
//                    글자 초기화
                    contentEdt.setText("");

                    while (c.moveToNext()) {
                        int id = c.getInt(0);
                        String stdNum = c.getString(1);
                        String name = c.getString(2);
                        String department = c.getString(3);
                        int grade = c.getInt(4);

//                        append : 이어붙이기.
                        contentEdt.append(String.format(Locale.KOREA,
                                "%s : %d, %s, %s, %d\n==================\n",
                                name, id, department, stdNum, grade));

                    }

                }

            }
        });

    }

    @Override
    public void setValues() {
        mDBManager = StudentDBManager.getInstance(mContext);
    }

    @Override
    public void bindViews() {
        this.contentEdt = (EditText) findViewById(R.id.contentEdt);
        this.queryBtn = (Button) findViewById(R.id.queryBtn);
        this.deleteBtn = (Button) findViewById(R.id.deleteBtn);
        this.updateBtn = (Button) findViewById(R.id.updateBtn);
        this.insertBtn = (Button) findViewById(R.id.insertBtn);
        this.gradeEdt = (EditText) findViewById(R.id.gradeEdt);
        this.departmentEdt = (EditText) findViewById(R.id.departmentEdt);
        this.nameEdt = (EditText) findViewById(R.id.nameEdt);
        this.stdNumEdt = (EditText) findViewById(R.id.stdNumEdt);
    }
}
