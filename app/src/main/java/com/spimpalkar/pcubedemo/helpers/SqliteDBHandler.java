package com.spimpalkar.pcubedemo.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class SqliteDBHandler extends SQLiteOpenHelper {

    private static SqliteDBHandler sqliteDBHandler;

    public static SqliteDBHandler getSqliteInstance(Context context) {
        if(sqliteDBHandler == null){
            sqliteDBHandler = new SqliteDBHandler(context);
        }
        return sqliteDBHandler;
    }

    /*Database details*/
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Deals.db";

    /*Top deals table info*/
    public static final String TABLE_TOP_DEALS = "top_deal";
    public static final String TOP_DEAL_ID = "top_deal_id";
    public static final String TOP_DEAL_TITLE = "top_deal_title";
    public static final String TOP_DEAL_IMAGE = "top_deal_image";
    public static final String TOP_DEAL_DESCRIPTION = "top_deal_description";

    /*Popular deals table info*/
    public static final String TABLE_POPULAR_DEALS = "popular_deal";
    public static final String POPULAR_DEAL_ID = "popular_deal_id";
    public static final String POPULAR_DEAL_TITLE = "popular_deal_title";
    public static final String POPULAR_DEAL_IMAGE = "popular_deal_image";
    public static final String POPULAR_DEAL_DESCRIPTION = "popular_deal_description";

    /*Creation of Top deals table*/
    private static final String CREATE_TABLE_TOP_DEALS = "create table "
            + TABLE_TOP_DEALS + "( " + TOP_DEAL_ID
            + " integer primary key autoincrement, " + TOP_DEAL_TITLE
            + " text not null, " + TOP_DEAL_IMAGE
            + " text not null, " + TOP_DEAL_DESCRIPTION
            + " text not null);";

    /*Creation of Popular deals table*/
    private static final String CREATE_TABLE_POPULAR_DEALS = "create table "
            + TABLE_POPULAR_DEALS + "( " + POPULAR_DEAL_ID
            + " integer primary key autoincrement, " + POPULAR_DEAL_TITLE
            + " text not null, " + POPULAR_DEAL_IMAGE
            + " text not null, " + POPULAR_DEAL_DESCRIPTION
            + " text not null);";


    /*Constructor*/
    public SqliteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TOP_DEALS);
        db.execSQL(CREATE_TABLE_POPULAR_DEALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqliteDBHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOP_DEALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POPULAR_DEALS);
        onCreate(db);
    }

    /***********************************CRUD operations on Top Deals Table*****************************************/
    /*Add top deal information to Top deal table*/
    public void addTopDeal(TopDealModelClass topDealModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOP_DEAL_TITLE, topDealModelClass.getTopDealTitle()); // add title
        values.put(TOP_DEAL_IMAGE, topDealModelClass.getTopDealImage()); // add image
        values.put(TOP_DEAL_DESCRIPTION, topDealModelClass.getTopDealDescription()); // add description

        // Inserting Row
        db.insert(TABLE_TOP_DEALS, null, values);
        db.close(); // Closing database connection
    }

    /*Add list of top deals Top deal table*/
    public void addTopDealList(List<TopDealModelClass> topDealModelClassList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (int i = 0; i < topDealModelClassList.size(); i++) {
            values = new ContentValues();
            values.put(TOP_DEAL_TITLE, topDealModelClassList.get(i).getTopDealTitle()); // add title
            values.put(TOP_DEAL_IMAGE, topDealModelClassList.get(i).getTopDealImage()); // add image
            values.put(TOP_DEAL_DESCRIPTION, topDealModelClassList.get(i).getTopDealDescription()); // add description

            // Inserting Row
            db.insert(TABLE_TOP_DEALS, null, values);
        }
        db.close(); // Closing database connection
    }

    /*Get list of top deals*/
    public List<TopDealModelClass> getAllTopDeals() {
        List<TopDealModelClass> topDealModelClassList = new ArrayList<TopDealModelClass>();
        String selectQuery = "SELECT  * FROM " + TABLE_TOP_DEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TopDealModelClass topDealModelClass = new TopDealModelClass();
                topDealModelClass.setTopDealId(Integer.parseInt(cursor.getString(0)));
                topDealModelClass.setTopDealImage(cursor.getString(1));
                topDealModelClass.setTopDealTitle(cursor.getString(2));
                topDealModelClass.setTopDealDescription(cursor.getString(3));
                // Adding top deal to list
                topDealModelClassList.add(topDealModelClass);
            } while (cursor.moveToNext());
        }

        return topDealModelClassList;
    }

    /*Get Top Deal by ID*/
    public TopDealModelClass getTopDealByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TOP_DEALS, new String[]{TOP_DEAL_ID,
                        TOP_DEAL_TITLE, TOP_DEAL_IMAGE, TOP_DEAL_DESCRIPTION}, TOP_DEAL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TopDealModelClass topDealModelClass = new TopDealModelClass();
        topDealModelClass.setTopDealId(Integer.parseInt(cursor.getString(0)));
        topDealModelClass.setTopDealTitle(cursor.getString(1));
        topDealModelClass.setTopDealImage(cursor.getString(2));
        topDealModelClass.setTopDealDescription(cursor.getString(3));

        return topDealModelClass;
    }

    /*Update particular row in Top deal table*/
    public int updateTopDealTable(TopDealModelClass topDealModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOP_DEAL_TITLE, topDealModelClass.getTopDealTitle());
        values.put(TOP_DEAL_IMAGE, topDealModelClass.getTopDealImage());
        values.put(TOP_DEAL_DESCRIPTION, topDealModelClass.getTopDealDescription());

        // updating row
        return db.update(TABLE_TOP_DEALS, values, TOP_DEAL_ID + " = ?",
                new String[]{String.valueOf(topDealModelClass.getTopDealId())});
    }

    // Deleting single contact
    public void deleteContact(TopDealModelClass topDealModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOP_DEALS, TOP_DEAL_ID + " = ?",
                new String[]{String.valueOf(topDealModelClass.getTopDealId())});
        db.close();
    }


    /*****************************CRUD operations on Popular Deals Table**********************************/
    /*Add Popular deal information to Popular deal table*/
    public void addPopularDeal(PopularDealModelClass popularDealModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POPULAR_DEAL_TITLE, popularDealModelClass.getPopularDealTitle()); // add title
        values.put(POPULAR_DEAL_IMAGE, popularDealModelClass.getPopularDealImage()); // add image
        values.put(POPULAR_DEAL_DESCRIPTION, popularDealModelClass.getPopularDealDescription()); // add description

        // Inserting Row
        db.insert(TABLE_POPULAR_DEALS, null, values);
        db.close(); // Closing database connection
    }

    /*Add list of popular deals to popular deal table*/
    public void addPopularDealList(List<PopularDealModelClass> popularDealModelClassList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (int i = 0; i < popularDealModelClassList.size(); i++) {
            values = new ContentValues();
            values.put(POPULAR_DEAL_TITLE, popularDealModelClassList.get(i).getPopularDealTitle()); // add title
            values.put(POPULAR_DEAL_IMAGE, popularDealModelClassList.get(i).getPopularDealImage()); // add image
            values.put(POPULAR_DEAL_DESCRIPTION, popularDealModelClassList.get(i).getPopularDealDescription()); // add description

            // Inserting Row
            db.insert(TABLE_POPULAR_DEALS, null, values);
        }
        db.close(); // Closing database connection
    }

    /*Get list of popular deals*/
    public List<PopularDealModelClass> getAllPopularDeals() {
        List<PopularDealModelClass> popularDealModelClassList = new ArrayList<PopularDealModelClass>();
        String selectQuery = "SELECT  * FROM " + TABLE_POPULAR_DEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PopularDealModelClass popularDealModelClass = new PopularDealModelClass();
                popularDealModelClass.setPopularDealId(Integer.parseInt(cursor.getString(0)));
                popularDealModelClass.setPopularDealImage(cursor.getString(1));
                popularDealModelClass.setPopularDealTitle(cursor.getString(2));
                popularDealModelClass.setPopularDealDescription(cursor.getString(3));
                // Adding popular deal to list
                popularDealModelClassList.add(popularDealModelClass);
            } while (cursor.moveToNext());
        }

        return popularDealModelClassList;
    }

    /*Get popular Deal by ID*/
    public PopularDealModelClass getPopularDealByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POPULAR_DEALS, new String[]{POPULAR_DEAL_ID,
                        POPULAR_DEAL_TITLE, POPULAR_DEAL_IMAGE, POPULAR_DEAL_DESCRIPTION}, POPULAR_DEAL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
        PopularDealModelClass popularDealModelClass = new PopularDealModelClass();
        popularDealModelClass.setPopularDealId(Integer.parseInt(cursor.getString(0)));
        popularDealModelClass.setPopularDealTitle(cursor.getString(1));
        popularDealModelClass.setPopularDealImage(cursor.getString(2));
        popularDealModelClass.setPopularDealDescription(cursor.getString(3));

        return popularDealModelClass;
    }

    /*Update particular row in Popular deal table*/
    public int updatePopularDealTable(PopularDealModelClass popularDealModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POPULAR_DEAL_TITLE, popularDealModelClass.getPopularDealTitle());
        values.put(POPULAR_DEAL_IMAGE, popularDealModelClass.getPopularDealImage());
        values.put(POPULAR_DEAL_DESCRIPTION, popularDealModelClass.getPopularDealDescription());

        // updating row
        return db.update(TABLE_POPULAR_DEALS, values, POPULAR_DEAL_ID + " = ?",
                new String[]{String.valueOf(popularDealModelClass.getPopularDealId())});
    }

    // Deleting single contact
    public void deleteContact(PopularDealModelClass popularDealModelClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POPULAR_DEALS, POPULAR_DEAL_ID + " = ?",
                new String[]{String.valueOf(popularDealModelClass.getPopularDealId())});
        db.close();
    }
}
