package com.tekmob.mapx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.tekmob.mapx.database.AkunDatabaseHandler;
import com.tekmob.mapx.domain.Akun;

import org.w3c.dom.Text;


public class ProfileDesign extends AppCompatActivity {
    private AkunDatabaseHandler db;
    TextView editText;
    TextView editTextNama;
    TextView editTextUsername;
    TextView editTextEmail;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        db = new AkunDatabaseHandler(this);
        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        showProfile();
    }
    public void showProfile(){
        final Context context = this;
        editTextUsername = (TextView)findViewById(R.id.textview_username_id);
        editTextNama = (TextView) findViewById(R.id.textview_name_id);
        editTextEmail = (TextView) findViewById(R.id.textview_email_id);

        Intent intent = getIntent();
        extras = getIntent().getExtras();
        int value = extras.getInt("id");


        db = new AkunDatabaseHandler(this);
        Akun akun = db.findOne(value);
        Log.d("data ", "ID :"+akun.getId()+" | NAMA :" + akun.getUsername()+" | KATEGORI:"+ akun.getEmail());
        editTextUsername.setText(akun.getUsername());
        editTextNama.setText(akun.getUsername());
        editTextEmail.setText(akun.getEmail());
    }
}