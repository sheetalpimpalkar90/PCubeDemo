package com.spimpalkar.pcubedemo.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.spimpalkar.pcubedemo.models.DealModel;
import com.spimpalkar.pcubedemo.models.DealModelDataList;

import java.util.ArrayList;
import java.util.List;

import static com.spimpalkar.pcubedemo.models.DealModel.DealDataModel.*;

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
    public static final String TOP_PRIMARY_KEY_ID = "top_primary_key_id";
    public static final String TOP_DEAL_ID = "top_deal_id";
    public static final String TOP_DEAL_TITLE = "top_deal_title";
    public static final String TOP_DEAL_IMAGE = "top_deal_image";
    public static final String TOP_DEAL_DESCRIPTION = "top_deal_description";

    /*Popular deals table info*/
    public static final String TABLE_POPULAR_DEALS = "popular_deal";
    public static final String POPULAR_PRIMARY_KEY_ID = "popular_primary_key_id";
    public static final String POPULAR_DEAL_ID = "popular_deal_id";
    public static final String POPULAR_DEAL_TITLE = "popular_deal_title";
    public static final String POPULAR_DEAL_IMAGE = "popular_deal_image";
    public static final String POPULAR_DEAL_DESCRIPTION = "popular_deal_description";

    /*Creation of Top deals table*/
    private static final String CREATE_TABLE_TOP_DEALS = "create table "
            + TABLE_TOP_DEALS + "( " + TOP_PRIMARY_KEY_ID
            + " integer primary key autoincrement, " + TOP_DEAL_ID
            + " text not null, " + TOP_DEAL_TITLE
            + " text not null, " + TOP_DEAL_IMAGE
            + " text not null, " + TOP_DEAL_DESCRIPTION
            + " text not null);";

    /*Creation of Popular deals table*/
    private static final String CREATE_TABLE_POPULAR_DEALS = "create table "
            + TABLE_POPULAR_DEALS + "( " + POPULAR_PRIMARY_KEY_ID
            + " integer primary key autoincrement, " + POPULAR_DEAL_ID
            + " text not null, " + POPULAR_DEAL_TITLE
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

