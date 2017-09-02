package com.laggiss.arboretumexplorer;

import android.content.Context;
import android.provider.ContactsContract;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by jyyae86 on 2017-06-19.
 */

interface FirebaseListHandler {
    public void onListReceived(ArrayList<String> list);
}

 interface FirebaseTreeHandler {
    public void onTreeReceived(Tree tree);
}

public class FirebaseDatabaseUtility {
    private FirebaseDatabase mDB;
    private DatabaseReference root;
    private DatabaseReference mRef;
    private Tree selected;
    private static FirebaseDatabaseUtility instance;

    private DataBaseHelper myDBHelper;

    private LinkedList<String> genera = new LinkedList<String>();
    public static FirebaseDatabaseUtility getInstance(){
        return instance;
    }

    public FirebaseDatabaseUtility(Context context){
        this.mDB = FirebaseDatabase.getInstance();
        this.mRef = mDB.getReference();
        this.root = mDB.getReference();
        this.myDBHelper = DataBaseHelper.getInstance(context);

        DatabaseReference master = root.child("master");
        master.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Tree t = dataSnapshot.getValue(Tree.class);
                int i = myDBHelper.addTreeToMaster(t);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Tree t = dataSnapshot.getValue(Tree.class);
                myDBHelper.editTree(t, t.getFirebaseID());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Tree t = dataSnapshot.getValue(Tree.class);
                myDBHelper.removeTreeFromMyTrees(t.getFirebaseID());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //add listeners for added, editted and deleted trees

        DatabaseReference userAddedTrees = root.child("userAddedTrees");
        userAddedTrees.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Tree selected = dataSnapshot.getValue(Tree.class);
                myDBHelper.removeTreeFromMyTrees(selected.getFirebaseID());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference userEditedTrees = root.child("userEditedTrees");
        userEditedTrees.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Tree selected = dataSnapshot.getValue(Tree.class);
                myDBHelper.removeTreeFromMyTrees(selected.getFirebaseID());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference userDeletedTrees = root.child("userDeletedTrees");
        userDeletedTrees.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Tree selected = dataSnapshot.getValue(Tree.class);
                myDBHelper.removeTreeFromMyTrees(selected.getFirebaseID());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.child("genera").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                genera.add(dataSnapshot.getValue(String.class));
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

        this.instance = this;
    }

    public void getTreeFromMaster(String id, final FirebaseTreeHandler handler){
        DatabaseReference master = root.child("master").child(id);
        master.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getTree(String id, final FirebaseTreeHandler handler){
        DatabaseReference tmpRef = mRef.child(id);
        tmpRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void addTreeToMaster(String id, Tree tree){
        root.child("master").child(id).setValue(tree);

    }

    public void checkIfGeneraExists(String s){
        boolean exist = false;
        for(String sciName : genera){
            if(s.contains(sciName)){
                exist = true;
                break;
            }
        }
        if(!exist){
            genera.add(s.split(" ")[0]);
            root.child("genera").setValue(genera);
        }
    }

    public void deleteTree(String id){
        mRef.child(id).removeValue();
    }

    public void deleteTreeInMaster(String id){
        root.child("master").child(id).removeValue();
    }

    public void deleteAllWithID(final String id, String type){
        final DatabaseReference reference;
        if(type.equals("edit")){
            reference = FirebaseDatabase.getInstance().getReference().child("userEditedTrees");
        }else{//delete
            reference = FirebaseDatabase.getInstance().getReference().child("userDeletedTrees");
        }
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Map<String, Object>>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Map<String,Object>>>() {};
                Map<String, Map<String,Object>> map = dataSnapshot.getValue(genericTypeIndicator );
                for(String k : map.keySet()){
                    Map<String, Object> t = map.get(k);
                    String tmp = t.get("firebaseID").toString();
                    if(tmp.equals(id)){
                        reference.child(k).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Query query = reference.orderByChild("firebaseID").equalTo(id);
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                reference.child(dataSnapshot.getKey()).removeValue();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
