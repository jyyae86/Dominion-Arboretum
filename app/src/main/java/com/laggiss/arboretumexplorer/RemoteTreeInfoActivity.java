package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jyyae86 on 2017-06-07.
 */

public class RemoteTreeInfoActivity extends AbstractTreeInfoActivity {
    @Override
    public void populateInfo(String id) {
        mRef = FirebaseDatabase.getInstance().getReference();
        if (type.equals("add")){
            FirebaseDatabaseUtility.getInstance().setUserAddedTrees();
        } else if ( type.equals("edit") ) {
            FirebaseDatabaseUtility.getInstance().setUserEditedTrees();
        } else {
            //delete
            FirebaseDatabaseUtility.getInstance().setUserDeletedTrees();
        }

        FirebaseDatabaseUtility.getInstance().getTree(id, new FirebaseTreeHandler() {
            @Override
            public void onTreeReceived(Tree tree) {
                commonName.setText(tree.getCommonName());
                sciName.setText(tree.getSciName());
                cArea.setText(tree.getCrownArea());
                lat.setText(Double.toString(tree.getLat()));
                lng.setText(Double.toString(tree.getLng()));
                creatorName.setText(tree.getCreatorName());
            }
        });


//        mRef = mFirebaseUtil.getRef();
//        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                selected =  dataSnapshot.getValue(Tree.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void deleteTree(View v){
        FirebaseDatabaseUtility.getInstance().deleteTree(id);
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void startEditTreeActivity(View v){
        Intent nIntent = new Intent(this, EditTreeActivity.class);
        nIntent.putExtra("id", id);
        startActivity(nIntent);
    }

    public void mergeTree(View v){
        if (type.equals("delete")) {
            FirebaseDatabaseUtility.getInstance().deleteTreeInMaster(id);
            FirebaseDatabaseUtility.getInstance().deleteTree(id);
        } else {
            FirebaseDatabaseUtility.getInstance().getTree(id, new FirebaseTreeHandler() {
                @Override
                public void onTreeReceived(Tree tree) {
                    FirebaseDatabaseUtility.getInstance().addTreeToMaster(id, tree);
                    FirebaseDatabaseUtility.getInstance().deleteTree(id);
                }
            });
        }
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

}
