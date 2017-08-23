package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jyyae86 on 2017-06-15.
 */

public class EditTreeActivity extends AbstractEditTreeActivity{

    protected void populateFields(String id){
        addTree.setText("Edit Tree");

        Tree nTree = mDBHelper.getTreeFromMyTrees(id);
        creatorName.setText(nTree.getCreatorName());
        commonName.setText(nTree.getCommonName());
        latitude.setText(Double.toString(nTree.getLat()));
        longitude.setText(Double.toString(nTree.getLng()));
        scientificName.setText(nTree.getSciName());
        crownArea.setText(nTree.getCrownArea());

    }

    protected void addOrEditTree(View v){
        String comName = commonName.getText().toString();
        String creName = creatorName.getText().toString();
        String cArea = crownArea.getText().toString();
        String sciName = scientificName.getText().toString();
        double lat = Double.parseDouble(latitude.getText().toString());
        double lng = Double.parseDouble(longitude.getText().toString());
        Tree nTree = new Tree(creName,comName,sciName,cArea,DataBaseHelper.ADD,lat,lng,firebaseID);
        mDBHelper.editTree(nTree, firebaseID);
        startActivity(new Intent(this, MainActivity.class));

    }
}
