package com.laggiss.arboretumexplorer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

public class MainActivity extends FragmentActivity implements
        ControlClass.onGPSChecked,
        ControlClass.onQueryButtonClicked,
        ControlClass.onFollowMeChecked,
        ControlClass.onLatinChecked,
        ControlClass.onSpeciesSpinnerChange,
        ControlClass.onHideButtonClicked,
        ControlClass.onClearButtonClicked,
        ControlClass.onHeatMapChecked,
        ControlClass.onRadiusChecked,
        ControlClass.onRadiusSpinnerChange,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static FragmentManager myFragmentManager;
    final int buttonID = View.generateViewId();
    private GoogleMap mMap;
    private LatLng myClickPt;
    private MapFragment mMapFragment;
    private ControlClass myFragment;
    private ArrayList<LatLng> pts = new ArrayList<LatLng>();
    private ArrayList<Marker> mkr = new ArrayList<Marker>();
    private ArrayList<MarkerOptions> mko = new ArrayList<MarkerOptions>();
    private ArrayList<String> pIds = new ArrayList<String>();
    private HashMap<String, String> mMarkerHash = new HashMap<String, String>();
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng GPSLatLng;
    private boolean useGPS;
    private boolean latinChecked;

    private boolean followChecked;
    private boolean heatMapChecked;
    private boolean radiusChecked;
    private String stringCurrentSpecies;
    ArrayList<LatLng> heatMapList = new ArrayList<LatLng>();
    private static final long INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 1000;
    private String dbaseName = "ArboretumData";
    private DataBaseHelper myDbHelper;
    private SQLiteDatabase arboretum;
    Polyline line;
    private double currRadius;
    private static final String LOCATION_KEY = "GPSLOC";
    private static final String LAST_UPDATED_TIME_STRING_KEY = "LASTGPSTIME";
    private static final String QUERY_BUTTON_WAS_CLICKED = "QUERYCLICKED";
    private static final String CURRENT_SPECIES_SELECTED = "CURRSPECIES";
    private static final String USE_GPS_VALUE = "USEGPS";
    private String mLastUpdateTime;
    private Location mCurrentLocation;
    private Location lastKnownPosition;
    private SharedPreferences sharedPref;
    private int userType;


    //Firebase
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabaseUtility mFirebase = new FirebaseDatabaseUtility();
        mFirebase.setMaster();
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        // Open database

        myDbHelper = new DataBaseHelper(this);//this,dbName,dbPath);
        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

        try {

            arboretum = myDbHelper.getWritableDatabase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        //Log.e("Path isLLLLLLLLL",arboretum.getPath());
        //List<String> x = makeSpeciesList("Acer");

//        ArrayList<Tree> trees = myDbHelper.createPOJO();
//        myDbHelper.createMasterFirebaseDatabase(trees, mRef);
//
//        myDbHelper.loop();

        setContentView(R.layout.activity_main);

        myFragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();

        if (savedInstanceState != null) {
            mMapFragment = (MapFragment) myFragmentManager.findFragmentByTag("map_tag");

            mMap = mMapFragment.getMap();
            myFragment = (ControlClass) myFragmentManager.findFragmentByTag("control_tag");
            fragmentTransaction.commit();
            //mMap=mMapFragment.getMap();

        } else {

            mMapFragment = MapFragment.newInstance();
            fragmentTransaction.add(R.id.map, mMapFragment, "map_tag");

            useGPS = true;

            mMapFragment.setRetainInstance(true);
            myFragment = new ControlClass();
            fragmentTransaction.add(R.id.myfragment, myFragment, "control_tag");
            fragmentTransaction.commit();

            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;

                    setUpMap();
                    //handleNewLocation(lastKnownPosition);

                }
            });

        }

//Log.e("asdf",String.valueOf(isLocationEnabled(this)));

        mLocationRequest = LocationRequest.create()

                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(0.25f)
                .setInterval(INTERVAL)        // 10 seconds, in milliseconds
                .setFastestInterval(FASTEST_INTERVAL); // 1 second, in milliseconds

        //setUpMapIfNeeded();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        if (savedInstanceState != null) {
            //Toast.makeText(this, "savedInstanceState", Toast.LENGTH_LONG).show();
            updateValuesFromBundle(savedInstanceState);

        }
        updateFragment();

        DatabaseReference master = mRef.child("master");
        master.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Tree t = dataSnapshot.getValue(Tree.class);
                int i = myDbHelper.addTreeToMaster(t);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Tree t = dataSnapshot.getValue(Tree.class);
                myDbHelper.editTree(t, t.getFirebaseID());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Tree t = dataSnapshot.getValue(Tree.class);
                myDbHelper.deleteTree(t.getFirebaseID());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
