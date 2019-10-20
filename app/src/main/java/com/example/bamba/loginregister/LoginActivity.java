package com.example.bamba.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText textUserName;
    EditText textPassword;
    Button buttonLogin;
    TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUserName = (EditText) findViewById(R.id.editText_username);
        textPassword = (EditText) findViewById(R.id.editText_pw);
        buttonLogin = (Button) findViewById(R.id.button_login);
        textViewRegister = (TextView) findViewById(R.id.textView_register);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = textUserName.getText().toString();
                final String password = textPassword.getText().toString();
                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success)
                            {
                                String name = jsonObject.getString("name");
                                String surname = jsonObject.getString("surname");
                                String email = jsonObject.getString("email");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("name",name);
                                intent.putExtra("surname",surname);
                                intent.putExtra("email",email);
                                LoginActivity.this.startActivity(intent);
                            }
                            else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login başarısız").setNegativeButton("Tekrar deneyin",null).create().show();
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userName,password,response);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }


}
