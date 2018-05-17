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
        Tree nTree = mDBHelper.getTreeFromSQL(id);
//        creatorName.setText(nTree.getCreatorName());
//        commonName.setText(nTree.getCommonName());
//        latitude.setText(Double.toString(nTree.getLat()));
//        longitude.setText(Double.toString(nTree.getLng()));
//        scientificName.setText(nTree.getSciName());
//        crownArea.setText(nTree.getCrownArea());
    }

    protected void addOrEditTree(View v){
//        String comName = commonName.getText().toString();
//        String creName = creatorName.getText().toString();
//        String cArea = crownArea.getText().toString();
//        String sciName = scientificName.getText().toString();
//        double lat = Double.parseDouble(latitude.getText().toString());
//        double lng = Double.parseDouble(longitude.getText().toString());
//        Tree nTree = new Tree(creName,comName,sciName,cArea,DataBaseHelper.EDIT,lat,lng,firebaseID);

//        mDBHelper.editTree(nTree, firebaseID);
        Intent intent = new Intent(this, LocalTreeInfoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", firebaseID);
        startActivity(intent);

    }
}
