package com.tekmob.mapx;

import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import com.tekmob.mapx.database.AkunDatabaseHandler;
import com.tekmob.mapx.domain.Akun;


/**
 * Created by Conqueror on 10/29/2017.
 */
public class AkunActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        Log.d("insert", "inserting data");
        databaseHandler.save(new Akun("agung", "Agung Setiawan", "fahri.conqueror@gmail.com"));
        databaseHandler.save(new Akun("hauril","Hauril Maulida Nisfari", "fahri.conqueror@gmail.com"));

        Log.d("reading", "reading all data");
//        List<Akun> listAkun=databaseHandler.findAll();
//        for(Akun b:listAkun){
//            Log.d("data", "ID :"+b.getId()+" | USERNAME :"+b.getUsername()+" | EMAIL:"+b.getEmail());
//        }

        Log.d("reading","reading one data");
        Akun b=databaseHandler.findOne(2);
        Log.d("data", "ID :"+b.getId()+" | USERNAME :"+b.getUsername()+" | EMAIL:"+b.getEmail());

        Log.d("update","updating data");
        b.setUsername("Map");
        databaseHandler.update(b);
        Log.d("reading","reading one data after update");
        Akun bUpdate=databaseHandler.findOne(2);
        Log.d("data", "ID :"+b.getId()+" | USERNAME :"+b.getUsername()+" | EMAIL:"+b.getEmail());

        Log.d("delete", "deleting data");
        databaseHandler.delete(b);
        Log.d("reading", "reading all data after delete");
        List<Akun> listAkun2=databaseHandler.findAll();
        for(Akun b2:listAkun2){
            Log.d("data", "ID :"+b.getId()+" | USERNAME :"+b.getUsername()+" | EMAIL:"+b.getEmail());
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.akun, menu);
//        return true;
//    }
}