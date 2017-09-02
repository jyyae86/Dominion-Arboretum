package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jyyae86 on 2017-06-15.
 */

public class AdminTreesActivity extends AppCompatActivity {

    FirebaseDatabase mDB;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    String type;

    ListView myTreeList;

    ArrayList<String> refs;

//    private final ArrayList<ArrayList<Tree>> trees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        mDB = FirebaseDatabase.getInstance();
        refs = new ArrayList<String>();

        if(type.equals("add")){
            mRef = mDB.getReference().child("userAddedTrees");
        }else if(type.equals("edit")){
            mRef = mDB.getReference().child("userEditedTrees");
        }else{//delete
            mRef = mDB.getReference().child("userDeletedTrees");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trees);

        myTreeList = (ListView) findViewById(R.id.myTreeList);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Tree> trees = new ArrayList<Tree>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    trees.add(child.getValue(Tree.class));
                    refs.add(child.getKey());
                }

                populateListView(trees);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void populateListView(ArrayList<Tree> trees){
        TreeArrayAdapter adapter = new TreeArrayAdapter(this, trees);
        myTreeList.setAdapter(adapter);
        myTreeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                Tree selected = (Tree) parent.getItemAtPosition(position);
                Intent nIntent = new Intent(getApplicationContext(), RemoteTreeInfoActivity.class);
                nIntent.putExtra("id", refs.get(position));
                nIntent.putExtra("source", "admin");
                nIntent.putExtra("type", type);
                startActivity(nIntent);
            }
        });


    }
}
