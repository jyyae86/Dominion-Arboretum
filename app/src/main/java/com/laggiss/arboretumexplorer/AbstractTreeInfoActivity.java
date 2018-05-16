package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by jyyae86 on 2017-06-07.
 * 
 */

public abstract class AbstractTreeInfoActivity extends AppCompatActivity {
    TextView family, familiarName, genus, species, rank,
            treeType, hybridCross, cultivar, nameStatus, authority,
            dateIntro, accessNo, recdFrom, dateRecd, howRecd,
            numRecd, nameRecd, commonName, nomCommun, nursery,
            location, lat, lng, donor, collSeed, sourceAcc, revised,
            numberNow, origins, herbSpec, idByDate, photo1, photo2, mortInfo, notes, memo;
    String id;

    Button editTree;
    Button deleteTree;
    Button mergeTree;

    Tree selected;

    DatabaseReference mRef;

    DataBaseHelper SQLiteDB;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SQLiteDB = DataBaseHelper.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_info);

        editTree = (Button)findViewById(R.id.buttonEditTree);
//        editTree.setEnabled(false);
        deleteTree = (Button)findViewById(R.id.buttonDeleteTree);
        mergeTree = (Button)findViewById(R.id.buttonMergeTree);

        family = (TextView)findViewById(R.id.textViewFamily);
        familiarName = (TextView)findViewById(R.id.textViewFamiliarName);
        genus = (TextView)findViewById(R.id.textViewGenus);
        species = (TextView)findViewById(R.id.textViewSpecies);
        rank = (TextView)findViewById(R.id.textViewRank);
        treeType = (TextView)findViewById(R.id.textViewType);
        hybridCross = (TextView)findViewById(R.id.textViewHybridCross);
        cultivar = (TextView)findViewById(R.id.textViewCultivar);
        nameStatus = (TextView)findViewById(R.id.textViewNameStatus);
        authority = (TextView)findViewById(R.id.textViewAuthority);
        dateIntro = (TextView)findViewById(R.id.textViewDateIntro);
        accessNo = (TextView)findViewById(R.id.textViewAccessno);
        recdFrom = (TextView)findViewById(R.id.textViewRecdFrom);
        dateRecd = (TextView)findViewById(R.id.textViewDateRecd);
        howRecd = (TextView)findViewById(R.id.textViewHowRecd);
        numRecd = (TextView)findViewById(R.id.textViewNumRecd);
        nameRecd = (TextView)findViewById(R.id.textViewNameRecd);
        commonName = (TextView)findViewById(R.id.editTextCommonName);
        nomCommun = (TextView)findViewById(R.id.textViewNomCommun);
        nursery = (TextView)findViewById(R.id.textViewNursery);
        location = (TextView)findViewById(R.id.textViewLocation);
        donor = (TextView)findViewById(R.id.textViewDonor);
        collSeed = (TextView)findViewById(R.id.textViewCollseed);
        sourceAcc = (TextView)findViewById(R.id.textViewSourceAcc);
        revised = (TextView)findViewById(R.id.textViewRevised);
        numberNow = (TextView)findViewById(R.id.textViewNumberNow);
        origins = (TextView)findViewById(R.id.textViewOrigins);
        herbSpec = (TextView)findViewById(R.id.textViewHerbSpec);
        idByDate = (TextView)findViewById(R.id.textViewIDByDate);
        photo1 = (TextView)findViewById(R.id.textViewPhoto1);
        photo2 = (TextView)findViewById(R.id.textViewPhoto2);
        mortInfo = (TextView)findViewById(R.id.textViewMortInfo);
        notes = (TextView)findViewById(R.id.textViewNotes);
        memo = (TextView)findViewById(R.id.textViewMemo);


        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");

        populateInfo(id);

    }

    //some abstract methods to be defined in subclasses
    public void populateInfo(String id){}

    public void deleteTree(View v){}

    public void startEditTreeActivity(View v){}

    public void startMainActivity(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}