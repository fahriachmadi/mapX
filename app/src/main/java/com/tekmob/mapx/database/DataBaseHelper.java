//package com.tekmob.mapx.database;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
///**
// * Created by Nazhif on 11/6/2017.
// */
//
//public class DataBaseHelper extends SQLiteOpenHelper {
//
//    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(AkunDatabaseHandler.DATABASE_CREATE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Log the version upgrade.
//        Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
//        db.execSQL(AkunDatabaseHandler.DATABASE_DROP);
//        onCreate(db);
//    }
//}
