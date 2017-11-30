package com.tekmob.mapx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.tekmob.mapx.database.AkunDatabaseHandler;
import com.tekmob.mapx.domain.Akun;

public class Signup extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static EditText confirmPassword;
    private static Button signup_button;
    private AkunDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new AkunDatabaseHandler(this);
        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        SignUpButton();
    }

    public void SignUpButton(){
        final Context context = this;
        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        confirmPassword = (EditText) findViewById(R.id.editText_confirm_password);
        signup_button = (Button) findViewById(R.id.button_signup);
        signup_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String usernameStr = username.getText().toString();
                        String passStr = password.getText().toString();
                        String confPassStr = confirmPassword.getText().toString();

                        if (usernameStr.equals("") || confPassStr.equals("") || passStr.equals("") || !passStr.equals(confPassStr)) {
                            Toast.makeText(Signup.this,"Cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            Akun res = db.findOne(usernameStr);
                            if (res != null) {
                                Toast.makeText(Signup.this,"Username exist", Toast.LENGTH_SHORT).show();
                            } else {
                                Akun newAkun = new Akun();
                                newAkun.setUsername(usernameStr);
                                newAkun.setPassword(passStr);
                                newAkun.setEmail("email");
                                db.save(newAkun);
                                Toast.makeText(Signup.this,"Signupas", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Login.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
        );
    }
}