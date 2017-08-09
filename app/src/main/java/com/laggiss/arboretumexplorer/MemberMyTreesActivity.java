package com.laggiss.arboretumexplorer;

import java.util.ArrayList;

/**
 * Created by jyyae86 on 2017-06-15.
 */

public class MemberMyTreesActivity extends AbstractMyTreesActivity {

    @Override
    public ArrayList<Tree> getTreeArray() {
        mDBHelper = DataBaseHelper.getInstance(this);
        mDBHelper.openDataBase();
        return mDBHelper.getAddedTrees();
    }
}
