package com.laggiss.arboretumexplorer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jyyae86 on 2017-06-19.
 */
 interface FirebaseTreeHandler {
    public void onTreeReceived(Tree tree);
}

public class FirebaseDatabaseUtility {
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private Tree selected;

    public FirebaseDatabaseUtility(){
        this.mDB = FirebaseDatabase.getInstance();
        this.mRef = mDB.getReference();
    }

    public FirebaseDatabaseUtility(String ref){
        this.mDB = FirebaseDatabase.getInstance();
        this.mRef = mDB.getReference().child(ref);
    }

//    public void goToChild(String ref){
//        mRef = mRef.child(ref);
//    }

    public void getTree(String id, final FirebaseTreeHandler handler){
        mRef = mRef.child(id);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tree n = dataSnapshot.getValue(Tree.class);
                handler.onTreeReceived(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setMaster(){
        mRef =  mDB.getReference().child("master");
    }

    public void setUserAddedTrees(){
        mRef = mDB.getReference().child("userAddedTrees");
    }

    public void setUserDeletedTrees(){
        mRef = mDB.getReference().child("userDeletedTrees");
    }

    public void setUserEditedTrees(){
        mRef = mDB.getReference().child("userEditedTrees");
    }

    public DatabaseReference getRef(){
        return mRef;
    }

    public FirebaseDatabase getDatabase(){
        return mDB;
    }

    public void addTree(final FirebaseTreeHandler handler){
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Tree n = dataSnapshot.getValue(Tree.class);
                handler.onTreeReceived(n);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
