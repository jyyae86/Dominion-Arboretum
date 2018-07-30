package com.laggiss.arboretumexplorer;


import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class UploadTreeActivity extends AbstractEditTreeActivity {

    public void addOrEditTree(View v){
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

    public void populateFields(String id){
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("lat", 0.0);
        double longitude = intent.getDoubleExtra("lng", 0.0);
        lat.setText(Double.toString(latitude));
        lng.setText(Double.toString(longitude));
        family.setVisibility(View.GONE);
        familiarName.setVisibility(View.GONE);
        rank.setVisibility(View.GONE);
        hybridCross.setVisibility(View.GONE);
        nameStatus.setVisibility(View.GONE);
        authority.setVisibility(View.GONE);
        dateIntro.setVisibility(View.GONE);
        accessNo.setVisibility(View.GONE);
        recdFrom.setVisibility(View.GONE);
        dateRecd.setVisibility(View.GONE);
        howRecd.setVisibility(View.GONE);
        numRecd.setVisibility(View.GONE);
        nameRecd.setVisibility(View.GONE);
        commonName.setVisibility(View.GONE);
        nomCommun.setVisibility(View.GONE);
        nursery.setVisibility(View.GONE);
        donor.setVisibility(View.GONE);
        collSeed.setVisibility(View.GONE);
        sourceAcc.setVisibility(View.GONE);
        revised.setVisibility(View.GONE);
        numberNow.setVisibility(View.GONE);
        origins.setVisibility(View.GONE);
        herbSpec.setVisibility(View.GONE);
        idByDate.setVisibility(View.GONE);
        photo1.setVisibility(View.GONE);
        photo2.setVisibility(View.GONE);
        mortInfo.setVisibility(View.GONE);
        notes.setVisibility(View.GONE);
        memo.setVisibility(View.GONE);

        familyText.setVisibility(View.GONE);
        familiarNameText.setVisibility(View.GONE);
        rankText.setVisibility(View.GONE);
        hybridCrossText.setVisibility(View.GONE);
        nameStatusText.setVisibility(View.GONE);
        authorityText.setVisibility(View.GONE);
        dateIntroText.setVisibility(View.GONE);
        accessNoText.setVisibility(View.GONE);
        recdFromText.setVisibility(View.GONE);
        dateRecdText.setVisibility(View.GONE);
        howRecdText.setVisibility(View.GONE);
        numRecdText.setVisibility(View.GONE);
        nameRecdText.setVisibility(View.GONE);
        commonNameText.setVisibility(View.GONE);
        nomCommunText.setVisibility(View.GONE);
        nurseryText.setVisibility(View.GONE);
        donorText.setVisibility(View.GONE);
        collSeedText.setVisibility(View.GONE);
        sourceAccText.setVisibility(View.GONE);
        revisedText.setVisibility(View.GONE);
        numberNowText.setVisibility(View.GONE);
        originsText.setVisibility(View.GONE);
        herbSpecText.setVisibility(View.GONE);
        idByDateText.setVisibility(View.GONE);
        photo1Text.setVisibility(View.GONE);
        photo2Text.setVisibility(View.GONE);
        mortInfoText.setVisibility(View.GONE);
        notesText.setVisibility(View.GONE);
        memoText.setVisibility(View.GONE);
    }

    public void startMainActivity(View v){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
