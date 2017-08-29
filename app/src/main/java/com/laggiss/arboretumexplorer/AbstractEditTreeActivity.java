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
    EditText creatorName;
    EditText commonName;
    EditText latitude;
    EditText longitude;
    EditText scientificName;
    EditText crownArea;
    ProgressDialog progressDialog;
    Button addTree;

    String firebaseID;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DataBaseHelper mDBHelper;

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
        }catch(NullPointerException e){

        }


        mDBHelper = DataBaseHelper.getInstance(this);

        setContentView(R.layout.activity_upload_tree);
        creatorName = (EditText) findViewById(R.id.editTextCreatorName);
        commonName = (EditText) findViewById(R.id.editTextCommonName);
        latitude = (EditText) findViewById(R.id.editTextLat);
        longitude = (EditText) findViewById(R.id.editTextLng);
        scientificName = (EditText) findViewById(R.id.editTextSciName);
        crownArea = (EditText) findViewById(R.id.editTextCrownArea);
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
