package com.tekmob.mapx;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tekmob.mapx.database.AkunDatabaseHandler;
import com.tekmob.mapx.database.MapsDatabaseHandler;
import com.tekmob.mapx.database.PenandaDatabaseHandler;
import com.tekmob.mapx.domain.Akun;
import com.tekmob.mapx.domain.Penanda;

import java.util.List;


public class PenandaDetail extends AppCompatActivity {
    private PenandaDatabaseHandler dbPenanda;
    private MapsDatabaseHandler dbMaps;
    TextView editTextNama;
    TextView editKeterangan;
    TextView jenisTempat;
    ImageView gambar;
    Bundle extras;
    Button go_to_location ;
    Button back_to_app ;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Sudah di detail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_penanda);

        editTextNama = (TextView) findViewById(R.id.textview_content_nama);
        editKeterangan = (TextView)findViewById(R.id.textview_content_keterangan);

        jenisTempat = (TextView)findViewById(R.id.textview_content_jenis_tempat);
        gambar = (ImageView) findViewById(R.id.gambar);
        Intent intent = getIntent();
        extras = intent.getExtras();
        int id_user = extras.getInt("id");
        int id_penanda = extras.getInt("id_button");



        dbPenanda = new PenandaDatabaseHandler(this);

        final Penanda penanda = dbPenanda.findOne(id_penanda);

    //    System.out.println(penanda.getKategori());
        editKeterangan.setText(penanda.getKeterangan(), TextView.BufferType.EDITABLE);
        editTextNama.setText(penanda.getNama(), TextView.BufferType.EDITABLE);
        jenisTempat.setText(penanda.getKategori(), TextView.BufferType.EDITABLE);

        String imageString = penanda.getGambar();
        //decode base64 string to image
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        gambar.setImageBitmap(decodedByte);
        gambar.setVisibility(View.VISIBLE);

        Button go_to_location = (Button)findViewById(R.id.go_to_location);
        Button back_to_app = (Button)findViewById(R.id.back_to_app);

        go_to_location.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(context, MainActivity.class);

                intent.putExtra("id", extras.getInt("id"));
                intent.putExtra("id_maps", penanda.getIdMaps());

                startActivity(intent);
            }
        });

        back_to_app.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", extras.getInt("id"));
                startActivity(intent);
            }
        });
    }

}