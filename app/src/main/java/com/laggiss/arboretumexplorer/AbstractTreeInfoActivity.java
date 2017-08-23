package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

    FirebaseDatabaseUtility mFirebaseUtil;
    DatabaseReference mRef;

    DataBaseHelper SQLiteDB;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mFirebaseUtil = new FirebaseDatabaseUtility();

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
        type = intent.getStringExtra("type");

        populateInfo(id);

    }

    //some abstract methods to be defined in subclasses
    public void populateInfo(String id){}

    public void deleteTree(View v){}

    public void startEditTreeActivity(View v){}

    public void startMainActivity(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}