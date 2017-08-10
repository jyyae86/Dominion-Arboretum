package com.laggiss.arboretumexplorer;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jyyae86 on 2017-06-19.
 */

public class FirebaseDatabaseUtility {
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;

    public FirebaseDatabaseUtility(){
        this.mDB = FirebaseDatabase.getInstance();
        this.mRef = mDB.getReference();
    }

    public DatabaseReference getRef(){
        return mRef;
    }

    public FirebaseDatabase getDatabase(){
        return mDB;
    }
}