//    public static boolean isLocationEnabled(Context context) {
//        int locationMode = 0;
//        String locationProviders;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            try {
//                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
//
//            } catch (Settings.SettingNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
//
//        }else{
//            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//            return !TextUtils.isEmpty(locationProviders);
//        }
//
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //handleNewLocation(lastKnownPosition);
    }

    private Boolean wasQuery = false;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {

            setUpMap();
        }//else{Log.e("Map was null", " in ONRESUME");}

        //mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            //Log.d(TAG, "Location update resumed .....................");
        }
        // In case Google Play services has since become available.
        ///setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
//                mRequestingLocationUpdates);
        outState.putParcelable(LOCATION_KEY, mCurrentLocation);
        outState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        outState.putBoolean(QUERY_BUTTON_WAS_CLICKED, wasQuery);
        outState.putString(CURRENT_SPECIES_SELECTED, stringCurrentSpecies);
        outState.putBoolean(USE_GPS_VALUE, useGPS);

        super.onSaveInstanceState(outState);

    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
//            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
//                mRequestingLocationUpdates = savedInstanceState.getBoolean(
//                        REQUESTING_LOCATION_UPDATES_KEY);
//                setButtonsEnabledState();
//            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(
                        LAST_UPDATED_TIME_STRING_KEY);
            }
            if (savedInstanceState.keySet().contains(CURRENT_SPECIES_SELECTED)) {
                stringCurrentSpecies = savedInstanceState.getString(
                        CURRENT_SPECIES_SELECTED);
            }

            if (savedInstanceState.keySet().contains(USE_GPS_VALUE)) {
                useGPS = savedInstanceState.getBoolean(
                        USE_GPS_VALUE);
            }

            handleNewLocation(mCurrentLocation);

