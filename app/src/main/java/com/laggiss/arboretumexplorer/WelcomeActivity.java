package com.laggiss.arboretumexplorer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            requestDBPermission();
        }


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

    private void requestLocationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(WelcomeActivity.this,
                                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void requestDBPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(WelcomeActivity.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void downloadTreeData(View v){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("This is a one time thing");
        progressDialog.show();
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
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putBoolean("downloadedDB", true);
                editor.commit(); // Very important to save the preference and delete the pro
                progressDialog.dismiss();
                finish();
                startActivity(new Intent(getApplicationContext(), EmailLoginActivity.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //add preference

    }
}
