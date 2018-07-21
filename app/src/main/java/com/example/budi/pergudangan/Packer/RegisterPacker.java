package com.example.budi.pergudangan.Packer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.budi.pergudangan.Packer.Profile.Profile;
import com.example.budi.pergudangan.R;
import com.example.budi.pergudangan.Server.AppController;
import com.example.budi.pergudangan.Server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterPacker extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register;
    EditText txt_nama, txt_email, txt_password, txt_confirm_password;
    Intent intent;
    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "register.php";
    private static final String TAG = RegisterPacker.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_packer);

        btn_register = findViewById(R.id.btn_signup);
        txt_nama = findViewById(R.id.input_name);
        txt_email = findViewById(R.id.input_email);
        txt_password = findViewById(R.id.input_password);
        txt_confirm_password = findViewById(R.id.konfirm_password);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nama = txt_nama.getText().toString();
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String confirm_password = txt_confirm_password.getText().toString();

                conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);{
                    if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkRegister(nama, email, password, confirm_password);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //membuat back button toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void checkRegister(final String nama, final String email, final String password, final String confirm_password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("RegisterPacker ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "RegisterPacker Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Check for error node in json
                    if (success == 1) {
                        Intent login = new Intent(RegisterPacker.this, LoginPacker.class);
                        startActivity(login);
                        Log.e("Successfully Register!", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        txt_nama.setText("");
                        txt_email.setText("");
                        txt_password.setText("");
                        txt_confirm_password.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "LoginPacker Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama);
                params.put("email", email);
                params.put("password", password);
                params.put("confirm_password", confirm_password);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //membuat fungsi back dengan mengirim data session
    @Override
    public void onBackPressed() {
        intent = new Intent(RegisterPacker.this, LoginPacker.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), LoginPacker.class);
        startActivity(intent);
        return true;
    }

}
