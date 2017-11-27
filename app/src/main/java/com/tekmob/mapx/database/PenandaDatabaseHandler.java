package com.tekmob.mapx.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tekmob.mapx.domain.Akun;
import com.tekmob.mapx.domain.Penanda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conqueror on 10/30/2017.
 */

public class PenandaDatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "PenandaManager";

    private static final String TABLE_PENANDA = "t_penanda";
    private static final String TABLE_MAPS = "t_maps";
    private static final String TABLE_USER = "t_user";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_USER = "idUser";
    private static final String KEY_ID_MAPS = "idMaps";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_KETERANGAN = "keterangan";
    private static final String KEY_KATEGORI = "kategori";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_GAMBAR = "gambar";


    public PenandaDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PENANDA_TABLE = "CREATE TABLE " + TABLE_PENANDA + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID_MAPS + " INTEGER,"
                + KEY_ID_USER + " INTEGER,"
                + KEY_NAMA + " TEXT,"
                + KEY_KETERANGAN + " TEXT,"
                + KEY_KATEGORI + " TEXT,"
                + KEY_TIMESTAMP + " TEXT,"
                + KEY_GAMBAR + " TEXT,"
                + "FOREIGN KEY(" + KEY_ID_USER + ")REFERENCES " + TABLE_USER + "(" + KEY_ID     +"),"
                + "FOREIGN KEY(" + KEY_ID_MAPS + ")REFERENCES " + TABLE_MAPS + "(" + KEY_ID +"))";
        db.execSQL(CREATE_PENANDA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENANDA);

        onCreate(db);
    }

    public void save(Penanda penanda){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID_MAPS, penanda.getIdMaps());
        values.put(KEY_ID_USER, penanda.getIdUser());

        values.put(KEY_NAMA, penanda.getNama());
        values.put(KEY_KETERANGAN, penanda.getKeterangan());
        values.put(KEY_KATEGORI, penanda.getKategori());

        values.put(KEY_TIMESTAMP, penanda.getTimestamp());
        values.put(KEY_GAMBAR, penanda.getGambar());
        db.insert(TABLE_PENANDA, null, values);
        db.close();
    }

    public Penanda findOne(int id){
        String query="SELECT * FROM "+ TABLE_PENANDA + " WHERE "+ KEY_ID + "=" + id ;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        cursor.moveToFirst();

        return new Penanda(Integer.valueOf(cursor.getString(0)),Integer.valueOf(cursor.getString(1))
                ,Integer.valueOf(cursor.getString(2)),cursor.getString(3),cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));
    }

    public List<Penanda> findAll(){
        List<Penanda> listPenanda=new ArrayList<Penanda>();
        String query="SELECT * FROM " + TABLE_PENANDA;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Penanda penanda=new Penanda();
                penanda.setId(Integer.valueOf(cursor.getString(0)));
                penanda.setIdMaps(Integer.valueOf(cursor.getString(1)));
                penanda.setIdUser(Integer.valueOf(cursor.getString(2)));

                penanda.setNama(cursor.getString(3));
                penanda.setKeterangan(cursor.getString(4));
                penanda.setKategori(cursor.getString(5));
                penanda.setTimestamp(cursor.getString(6));
                listPenanda.add(penanda);
            }while(cursor.moveToNext());
        }

        return listPenanda;
    }

    public void update(Penanda penanda){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_ID_MAPS, penanda.getIdMaps());
        values.put(KEY_NAMA, penanda.getNama());
        values.put(KEY_KETERANGAN, penanda.getKeterangan());
        values.put(KEY_KATEGORI, penanda.getKategori());
        values.put(KEY_TIMESTAMP, penanda.getTimestamp());
        values.put(KEY_GAMBAR, penanda.getGambar());


        db.update(TABLE_PENANDA, values, KEY_ID+"=?", new String[]{String.valueOf(penanda.getId())});
        db.close();
    }

    public void delete(Penanda penanda){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_PENANDA, KEY_ID+"=?", new String[]{String.valueOf(penanda.getId())});
        db.close();
    }
}
