package com.tekmob.mapx;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tekmob.mapx.database.AkunDatabaseHandler;
import com.tekmob.mapx.database.MapsDatabaseHandler;
import com.tekmob.mapx.database.PenandaDatabaseHandler;
import com.tekmob.mapx.domain.Akun;
import com.tekmob.mapx.domain.Penanda;

import java.util.List;


public class PenandaActivity extends AppCompatActivity {
    private PenandaDatabaseHandler dbPenanda;
    private MapsDatabaseHandler dbMaps;
    TextView editTextNama;
    TextView editTextKategori;
    Bundle extras;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penanda);


        showPenanda();
    }
    public void showPenanda(){
        final Context context = this;
//        editTextNama = (TextView) findViewById(R.id.table_row_nama_id);
//        editTextKategori = (TextView) findViewById(R.id.table_row_kategori_id);
        TableLayout tl = (TableLayout) findViewById(R.id.table_layout_id);

        TableRow tr_head = new TableRow(this);

        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

        String[] headerText={"No","Nama","Kategori"};
        Log.d("data" , "data set");
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            if(!c.equals("No"))
            tv.setWidth(250);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            tr_head.addView(tv);
        }
        tl.addView(tr_head);

        Intent intent = getIntent();
        extras = getIntent().getExtras();
        int value = extras.getInt("id");

        dbPenanda = new PenandaDatabaseHandler(this);
        List<Penanda> allPenanda = dbPenanda.findAll();
        Log.d("data", "datanya - " + allPenanda.size());
        Integer count=1;
        for(Penanda p : allPenanda){
            Log.d("data ", "ID :"+p.getId()+" | NAMA :"+p.getNama()+" | KATEGORI:"+p.getKategori());

            String nama = p.getNama();
            String kategori = p.getKategori();

            // dara rows
            TableRow row = new TableRow(context);

            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            String[] colText={count+"",nama,kategori, "button"};
            for(String text:colText) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                if(text.equals("button")){
                    button = new Button(this);
                    button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    button.setText("Detail");
                    button.setBackgroundColor(Color.WHITE);
                    button.setPadding(10,5,5,5);

                    button.setTextColor(Color.BLACK);
                    button.setId(count);
                    row.addView(button);

                    System.out.println(button.getId());


                   button.setOnClickListener( new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            Intent intent = new Intent(context,PenandaDetail.class);


                            intent.putExtra("id_button",button.getId());

                            intent.putExtra("id", extras.getInt("id"));


                            startActivity(intent);

                        }
                    });

                    continue;
                }
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(16);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                tv.setTextColor(Color.BLACK);
                row.addView(tv);
            }
            count++;

            tl.addView(row);

        }
//
//            View tableRow = LayoutInflater.from(this).inflate(R.layout.penanda,null,false);
//            TextView nama  = (TextView) tableRow.findViewById(R.id.table_row_nama_id);
//            TextView kategori = (TextView) tableRow.findViewById(R.id.table_row_kategori_id);
//
//            nama.setText(p.getNama());
//            kategori.setText(p.getKategori());
//            tl.addView(tableRow);
//
//            count++;
//        }
    }
}