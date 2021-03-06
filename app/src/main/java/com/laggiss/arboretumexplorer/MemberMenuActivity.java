package com.laggiss.arboretumexplorer;

import android.content.Intent;
import android.view.View;

/**
 * Created by hjx on 2017-08-22.
 */

public class MemberMenuActivity extends AbstractMenuActivity {

    public void startAddedTreesActivity(View v){
        Intent nIntent = new Intent(this, MemberMyTreesActivity.class);
        nIntent.putExtra("type", "add");
        startActivity(nIntent);
    }

    public void startEditedTreesActivity(View v){
        Intent nIntent = new Intent(this, MemberMyTreesActivity.class);
        nIntent.putExtra("type", "edit");
        startActivity(nIntent);
    }

    public void startDeletedTreesActivity(View v){
        Intent nIntent = new Intent(this, MemberMyTreesActivity.class);
        nIntent.putExtra("type", "delete");
        startActivity(nIntent);
    }
}
