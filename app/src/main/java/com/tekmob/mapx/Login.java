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

public class Login extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static Button login_button;
    private static Button signup_button;
    private AkunDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new AkunDatabaseHandler(this);
        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        Log.d("insert", "inserting data");
        databaseHandler.save(new Akun("agung", "Agung Setiawan", "fahri.conqueror@gmail.com"));
        databaseHandler.save(new Akun("hauril","Hauril Maulida Nisfari", "fahri.conqueror@gmail.com"));
        databaseHandler.save(new Akun("user","pass", "fahri.conqueror@gmail.com"));

        LoginButton();
    }

    public void LoginButton(){
        final Context context = this;
        username = (EditText)findViewById(R.id.editText_user);
        password = (EditText)findViewById(R.id.editText_password);
        login_button = (Button)findViewById(R.id.button_login);
        signup_button = (Button) findViewById(R.id.button_signup);
        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String usernameStr = username.getText().toString();
                        String passStr = password.getText().toString();
                        Akun res = db.findOne(usernameStr);

                        if (res == null) {
                            Toast.makeText(Login.this,"Username doesn't exist",
                                    Toast.LENGTH_SHORT).show();
                        } else if(!res.getPassword().equals(passStr)){
                            Toast.makeText(Login.this,"Not Match",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Login.this,"Username and password is correct with user id = " + res.getId(),
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("id", res.getId());
                            startActivity(intent);
                        }
//                        if (username.getText().toString().equals("user") && password.getText().toString().equals("pass")) {
//                                Toast.makeText(Login.this,"Username and password is correct",
//                                        Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(context, MainActivity.class);
//                                startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(Login.this,"Username and password is NOT correct",
//                                    Toast.LENGTH_SHORT).show();
//                        }
                    }
                }
        );

        signup_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Signup.class);
                        startActivity(intent);
                    }
                }
        );
    }
}