package com.laggiss.arboretumexplorer;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    SharedPreferences mPrefs;
    FirebaseDatabase mDB;
    DatabaseReference mRef;
    DataBaseHelper mDBHelper;
    private SQLiteDatabase arboretum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDB = FirebaseDatabase.getInstance();
        mRef = mDB.getReference();
        mRef = mRef.child("master");

        mDBHelper = new DataBaseHelper(this);//this,dbName,dbPath);
        try {

            mDBHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            mDBHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

        try {

            arboretum = mDBHelper.getWritableDatabase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        mDBHelper.loop();

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean downloaded = mPrefs.getBoolean("downloadedDB", false);

        if(downloaded){
            finish();
            startActivity(new Intent(this,EmailLoginActivity.class));
        }

    }

    protected void downloadTreeData(View v){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> masterDB = (Map<String, Object>) dataSnapshot.getValue();
                for(String k : masterDB.keySet()) {
                    Map<String, Object> m1 = (Map<String, Object>)masterDB.get(k);
                    ContentValues cv = new ContentValues();
                    for(String k1 : m1.keySet()) {
                        try{
                            cv.put(k1, (String)m1.get(k1));
                        }catch(Exception e){
                            try{
                                cv.put(k1, (double)m1.get(k1));
                            }catch(Exception f){
                                cv.put(k1, (long)m1.get(k1));
                            }
                        }
                    }
                    mDBHelper.addRowToMaster(cv);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //add preference
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean("downloadedDB", true);
        editor.commit(); // Very important to save the preference and delete the pro
        finish();
        startActivity(new Intent(this, EmailLoginActivity.class));
    }
}
