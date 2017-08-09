package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jyyae86 on 2017-06-07.
 * 
 */

public abstract class AbstractTreeInfoActivity extends AppCompatActivity {
    TextView commonName;
    TextView sciName;
    TextView cArea;
    TextView lat;
    TextView lng;
    TextView creatorName;
    String id;

    Tree selected;

    FirebaseDatabase mDB;
    DatabaseReference mRef;

    DataBaseHelper SQLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDB = FirebaseDatabase.getInstance();
        mRef = mDB.getReference().child("userAddedTrees");

        SQLiteDB = DataBaseHelper.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_info);

        commonName = (TextView) findViewById(R.id.textViewCommonName);
        sciName = (TextView) findViewById(R.id.textViewScientificName);
        cArea = (TextView) findViewById(R.id.textViewCrownArea);
        lat = (TextView) findViewById(R.id.textViewLatitude);
        lng = (TextView) findViewById(R.id.textViewLongitude);
        creatorName = (TextView) findViewById(R.id.textViewcreatorName);
        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        populateInfo(id);

    }

    //some abstract methods to be defined in subclasses
    public void populateInfo(String id){}

    public void deleteTree(View v){}

    public void startEditTreeActivity(View v){
        finish();
        Intent intent = new Intent(getApplicationContext(), EditTreeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}