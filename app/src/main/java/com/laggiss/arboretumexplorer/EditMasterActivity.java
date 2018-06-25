package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hjx on 2017-08-08.
 */

public class EditMasterActivity extends AbstractEditTreeActivity {
    protected void populateFields(String id){
        addTree.setText("Edit Tree");
        Tree nTree = mDBHelper.getTreeFromSQL(id);
//        creatorName.setText(nTree.getCreatorName());
//        commonName.setText(nTree.getCommonName());
//        latitude.setText(Double.toString(nTree.getLat()));
//        longitude.setText(Double.toString(nTree.getLng()));
//        scientificName.setText(nTree.getSciName());
//        crownArea.setText(nTree.getCrownArea());

        family.setText(nTree.getFamily());
        familiarName.setText(nTree.getFamiliarName());
        genus.setText(nTree.getGenus());
        species.setText(nTree.getSpecies());
        rank.setText(nTree.getRank());
        typeTree.setText(nTree.getType());
        hybridCross.setText(nTree.getHybridCross());
        cultivar.setText(nTree.getCultivar());
        nameStatus.setText(nTree.getNameStatus());
        authority.setText(nTree.getAuthority());
        dateIntro.setText(nTree.getDateIntro());
        accessNo.setText(nTree.getAccessNo());
        recdFrom.setText(nTree.getRecdFrom());
        dateRecd.setText(nTree.getDateRecd());
        howRecd.setText(nTree.getHowRecd());
        numRecd.setText(nTree.getNumRecd());
        nameRecd.setText(nTree.getNameRecd());
        commonName.setText(nTree.getCommonName());
        nomCommun.setText(nTree.getNomCommun());
        nursery.setText(nTree.getNursery());
        location.setText(nTree.getLocation());
        lat.setText(Double.toString(nTree.getLat()));
        lng.setText(Double.toString(nTree.getLng()));
        donor.setText(nTree.getDonor());
        collSeed.setText(nTree.getCollSeed());
        sourceAcc.setText(nTree.getSourceAcc());
        revised.setText(nTree.getRevised());
        numberNow.setText(nTree.getNumberNow());
        origins.setText(nTree.getOrigins());
        herbSpec.setText(nTree.getHerbSpec());
        idByDate.setText(nTree.getIdByDate());
        photo1.setText(nTree.getPhoto1());
        photo2.setText(nTree.getPhoto2());
        mortInfo.setText(nTree.getMortInfo());
        notes.setText(nTree.getNotes());
        memo.setText(nTree.getMemo());
    }

    protected void addOrEditTree(View v){
        String familyString = family.getText().toString();
        String famNameString = familiarName.getText().toString();
        String genusString = genus.getText().toString();
        String speciesString = species.getText().toString();
        String rankString = rank.getText().toString();
        String typeString = typeTree.getText().toString();
        String hCrossString= hybridCross.getText().toString();
        String culString = cultivar.getText().toString();
        String nameStatString = nameStatus.getText().toString();
        String authString = authority.getText().toString();
        String dIntroString = dateIntro.getText().toString();
        String aNumString = accessNo.getText().toString();
        String rFromString = recdFrom.getText().toString();
        String dRecdString = dateRecd.getText().toString();
        String hRecdString = howRecd.getText().toString();
        String numRecdString = numRecd.getText().toString();
        String nameRecdString = nameRecd.getText().toString();
        String cNameString = commonName.getText().toString();
        String nCommunString = nomCommun.getText().toString();
        String nurString = nursery.getText().toString();
        String locString = location.getText().toString();
        String donorString = donor.getText().toString();
        String cSeedString = collSeed.getText().toString();
        String sAccString = sourceAcc.getText().toString();
        String revisedString = revised.getText().toString();
        String numNowString = numberNow.getText().toString();
        String originsString = origins.getText().toString();
        String hSpecString = herbSpec.getText().toString();
        String idByDateString = idByDate.getText().toString();
        String p1String = photo1.getText().toString();
        String p2String = photo2.getText().toString();
        String mInfoString = mortInfo.getText().toString();
        String notesString = notes.getText().toString();
        String memoString = memo.getText().toString();

        String latString = lat.getText().toString();
        String lngString = lng.getText().toString();
        double lat = Double.parseDouble(latString);
        double lng = Double.parseDouble(lngString);


        Tree nTree = new Tree( familyString,  famNameString,  genusString,  speciesString,  rankString,
                typeString,  hCrossString,  culString,  nameStatString,  authString,
                dIntroString,  aNumString,  rFromString,  dRecdString,  hRecdString,
                numRecdString,  nameRecdString,  cNameString,  nCommunString,  nurString,
                locString,  donorString,  cSeedString,  sAccString,  revisedString,
                numNowString,  originsString,  hSpecString,  idByDateString,  p1String,
                p2String,  mInfoString,  notesString,  memoString, lat,  lng,
                firebaseID, DataBaseHelper.EDIT);

        mDBHelper.editTree(nTree, firebaseID);
        Intent intent = new Intent(this, LocalTreeInfoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", firebaseID);
        startActivity(intent);

    }
}
