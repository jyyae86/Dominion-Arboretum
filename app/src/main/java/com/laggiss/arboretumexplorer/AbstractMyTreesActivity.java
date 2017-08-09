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

public abstract class AbstractMyTreesActivity extends AppCompatActivity {
    DataBaseHelper mDBHelper;
    FirebaseDatabase mDB;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    ListView myTreeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trees);

        myTreeList = (ListView) findViewById(R.id.myTreeList);
        ArrayList<Tree> trees = getTreeArray();
        TreeArrayAdapter adapter = new TreeArrayAdapter(this, trees);
        myTreeList.setAdapter(adapter);
        myTreeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Log.e("clicked","clicked");
                Tree selected = (Tree) parent.getItemAtPosition(position);
                Intent nIntent = new Intent(getApplicationContext(), LocalTreeInfoActivity.class);
                nIntent.putExtra("id", selected.getFirebaseID());

                finish();
                startActivity(nIntent);

            }
        });
    }

    public abstract ArrayList<Tree> getTreeArray();

    public void startMainActivity(View v){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
