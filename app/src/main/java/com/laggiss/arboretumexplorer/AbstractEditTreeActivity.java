package com.laggiss.arboretumexplorer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jyyae86 on 2017-06-08.
 */

public abstract class AbstractEditTreeActivity extends AppCompatActivity{
    EditText family, familiarName, genus, species, rank, typeTree,
        hybridCross, cultivar, nameStatus, authority, dateIntro,
        accessNo, recdFrom, dateRecd, howRecd, numRecd, nameRecd,
        commonName, nomCommun, nursery, location, lat, lng, donor,
        collSeed, sourceAcc, revised, numberNow, origins, herbSpec,
        idByDate, photo1, photo2, mortInfo, notes, memo;

    ProgressDialog progressDialog;
    Button addTree;

    String firebaseID;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DataBaseHelper mDBHelper;
    String type;

    Button latitude;
    Button longitutde;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final long INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent nIntent = getIntent();
        try{
            firebaseID = nIntent.getStringExtra("id");
            type = nIntent.getStringExtra("type");
        }catch(NullPointerException e){

        }


        mDBHelper = DataBaseHelper.getInstance(this);

        setContentView(R.layout.activity_upload_tree);

        family = (EditText) findViewById(R.id.editTextFamily);
        familiarName = (EditText) findViewById(R.id.editTextFamiliarName);
        genus = (EditText) findViewById(R.id.editTextGenus);
        species = (EditText) findViewById(R.id.editTextSpecies);
        rank = (EditText) findViewById(R.id.editTextRank);
        typeTree = (EditText) findViewById(R.id.editTextType);
        hybridCross = (EditText) findViewById(R.id.editTextHybridCross);
        cultivar = (EditText) findViewById(R.id.editTextCultivar);
        nameStatus = (EditText) findViewById(R.id.editTextNameStatus);
        authority = (EditText) findViewById(R.id.editTextAuthority);
        dateIntro = (EditText) findViewById(R.id.editTextDateIntro);
        accessNo = (EditText) findViewById(R.id.editTextAccessno);
        recdFrom = (EditText) findViewById(R.id.editTextRecdFrom);
        dateRecd = (EditText) findViewById(R.id.editTextDateRecd);
        howRecd = (EditText) findViewById(R.id.editTextHowRecd);
        numRecd = (EditText) findViewById(R.id.editTextNumRecd);
        nameRecd = (EditText) findViewById(R.id.editTextNameRecd);
        commonName = (EditText) findViewById(R.id.editTextCommonName);
        nomCommun = (EditText) findViewById(R.id.editTextNomCommun);
        nursery = (EditText) findViewById(R.id.editTextNursery);
        location = (EditText) findViewById(R.id.editTextLocation);
        lat = (EditText) findViewById(R.id.editTextLat);
        lng = (EditText) findViewById(R.id.editTextLng);
        donor = (EditText) findViewById(R.id.editTextDonor);
        collSeed = (EditText) findViewById(R.id.editTextCollSeed);
        sourceAcc = (EditText) findViewById(R.id.editTextSourceAcc);
        revised = (EditText) findViewById(R.id.editTextRevised);
        numberNow = (EditText) findViewById(R.id.editTextNumberNow);
        origins = (EditText) findViewById(R.id.editTextOrigins);
        herbSpec = (EditText) findViewById(R.id.editTextHerbSpec);
        idByDate = (EditText) findViewById(R.id.editTextIdByDate);
        photo1 = (EditText) findViewById(R.id.editTextPhoto1);
        photo2 = (EditText) findViewById(R.id.editTextPhoto2);
        mortInfo = (EditText) findViewById(R.id.editTextMortInfo);
        notes = (EditText) findViewById(R.id.editTextNotes);
        memo = (EditText) findViewById(R.id.editTextMemo);

        progressDialog = new ProgressDialog(this);
        addTree = (Button) findViewById(R.id.buttonAdd);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = mDatabase.getReference();

//        if(firebaseID != null){
            populateFields(firebaseID);
//        }


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }
        };
        //provider, minTime, minDistance, locationListener
        long minTime = 1000;
        float minDistance = 0.0f;


    }

    protected abstract void populateFields(String id);

    protected void startMainActivity(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    protected void populateLatAndLng(View v){
//        MyLocation.LocationResult locationResult = new LocationResult(){
//            @Override
//            public void gotLocation(Location location){
//
//            }
//        };

//        MyLocation myLocation = new MyLocation();
//        myLocation.getLocation(this,locationResult);
//    }

//    protected void populateLatAndLng(View v) {
//        double[] lats = new double[10];
//        double[] lngs = new double[10];
//        for(int i = 0; i < 10; i++) {
//            lats[i] = mCurrentLocation.getLatitude();
//            lngs[i] = mCurrentLocation.getLongitude();
//        }
//        double totalLat = 0;
//        double totalLng = 0;
//        for(int i = 0; i < 10; i++){
//            totalLat = totalLat + lats[i];
//            totalLng = totalLng + lngs[i];
//        }
//        latitude.setText(Double.toString(totalLat/10));
//        longitude.setText(Double.toString((totalLng/10)));
//
    }
}
