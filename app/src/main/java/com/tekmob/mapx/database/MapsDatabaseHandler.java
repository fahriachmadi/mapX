package com.tekmob.mapx.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tekmob.mapx.domain.Akun;
import com.tekmob.mapx.domain.Maps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conqueror on 10/30/2017.
 */

public class MapsDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MapsManager";

    private static final String TABLE_MAPS = "t_maps";

    private static final String KEY_ID = "id";
    private static final String KEY_KOORDINAT_X = "koordinatX";
    private static final String KEY_KOORDINAT_Y = "koordinatY";
    private static final String KEY_NAMA = "nama";

    public MapsDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAPS_TABLE = "CREATE TABLE " + TABLE_MAPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_KOORDINAT_X + " TEXT,"
                + KEY_KOORDINAT_Y + " TEXT,"
                + KEY_NAMA + " TEXT" + ")";
        db.execSQL(CREATE_MAPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAPS);

        onCreate(db);
    }

    public void save(Maps maps){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_KOORDINAT_X, maps.getKoordinatX());
        values.put(KEY_KOORDINAT_Y, maps.getKoordinatY());
        values.put(KEY_NAMA, maps.getNama());

        db.insert(TABLE_MAPS, null, values);
        db.close();
    }

    public Maps findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_MAPS, new String[]{KEY_ID,KEY_KOORDINAT_X,KEY_KOORDINAT_Y,KEY_NAMA},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }


        return new Maps(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3));
    }

    public List<Maps> findAll(){
        List<Maps> listMaps=new ArrayList<Maps>();
        String query="SELECT * FROM "+ TABLE_MAPS;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Maps maps=new Maps();
                maps.setId(Integer.valueOf(cursor.getString(0)));
                maps.setKoordinatX(cursor.getString(1));
                maps.setKoordinatY(cursor.getString(2));
                maps.setNama(cursor.getString(3));
                listMaps.add(maps);
            }while(cursor.moveToNext());
        }

        return listMaps;
    }


    public int findlastid(){
        List<Maps> listMaps=new ArrayList<Maps>();
        String query="SELECT * FROM "+ TABLE_MAPS + " ORDER BY "+ KEY_ID + " DESC LIMIT 1";

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        cursor.moveToFirst();
        return Integer.valueOf(cursor.getString(0));
    }




    public void update(Maps maps){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_KOORDINAT_X, maps.getKoordinatX());
        values.put(KEY_KOORDINAT_Y, maps.getKoordinatY());
        values.put(KEY_NAMA, maps.getNama());

        db.update(TABLE_MAPS, values, KEY_ID+"=?", new String[]{String.valueOf(maps.getId())});
        db.close();
    }

    public void delete(Maps maps){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_MAPS, KEY_ID+"=?", new String[]{String.valueOf(maps.getId())});
        db.close();
    }
}