//    /***********************************CRUD operations on Top Deals Table*****************************************/
    /*Add top deal information to Top deal table*/
    public void addTopDeal(DealModelDataList dealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOP_DEAL_ID, dealModel.getTopDealId()); // add ID
        values.put(TOP_DEAL_TITLE, dealModel.getTopDealTitle()); // add title
        values.put(TOP_DEAL_IMAGE, dealModel.getTopDealImage()); // add image
        values.put(TOP_DEAL_DESCRIPTION, dealModel.getTopDealShareUrl()); // add description

        // Inserting Row
        db.insert(TABLE_TOP_DEALS, null, values);
        db.close(); // Closing database connection
    }

    /*Add list of top deals Top deal table*/
    public void addTopDealList(List<DealModelDataList> dealModelList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (int i = 0; i < dealModelList.size(); i++) {
            values = new ContentValues();
            values.put(TOP_DEAL_ID, dealModelList.get(i).getTopDealId()); // add ID
            values.put(TOP_DEAL_TITLE, dealModelList.get(i).getTopDealTitle()); // add title
            values.put(TOP_DEAL_IMAGE, dealModelList.get(i).getTopDealImage()); // add image
            values.put(TOP_DEAL_DESCRIPTION, dealModelList.get(i).getTopDealShareUrl()); // add description

            if(existsTopDeal(String.valueOf(dealModelList.get(i).getTopDealId())))
            {
                // Update Row
                updateTopDealTable(dealModelList.get(i));
            }else
            {
                // Insert Row
                db.insert(TABLE_TOP_DEALS, null, values);
            }

        }
        db.close(); // Closing database connection
    }

    /*Check if Top deal ID exists*/
    public boolean existsTopDeal(String id) {
        Cursor cursor = this.getWritableDatabase().rawQuery("select 1 from top_deal where top_deal_id=?",
                new String[] { id });
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /*Get list of top deals*/
    public List<DealModelDataList> getAllTopDeals() {
        List<DealModelDataList> dealModelList = new ArrayList<DealModelDataList>();
        String selectQuery = "SELECT  * FROM " + TABLE_TOP_DEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DealModelDataList dealModel = new DealModelDataList();
                dealModel.setTopDealId(Integer.parseInt(cursor.getString(0)));
                dealModel.setTopDealImage(cursor.getString(1));
                dealModel.setTopDealTitle(cursor.getString(2));
                dealModel.setTopDealShareUrl(cursor.getString(3));
                // Adding top deal to list
                dealModelList.add(dealModel);
            } while (cursor.moveToNext());
        }

        return dealModelList;
    }


    /*Update particular row in Top deal table*/
    public int updateTopDealTable(DealModelDataList dealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOP_DEAL_ID, dealModel.getTopDealId());
        values.put(TOP_DEAL_TITLE, dealModel.getTopDealTitle());
        values.put(TOP_DEAL_IMAGE, dealModel.getTopDealImage());
        values.put(TOP_DEAL_DESCRIPTION, dealModel.getTopDealShareUrl());

        // updating row
        return db.update(TABLE_TOP_DEALS, values, TOP_PRIMARY_KEY_ID + " = ?",
                new String[]{String.valueOf(dealModel.getTopDealId())});
    }

    /*Delete single top deal*/
    public void deleteTopDeal(DealModelDataList dealModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOP_DEALS, TOP_PRIMARY_KEY_ID + " = ?",
                new String[]{String.valueOf(dealModel.getTopDealId())});
        db.close();
    }

    /*Delete Top deal table*/
    public void deleteTopDealTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_TOP_DEALS);
        db.close();
    }


    /****************************CRUD operations on Popular Deals Table*********************************/

    /*Add list of popular deals to popular deal table*/
    public void addPopularDealList(List<DealModelDataList> popularDealModelList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for (int i = 0; i < popularDealModelList.size(); i++) {
            values = new ContentValues();
            values.put(POPULAR_DEAL_ID, popularDealModelList.get(i).getTopDealId()); // add ID
            values.put(POPULAR_DEAL_TITLE, popularDealModelList.get(i).getTopDealTitle()); // add title
            values.put(POPULAR_DEAL_IMAGE, popularDealModelList.get(i).getTopDealImage()); // add image
            values.put(POPULAR_DEAL_DESCRIPTION, popularDealModelList.get(i).getTopDealShareUrl()); // add description

            // Inserting Row
            if(existsPopularDeal(String.valueOf(popularDealModelList.get(i).getTopDealId())))
            {
                // Update Row
                updatePopularDealTable(popularDealModelList.get(i));
            }else
            {
                // Insert Row
                db.insert(TABLE_POPULAR_DEALS, null, values);
            }

        }
        db.close(); // Closing database connection
    }

    /*Check if Popular deal ID exists*/
    public boolean existsPopularDeal(String id) {
        Cursor cursor = this.getWritableDatabase().rawQuery("select 1 from popular_deal where popular_deal_id=?",
                new String[] { id });
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /*Get list of popular deals*/
    public List<DealModelDataList> getAllPopularDeals() {
        List<DealModelDataList> popularDealModelList = new ArrayList<DealModelDataList>();
        String selectQuery = "SELECT  * FROM " + TABLE_POPULAR_DEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DealModelDataList popularDealModel = new DealModelDataList();
                popularDealModel.setTopDealId(Integer.parseInt(cursor.getString(0)));
                popularDealModel.setTopDealImage(cursor.getString(1));
                popularDealModel.setTopDealTitle(cursor.getString(2));
                popularDealModel.setTopDealShareUrl(cursor.getString(3));
                // Adding popular deal to list
                popularDealModelList.add(popularDealModel);
            } while (cursor.moveToNext());
        }

        return popularDealModelList;
    }

    /*Update particular row in Popular deal table*/
    public int updatePopularDealTable(DealModelDataList dealModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(POPULAR_DEAL_ID, dealModel.getTopDealId());
        values.put(POPULAR_DEAL_TITLE, dealModel.getTopDealTitle());
        values.put(POPULAR_DEAL_IMAGE, dealModel.getTopDealImage());
        values.put(POPULAR_DEAL_DESCRIPTION, dealModel.getTopDealShareUrl());

        // updating row
        return db.update(TABLE_POPULAR_DEALS, values, POPULAR_PRIMARY_KEY_ID + " = ?",
                new String[]{String.valueOf(dealModel.getTopDealId())});
    }

    /*Delete single popular deal*/
    public void deletePopularDeal(DealModelDataList dealModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POPULAR_DEALS, POPULAR_PRIMARY_KEY_ID + " = ?",
                new String[]{String.valueOf(dealModel.getTopDealId())});
        db.close();
    }

    /*Delete popular deal table*/
    public void deletePopularDealTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_POPULAR_DEALS);
        db.close();
    }

}
