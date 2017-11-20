package com.tekmob.mapx;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tekmob.mapx.database.AkunDatabaseHandler;


public class ProfileDesign extends AppCompatActivity {
    private AkunDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penanda);

        db = new AkunDatabaseHandler(this);
        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        showPenanda();
    }
    public void showPenanda(){
        final Context context = this;
    }
}