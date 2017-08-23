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
    public void populateInfo(String id) {
        if (type.equals("add")){
            mFirebaseUtil.setUserAddedTrees();
        } else if ( type.equals("edit") ) {
            mFirebaseUtil.setUserEditedTrees();
        } else {
            //delete
            mFirebaseUtil.setUserDeletedTrees();
        }

        mFirebaseUtil.getTree(id, new FirebaseTreeHandler() {
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
        mRef.removeValue();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void startEditTreeActivity(View v){
        Intent nIntent = new Intent(this, EditTreeActivity.class);
        nIntent.putExtra("id", id);
        startActivity(nIntent);
    }

//    public void mergeTree(View v){
//        if (type.equals("delete")) {
//            mRef.removeValue();
//            master.child(id).removeValue();
//        } else {
//            mRef.removeValue();
//            master.child(id).setValue(selected);
//            startActivity(new Intent(this,MainActivity.class));
//        }
//
//    }

}
