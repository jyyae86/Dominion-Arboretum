package com.laggiss.arboretumexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jyyae86 on 2017-05-29.
 */

public class TreeArrayAdapter extends ArrayAdapter<Tree> {
    /**/
    private final Context context;
    private final ArrayList<Tree> results;

    public TreeArrayAdapter(Context context, ArrayList<Tree> values) {
        super(context, R.layout.activity_my_trees, values);
        this.context = context;
        this.results = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Getting Recipe
        Tree curTree = results.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.tree_item_layout, parent, false);
        TextView treeName = (TextView) rowView.findViewById(R.id.treeCommonName);
        TextView memo = (TextView) rowView.findViewById(R.id.treeCreator);

        //Placing content into recipe List Item
        String title = curTree.getGenus() + " " + curTree.getSpecies();
        treeName.setText(title);
        memo.setText(curTree.getMemo());

        //TODO: Perform decision regarding image selection for recipe prior to setting an image

        return rowView;
    }
}