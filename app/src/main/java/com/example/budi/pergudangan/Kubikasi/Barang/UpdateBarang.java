package com.example.budi.pergudangan.Kubikasi.Barang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.budi.pergudangan.Kubikasi.HomeKubikasi;
import com.example.budi.pergudangan.R;
import com.example.budi.pergudangan.Server.AppController;
import com.example.budi.pergudangan.Server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateBarang extends AppCompatActivity {

    EditText tvidb, tvnamab, tvlebar, tvpanjang, tvtinggi, tvberat, tvharga, tvTgl, tvqty, tvTotal;
    Spinner spTujuan;

    //untuk delete barang
    private String urld = Server.URLK + "delete_barang.php";
    ProgressDialog pdd;

    //untuk update barang
    ProgressDialog pdu;
    int success;
    ConnectivityManager conMgr;
    private String urlu = Server.URLK + "update_barang.php";
    private static final String TAG = UpdateBarang.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);

        tvidb = findViewById(R.id.idb);
        tvnamab = findViewById(R.id.namab);
        tvlebar = findViewById(R.id.lebar);
        tvpanjang = findViewById(R.id.panjang);
        tvtinggi = findViewById(R.id.tinggi);
        tvberat = findViewById(R.id.berat);
        tvharga = findViewById(R.id.harga);
        tvTgl = findViewById(R.id.tgl);
        spTujuan = findViewById(R.id.tujuan);
        tvqty = findViewById(R.id.qty);
        tvTotal = findViewById(R.id.total);

        tvidb.setText(getIntent().getStringExtra("id_barang"));
        tvnamab.setText(getIntent().getStringExtra("nama_barang"));
        tvlebar.setText(getIntent().getStringExtra("lebar"));
        tvpanjang.setText(getIntent().getStringExtra("panjang"));
        tvtinggi.setText(getIntent().getStringExtra("tinggi"));
        tvberat.setText(getIntent().getStringExtra("berat"));
        tvharga.setText(getIntent().getStringExtra("harga"));
        tvTgl.setText(getIntent().getStringExtra("tgl_masuk"));
//        spTujuan.setText(getIntent().getStringExtra("tujuan"));
        tvqty.setText(getIntent().getStringExtra("qty"));
        tvTotal.setText(getIntent().getStringExtra("total"));

        pdd = new ProgressDialog(UpdateBarang.this);

        // Spinner Drop down elements
        List<String> tujuan = new ArrayList<String>();
        tujuan.add("Paster");
        tujuan.add("Cimahi");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tujuan);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spTujuan.setAdapter(dataAdapter);

        //membuat back button toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void update (View v) {
        String idb = tvidb.getText().toString();
        String namab = tvnamab.getText().toString();
        String lebar = tvlebar.getText().toString();
        String panjang = tvpanjang.getText().toString();
        String tinggi = tvtinggi.getText().toString();
        String berat = tvberat.getText().toString();
        String harga = tvharga.getText().toString();
        String tujuan = spTujuan.getSelectedItem().toString();
        String qty = tvqty.getText().toString();

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);{
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                updateLokasi(idb, namab,lebar, panjang, tinggi, berat, harga, tujuan, qty);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateLokasi (final String idb, final String namab, final String lebar, final String panjang, final String tinggi, final String berat, final String harga, final String tujuan, final String qty) {
        pdu = new ProgressDialog(this);
        pdu.setCancelable(false);
        pdu.setMessage("Update ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, urlu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Update Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Check for error node in json
                    if (success == 1) {
                        Intent login = new Intent(getApplicationContext(), HomeKubikasi.class);
                        startActivity(login);
                        Log.e("Update berhasil", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Update Error : " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_barang", idb);
                params.put("nama_barang", namab);
                params.put("lebar", lebar);
                params.put("panjang", panjang);
                params.put("tinggi", tinggi);
                params.put("berat", berat);
                params.put("harga", harga);
                params.put("tujuan", tujuan);
                params.put("qty", qty);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pdu.isShowing())
            pdu.show();
    }

    private void hideDialog() {
        if (pdu.isShowing())
            pdu.dismiss();
    }

    public void delete (View view) {
        deleteData();
    }

    //membuat fungsi back dengan mengirim data session
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), HomeKubikasi.class);
        startActivity(intent);
        return true;
    }

    //melakukan eksekusi delete data lokasi
    private void deleteData() {
        pdd.setMessage("Delete Data ...");
        pdd.setCancelable(false);
        pdd.show();
        StringRequest delReq = new StringRequest(Request.Method.POST, urld, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pdd.cancel();
                Log.d("volley","response : " + response.toString());
                try {
                    JSONObject res = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),res.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(), HomeKubikasi.class));
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Gagal menghapus lokasi", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id_barang", tvidb.getText().toString());
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(delReq);
    }

}