//            if (savedInstanceState.getBoolean(QUERY_BUTTON_WAS_CLICKED)) {
//                addMarkersFromDb();
//            }
        }
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

        // Check if location services are availalbe and if not put dialog to put on the things.
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 1000);//com.laggiss.arboretumexplorer.MainActivity.this,1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }


            }
        });
        //Log.e(TAG, "Location update started ..............: ");
    }

    @Override
    public void buttonQueryClicked(String link) {
        // updateMap(link);

        if (radiusChecked) {
            pointsWithinRadius(currRadius);
        } else {
//            Log.e("asdfasdf", "Quyery button");
            addMarkersFromDb();
        }


        wasQuery = true;

    }


    private void addMarkersFromDb() {


        String table = dbaseName;
        String[] columns = null;
        String sel = null;
        String[] selargs = null;
        if (!latinChecked) {
            sel = "commonName = ?";
            selargs = new String[]{stringCurrentSpecies.replace("'", "''")};
        }
        if (latinChecked) {
            sel = "sciName = ?";
            selargs = new String[]{stringCurrentSpecies.replace("'", "\'")};
        }


        String orderby = null;


        int myHue = randInt(0, 360);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //try (SQLiteDatabase arboretum = myDbHelper.getWritableDatabase()) {
        //try (Cursor dbCursor = arboretum.rawQuery(sb.toString(), null)) {
        try (Cursor dbCursor = arboretum.query(table, columns, sel, selargs, orderby, null, null, null)) {

            if (dbCursor.moveToFirst()) {

                while (!dbCursor.isAfterLast()) {

                    LatLng cpt = new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6));
//                    Log.e("asdfadfad", selargs[0]);
//                    Log.e("XXXXXXXXXXXXX", String.valueOf(cpt.latitude));
                    if (heatMapChecked) {

                        heatMapList.add(cpt);
                        builder.include(cpt);

                    } else {

                        //Log.e("LTLNG", String.valueOf(cpt.latitude));
                        MarkerOptions thisMarkerOpt = new MarkerOptions()
                                .position(cpt)
                                .title(dbCursor.getString(7))
                                .snippet(dbCursor.getString(dbCursor.getColumnIndex("sciName")))
                                .draggable(true);

                        thisMarkerOpt.icon(BitmapDescriptorFactory.defaultMarker(myHue));
                        if (mMap == null) {

                        }
                        Marker marker = mMap.addMarker(thisMarkerOpt);

                        builder.include(marker.getPosition());


                        mMarkerHash.put(marker.getId(), dbCursor.getString(dbCursor.getColumnIndex("changeType")));


                    }
                    dbCursor.moveToNext();
                }

            }
            dbCursor.close();
            //arboretum.close();
        }


        //}
        finally {
            LatLngBounds bounds = builder.build();
            int padding = 10; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
            //
        }
        if (heatMapChecked) {
            addHeatMap();
        }

    }


    private void setUpMap() {
//        Log.e("setupmap", "was CALLED");
//        mMap.setOnMapLongClickListener(my1_OnMapLongClickListener);
        mMap.setOnMarkerDragListener(this);
        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mapSettings.setZoomControlsEnabled(true);


        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String id = marker.getTitle();
                Intent nIntent = new Intent(getApplicationContext(),LocalTreeInfoActivity.class);
                nIntent.putExtra("id", id);
                nIntent.putExtra("type","master");
                startActivity(nIntent);
                return true;
            }
        });


    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //Log.e("Start latiude 1: ", String.valueOf(marker.getPosition().latitude));
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {


        marker.setPosition(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
        String x;
        x = marker.getId();
        String parseID = mMarkerHash.get(x);
//        Log.e("After drag latiude: ", String.valueOf(marker.getPosition().latitude));
        //Toast.makeText(this, parseID, Toast.LENGTH_SHORT).show();
        updatePointInDb(parseID, marker);


    }

    private void addGroundOverlay() {

        BitmapDescriptor image = BitmapDescriptorFactory.fromAsset("density.png"); // get an image.
        LatLngBounds bounds = new LatLngBounds(new LatLng(45.3830218325235, -75.7106853758106), new LatLng(45.3948806250693, -75.6985211746628)); // get a bounds
        // Adds a ground overlay with 50% transparency.
        GroundOverlay groundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(image)
                .positionFromBounds(bounds)
                .transparency((float) 0.50));
        //Log.e("Add Ground Overlay ", "was CALLED");
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        myClickPt = latLng;


    }

    @Override
    public void onConnected(Bundle bundle) {

        lastKnownPosition = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (lastKnownPosition != null) {
            handleNewLocation(lastKnownPosition);
        }
        startLocationUpdates();

    }

    private void updatePointInDb(String mMarkerParseId, Marker marker) {
//        Log.e("After  update: ", String.valueOf(marker.getPosition().latitude));


        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(dbaseName).append(" SET Lat = ");
        sb.append(String.valueOf(marker.getPosition().latitude));
        sb.append(",");
        sb.append("Lng = ");
        sb.append(String.valueOf(marker.getPosition().longitude));
        sb.append(" ");
        sb.append("WHERE changeType = '");
        sb.append(mMarkerParseId).append("';");
//        Log.e("Query String: ", sb.toString());
        // arboretum.update("ArboretumData",)
        arboretum.execSQL(sb.toString());
        //arboretum.close();


    }

    private void followMe() {

        double radius = 50;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(GPSLatLng, 21));
        //mMap.moveCamera();

        LatLongOffset x = new LatLongOffset(GPSLatLng, radius);


        String table = dbaseName;
        String[] columns = null;
        String sel = "Lat > ? AND Lat < ? AND Lng > ? AND Lng < ?";
        String[] selargs = new String[]{String.valueOf(x.getMinLat()), String.valueOf(x.getMaxLat()), String.valueOf(x.getMinLong()), String.valueOf(x.getMaxLong())};
        String orderby = null;

        ArrayList darray = new ArrayList();

        // try (Cursor dbCursor = arboretum.rawQuery(sb.toString(), null)) {
        try (Cursor dbCursor = arboretum.query(table, null, sel, selargs, null, null, null, null)) {
            if (dbCursor.moveToFirst()) {
                while (!dbCursor.isAfterLast()) {

                    LatLng dbPt = new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6));

                    darray.add((double) computeDistanceBetween(dbPt, GPSLatLng));


                    dbCursor.moveToNext();


                }

                int minIndex = darray.indexOf(Collections.min(darray));
                dbCursor.moveToPosition(minIndex);

                IconGenerator iconFactory = new IconGenerator(this);
                //iconFactory.setRotation(90);

                String name = null;
                if (latinChecked) {
                    name = "sciName";
                } else {
                    name = "commonName";
                }

                MarkerOptions markerOptions = new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(dbCursor.getString(dbCursor.getColumnIndex(name)))))
                        .position(new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6)))
                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

                MarkerOptions thisMarkerOpt = new MarkerOptions()
                        .position(new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6)))
                        .title(dbCursor.getString(dbCursor.getColumnIndex("commonName")))
                        .snippet(dbCursor.getString(dbCursor.getColumnIndex("sciName")))
                        .anchor(0.5f, 0f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pticonsm));

                mMap.addMarker(thisMarkerOpt);
                Marker marker = mMap.addMarker(markerOptions);
                mMarkerHash.put(marker.getId(), dbCursor.getString(dbCursor.getColumnIndex("changeType")));
                if (!(line == null)) {
                    if (line.isVisible()) {
                        line.remove();
                    }
                }
                line = mMap.addPolyline(new PolylineOptions()
                        .add(GPSLatLng, new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6)))
                        .width(5)
                        .color(Color.RED));


                dbCursor.close();
                //arboretum.close();
            }

        }
    }


    private void pointsWithinRadius(double inRadius) {

        double radius = inRadius;

        LatLongOffset x = new LatLongOffset(GPSLatLng, radius);

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(GPSLatLng)
                .radius(radius)
                .strokeColor(Color.RED)
                .strokeWidth(0f)
                .fillColor(Color.argb(75, 240, 240, 140)));

        LatLngBounds bnds = new LatLngBounds(new LatLng(x.getMinLat(), x.getMinLong()), new LatLng(x.getMaxLat(), x.getMaxLong()));
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(new LatLng(x.getMinLat(),x.getMinLong()));
//        builder.include(new LatLng(x.getMaxLat(),x.getMaxLong()));


        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bnds, 15));//newLatLngZoom(GPSLatLng, 20));
        //mMap.moveCamera();


        String table = dbaseName;
        String[] columns = null;


        String sel = null;
        if (!latinChecked) {
            sel = "Lat > ? AND Lat < ? AND Lng > ? AND Lng < ? AND commonName = ?";

        } else {
            sel = "Lat > ? AND Lat < ? AND Lng > ? AND Lng < ? AND sciName = ?";

        }

        String[] selargs = new String[]{String.valueOf(x.getMinLat()), String.valueOf(x.getMaxLat()), String.valueOf(x.getMinLong()), String.valueOf(x.getMaxLong()), stringCurrentSpecies};
        String orderby = null;

        ArrayList darray = new ArrayList();
        IconGenerator iconFactory = new IconGenerator(this);
        // try (Cursor dbCursor = arboretum.rawQuery(sb.toString(), null)) {
        try (Cursor dbCursor = arboretum.query(table, null, sel, selargs, null, null, null, null)) {
            if (dbCursor.moveToFirst()) {
                while (!dbCursor.isAfterLast()) {

                    LatLng dbPt = new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6));

                    if (computeDistanceBetween(dbPt, GPSLatLng) < radius) {
                        if (heatMapChecked) {

                            heatMapList.add(dbPt);


                        } else {
//                        int minIndex = darray.indexOf(Collections.min(darray));
//                        dbCursor.moveToPosition(minIndex);


                            //iconFactory.setRotation(90);
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(dbCursor.getString(dbCursor.getColumnIndex("commonName")))));
//                        markerOptions.position(new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6)));
//                        markerOptions.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

                            MarkerOptions thisMarkerOpt = new MarkerOptions();
                            thisMarkerOpt.position(new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6)));
                            thisMarkerOpt.title(dbCursor.getString(dbCursor.getColumnIndex("sciName")));
                            thisMarkerOpt.snippet(dbCursor.getString(dbCursor.getColumnIndex("commonName")));
                            thisMarkerOpt.anchor(0.5f, 0.5f);
                            thisMarkerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.pticonsm));

                            Marker marker = mMap.addMarker(thisMarkerOpt);

                            // Marker marker = mMap.addMarker(markerOptions);
                            mMarkerHash.put(marker.getId(), dbCursor.getString(dbCursor.getColumnIndex("changeType")));
