package com.tekmob.mapx.database;

import java.util.ArrayList;
import java.util.List;

import com.tekmob.mapx.domain.Akun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Conqueror on 10/29/2017.
 */

public class AkunDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "AkunManager";

    private static final String TABLE_AKUN = "t_akun";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    public AkunDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AKUN_TABLE = "CREATE TABLE " + TABLE_AKUN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_AKUN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AKUN);

        onCreate(db);
    }

    public void save(Akun akun){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_USERNAME, akun.getUsername());
        values.put(KEY_PASSWORD, akun.getPassword());
        values.put(KEY_EMAIL, akun.getEmail());

        db.insert(TABLE_AKUN, null, values);
        db.close();
    }

    public Akun findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_AKUN, new String[]{KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_EMAIL},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }


        return new Akun(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3));
    }

    public Akun findOne(String username){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_AKUN, new String[]{KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_EMAIL},
                KEY_USERNAME+"=?", new String[]{String.valueOf(username)}, null, null, null);

        if(cursor.getCount() < 1){
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        Akun res = new Akun(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3));
        cursor.close();
        return res;
    }

    public List<Akun> findAll(){
        List<Akun> listAkun=new ArrayList<Akun>();
        String query="SELECT * FROM "+TABLE_AKUN;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Akun akun=new Akun();
                akun.setId(Integer.valueOf(cursor.getString(0)));
                akun.setUsername(cursor.getString(1));
                akun.setPassword(cursor.getString(2));
                akun.setEmail(cursor.getString(3));
                listAkun.add(akun);
            }while(cursor.moveToNext());
        }

        return listAkun;
    }

    public void update(Akun akun){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_USERNAME, akun.getUsername());
        values.put(KEY_PASSWORD, akun.getPassword());
        values.put(KEY_EMAIL, akun.getEmail());

        db.update(TABLE_AKUN, values, KEY_ID+"=?", new String[]{String.valueOf(akun.getId())});
        db.close();
    }

    public void delete(Akun akun){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_AKUN, KEY_ID+"=?", new String[]{String.valueOf(akun.getId())});
        db.close();
    }
}