package com.laggiss.arboretumexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jyyae86 on 2017-06-25.
 */

public class ListArrayAdapter extends ArrayAdapter<ArrayList<Tree>>{
    private final Context context;
    private final ArrayList<ArrayList<Tree>> results;

    public ListArrayAdapter(Context context, ArrayList<ArrayList<Tree>> values) {
        super(context, R.layout.activity_my_trees, values);
        this.context = context;
        this.results = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Getting Recipe
        ArrayList<Tree> curItem = results.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.tree_item_layout, parent, false);
        TextView treeName = (TextView) rowView.findViewById(R.id.treeCommonName);
        TextView treeCreator = (TextView) rowView.findViewById(R.id.treeCreator);

        //Placing content into recipe List Item

        //TODO: Perform decision regarding image selection for recipe prior to setting an image

        return rowView;
    }
}
