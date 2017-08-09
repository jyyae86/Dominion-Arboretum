package com.laggiss.arboretumexplorer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.view.View;

/**
 * Created by jyyae86 on 2017-06-07.
 */

public class RemoteTreeInfoActivity extends AbstractTreeInfoActivity {
    @Override
    public void populateInfo(String id){
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
        finish();
        startActivity(new Intent(this,AdminTreesActivity.class));
    }

    public void startEditTreeActivity(View v){
        finish();
        Intent nIntent = new Intent(this, EditTreeActivity.class);
        nIntent.putExtra("id", id);
        startActivity(nIntent);
    }

    public void mergeTree(View v){
        mRef.removeValue();
        DatabaseReference masterDB = FirebaseDatabase.getInstance().getReference().child("master");
        String ref = masterDB.push().getKey();
        masterDB.child(ref).setValue(selected);
        finish();
        startActivity(new Intent(this,AdminTreesActivity.class));
    }
}
