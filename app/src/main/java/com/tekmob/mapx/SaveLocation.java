package com.tekmob.mapx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.tekmob.mapx.database.MapsDatabaseHandler;

import com.tekmob.mapx.database.PenandaDatabaseHandler;
import com.tekmob.mapx.domain.Akun;
import com.tekmob.mapx.domain.Maps;
import com.tekmob.mapx.domain.Penanda;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.lang.String;
/**
 * Created by afiq-pc on 11/5/2017.
 */

public class SaveLocation extends AppCompatActivity {

    String koorX;
    String koorY;
    MapsDatabaseHandler databaseMapsHandler;
    PenandaDatabaseHandler databasePenandaHandler;

    EditText editText ;
    EditText editTextNamaTempat ;
    EditText editTextKeterangan;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_layer);
        Intent intent = getIntent();
        //Constructor

        editText = (EditText)findViewById(R.id.text_koordinat);
        editTextNamaTempat = (EditText)findViewById(R.id.nama_tempat);
        editTextKeterangan = (EditText)findViewById(R.id.keterangan);
        radioGroup= (RadioGroup) findViewById(R.id.jenis_tempat);

        String koordinat = intent.getStringExtra("koordinat");
        StringTokenizer str = new StringTokenizer(koordinat);


        str.nextToken("(");

        koorX = str.nextToken(",");
        koorX = koorX.substring(1,koorX.length());



         koorY = str.nextToken("),");


//        System.out.println(koorX);
        final Context context = this;
        Button not_this_time = (Button)findViewById(R.id.not_this_time);
        Button save_this_location = (Button)findViewById(R.id.save_this_location);








        editText.setText(koordinat, TextView.BufferType.EDITABLE);


        databaseMapsHandler=new MapsDatabaseHandler(this);
        databasePenandaHandler=new PenandaDatabaseHandler(this);


        not_this_time.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(context, MainActivity.class);



                startActivity(intent);

            }
        });

        save_this_location.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



                Log.d("insert", "inserting data");
                Maps map =  new Maps(koorX, koorY, "1");

                databaseMapsHandler.save(map);



                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                System.out.println(map.getId());

                String isiRadio = radioButton.getText().toString();

                Penanda penanda = new Penanda(databaseMapsHandler.findlastid(),editTextNamaTempat.getText().toString(),editTextKeterangan.getText().toString()
                        ,isiRadio, DateFormat.getDateTimeInstance().format(new Date()));


                databasePenandaHandler.save(penanda);


//            //Test Save DB
                List<Penanda> listPenanda=databasePenandaHandler.findAll();
                for(Penanda b:listPenanda){
                    Log.d("data", "ID :"+b.getId()+ "|ID MAPS :"+b.getIdMaps() +" | Nama Tempat :"+b.getNama()+" | Nama Keterangan:"+b.getKeterangan() +
                            " |Kategori :"+b.getKategori()+" |Waktu :"+b.getTimestamp()
                    );
                }
//
//                //Test Save DB
                List<Maps> listMap=databaseMapsHandler.findAll();
                for(Maps b:listMap){
                    Log.d("data", "ID :"+b.getId()
                    );
                }



                Intent intent = new Intent(context, MainActivity.class);



                startActivity(intent);

            }
        });




    }


}
