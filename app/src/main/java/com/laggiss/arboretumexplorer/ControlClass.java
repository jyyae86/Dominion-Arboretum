package com.laggiss.arboretumexplorer;

/**
 * Created by Laggiss on 6/27/2015.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import java.util.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ControlClass extends Fragment {
    private static String titleString;

    private onQueryButtonClicked listenerQueryButton;
    private onHideButtonClicked listenerHideButton;
    private onFollowMeChecked listenerSwitchFollowMe;
    private onSpeciesSpinnerChange listenerSpeciesSpinner;
    private onGenusSpinnerChange listenerGenusSpinner;
    private onClearButtonClicked listenerClearButton;
    private onHeatMapChecked listenerSwitchHeatMap;
    private onRadiusChecked listenerSwitchRadius;
    private onRadiusSpinnerChange listenerRadiusSpinner;
    private onGPSChecked listenerGPSChecked;
    private onLatinChecked listenerLatinChecked;

    private final static String CURRENT_SPECIES = "currentSpecies";
    private final static String CURRENT_GENERA = "currentGenera";
    private boolean followMeState;
    private String[] genera;
//    private String[] genera = {"Acer", "Actinidia", "Aesculus", "Alnus", "Amelanchier",
//            "Amorpha", "Aralia", "Aronia", "Betula", "Buddleia", "Calycanthus", "Carpinus", "Carya",
//            "Catalpa", "Celastrus", "Celtis", "Cephalanthus", "Cercidiphyllum", "Chamaecyparis",
//            "Chionanthus", "Colutea", "Cornus", "Corylus", "Cotoneaster", "Crataegus", "Cytisus",
//            "Daphne", "Deutzia", "Diervilla", "Dirca", "Eleutherococcus", "Euonymus", "Fagus",
//            "Forsythia", "Fraxinus", "Ginkgo", "Gleditsia", "Gymnocladus", "Hibiscus", "Hydrangea",
//            "Juniperus", "Kolkwitzia", "Laburnum", "Larix", "Lennea", "Lespedeza", "Lonicera",
//            "Maackia", "Magnolia", "Malus", "Metasequoia", "Morus", "Myrica", "Ostrya",
//            "Phellodendron", "Philadelphus", "Physocarpus", "Picea", "Pinus", "Populus", "Pseudotsuga",
//            "Ptelea", "Pterostyrax", "Quercus", "Rhamnus", "Rhododendron", "Ribes", "Robinia", "Rubus",
//            "Salix", "Sambucus", "Shepherdia", "Sorbaria", "Sorbus", "Staphylea", "Stephanandra",
//            "Syringa", "Taxodium", "Thuja", "Tilia", "Tsuga", "Ulmus", "Vaccinium", "Viburnum"};

//    private String[] species = {"Alder sp.", "Almond-leaved Willow", "Alternate Leaf Dogwood", "American Elm",
//            "Amur Cork", "Amur maackia", "Ash", "Austrian pine", "Azalea", "Bald Cypress", "Balsam Poplar", "Basswood",
//            "Bay Willow", "Bearded Maple", "Beautybush", "Beech", "Birch", "Bittersweet", "Black Ash", "Black locust",
//            "Black Maple", "Black Oak", "Black Poplar", "Black spruce", "Blackberry", "Bladdernut", "Blue-beech",
//            "Blue Ash", "Blueberry", "Broom", "Buckeye/Horse Chestnut", "Buckthorn", "Buffaloberry", "Bur Oak",
//            "Bush Honeysuckle", "Butterfly-bush", "Butterfly Maple", "Button-bush", "Calycanthus", "Capadocian Maple",
//            "Carolina Poplar", "Catalpa", "Caucasian Linden", "Chamaecyparis", "Cherry and Plum", "Chinese Ash",
//            "Chinese Juniper", "Chinese Red Birch", "Chokeberry", "Colorado spruce", "Colutea", "Common Dogwood",
//            "Common Lilacs", "Common Lime", "Coolshade Elm", "Cornelian Cherry Dogwood", "Cotoneaster", "Crab Apple",
//            "Cranberry", "Creeping Juniper", "Currant", "Daphne", "Dawn Redwood", "Deutzia", "Devils Walking-stick",
//            "Dogwood", "Douglas Fir", "Dragon spruce", "Dwarf Dogwood", "Eastern Cottonwood", "Eastern Redcedar (Juniper)",
//            "Eastern White Cedar (Arborvitae)", "Eastern White Pine", "Elderberry", "Eleutherococcus", "Elm",
//            "Engelmann spruce", "English Oak", "Epaulette Tree", "Euonymus", "European Ash", "False indigo",
//            "False Spirea", "Field Elm", "Field Maple", "Forsythia", "Freeman Maple", "Fringe Tree", "Ginkgo",
//            "Glenleven Linden", "Goat Willow", "Gray Birch", "Green Ash", "Grey Poplar", "Griffin Poplar", "Hackberry",
//            "Hardy kiwi", "Hawthorn", "hazelnut", "Hazelnut", "Hemlock", "Hibiscus", "Hickory", "Himalayan Pine",
//            "Homestead Elm", "Honey locust", "Honeysuckle", "Hoptree", "Hydrangea", "Ironwood", "Jack Pine",
//            "Japanese Angelica-tree", "Japanese Ash", "Japanese Elm", "Japanese Maple", "Japanese Poplar", "Japanese Thuja",
//            "Jezo spruce", "Kaiser Linden", "Katsura", "Kentucky coffee tree", "Korean Maple", "Laburnum", "Lace Shrub",
//            "Larch (or Tamarack)", "Large-leaved Linden", "Largetooth Aspen", "Late-blooming Lilac Hybrids",
//            "Late-blooming Lilacs", "Leatherwood", "Lennea", "Lespedeza", "Lilac", "Linden", "Lodgepole Pine", "Magnolia",
//            "Manchu Striped Maple", "Manchurian Ash", "Manitoba Maple", "Manna Ash", "Maple", "Miyabei Maple",
//            "Mock-orange", "Moltkei Linden", "Monarch Birch", "Mongolian Linden", "Montain Maple", "Montpelier Maple",
//            "Moraine Ash", "Mountain-ash", "Mugo Pine", "Mulberry", "Ninebark", "Northern Bayberry", "Northern Pin Oak",
//            "Norway Maple", "Norway spruce", "Oak", "Oregon-grape", "Oregon Ash", "Other Ash", "Other Birch",
//            "Other Dogwood", "Other Lilac", "Other Oak", "Other Willow", "Painted Maple", "Paper Birch",
//            "Paperback Maple", "Pea Shrub", "Pear", "Persian Ironwood", "Persimmon", "Pin Oak", "Pine", "Pitch Pine",
//            "Plum Yew", "Ponderosa Pine", "Poplar", "Prickly Castor-oil", "Princess Tree", "Privet", "Purple-leaved Elm",
//            "Red Birch", "Red Maple", "Red Oak", "Red Pine", "Red spruce", "Redbud", "River Birch", "Rock Elm",
//            "Rocky Mountain Juniper", "Rose", "Roundleaf Dogwood", "Russain-olive", "Russian Elm", "Salt Tree",
//            "Saltcedar", "Sapporo Elm (Autumn Gold)", "Savin Juniper", "Scarlet Oak", "Scots Pine", "Sea-buckthorn",
//            "Serbian Elm", "Serbian spruce", "Serviceberry", "Seven-son Flower", "Shandong Maple", "Siberian Pine",
//            "Silver (White) Poplar", "Silver Linden", "Silver Maple", "Silverbell", "Small-leaved Linden", "Smoketree",
//            "Snowbell", "Snowberry", "Sophora", "Spirea", "Spoil-ax", "Spruce", "Staggerbush", "Striped Maple",
//            "Sugar Maple", "Sumac", "Swamp Chestnut Oak", "Swamp White Oak", "Sweet Birch", "Sweet Pepperbush", "Sweetgum",
//            "Swiss Stone Pine", "Sycamore Maple", "Sycamore or Plane Tree", "Syrian Ash", "Tatarian (or Amur) Maple",
//            "Texas Ash", "Three-flowered Maple", "Tree Lilac", "Trembling Aspen", "True Fir", "Tulip Tree",
//            "Ukurunduense Maple", "Variegated Redtwig Dogwood", "Walnut/Butternut", "Weeping Willow", "Weigela",
//            "Western Red Cedar", "Western White Pine", "White Ash", "White Oak", "White Spruce", "White Willow",
//            "Willow", "Wingnut", "Winterberry", "Wisteria", "Witch Hazel", "Wych Elm", "Yellow Birch", "Yellowwood",
//            "Yew", "Yucca"};

    private Spinner speciesList;
    private Spinner generaList;
    Spinner radiusSpinner;
    private int xind = 0;
    private String cgen;
    // Define the events that the fragment will use to communicate

    private Button addTree;
    private Button myTrees;
    private Button allTrees;
    private Button signOut;
    private Button buttonQuery;



    public interface onQueryButtonClicked {

        void buttonQueryClicked(String link);
    }

    public interface onFollowMeChecked {
        void switchFollowMeChecked(Boolean switchState);
    }

    public interface onGPSChecked {
        void switchGPSChecked(Boolean switchState);
    }

    public interface onRadiusChecked {
        void switchRadiusChecked(Boolean switchState);
    }

    public interface onLatinChecked {
        void checkboxLatinChecked(Boolean switchState);
    }

    public interface onHeatMapChecked {
        void switchHeatMapChecked(Boolean switchState);
    }

    public interface onRadiusSpinnerChange {
        void radiusSpinnerChange(String currentRadius);
    }

    public interface onSpeciesSpinnerChange {
        void speciesSpinnerChanged(String currentSpecies);
    }

    public interface onGenusSpinnerChange {
        void genusSpinnerChanged(String currentGenus);
    }

    public interface onHideButtonClicked {
        void buttonHideClicked();
    }

    public interface onClearButtonClicked {
        void clearButtonClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showButtons(int userType){
        if(userType == 1){
            addTree.setEnabled(true);
            myTrees.setEnabled(true);
            signOut.setEnabled(true);
        }else if(userType == 2){
            addTree.setEnabled(true);
            myTrees.setEnabled(true);
            signOut.setEnabled(true);
            allTrees.setEnabled(true);
        }
    }

    // Store the listenerx (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        if (activity instanceof onQueryButtonClicked) {
            listenerQueryButton = (onQueryButtonClicked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onHideButtonClicked) {
            listenerHideButton = (onHideButtonClicked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onClearButtonClicked) {
            listenerClearButton = (onClearButtonClicked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

        if (activity instanceof onFollowMeChecked) {
            listenerSwitchFollowMe = (onFollowMeChecked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }

        if (activity instanceof onLatinChecked) {
            listenerLatinChecked = (onLatinChecked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onGPSChecked) {
            listenerGPSChecked = (onGPSChecked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onHeatMapChecked) {
            listenerSwitchHeatMap = (onHeatMapChecked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onRadiusChecked) {
            listenerSwitchRadius = (onRadiusChecked) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onSpeciesSpinnerChange) {
            listenerSpeciesSpinner = (onSpeciesSpinnerChange) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
        if (activity instanceof onGenusSpinnerChange) {
            listenerGenusSpinner = (onGenusSpinnerChange) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
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

        if (savedInstanceState != null) {
//            Log.e("XXXXXXXXXXXXXXXXXXX", String.valueOf(savedInstanceState.getInt(CURRENT_SPECIES)));
            cgen = savedInstanceState.getString(CURRENT_GENERA);
            xind = savedInstanceState.getInt(CURRENT_SPECIES);//speciesList.setSelection(savedInstanceState.getInt(CURRENT_SPECIES));
        }

//        //one time method for uploading the spinner
//        LinkedList<String> list = new LinkedList<String>();
//        for(int i = 0; i < genera.length; i++){
//            list.add(i,genera[i]);
//        }
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        ref.child("genera").setValue(list);
        DataBaseHelper mDBHelper = new DataBaseHelper(getActivity());
        mDBHelper.openDataBase();
        genera = mDBHelper.getGenera();
        Arrays.sort(genera);
        myTrees = (Button) view.findViewById(R.id.buttonMyTrees);
        addTree = (Button) view.findViewById(R.id.buttonAddTree);
        allTrees = (Button) view.findViewById(R.id.buttonAllTrees);
        signOut = (Button) view.findViewById(R.id.buttonSignOut);

        addTree.setEnabled(false);
        myTrees.setEnabled(false);
        signOut.setEnabled(false);
        allTrees.setEnabled(false);

        buttonQuery = (Button) view.findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendQueryClick(v);
            }
        });
        buttonQuery.setEnabled(false);

        final Button buttonHide = (Button) view.findViewById(R.id.hideButton);
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideButtonClick(v);
            }
        });

        final Button buttonClear = (Button) view.findViewById(R.id.clearButton);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButtonClick(v);
            }
        });




        Switch followSwitch = (Switch) view.findViewById(R.id.switchFollowMe);
        followSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendFollowCheck(buttonView, isChecked);
            }
        });

        Switch gpsSwitch = (Switch) view.findViewById(R.id.switchGPS);
        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendGPSCheck(buttonView, isChecked);
            }
        });

        Switch radiusSwitch = (Switch) view.findViewById(R.id.switchRadius);
        radiusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendRadiusCheck(buttonView, isChecked);
            }
        });

        Switch heatMapSwitch = (Switch) view.findViewById(R.id.switchHeat);
        heatMapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendHeatMapCheck(buttonView, isChecked);
            }
        });

        // Setup Radius Spinner
        ArrayList<String> radii = new ArrayList<String>();
        for (int i = 5; i < 1000; i += 5) {
            radii.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, radii);
        radiusSpinner = (Spinner) view.findViewById(R.id.radiusSpinner);
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
        radiusSpinner.setEnabled(false);
        // Setup Genera, Species Spinners
        speciesList = (Spinner) view.findViewById(R.id.speciesSpinner);
        generaList = (Spinner) view.findViewById(R.id.generaSpinner);

        ArrayAdapter<String> generaAdapter;
        generaAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                genera);
        generaList.setAdapter(generaAdapter);
        generaList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendGenusSelected(parent.getItemAtPosition(position).toString());
                ArrayAdapter<String> speciesAdapter;
                List<String> sList = makeSpeciesList(parent.getItemAtPosition(position).toString());
                speciesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sList);
                speciesList.setAdapter(speciesAdapter);
                if (xind != 0 & generaList.getSelectedItem().toString() == cgen) {
                    //Log.e("UUUUUUUUUUUUUUU",String.valueOf(xind));
                    speciesList.setSelection(xind);
                }
                speciesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sendSpeciesSelected(parent.getItemAtPosition(position).toString());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        buttonQuery.setEnabled(true);


        CheckBox latinCheck = (CheckBox) view.findViewById(R.id.checkBox);
        latinCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendLatinChecked(buttonView,isChecked);
                ArrayAdapter<String> speciesAdapter;
                List<String> sList = makeSpeciesList(generaList.getSelectedItem().toString());
                speciesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sList);
                speciesList.setAdapter(speciesAdapter);
//                speciesList.setSelection(speciesList.getSelectedItemPosition());
//                int h = speciesList.getSelectedItemPosition();
//                Log.e("xxxxxxx",String.valueOf(h));
            }
        });

//        speciesList = (Spinner) view.findViewById(R.id.speciesSpinner);
//        ArrayAdapter<String> speciesAdapter;
//        speciesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, species);
//        speciesList.setAdapter(speciesAdapter);

        return view;
    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(CURRENT_SPECIES, speciesList.getSelectedItemPosition());
//        outState.putString(CURRENT_GENERA, generaList.getSelectedItem().toString());
//
//    }

    private void sendRadiusCheck(CompoundButton b, boolean checkedState) {

        if (checkedState == true) {
            radiusSpinner.setEnabled(true);
        } else {
            radiusSpinner.setEnabled(false);
        }

        listenerSwitchRadius.switchRadiusChecked(checkedState);

    }

    private void sendQueryClick(View v) {
        if(speciesList.getSelectedItem() != null){
            String selTree = speciesList.getSelectedItem().toString();
            listenerQueryButton.buttonQueryClicked(selTree);
        }else{
            Toast.makeText(getActivity(), "There are no trees with this genera", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendFollowCheck(CompoundButton b, boolean checkedState) {

        listenerSwitchFollowMe.switchFollowMeChecked(checkedState);

    }

    private void sendLatinChecked(CompoundButton b, boolean checkedState) {

        listenerLatinChecked.checkboxLatinChecked(checkedState);

    }
    private void sendGPSCheck(CompoundButton b, boolean checkedState) {
        listenerGPSChecked.switchGPSChecked(checkedState);
    }

    private void sendHeatMapCheck(CompoundButton b, boolean checkedState) {

        listenerSwitchHeatMap.switchHeatMapChecked(checkedState);

    }

    private void sendRadiusSelected(String s) {

        listenerRadiusSpinner.radiusSpinnerChange(s);

    }

    private void sendSpeciesSelected(String s) {

        listenerSpeciesSpinner.speciesSpinnerChanged(s);

    }

    private void sendGenusSelected(String s) {

        listenerGenusSpinner.genusSpinnerChanged(s);

    }

    private void hideButtonClick(View v) {
        listenerHideButton.buttonHideClicked();
    }

    private void clearButtonClick(View v) {
        listenerClearButton.clearButtonClicked();

    }

    private List<String> makeSpeciesList(String inGenera) {

        MainActivity x = (MainActivity) getActivity();
        List<String> labels;
        labels = x.makeSpeciesList(inGenera);

        return labels;

    }
}