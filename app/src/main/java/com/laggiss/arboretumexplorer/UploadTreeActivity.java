package com.laggiss.arboretumexplorer;


import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class UploadTreeActivity extends AbstractEditTreeActivity {

    protected void addOrEditTree(View v){
        progressDialog.setMessage("loading, please wait");
        progressDialog.show();

        String creator = creatorName.getText().toString();
        String common = commonName.getText().toString();
        String latString = latitude.getText().toString();
        String lngString = longitude.getText().toString();
        String sciName = scientificName.getText().toString();
        String cArea = crownArea.getText().toString();
        double lat = Double.parseDouble(latString);
        double lng = Double.parseDouble(lngString);
        DataBaseHelper SQLiteDB = DataBaseHelper.getInstance(this);

        String tempRef = mRef.push().getKey();

        Tree nTree = new Tree(creator, common, sciName, cArea, DataBaseHelper.ADD, lat, lng, tempRef);

        //for MyTrees
        ContentValues values = new ContentValues();
        values.put("creatorName",creator);
        values.put("commonName", common);
        values.put("sciName", sciName);
        values.put("crownArea", cArea);
        values.put("Lat", lat);
        values.put("Lng", lng);
        values.put("changeType", DataBaseHelper.ADD);
        values.put("firebaseID", tempRef);


        long e = SQLiteDB.getMyDataBase().insert("MyTrees", null, values);
        long f = SQLiteDB.getMyDataBase().insert("ArboretumData", null, values);

        progressDialog.dismiss();
        Toast.makeText(this,"finished",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,MainActivity.class));
    }

    protected void populateFields(String id){
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 0.0);
        double lng = intent.getDoubleExtra("lng", 0.0);
        latitude.setText(Double.toString(lat));
        longitude.setText(Double.toString(lng));
    }

    public void startMainActivity(View v){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
