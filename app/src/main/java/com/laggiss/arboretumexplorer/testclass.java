package com.laggiss.arboretumexplorer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by msawada on 19/08/2015.
 */
public class testclass extends Fragment {
    private onRadiusSpinnerChange listenerRadiusSpinner;

    public interface onRadiusSpinnerChange {
        void radiusSpinnerChange(String currentRadius);
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);


        if (activity instanceof onRadiusSpinnerChange) {
            listenerRadiusSpinner = (onRadiusSpinnerChange) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.control_fragment,
                container, false);



        // Setup Radius Spinner
        ArrayList<String> radii = new ArrayList<String>();
        for (int i = 5; i < 1000; i += 5) {
            radii.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, radii);
        Spinner radiusSpinner = (Spinner) view.findViewById(R.id.radiusSpinner);
        radiusSpinner.setAdapter(adapter);
        radiusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendRadiusSelected(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    private void sendRadiusSelected(String s) {

        listenerRadiusSpinner.radiusSpinnerChange(s);

    }
}
