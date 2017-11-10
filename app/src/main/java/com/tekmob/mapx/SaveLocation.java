package com.tekmob.mapx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by afiq-pc on 11/5/2017.
 */

public class SaveLocation extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_layer);
        Intent intent = getIntent();
        String koordinat = intent.getStringExtra("koordinat");
        System.out.println(koordinat);
        final Context context = this;
        Button not_this_time = (Button)findViewById(R.id.not_this_time);
        EditText editText = (EditText)findViewById(R.id.text_koordinat);
        editText.setText(koordinat, TextView.BufferType.EDITABLE);

        not_this_time.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(context, MainActivity.class);



                startActivity(intent);

            }
        });



    }


}
