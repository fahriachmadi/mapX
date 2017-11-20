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
        setContentView(R.layout.material_design_profile_screen_xml_ui_design);

        db = new AkunDatabaseHandler(this);
        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        showProfile();
    }
    public void showProfile(){
        final Context context = this;
    }
}