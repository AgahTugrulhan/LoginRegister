package com.example.bamba.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText editTextName = (EditText) findViewById(R.id.editText_name_register);
        final EditText editTextSurname = (EditText) findViewById(R.id.editText_surname_register);
        final EditText editTextUn = (EditText) findViewById(R.id.editText_username_register);
        final EditText editTextPw = (EditText) findViewById(R.id.editText_pw_register);
        final EditText editTextEmail = (EditText) findViewById(R.id.editText_email_register);
        Button buttonRegister = (Button) findViewById(R.id.button_register);



buttonRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String name = editTextName.getText().toString();
        final String surname = editTextSurname.getText().toString();
        final String un = editTextUn.getText().toString();
        final String mail = editTextEmail.getText().toString();
        final String pw = editTextPw.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intentRegister = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intentRegister);
                    }else {AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Kayıt başarısız").setNegativeButton("Tekrar deneyin",null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterRequestNew registerRequestNew = new RegisterRequestNew(name,surname,un,mail,pw,responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(registerRequestNew);

    }
});
    }
}
