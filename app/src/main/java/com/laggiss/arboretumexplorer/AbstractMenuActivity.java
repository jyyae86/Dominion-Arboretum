package com.laggiss.arboretumexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hjx on 2017-08-22.
 */

public abstract class AbstractMenuActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    abstract void startAddedTreesActivity(View v);

    abstract void startEditedTreesActivity(View v);

    abstract void startDeletedTreesActivity(View v);


}
