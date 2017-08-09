package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    protected void startAdminAddedTreesActivity(View v){
        finish();
        Intent nIntent = new Intent(this, AdminTreesActivity.class);
        nIntent.putExtra("type", "add");
        startActivity(nIntent);
    }

    protected void startAdminEditedTreesActivity(View v){
        finish();
        Intent nIntent = new Intent(this, AdminTreesActivity.class);
        nIntent.putExtra("type", "edit");
        startActivity(nIntent);
    }

    protected void startAdminDeletedTreesActivity(View v){
        finish();
        Intent nIntent = new Intent(this, AdminTreesActivity.class);
        nIntent.putExtra("type", "delete");
        startActivity(nIntent);
    }
}
