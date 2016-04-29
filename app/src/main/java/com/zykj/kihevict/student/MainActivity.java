package com.zykj.kihevict.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.R.xml;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.app.Activity;
import android.content.Context;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ArrayList<Student> mListStudents;
    private EditText mEd_name;
    private EditText mEd_sex;
    private EditText mEd_age;
    private Button mBtn_add;
    private Button mBtn_save;
    private Button mBtn_recover;
    private LinearLayout mLl_data;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mListStudents = new ArrayList<Student>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void init()
    {
        initView();
        initData();
    }

    private void initData() {
        mBtn_add.setOnClickListener(this);
        mBtn_recover.setOnClickListener(this);
        mBtn_save.setOnClickListener(this);
    }

    private void initView() {
        mEd_name = (EditText) findViewById(R.id.ed_name);
        mEd_sex = (EditText) findViewById(R.id.ed_sex);
        mEd_age = (EditText) findViewById(R.id.ed_age);

        mBtn_add = (Button) findViewById(R.id.bt_add);
        mBtn_save = (Button) findViewById(R.id.btn_save);
        mBtn_recover = (Button) findViewById(R.id.btn_recover);

        mLl_data = (LinearLayout) findViewById(R.id.ll_data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                AddData();
                break;
            case R.id.btn_recover:
                Recoverdata();
                break;
            case R.id.btn_save:
                SaveData();
                break;

            default:
                break;
        }
    }

    private void SaveData() {
        XmlSerializer serializer = Xml.newSerializer();
        FileOutputStream outstream = null;
        try {
            outstream = this.openFileOutput("Student.xml", Context.MODE_PRIVATE);
            serializer.setOutput(outstream, "utf-8");
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "students");
            for(Student student:mListStudents )
            {
                serializer.startTag(null, "student");
                serializer.attribute(null, "id", student.getId()+"");

                serializer.startTag(null, "name");
                serializer.text(student.getName());
                serializer.endTag(null, "name");

                serializer.startTag(null, "sex");
                serializer.text(student.getSex());
                serializer.endTag(null, "sex");

                serializer.startTag(null, "age");
                serializer.text(student.getAge());
                serializer.endTag(null, "age");

                serializer.endTag(null, "student");
            }
            serializer.endTag(null, "students");
            serializer.endDocument();

            Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
            outstream.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void Recoverdata() {
        XmlPullParser pullparser = Xml.newPullParser();
        File file = new File("/data/data/com.zykj.kihevict.student/files/Student.xml");
        mListStudents = null;

        mListStudents = new ArrayList<Student>();
        try {
            FileInputStream inputstream = new FileInputStream(file);
            pullparser.setInput(inputstream, "utf-8");

            int eventType = pullparser.getEventType();
            Student currentstudent = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        String strtag = pullparser.getName();
                        if(strtag.equals("students"))
                        {

                        }
                        else if(strtag.equals("student"))
                        {
                            currentstudent = new Student();
                            currentstudent.setId(new Integer(pullparser.getAttributeValue(null, "id")));
                        }
                        else if(currentstudent != null)
                        {
                            if(strtag.equals("name"))
                            {
                                currentstudent.setName(pullparser.nextText());
                            }
                            else if(strtag.equals("sex"))
                            {
                                currentstudent.setSex(pullparser.nextText());
                            }
                            else if(strtag.equals("age"))
                            {
                                currentstudent.setAge(pullparser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(pullparser.getName().equals("student") && currentstudent != null)
                        {
                            mListStudents.add(currentstudent);
                            currentstudent = null;
                        }
                        break;
                }
                eventType = pullparser.next();
            }
            inputstream.close();
            Toast.makeText(getApplicationContext(), "恢复数据成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "恢复数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        for(Student student:mListStudents)
        {
            TextView textiew = new TextView(this);
            textiew.setText("姓名：" + student.getName().toString().trim() + "\t性别：" + student.getSex().toString().trim() + "\t年龄：" + student.getAge().toString().trim());
            mLl_data.addView(textiew);
        }
    }

    private void AddData() {
        String strName = mEd_name.getText().toString().trim();
        String strSex = mEd_sex.getText().toString().trim();
        String strAge = mEd_age.getText().toString().trim();

        if(strName.equals("") || strSex.equals("") || strAge.equals(""))
        {
            Toast.makeText(getApplicationContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
            TextView child = new TextView(this);
            child.setText("姓名：" + strName + "\t性别：" + strSex + "\t年龄：" + strAge);
            mLl_data.addView(child);
            Student student = new Student(i++, strName, strSex, strAge);
            mListStudents.add(student);

            mEd_name.setText("");
            mEd_sex.setText("");
            mEd_age.setText("");

            mEd_name.setFocusable(true);
            mEd_name.requestFocus();
        }
    }
}
