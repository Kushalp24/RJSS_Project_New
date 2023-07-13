package com.example.kushal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    public EditText usernameEditText, passwordEditText;
    private Button loginButton;


    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);


        mRequestQueue = Volley.newRequestQueue(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FullName="Kushal Pawar";

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                login(username, password);
            }
        });
    }


    private void login(String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://5e27-122-169-92-59.ngrok-free.app/api/user?username=bhausahebp0106@gmail.com";



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                           JSONArray jsonArray=  new JSONArray(response);
                            String Newpassword = passwordEditText.getText().toString();
                            Boolean passFound=Boolean.FALSE;


                            for (int i = 0; i < jsonArray.length(); i++) {
                                String rjss_Pwd = jsonArray.getJSONObject(i).getString("rjss_password");

                                if (Newpassword.equals(rjss_Pwd)) {
                                    passFound = true;
                                    break;
                                }
                            }
                                    if (passFound==true){
                                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, Spinner.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(Login.this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();
                                    }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("LoginError", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("LoginError", error.toString());
            }
        });

        queue.add(stringRequest);
    }
}