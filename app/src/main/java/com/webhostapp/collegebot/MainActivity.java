package com.webhostapp.collegebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ResponseListener {

    Button loginButton;
    EditText emailEditText, passwordEditText;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
        if(session.getLogin()){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(),"Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String,String> data = new HashMap<>();
                data.put("email",email);
                data.put("password",password);
                Network network  = new Network(MainActivity.this);
                network.setMessage("Logging In");
                network.setResponseListener(MainActivity.this);
                network.setPage("login.php");
                network.execute(data);
            }
        });
    }

    @Override
    public void responseReceived(String url, String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            if(jsonObject.getBoolean("success")){
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                Session session = new Session(this);
                session.setLogin(true);
                session.setId(String.valueOf(jsonObject.getInt("id")));
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
