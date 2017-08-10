package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hjx on 2017-08-08.
 */

public class EditMasterActivity extends AbstractEditTreeActivity {
    protected void populateFields(String id){
        addTree.setText("Edit Tree");

        mRef = mRef.child("master").child(firebaseID);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tree nTree = dataSnapshot.getValue(Tree.class);
                creatorName.setText(nTree.getCreatorName());
                commonName.setText(nTree.getCommonName());
                latitude.setText(Double.toString(nTree.getLat()));
                longitude.setText(Double.toString(nTree.getLng()));
                scientificName.setText(nTree.getSciName());
                crownArea.setText(nTree.getCrownArea());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addOrEditTree(View v){
        String comName = commonName.getText().toString();
        String creName = creatorName.getText().toString();
        String cArea = crownArea.getText().toString();
        String sciName = scientificName.getText().toString();
//        String lat = latitude.getText().toString();
//        String lng = longitude.getText().toString();
        double lat = Double.parseDouble(latitude.getText().toString());
        double lng = Double.parseDouble(longitude.getText().toString());
        Tree nTree = new Tree(creName,comName,sciName,cArea,DataBaseHelper.ADD,lat,lng,firebaseID);
        mDBHelper.editTree(nTree, firebaseID);
        DatabaseReference usrEditedTrees = FirebaseDatabase.getInstance().getReference().child("userEditedTrees");
        usrEditedTrees.child(firebaseID).setValue(nTree);
        startActivity(new Intent(this, MainActivity.class));

    }
}
