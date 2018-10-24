package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jyyae86 on 2017-06-07.
 */

public class RemoteTreeInfoActivity extends AbstractTreeInfoActivity {
    @Override
    public void populateInfo(String id) {
        FirebaseDatabaseUtility n = new FirebaseDatabaseUtility(this);
        editTree.setEnabled(false);
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
                selected = tree;
                family.setText(tree.getFamily());
                familiarName.setText(tree.getFamiliarName());
                genus.setText(tree.getGenus());
                species.setText(tree.getSpecies());
                rank.setText(tree.getRank());
                treeType.setText(tree.getType());
                hybridCross.setText(tree.getHybridCross());
                cultivar.setText(tree.getCultivar());
                nameStatus.setText(tree.getStringNameStatus());
                authority.setText(tree.getAuthority());
                dateIntro.setText(tree.getDateIntro());
                accessNo.setText(tree.getAccessNo());
                recdFrom.setText(tree.getRecdFrom());
                dateRecd.setText(tree.getDateRecd());
                howRecd.setText(tree.getHowRecd());
                numRecd.setText(tree.getNumRecd());
                nameRecd.setText(tree.getNameRecd());
                commonName.setText(tree.getCommonName());
                nomCommun.setText(tree.getNomCommun());
                nursery.setText(tree.getNursery());
                location.setText(tree.getLocation());
                lat.setText(Double.toString(tree.getLat()));
                lng.setText(Double.toString(tree.getLng()));
                donor.setText(tree.getStringDonor());
                collSeed.setText(tree.getCollSeed());
                sourceAcc.setText(tree.getSourceAcc());
                revised.setText(tree.getRevised());
                numberNow.setText(tree.getNumberNow());
                origins.setText(tree.getOrigins());
                herbSpec.setText(tree.getHerbSpec());
                idByDate.setText(tree.getIdByDate());
                photo1.setText(tree.getPhoto1());
                photo2.setText(tree.getPhoto2());
                mortInfo.setText(tree.getMortInfo());
                notes.setText(tree.getNotes());
                memo.setText(tree.getMemo());
            }
        });


//
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
        if (type.equals("add")){
            FirebaseDatabaseUtility.getInstance().getTree(id, new FirebaseTreeHandler() {
                @Override
                public void onTreeReceived(Tree tree) {
                    FirebaseDatabaseUtility.getInstance().addTreeToMaster(id, tree);
                    FirebaseDatabaseUtility.getInstance().deleteTree(id);
                }
            });
        } else {
            FirebaseDatabaseUtility.getInstance().deleteAllWithID(selected.getFirebaseID(), type);
        }
//        if (type.equals("delete")) {
//            FirebaseDatabaseUtility.getInstance().deleteAllWithID(id, type);
//        } else if (type.equals("edit")) {
//            FirebaseDatabaseUtility.getInstance().deleteAllWithID(id, type);
//            FirebaseDatabaseUtility.getInstance().getTree(id, new FirebaseTreeHandler() {
//                @Override
//                public void onTreeReceived(Tree tree) {
//                    FirebaseDatabaseUtility.getInstance().addTreeToMaster(id, tree);
//                    FirebaseDatabaseUtility.getInstance().deleteTree(id);
//                }
//            });
//        } else {
//            FirebaseDatabaseUtility.getInstance().checkIfGeneraExists(selected.getSciName());
//            FirebaseDatabaseUtility.getInstance().getTree(id, new FirebaseTreeHandler() {
//                @Override
//                public void onTreeReceived(Tree tree) {
//                    FirebaseDatabaseUtility.getInstance().addTreeToMaster(id, tree);
//                    FirebaseDatabaseUtility.getInstance().deleteTree(id);
//                }
//            });
//        }
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

}
