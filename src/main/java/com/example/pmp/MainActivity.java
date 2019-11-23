package com.example.pmp;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class MainActivity extends AppCompatActivity  {

    private static final String DATABASE_NAME = "SubjectDB.db";
    private static final String PACKAGE_DIR = "/data/data/com.example.pmp/databases";
    private static final String MYFILENAME = "fuck";

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
        FrameLayout root = (FrameLayout) findViewById(R.id.Root);
        Button bt3 = new Button(this);
        FrameLayout.LayoutParams bt3LP = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        bt3.setText("Button 3");

        root.addView(bt3,bt3LP);

        SQLiteDatabase db = SQLiteDatabase.openDatabase(PACKAGE_DIR + "/" +MYFILENAME, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select S_NAME,NAME,TIME from fuck",null);

        while (cursor.moveToNext()) {
            Subject subject = new Subject();

            String temp = cursor.getString(2);
            temp = temp.replace(")",")\n");

            subject.mSname = cursor.getString(0);
            subject.mName = cursor.getString(1);
            subject.mTime = temp;

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


}





