package com.laggiss.arboretumexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by Laggiss on 6/14/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper mInstance = null;
    //The Android's default system path of your application database.
    private static String DB_PATH;//= "/data/data/geoquiz.com.mycompanyx.geoquiz/databases/";

    private static String DB_NAME="trees.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    public static int MASTER = 0;
    public static int ADD = 1;
    public static int EDIT = 2;
    public static int DELETE = -1;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public static DataBaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DataBaseHelper(context.getApplicationContext());
            mInstance.openDataBase();
            Log.e("opened db", "asdf");
        }
        return mInstance;
    }

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
//        DB_NAME=dbName;
//        DB_PATH=dbPath;

        String dbPath = context.getDatabasePath(DB_NAME).getPath();//"/data/data/geoquiz.com.mycompanyx.geoquiz/databases/";//
        dbPath=dbPath.substring(0,dbPath.lastIndexOf("/")+1);
        DB_PATH=dbPath;
//        Log.e("DH : ",DB_NAME+" "+DB_PATH);
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
//        Log.e("Database Exist?: ",String.valueOf(dbExist));

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
//            Log.e("Copy done: ", "copied database");
            try {

                copyDataBase();
//                Log.e("Copy done: ","copied database");
            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
//            Log.e("CHK: ",myPath);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
            //Log.e("Write", "Buffer");
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory()) {
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //one time use method to populate the database
    public ArrayList<Tree> createPOJO(){
        ArrayList<Tree> trees = new ArrayList<Tree>();
        Cursor c = myDataBase.rawQuery("SELECT * FROM " +"ArboretumData "+ " ",null);
        //Move to first row
        int counter = 0;
        if(c.moveToFirst()){
            //create a tree object using the columns of the row
            do{
                Tree newTree = new Tree(c.getString(0),c.getString(1),c.getString(2),c.getString(3),
                        c.getString(4),c.getString(5),c.getString(6),c.getString(7),
                        Boolean.parseBoolean(c.getString(8)),c.getString(9),c.getString(10),c.getString(11),
                        c.getString(12),c.getString(13),c.getString(14),c.getString(15),
                        c.getString(16),c.getString(17),c.getString(18),c.getString(19),
                        c.getString(20),Boolean.parseBoolean(c.getString(21)),c.getString(22),c.getString(23),
                        c.getString(24),c.getString(25),c.getString(26),c.getString(27),
                        c.getString(28),c.getString(29),c.getString(30),c.getString(31),
                        c.getString(32),c.getString(33),c.getDouble(34), c.getDouble(35),
                        c.getString(36),c.getInt(37));
                trees.add(newTree);
            }while(c.moveToNext());
        }
        c.close();
        return trees;

    }



    public void createMasterFirebaseDatabase(ArrayList<Tree> trees, DatabaseReference mRef){
//        mRef = mRef.child("master");
        for(int i = 0; i < trees.size(); i++) {
            DatabaseReference tempRef = mRef.push();
            //maybe dont need this
            String firebaseKey = tempRef.getKey();
            trees.get(i).setFirebaseID(firebaseKey);

            tempRef.setValue(trees.get(i));

            trees.get(i).setFirebaseID(firebaseKey);
            ContentValues values = new ContentValues();

//            values.put("commonName", trees.get(i).getCommonName());
            values.put("firebaseID", firebaseKey);

            int test = i + 1;
            long e = myDataBase.update("ArboretumData", values, "firebaseID = " + test, null);

        }
    }

    public SQLiteDatabase getMyDataBase(){
        return myDataBase;
    }

    public void loop(){
        Cursor c = myDataBase.rawQuery("SELECT * FROM " +"ArboretumData"+ " ",null);
        c.moveToFirst();
        for(int i = 0; i < 1; i++){

            Log.e("asdf","asdf");
            Log.e("0", c.getString(0));
            Log.e("1", c.getString(1));
            Log.e("2", c.getString(2));
            Log.e("3", c.getString(3));
            Log.e("4", c.getString(4));
            Log.e("5", c.getString(5));
            Log.e("6", c.getString(6));
            Log.e("7", c.getString(7));
            Log.e("8", c.getString(8));
            Log.e("9", c.getString(9));
            Log.e("10", c.getString(10));
            Log.e("11", c.getString(11));
            Log.e("12", c.getString(12));
            Log.e("13", c.getString(13));
            Log.e("14", c.getString(14));
            Log.e("15", c.getString(15));
            Log.e("16", c.getString(16));
            Log.e("17", c.getString(17));
            Log.e("18", c.getString(18));
            Log.e("19", c.getString(19));
            Log.e("20", c.getString(20));
            Log.e("21", c.getString(21));
            Log.e("22", c.getString(22));
            Log.e("23", c.getString(23));
            Log.e("24", c.getString(24));
            Log.e("25", c.getString(25));
            Log.e("26", c.getString(26));
            Log.e("27", c.getString(27));
            Log.e("28", c.getString(28));
            Log.e("29", c.getString(29));
            Log.e("30", c.getString(30));
            Log.e("31", c.getString(31));
            Log.e("32", c.getString(32));
            Log.e("33", c.getString(33));
            Log.e("34", Double.toString(c.getDouble(34)));
            Log.e("35", Double.toString(c.getDouble(35)));
            Log.e("36", c.getString(36));
            Log.e("37", Integer.toString(c.getInt(37)));
            c.moveToNext();
        }
        c.close();
    }

    public int getCount(){
        Cursor c = myDataBase.rawQuery("Select * from ArboretumData",null);
        c.moveToFirst();
        return c.getCount();
    }

    public ArrayList<Tree> getTrees(String type){
        ArrayList<Tree> addedTrees = new ArrayList<Tree>();
        int changeType;
        if(type.equals("add")){
            changeType = ADD;
        }else if(type.equals("edit")){
            changeType = EDIT;
        }else{
            changeType = DELETE;
        }

        Cursor c = myDataBase.rawQuery("SELECT * FROM MyTrees WHERE changeType = '" + changeType + "'", null);
        if(c.moveToFirst()){
            //create a tree object using the columns of the row
            do{
                Tree newTree = new Tree(c.getString(0),c.getString(1),c.getString(2),c.getString(3),
                        c.getString(4),c.getString(5),c.getString(6),c.getString(7),
                        Boolean.parseBoolean(c.getString(8)),c.getString(9),c.getString(10),c.getString(11),
                        c.getString(12),c.getString(13),c.getString(14),c.getString(15),
                        c.getString(16),c.getString(17),c.getString(18),c.getString(19),
                        c.getString(20),Boolean.parseBoolean(c.getString(21)),c.getString(22),c.getString(23),
                        c.getString(24),c.getString(25),c.getString(26),c.getString(27),
                        c.getString(28),c.getString(29),c.getString(30),c.getString(31),
                        c.getString(32),c.getString(33),c.getDouble(34), c.getDouble(35),
                        c.getString(36),c.getInt(27));
                addedTrees.add(newTree);
            }while(c.moveToNext());
        }
        c.close();
        return addedTrees;
    }

    public Tree getTreeFromSQL(String id){
        Cursor c = myDataBase.rawQuery("SELECT * FROM ArboretumData WHERE firebaseID = '" + id + "'", null);
        c.moveToFirst();
        Tree nTree = new Tree(c.getString(0),c.getString(1),c.getString(2),c.getString(3),
                c.getString(4),c.getString(5),c.getString(6),c.getString(7),
                Boolean.parseBoolean(c.getString(8)),c.getString(9),c.getString(10),c.getString(11),
                c.getString(12),c.getString(13),c.getString(14),c.getString(15),
                c.getString(16),c.getString(17),c.getString(18),c.getString(19),
                c.getString(20),Boolean.parseBoolean(c.getString(21)),c.getString(22),c.getString(23),
                c.getString(24),c.getString(25),c.getString(26),c.getString(27),
                c.getString(28),c.getString(29),c.getString(30),c.getString(31),
                c.getString(32),c.getString(33),c.getDouble(34), c.getDouble(35),
                c.getString(36),c.getInt(27));
        c.close();
        return nTree;

    }

    public Tree getTreeFromMyTrees(String id){
        Cursor c = myDataBase.rawQuery("SELECT * FROM MyTrees WHERE firebaseID = '" + id + "'", null);
        c.moveToFirst();
        Tree nTree = new Tree(c.getString(0),c.getString(1),c.getString(2),c.getString(3),
                c.getString(4),c.getString(5),c.getString(6),c.getString(7),
                Boolean.parseBoolean(c.getString(8)),c.getString(9),c.getString(10),c.getString(11),
                c.getString(12),c.getString(13),c.getString(14),c.getString(15),
                c.getString(16),c.getString(17),c.getString(18),c.getString(19),
                c.getString(20),Boolean.parseBoolean(c.getString(21)),c.getString(22),c.getString(23),
                c.getString(24),c.getString(25),c.getString(26),c.getString(27),
                c.getString(28),c.getString(29),c.getString(30),c.getString(31),
                c.getString(32),c.getString(33),c.getDouble(34), c.getDouble(35),
                c.getString(36),c.getInt(27));
        c.close();
        return nTree;

    }

    public int getMyTreesCount(){
        Cursor c = myDataBase.rawQuery("select * from myTrees", null);
        c.moveToFirst();
        return c.getCount();
    }

    public boolean exists(String id){
        boolean result = true;
        Cursor c = myDataBase.rawQuery("SELECT * FROM ArboretumData WHERE firebaseID = '" + id + "'", null);
        c.moveToFirst();
        if(c.getCount() <= 0){
            result = false;
        }
        c.close();
        return result;
    }

    public boolean existsInMyTrees(String id){
        boolean result = true;
        Cursor c = myDataBase.rawQuery("SELECT * FROM MyTrees WHERE firebaseID = '" + id + "'", null);
        c.moveToFirst();
        int count = c.getCount();
        if(c.getCount() <= 0){
            result = false;
        }
        c.close();
        return result;
    }

    public void deleteTree(String id){
        //check to see if it is a tree added by user

        Cursor added = myDataBase.rawQuery("SELECT * FROM MyTrees WHERE firebaseID = '" + id + "' AND changeType = '" + ADD + "'", null);
        if(added.getCount() <= 0){
            //if it isnt a user added tree, ie it is from master, delete the tree and add the tree to MyTrees with change type of delete. Also clear any edits of this tree.
            Cursor cMyTrees = myDataBase.rawQuery("DELETE FROM MyTrees WHERE firebaseID = '" + id + "'", null);//clear edits first
            cMyTrees.moveToFirst();
            cMyTrees.close();

            Tree tree = getTreeFromSQL(id);
            ContentValues cv = new ContentValues(); //copy tree
            cv.put("family", tree.getFamily());
            cv.put("familiarName",tree.getFamiliarName());
            cv.put("genus",tree.getGenus());
            cv.put("species",tree.getSpecies());
            cv.put("rank",tree.getRank());
            cv.put("type",tree.getType());
            cv.put("hybridCross",tree.getHybridCross());
            cv.put("cultivar",tree.getCultivar());
            cv.put("nameStatus",tree.getStringNameStatus());
            cv.put("authority",tree.getAuthority());
            cv.put("dateIntro",tree.getDateIntro());
            cv.put("accessNo",tree.getAccessNo());
            cv.put("recdFrom",tree.getRecdFrom());
            cv.put("dateRecd",tree.getDateRecd());
            cv.put("howRecd",tree.getHowRecd());
            cv.put("numRecd",tree.getNumRecd());
            cv.put("nameRecd",tree.getNameRecd());
            cv.put("commonName",tree.getCommonName());
            cv.put("nomCommun",tree.getNomCommun());
            cv.put("nursery",tree.getNursery());
            cv.put("location",tree.getLocation());
            cv.put("donor",tree.getStringDonor());
            cv.put("collSeed",tree.getCollSeed());
            cv.put("sourceAcc",tree.getSourceAcc());
            cv.put("revised",tree.getRevised());
            cv.put("numberNow",tree.getNumberNow());
            cv.put("origins",tree.getOrigins());
            cv.put("herbSpec",tree.getHerbSpec());
            cv.put("idByDate",tree.getIdByDate());
            cv.put("photo1",tree.getPhoto1());
            cv.put("photo2",tree.getPhoto2());
            cv.put("mortInfo",tree.getMortInfo());
            cv.put("notes",tree.getNotes());
            cv.put("memo",tree.getMemo());
            cv.put("lat",tree.getLat());
            cv.put("lng",tree.getLng());
            cv.put("firebaseID", id);
            if(tree.getChangeType() == EDIT){
                cv.put("changeType",tree.getChangeType());
            }else{
                cv.put("changeType",DELETE);
            }


            myDataBase.insert("MyTrees", null, cv);

            Cursor cArboreData = myDataBase.rawQuery("DELETE FROM ArboretumData WHERE firebaseID = '" + id + "'", null); //delete tree from master
            cArboreData.moveToFirst();
            cArboreData.close();
        }else{
            //if the tree is added by user, just delete it from arboretumData and MyTrees
            Cursor cMyTrees = myDataBase.rawQuery("DELETE FROM MyTrees WHERE firebaseID = '" + id + "'", null);//clear edits first
            cMyTrees.moveToFirst();
            cMyTrees.close();

            Cursor cArboreData = myDataBase.rawQuery("DELETE FROM ArboretumData WHERE firebaseID = '" + id + "'", null); //delete tree from master
            cArboreData.moveToFirst();
            cArboreData.close();
        }
    }

    public void undoDelete(Tree tree){
        addTreeToMaster(tree);
        Cursor cMyTrees = myDataBase.rawQuery("DELETE FROM MyTrees WHERE firebaseID = '" + tree.getFirebaseID() + "'", null);//clear edits first
        cMyTrees.moveToFirst();
        cMyTrees.close();
    }

    public void editTree(Tree tree, String firebaseID){
        ContentValues cv = new ContentValues();
        cv.put("family", tree.getFamily());
        cv.put("familiarName",tree.getFamiliarName());
        cv.put("genus",tree.getGenus());
        cv.put("species",tree.getSpecies());
        cv.put("rank",tree.getRank());
        cv.put("type",tree.getType());
        cv.put("hybridCross",tree.getHybridCross());
        cv.put("cultivar",tree.getCultivar());
        cv.put("nameStatus",tree.getStringNameStatus());
        cv.put("authority",tree.getAuthority());
        cv.put("dateIntro",tree.getDateIntro());
        cv.put("accessNo",tree.getAccessNo());
        cv.put("recdFrom",tree.getRecdFrom());
        cv.put("dateRecd",tree.getDateRecd());
        cv.put("howRecd",tree.getHowRecd());
        cv.put("numRecd",tree.getNumRecd());
        cv.put("nameRecd",tree.getNameRecd());
        cv.put("commonName",tree.getCommonName());
        cv.put("nomCommun",tree.getNomCommun());
        cv.put("nursery",tree.getNursery());
        cv.put("location",tree.getLocation());
        cv.put("donor",tree.getStringDonor());
        cv.put("collSeed",tree.getCollSeed());
        cv.put("sourceAcc",tree.getSourceAcc());
        cv.put("revised",tree.getRevised());
        cv.put("numberNow",tree.getNumberNow());
        cv.put("origins",tree.getOrigins());
        cv.put("herbSpec",tree.getHerbSpec());
        cv.put("idByDate",tree.getIdByDate());
        cv.put("photo1",tree.getPhoto1());
        cv.put("photo2",tree.getPhoto2());
        cv.put("mortInfo",tree.getMortInfo());
        cv.put("notes",tree.getNotes());
        cv.put("memo",tree.getMemo());
        cv.put("lat",tree.getLat());
        cv.put("lng",tree.getLng());
        cv.put("firebaseID", firebaseID);
        cv.put("changeType",tree.getChangeType());

        myDataBase.update("ArboretumData", cv, "firebaseID = '" + firebaseID + "'", null);
        if(existsInMyTrees(firebaseID)){
            myDataBase.update("MyTrees", cv, "firebaseID = '" + firebaseID + "'", null);
        }else{
            myDataBase.insert("MyTrees", null, cv);
        }


    }

    public int addTreeToMaster(Tree tree){
        //check if tree exists
        boolean existsInMaster = exists(tree.getFirebaseID());
        if(existsInMaster){
            return 0;
        }else{
            ContentValues cv = new ContentValues();
            cv.put("family", tree.getFamily());
            cv.put("familiarName",tree.getFamiliarName());
            cv.put("genus",tree.getGenus());
            cv.put("species",tree.getSpecies());
            cv.put("rank",tree.getRank());
            cv.put("type",tree.getType());
            cv.put("hybridCross",tree.getHybridCross());
            cv.put("cultivar",tree.getCultivar());
            cv.put("nameStatus",tree.getStringNameStatus());
            cv.put("authority",tree.getAuthority());
            cv.put("dateIntro",tree.getDateIntro());
            cv.put("accessNo",tree.getAccessNo());
            cv.put("recdFrom",tree.getRecdFrom());
            cv.put("dateRecd",tree.getDateRecd());
            cv.put("howRecd",tree.getHowRecd());
            cv.put("numRecd",tree.getNumRecd());
            cv.put("nameRecd",tree.getNameRecd());
            cv.put("commonName",tree.getCommonName());
            cv.put("nomCommun",tree.getNomCommun());
            cv.put("nursery",tree.getNursery());
            cv.put("location",tree.getLocation());
            cv.put("donor",tree.getStringDonor());
            cv.put("collSeed",tree.getCollSeed());
            cv.put("sourceAcc",tree.getSourceAcc());
            cv.put("revised",tree.getRevised());
            cv.put("numberNow",tree.getNumberNow());
            cv.put("origins",tree.getOrigins());
            cv.put("herbSpec",tree.getHerbSpec());
            cv.put("idByDate",tree.getIdByDate());
            cv.put("photo1",tree.getPhoto1());
            cv.put("photo2",tree.getPhoto2());
            cv.put("mortInfo",tree.getMortInfo());
            cv.put("notes",tree.getNotes());
            cv.put("memo",tree.getMemo());
            cv.put("lat",tree.getLat());
            cv.put("lng",tree.getLng());
            cv.put("firebaseID", tree.getFirebaseID());
            cv.put("changeType",tree.getChangeType());

            myDataBase.insertOrThrow("ArboretumData", null, cv);
            return 1;
        }

    }

    public int addTreeToMyTrees(Tree tree){
        ContentValues cv = new ContentValues();
        cv.put("family", tree.getFamily());
        cv.put("familiarName",tree.getFamiliarName());
        cv.put("genus",tree.getGenus());
        cv.put("species",tree.getSpecies());
        cv.put("rank",tree.getRank());
        cv.put("type",tree.getType());
        cv.put("hybridCross",tree.getHybridCross());
        cv.put("cultivar",tree.getCultivar());
        cv.put("nameStatus",tree.getStringNameStatus());
        cv.put("authority",tree.getAuthority());
        cv.put("dateIntro",tree.getDateIntro());
        cv.put("accessNo",tree.getAccessNo());
        cv.put("recdFrom",tree.getRecdFrom());
        cv.put("dateRecd",tree.getDateRecd());
        cv.put("howRecd",tree.getHowRecd());
        cv.put("numRecd",tree.getNumRecd());
        cv.put("nameRecd",tree.getNameRecd());
        cv.put("commonName",tree.getCommonName());
        cv.put("nomCommun",tree.getNomCommun());
        cv.put("nursery",tree.getNursery());
        cv.put("location",tree.getLocation());
        cv.put("donor",tree.getStringDonor());
        cv.put("collSeed",tree.getCollSeed());
        cv.put("sourceAcc",tree.getSourceAcc());
        cv.put("revised",tree.getRevised());
        cv.put("numberNow",tree.getNumberNow());
        cv.put("origins",tree.getOrigins());
        cv.put("herbSpec",tree.getHerbSpec());
        cv.put("idByDate",tree.getIdByDate());
        cv.put("photo1",tree.getPhoto1());
        cv.put("photo2",tree.getPhoto2());
        cv.put("mortInfo",tree.getMortInfo());
        cv.put("notes",tree.getNotes());
        cv.put("memo",tree.getMemo());
        cv.put("lat",tree.getLat());
        cv.put("lng",tree.getLng());
        cv.put("firebaseID", tree.getFirebaseID());
        cv.put("changeType",tree.getChangeType());

        long e = myDataBase.insert("MyTrees", null, cv);
        return 1;


    }

    public void addRowToMaster(ContentValues cv){
        long e = myDataBase.insert("ArboretumData", null, cv);
    }

    public String[] getGenera(){
        Cursor table = myDataBase.rawQuery("select distinct genus from  ArboretumData", null);
        table.moveToFirst();
        String[] genera = new String[table.getCount()];
        int i = 0;
        for (table.moveToFirst(); !table.isAfterLast(); table.moveToNext()){
            genera[i] = table.getString(0).split(" ")[0];
            i++;
        }
        return genera;
    }

    public void removeTreeFromMyTrees(String id){
        Cursor selected = myDataBase.rawQuery("SELECT * FROM MyTrees WHERE firebaseID = '" + id + "'", null);
        if(selected.getCount() > 0){
            //if it is not in myTrees, it do nothing
            Cursor c = myDataBase.rawQuery("DELETE FROM MyTrees WHERE firebaseID = '" + id + "'", null);
            c.moveToFirst();
            c.close();
        }
    }

    public void clearDB (){
        myDataBase.rawQuery("DELETE FROM ArboretumData", null);
    }


    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.


    public static void setADD(int ADD) {
        DataBaseHelper.ADD = ADD;
    }
}
