package com.example.pmp;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements BaseAdapterEx.OnClickEvent {

    private static final String DATABASE_NAME = "SubjectDB.db";
    private static final String PACKAGE_DIR = "/data/data/com.example.pmp/databases";
    private static final String MYFILENAME = "test";

    FrameLayout root;

    ListView mListView = null;
    BaseAdapterEx mAdapter = null;
    ArrayList<Subject>  mData =null;
    ArrayList<Subject>  mDataShow =null;
    EditText editSearch = null;


    public static void initialize(Context ctx) {
        File folder = new File(PACKAGE_DIR);
        folder.mkdirs();

        File outfile = new File(PACKAGE_DIR + "/" + MYFILENAME );

        if (outfile.length() <= 0) {
            AssetManager assetManager = ctx.getResources().getAssets();
            try {
                InputStream is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte [] tempdata = new byte[(int)filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(getApplicationContext());

        editSearch = (EditText)findViewById(R.id.edit);
        mData = new ArrayList<Subject>();
        mDataShow = new ArrayList<Subject>();




        SQLiteDatabase db = SQLiteDatabase.openDatabase(PACKAGE_DIR + "/" +MYFILENAME, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select S_NAME,NAME,TIME,Day1,Day2,Day3,Day4,C_Shour,C_Smin,C_Fhour,C_Fmin,Shour1,Smin1,Fhour1,Fmin1,Shour2,Smin2,Fhour2,Fmin2 from DataTable",null);

        while (cursor.moveToNext()) {
            Subject subject = new Subject();

            String temp = cursor.getString(2);
            temp = temp.replace(")",")\n");

            subject.mSname = cursor.getString(0);
            subject.mName = cursor.getString(1);
            subject.mTime = temp;
            subject.mDay1 = cursor.getString(3);
            subject.mDay2 = cursor.getString(4);
            subject.mDay3 = cursor.getString(5);
            subject.mDay4 = cursor.getString(6);
            subject.mCshour = cursor.getInt(7);
            subject.mCsmin = cursor.getInt(8);
            subject.mCfhour = cursor.getInt(9);
            subject.mCfmin = cursor.getInt(10);
            subject.mShour1 = cursor.getInt(11);
            subject.mSmin1 = cursor.getInt(12);
            subject.mFhour1 = cursor.getInt(13);
            subject.mFmin1 = cursor.getInt(14);
            subject.mShour2 = cursor.getInt(15);
            subject.mSmin2 = cursor.getInt(16);
            subject.mFhour2 = cursor.getInt(17);
            subject.mFmin2 = cursor.getInt(18);



            mData.add(subject);
        }


        mDataShow.addAll(mData);
        mAdapter = new BaseAdapterEx(this,mDataShow);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            String searchText = editSearch.getText().toString();
            fillter(searchText);
        }
    });
    }

    public void fillter(String searchText) {
        mDataShow.clear();
        if(searchText.length() == 0)
        {
            mDataShow.addAll(mData);
        }
        else
        {
            for(Subject item : mData )
            {
                if(item.getmSname().contains(searchText))
                {
                    mDataShow.add(item);
                }
                else if(item.getmName().contains(searchText))
                {
                    mDataShow.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void Setting(View v){
        FrameLayout Root = (FrameLayout) findViewById(R.id.Root);

        Root.addView(v);
    }


    @Override
    public void onClick(Subject subject) {
        Log.d("hi", subject.getmName());
        String test = subject.getmSname()+"\n"+subject.getmName();
        root  = (FrameLayout)findViewById(R.id.Root);
        Button bt1 = new Button(this);
        Sublist test1 = new Sublist(this,subject);
        bt1.setText(test);
        FrameLayout.LayoutParams bt1LP = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        bt1LP.topMargin=subject.mShour1*10;
        root.addView(test1,bt1LP);
        //root.addView(bt1,bt1LP);

    }
}





