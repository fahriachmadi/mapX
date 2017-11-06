package com.tekmob.mapx.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tekmob.mapx.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conqueror on 10/30/2017.
 */

public class UserDatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UserManager";

    private static final String TABLE_USER = "t_user";
    private static final String TABLE_AKUN = "t_akun";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_AKUN = "idAkun";
    private static final String KEY_TANGGAL_LAHIR = "tanggalLahir";
    private static final String KEY_NO_TELEPON = "noTelepon";
    private static final String KEY_JENIS_KELAMIN = "jenisKelamin";
    private static final String KEY_NEGARA = "negara";
    private static final String KEY_PROVINSI = "provinsi";
    private static final String KEY_KOTA = "kota";
    private static final String KEY_KECAMATAN = "kecamatan";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_FOTO = "foto";

    public UserDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_AKUN + " INTEGER,"
                + KEY_TANGGAL_LAHIR + " TEXT," + KEY_NO_TELEPON + " TEXT, " + KEY_JENIS_KELAMIN + " TEXT," +
                KEY_NEGARA + " TEXT," + KEY_PROVINSI + " TEXT," + KEY_KOTA + " TEXT," + KEY_KECAMATAN + " TEXT,"
                + KEY_ALAMAT + " TEXT," + KEY_FOTO + " TEXT, FOREIGN KEY(" + KEY_ID_AKUN + ")REFERENCES " + TABLE_AKUN + "(" +
                KEY_ID_AKUN +"))";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public void save(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID_AKUN, user.getIdAkun());
        values.put(KEY_TANGGAL_LAHIR, user.getTanggalLahir());
        values.put(KEY_NO_TELEPON, user.getNoTelepon());
        values.put(KEY_JENIS_KELAMIN, user.getJenisKelamin());
        values.put(KEY_NEGARA, user.getNegara());
        values.put(KEY_PROVINSI, user.getProvinsi());
        values.put(KEY_KOTA, user.getKota());
        values.put(KEY_KECAMATAN, user.getKecamatan());
        values.put(KEY_ALAMAT, user.getAlamat());
        values.put(KEY_FOTO, user.getFoto());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USER, new String[]{KEY_ID,KEY_ID_AKUN,KEY_TANGGAL_LAHIR,KEY_NO_TELEPON,KEY_JENIS_KELAMIN,KEY_NEGARA,
                KEY_PROVINSI,KEY_KOTA,KEY_KECAMATAN,KEY_ALAMAT,KEY_FOTO},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        return new User(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7),
                cursor.getString(8),cursor.getString(9),cursor.getString(10));
    }

    public List<User> findAll(){
        List<User> listUser=new ArrayList<User>();
        String query="SELECT * FROM "+TABLE_USER;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                User user=new User();
                user.setId(Integer.valueOf(cursor.getString(0)));
                user.setIdAkun(Integer.valueOf(cursor.getString(1)));
                user.setTanggalLahir(cursor.getString(2));
                user.setNoTelepon(cursor.getString(3));
                user.setJenisKelamin(cursor.getString(4));
                user.setNegara(cursor.getString(5));
                user.setProvinsi(cursor.getString(6));
                user.setKota(cursor.getString(7));
                user.setKecamatan(cursor.getString(8));
                user.setAlamat(cursor.getString(9));
                user.setFoto(cursor.getString(10));

                listUser.add(user);
            }while(cursor.moveToNext());
        }

        return listUser;
    }

    public void update(User user){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_ID_AKUN, user.getIdAkun());
        values.put(KEY_TANGGAL_LAHIR, user.getTanggalLahir());
        values.put(KEY_NO_TELEPON, user.getNoTelepon());
        values.put(KEY_JENIS_KELAMIN, user.getJenisKelamin());
        values.put(KEY_NEGARA, user.getNegara());
        values.put(KEY_PROVINSI, user.getProvinsi());
        values.put(KEY_KOTA, user.getKota());
        values.put(KEY_KECAMATAN, user.getKecamatan());
        values.put(KEY_ALAMAT, user.getAlamat());
        values.put(KEY_FOTO, user.getFoto());

        db.update(TABLE_USER, values, KEY_ID+"=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void delete(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID+"=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
