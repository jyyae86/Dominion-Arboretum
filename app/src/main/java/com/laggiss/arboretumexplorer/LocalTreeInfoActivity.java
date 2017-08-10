package com.laggiss.arboretumexplorer;

import android.content.Intent;
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
        Button merge = (Button)findViewById(R.id.buttonMerge);
        merge.setVisibility(View.GONE);
    }

    public void startEditTreeActivity(View v){
        Intent nIntent = new Intent(this, EditMasterActivity.class);
        nIntent.putExtra("id", id);
        startActivity(nIntent);
    }

    public void deleteTree(View v){
        DatabaseReference usrDeletedTree = FirebaseDatabase.getInstance().getReference().child("userDeletedTrees");
        usrDeletedTree.child(id).setValue(SQLiteDB.getTreeFromSQL(id));
        SQLiteDB.deleteTree(id);
        startActivity(new Intent(this, MainActivity.class));
    }
}
