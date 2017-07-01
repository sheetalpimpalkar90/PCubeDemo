package com.spimpalkar.pcubedemo.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.spimpalkar.pcubedemo.models.PopularDealModel;
import com.spimpalkar.pcubedemo.models.TopDealModel;

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
    public void addTopDeal(TopDealModel topDealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOP_DEAL_TITLE, topDealModel.getTopDealTitle()); // add title
        values.put(TOP_DEAL_IMAGE, topDealModel.getTopDealImage()); // add image
        values.put(TOP_DEAL_DESCRIPTION, topDealModel.getTopDealDescription()); // add description

        // Inserting Row
        db.insert(TABLE_TOP_DEALS, null, values);
        db.close(); // Closing database connection
    }

    /*Add list of top deals Top deal table*/
    public void addTopDealList(List<TopDealModel> topDealModelList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (int i = 0; i < topDealModelList.size(); i++) {
            values = new ContentValues();
            values.put(TOP_DEAL_TITLE, topDealModelList.get(i).getTopDealTitle()); // add title
            values.put(TOP_DEAL_IMAGE, topDealModelList.get(i).getTopDealImage()); // add image
            values.put(TOP_DEAL_DESCRIPTION, topDealModelList.get(i).getTopDealDescription()); // add description

            // Inserting Row
            db.insert(TABLE_TOP_DEALS, null, values);
        }
        db.close(); // Closing database connection
    }

    /*Get list of top deals*/
    public List<TopDealModel> getAllTopDeals() {
        List<TopDealModel> topDealModelList = new ArrayList<TopDealModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_TOP_DEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TopDealModel topDealModel = new TopDealModel();
                topDealModel.setTopDealId(Integer.parseInt(cursor.getString(0)));
                topDealModel.setTopDealImage(cursor.getString(1));
                topDealModel.setTopDealTitle(cursor.getString(2));
                topDealModel.setTopDealDescription(cursor.getString(3));
                // Adding top deal to list
                topDealModelList.add(topDealModel);
            } while (cursor.moveToNext());
        }

        return topDealModelList;
    }

    /*Get Top Deal by ID*/
    public TopDealModel getTopDealByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TOP_DEALS, new String[]{TOP_DEAL_ID,
                        TOP_DEAL_TITLE, TOP_DEAL_IMAGE, TOP_DEAL_DESCRIPTION}, TOP_DEAL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        TopDealModel topDealModel = new TopDealModel();
        topDealModel.setTopDealId(Integer.parseInt(cursor.getString(0)));
        topDealModel.setTopDealTitle(cursor.getString(1));
        topDealModel.setTopDealImage(cursor.getString(2));
        topDealModel.setTopDealDescription(cursor.getString(3));

        return topDealModel;
    }

    /*Update particular row in Top deal table*/
    public int updateTopDealTable(TopDealModel topDealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOP_DEAL_TITLE, topDealModel.getTopDealTitle());
        values.put(TOP_DEAL_IMAGE, topDealModel.getTopDealImage());
        values.put(TOP_DEAL_DESCRIPTION, topDealModel.getTopDealDescription());

        // updating row
        return db.update(TABLE_TOP_DEALS, values, TOP_DEAL_ID + " = ?",
                new String[]{String.valueOf(topDealModel.getTopDealId())});
    }

    // Deleting single contact
    public void deleteContact(TopDealModel topDealModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOP_DEALS, TOP_DEAL_ID + " = ?",
                new String[]{String.valueOf(topDealModel.getTopDealId())});
        db.close();
    }


    /*****************************CRUD operations on Popular Deals Table**********************************/
    /*Add Popular deal information to Popular deal table*/
    public void addPopularDeal(PopularDealModel popularDealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POPULAR_DEAL_TITLE, popularDealModel.getPopularDealTitle()); // add title
        values.put(POPULAR_DEAL_IMAGE, popularDealModel.getPopularDealImage()); // add image
        values.put(POPULAR_DEAL_DESCRIPTION, popularDealModel.getPopularDealDescription()); // add description

        // Inserting Row
        db.insert(TABLE_POPULAR_DEALS, null, values);
        db.close(); // Closing database connection
    }

    /*Add list of popular deals to popular deal table*/
    public void addPopularDealList(List<PopularDealModel> popularDealModelList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (int i = 0; i < popularDealModelList.size(); i++) {
            values = new ContentValues();
            values.put(POPULAR_DEAL_TITLE, popularDealModelList.get(i).getPopularDealTitle()); // add title
            values.put(POPULAR_DEAL_IMAGE, popularDealModelList.get(i).getPopularDealImage()); // add image
            values.put(POPULAR_DEAL_DESCRIPTION, popularDealModelList.get(i).getPopularDealDescription()); // add description

            // Inserting Row
            db.insert(TABLE_POPULAR_DEALS, null, values);
        }
        db.close(); // Closing database connection
    }

    /*Get list of popular deals*/
    public List<PopularDealModel> getAllPopularDeals() {
        List<PopularDealModel> popularDealModelList = new ArrayList<PopularDealModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_POPULAR_DEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PopularDealModel popularDealModel = new PopularDealModel();
                popularDealModel.setPopularDealId(Integer.parseInt(cursor.getString(0)));
                popularDealModel.setPopularDealImage(cursor.getString(1));
                popularDealModel.setPopularDealTitle(cursor.getString(2));
                popularDealModel.setPopularDealDescription(cursor.getString(3));
                // Adding popular deal to list
                popularDealModelList.add(popularDealModel);
            } while (cursor.moveToNext());
        }

        return popularDealModelList;
    }

    /*Get popular Deal by ID*/
    public PopularDealModel getPopularDealByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POPULAR_DEALS, new String[]{POPULAR_DEAL_ID,
                        POPULAR_DEAL_TITLE, POPULAR_DEAL_IMAGE, POPULAR_DEAL_DESCRIPTION}, POPULAR_DEAL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
        PopularDealModel popularDealModel = new PopularDealModel();
        popularDealModel.setPopularDealId(Integer.parseInt(cursor.getString(0)));
        popularDealModel.setPopularDealTitle(cursor.getString(1));
        popularDealModel.setPopularDealImage(cursor.getString(2));
        popularDealModel.setPopularDealDescription(cursor.getString(3));

        return popularDealModel;
    }

    /*Update particular row in Popular deal table*/
    public int updatePopularDealTable(PopularDealModel popularDealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POPULAR_DEAL_TITLE, popularDealModel.getPopularDealTitle());
        values.put(POPULAR_DEAL_IMAGE, popularDealModel.getPopularDealImage());
        values.put(POPULAR_DEAL_DESCRIPTION, popularDealModel.getPopularDealDescription());

        // updating row
        return db.update(TABLE_POPULAR_DEALS, values, POPULAR_DEAL_ID + " = ?",
                new String[]{String.valueOf(popularDealModel.getPopularDealId())});
    }

    // Deleting single contact
    public void deleteContact(PopularDealModel popularDealModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POPULAR_DEALS, POPULAR_DEAL_ID + " = ?",
                new String[]{String.valueOf(popularDealModel.getPopularDealId())});
        db.close();
    }
}
