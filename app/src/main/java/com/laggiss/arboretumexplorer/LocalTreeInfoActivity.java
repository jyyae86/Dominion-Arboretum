package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocalTreeInfoActivity extends AbstractTreeInfoActivity {

    @Override
    public void populateInfo(String id){
        //can also be done
        Tree nTree = SQLiteDB.getTreeFromSQL(id);
        commonName.setText(nTree.getCommonName());
        sciName.setText(nTree.getSciName());
        cArea.setText(nTree.getCrownArea());
        lat.setText(Double.toString(nTree.getLat()));
        lng.setText(Double.toString(nTree.getLng()));
        creatorName.setText(nTree.getCreatorName());

//        if(type.equals("add") || type.equals("edit")||type.equals(null)){
//            editTree.setEnabled(true);
//        }

    }

    public void startEditTreeActivity(View v){
        String type = getIntent().getStringExtra("type");
        if(type.equals("master")){
            Intent nIntent = new Intent(this, EditMasterActivity.class);
            nIntent.putExtra("id", id);
            startActivity(nIntent);
        }else{
            Intent nIntent = new Intent(this, EditTreeActivity.class);
            nIntent.putExtra("id", id);
            startActivity(nIntent);
        }

    }

    public void deleteTree(View v){
        DatabaseReference usrDeletedTree = FirebaseDatabase.getInstance().getReference().child("userDeletedTrees");
        usrDeletedTree.child(id).setValue(SQLiteDB.getTreeFromSQL(id));
        SQLiteDB.deleteTree(id);
        startActivity(new Intent(this, MainActivity.class));
    }

    public void mergeTree(View v){
        Tree selected = SQLiteDB.getTreeFromSQL(id);
        //upload to firebase
        if(type.equals("add")){
            DatabaseReference userAddedTrees = FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("userAddedTrees");
            String tmpRef = userAddedTrees.push().getKey();
            userAddedTrees.child(tmpRef).setValue(selected);
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }else if(type.equals("edit")){
            DatabaseReference userEditedTrees = FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("userEditedTrees");
            String tmpRef = userEditedTrees.push().getKey();
            userEditedTrees.child(tmpRef).setValue(selected);
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else{
            //delete
            DatabaseReference userDeletedTrees = FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("userDeletedTrees");
            String tmpRef = userDeletedTrees.push().getKey();
            userDeletedTrees.child(tmpRef).setValue(selected);
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