//                        if (!(line == null)) {
//                            if (line.isVisible()) {
//                                line.remove();
//                            }
//                        }
                            line = mMap.addPolyline(new PolylineOptions()
                                    .add(GPSLatLng, new LatLng(dbCursor.getDouble(5), dbCursor.getDouble(6)))
                                    .width(5)

                                    .color(Color.argb(20, 44, 33, 11)));


                        }
                    }

                    dbCursor.moveToNext();


                }


                dbCursor.close();
                //arboretum.close();
            } else {
                Toast.makeText(this, "No trees found. Increase radius.", Toast.LENGTH_SHORT).show();
            }

        }
        if (heatMapChecked) {
            addHeatMap();
        }
    }

    private void handleNewLocation(Location location) {
        //Log.e(TAG, "Handle LOcation Called");//location.toString());
        //Toast.makeText(this, "Handle Location Called", Toast.LENGTH_SHORT).show();
        if (useGPS) {
            if (location != null) {
                double currentLatitude = location.getLatitude();
                double currentLongitude = location.getLongitude();
                GPSLatLng = new LatLng(currentLatitude, currentLongitude);

                if (location == lastKnownPosition) {

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownPosition.getLatitude(), lastKnownPosition.getLongitude()), 18f));
                    //                Log.e("CAMERA","MODE LAST KL");
                }

                if (followChecked) {
                    followMe();


                }
            }
        }

    }

    public List<String> makeSpeciesList(String inGenera) {

        List<String> labels = new ArrayList<String>();

        String table = dbaseName;
        String[] columns = new String[]{"commonName", "sciName"};
        String sel = "sciName LIKE ?";
        String[] selargs = new String[]{inGenera + "%"};

        String orderby = null;
        if (!latinChecked) {
            orderby = "commonName";
        } else {
            orderby = "sciName";
        }

        // try (Cursor dbCursor = arboretum.query(table, columns, sel, selargs, orderby, null, null, null)) {
        try (Cursor dbCursor = arboretum.query(table, columns, sel, selargs, orderby, null, null, null)) {
            if (dbCursor.moveToFirst()) {
                while (!dbCursor.isAfterLast()) {
                    //Log.e("asdfas",dbCursor.getString(0));
                    if (!latinChecked) {
                        labels.add(dbCursor.getString(0));
                    } else {
                        labels.add(dbCursor.getString(1));
                    }


                    dbCursor.moveToNext();
                }
            }
            dbCursor.close();
        }


        return labels;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        //Log.d(TAG, "Location update stopped .......................");
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
//            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this, "onLocationChanged was called", Toast.LENGTH_SHORT).show();
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        handleNewLocation(location);

    }

    @Override
    public void switchFollowMeChecked(Boolean switchState) {
        followChecked = switchState;

    }

    @Override
    public void speciesSpinnerChanged(String currentSpecies) {
        stringCurrentSpecies = currentSpecies;
        //Toast.makeText(this, stringCurrentSpecies, Toast.LENGTH_LONG).show();
    }

    @Override
    public void buttonHideClicked() {
        resizeFragment();
    }


    @Override
    public void clearButtonClicked() {
        mMap.clear();
        wasQuery = false;

    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private void addHeatMap() {


        Gradient gradient = new HeatMapColors().getGradient(); //Gradient(colors, startPoints);
        // Create a heat map tile provider, passing it the latlngs of the trees.

        if (!heatMapList.isEmpty()) {

            TileProvider mProvider = new HeatmapTileProvider.Builder()
                    .data(heatMapList)
                    .gradient(gradient)
                    .radius(15)
                    .build();
            // Add a tile overlay to the map, using the heat map tile provider.
            TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        } else {
            Toast.makeText(this, "No trees found. Increase radius or change species.", Toast.LENGTH_SHORT).show();
        }

        heatMapList.clear();
    }

    @Override
    public void switchHeatMapChecked(Boolean switchState) {
        heatMapChecked = switchState;
        //Log.e("heat map",String.valueOf(switchState));
    }


    @Override
    public void switchRadiusChecked(Boolean switchState) {


        radiusChecked = switchState;

    }


    @Override
    public void radiusSpinnerChange(String currentRadius) {

        currRadius = Double.parseDouble(currentRadius);

    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    private void resizeFragment() {

        final ViewGroup mRootView = (ViewGroup) findViewById(R.id.layout_root_view);


        TransitionManager.beginDelayedTransition(mRootView, new AutoTransition());

        FragmentTransaction ft = myFragmentManager.beginTransaction();
        ft.hide(myFragment);
        ft.commit();


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMapFragment.getView().getLayoutParams();
        params.gravity = Gravity.FILL;
        params.width = LayoutParams.MATCH_PARENT;
        params.weight = 1;
        View mFrag = findViewById(R.id.map);
        mFrag.setLayoutParams(params);

        final Configuration config = getResources().getConfiguration();


        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            VerticalButton bt = new VerticalButton(this, null);
            LinearLayout.LayoutParams btlayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            bt.setText(R.string.mapSettings);
            bt.setLayoutParams(btlayout);
            mRootView.addView(bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View cFrag = findViewById(R.id.myfragment);
                    LinearLayout.LayoutParams cParams = (LinearLayout.LayoutParams) myFragment.getView().getLayoutParams();
//                    if (config.orientation == config.ORIENTATION_LANDSCAPE) {
                    cParams.width = LayoutParams.WRAP_CONTENT;
//                    }
//                    else {
//                        cParams.height = LayoutParams.WRAP_CONTENT;
//                    }
                    cFrag.setLayoutParams(cParams);

                    FragmentTransaction ft = myFragmentManager.beginTransaction();
                    ft.show(myFragment);
                    ft.commit();

                    mRootView.removeView(v);


                }
            });
        } else {
            Button bt = new Button(this);
            bt.setId(buttonID);
            LinearLayout.LayoutParams btlayout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            bt.setText(R.string.mapSettings);
            bt.setLayoutParams(btlayout);
            mRootView.addView(bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    View cFrag = findViewById(R.id.myfragment);
                    LinearLayout.LayoutParams cParams = (LinearLayout.LayoutParams) myFragment.getView().getLayoutParams();
//                    if (config.orientation == config.ORIENTATION_LANDSCAPE) {
//                        cParams.width = LayoutParams.WRAP_CONTENT;
//                    }
//                    else {
                    cParams.height = LayoutParams.WRAP_CONTENT;
//                    }
                    cFrag.setLayoutParams(cParams);

                    FragmentTransaction ft = myFragmentManager.beginTransaction();
                    ft.show(myFragment);
                    ft.commit();

                    mRootView.removeView(v);

                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchGPSChecked(Boolean switchState) {

        useGPS = switchState;

        if (switchState) {
            handleNewLocation(lastKnownPosition);
        }
    }

    @Override
    public void checkboxLatinChecked(Boolean switchState) {

        latinChecked = switchState;

    }



//    private void setUpMapIfNeeded() {
//
//        if (mMap == null) {
//
//            mMapFragment.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//                    mMap = googleMap;
//                    if (mMap != null) {
//
//                        setUpMap();
//                    }
//                }
//            });
//
//        }
//    }
//public void showHideFrgament(final Fragment fragment) {
//
//    FragmentTransaction ft = getFragmentManager().beginTransaction();
//    ft.setCustomAnimations(android.R.animator.fade_in,
//            android.R.animator.fade_out);
//
//    if (fragment.isHidden()) {
//        ft.show(fragment);
//        Log.d("hidden", "Show");
//    } else {
//        ft.hide(fragment);
//        Log.d("Shown", "Hide");
//    }
//    ft.commit();
//
//}
    public void startUploadTreeActivity(View v){
        startActivity(new Intent(this, UploadTreeActivity.class));

    }

    public void startMyTreesActivity(View v){
        startActivity(new Intent(this, MemberMenuActivity.class));
    }

    public void startAdminMenuActivity(View v){
        startActivity(new Intent(this, AdminMenuActivity.class));
    }

    public void updateFragment(){
       if(mUser == null){
           myFragment.showButtons(0);
       }else{
           mRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   String id = mUser.getUid();
                   userType = dataSnapshot.child("users").child(id).getValue(Integer.class);
                   myFragment.showButtons(userType);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
       }
    }

    public void signOut(View v){
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, EmailLoginActivity.class));
    }

//    public void updateDB(View v){
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("downloading...");
//        progressDialog.show();
//        myDbHelper.clearDB();
//        DatabaseReference master = FirebaseDatabase.getInstance().getReference().child("master");
//        master.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, Object> masterDB = (Map<String, Object>) dataSnapshot.getValue();
//                for(String k : masterDB.keySet()) {
//                    Map<String, Object> m1 = (Map<String, Object>)masterDB.get(k);
//                    ContentValues cv = new ContentValues();
//                    for(String k1 : m1.keySet()) {
//                        try{
//                            cv.put(k1, (String)m1.get(k1));
//                        }catch(Exception e){
//                            try{
//                                cv.put(k1, (double)m1.get(k1));
//                            }catch(Exception f){
//                                cv.put(k1, (long)m1.get(k1));
//                            }
//                        }
//                    }
//                    myDbHelper.addRowToMaster(cv);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        progressDialog.dismiss();
//    }
}


