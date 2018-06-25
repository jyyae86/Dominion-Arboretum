package com.laggiss.arboretumexplorer;


import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class UploadTreeActivity extends AbstractEditTreeActivity {

    protected void addOrEditTree(View v){
        progressDialog.setMessage("loading, please wait");
        progressDialog.show();

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

        String firebaseID = mRef.push().getKey();

        Tree nTree = new Tree( familyString,  famNameString,  genusString,  speciesString,  rankString,
                 typeString,  hCrossString,  culString,  nameStatString,  authString,
                 dIntroString,  aNumString,  rFromString,  dRecdString,  hRecdString,
                 numRecdString,  nameRecdString,  cNameString,  nCommunString,  nurString,
                 locString,  donorString,  cSeedString,  sAccString,  revisedString,
                 numNowString,  originsString,  hSpecString,  idByDateString,  p1String,
                 p2String,  mInfoString,  notesString,  memoString, lat,  lng,
         firebaseID, DataBaseHelper.ADD);
        mDBHelper.addTreeToMaster(nTree);
        mDBHelper.addTreeToMyTrees(nTree);
        Log.e("firebaseID", firebaseID);
        Log.e("num of trees in myTrees", Integer.toString(mDBHelper.getMyTreesCount()));

        progressDialog.dismiss();
        Toast.makeText(this,"finished",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,MainActivity.class));
    }

    protected void populateFields(String id){
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 0.0);
        double lng = intent.getDoubleExtra("lng", 0.0);
//        latitude.setText(Double.toString(lat));
//        longitude.setText(Double.toString(lng));
    }

    public void startMainActivity(View v){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
