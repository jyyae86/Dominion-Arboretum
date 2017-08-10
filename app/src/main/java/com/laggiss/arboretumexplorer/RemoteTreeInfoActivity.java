package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jyyae86 on 2017-06-07.
 */

public class RemoteTreeInfoActivity extends AbstractTreeInfoActivity {
    @Override
    public void populateInfo(String id){
        if(type.equals("add")){
            mRef = mDB.getReference().child("userAddedTrees");
        }else if(type.equals("edit")){
            mRef = mDB.getReference().child("userEditedTrees");
        }else{//delete
            mRef = mDB.getReference().child("userDeletedTrees");
        }
        mRef = mRef.child(id);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                selected =  dataSnapshot.getValue(Tree.class);
                commonName.setText(selected.getCommonName());
                sciName.setText(selected.getSciName());
                cArea.setText(selected.getCrownArea());
                lat.setText(Double.toString(selected.getLat()));
                lng.setText(Double.toString(selected.getLng()));
                creatorName.setText(selected.getCreatorName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteTree(View v){
        mRef.removeValue();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void startEditTreeActivity(View v){
        Intent nIntent = new Intent(this, EditTreeActivity.class);
        nIntent.putExtra("id", id);
        startActivity(nIntent);
    }

    public void mergeTree(View v){
        if(type.equals("delete")){
            mRef.removeValue();
            master.child(id).removeValue();
        }else{
            mRef.removeValue();
            master.child(id).setValue(selected);
            startActivity(new Intent(this,MainActivity.class));
        }

    }

}
