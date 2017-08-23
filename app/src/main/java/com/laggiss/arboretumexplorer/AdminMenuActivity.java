package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminMenuActivity extends AbstractMenuActivity {

    protected void startAddedTreesActivity(View v){
        Intent nIntent = new Intent(this, AdminTreesActivity.class);
        nIntent.putExtra("type", "add");
        startActivity(nIntent);
    }

    protected void startEditedTreesActivity(View v){
        Intent nIntent = new Intent(this, AdminTreesActivity.class);
        nIntent.putExtra("type", "edit");
        startActivity(nIntent);
    }

    protected void startDeletedTreesActivity(View v){
        Intent nIntent = new Intent(this, AdminTreesActivity.class);
        nIntent.putExtra("type", "delete");
        startActivity(nIntent);
    }
}
