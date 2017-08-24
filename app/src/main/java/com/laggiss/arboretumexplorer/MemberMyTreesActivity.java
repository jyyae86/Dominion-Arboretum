package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by jyyae86 on 2017-06-15.
 */

public class MemberMyTreesActivity extends AppCompatActivity {
    DataBaseHelper mDBHelper;
    FirebaseDatabase mDB;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    ListView myTreeList;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trees);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        myTreeList = (ListView) findViewById(R.id.myTreeList);

        ArrayList<Tree> trees = getTreeArray(intent.getStringExtra("type"));
        TreeArrayAdapter adapter = new TreeArrayAdapter(this, trees);
        myTreeList.setAdapter(adapter);
        myTreeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Log.e("clicked","clicked");
                Tree selected = (Tree) parent.getItemAtPosition(position);
                Intent nIntent = new Intent(getApplicationContext(), LocalTreeInfoActivity.class);
                nIntent.putExtra("id", selected.getFirebaseID());
                nIntent.putExtra("type", type);

                startActivity(nIntent);

            }
        });
    }

    public ArrayList<Tree> getTreeArray(String type) {
        mDBHelper = DataBaseHelper.getInstance(this);
        mDBHelper.openDataBase();
        return mDBHelper.getTrees(type);
    }
}
