package com.example.budi.pergudangan.Kubikasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.budi.pergudangan.R;
import com.example.budi.pergudangan.Server.AdapterBarang;
import com.example.budi.pergudangan.Server.AppController;
import com.example.budi.pergudangan.Server.ModelBarang;
import com.example.budi.pergudangan.Server.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeKubikasi extends AppCompatActivity {

    //untuk login kubikasi
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id_kubikasi";
    public final static String TAG_NAMA = "nama";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_FOTO = "foto";
    String idx;

    //untuk menampilkan data list barang pada recyclerview
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelBarang> mItems;
    ProgressDialog pd;
    private String urld = Server.URLK + "getallbarang.php";
    public static final String TAG_IDB = "id_barang";
    public static final String TAG_NAMAB = "nama_barang";
    public static final String TAG_LEBAR = "lebar";
    public static final String TAG_PANJANG = "panjang";
    public static final String TAG_TINGGI = "tinggi";
    public static final String TAG_BERAT = "berat";
    public static final String TAG_HARGA = "harga";
    public static final String TAG_TUJUAN = "tujuan";
    public static final String TAG_QTY = "qty";
    public static final String TAG_STOCK = "stock";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kubikasi);

        sharedpreferences = getSharedPreferences(LoginKubikasi.my_shared_preferences, Context.MODE_PRIVATE);
        idx = sharedpreferences.getString(TAG_ID, null);

        //menampilkan data lokasi dari database ke recycleview
        mRecyclerview = findViewById(R.id.recyclerviewTemp);
        pd = new ProgressDialog(HomeKubikasi.this);
        mItems = new ArrayList<>();
        loadJson();
        mManager = new LinearLayoutManager(HomeKubikasi.this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterBarang(HomeKubikasi.this,mItems);
        mRecyclerview.setAdapter(mAdapter);

    }

    //melakukan pengambilan data dari database
    private void loadJson() {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, urld,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pd.cancel();
                Log.d("volley","response : " + response.toString());
                for(int i = 0 ; i < response.length(); i++)
                {
                    try {
                        JSONObject data = response.getJSONObject(i);
                        ModelBarang md = new ModelBarang();
                        md.setIdb(data.getString(TAG_IDB));
                        md.setNamab(data.getString(TAG_NAMAB));
                        md.setLebar(data.getString(TAG_LEBAR));
                        md.setPanjang(data.getString(TAG_PANJANG));
                        md.setTinggi(data.getString(TAG_TINGGI));
                        md.setBerat(data.getString(TAG_BERAT));
                        md.setHarga(data.getString(TAG_HARGA));
                        md.setTujuan(data.getString(TAG_TUJUAN));
                        md.setQty(data.getString(TAG_QTY));
                        md.setStock(data.getString(TAG_STOCK));
                        mItems.add(md);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(reqData);
    }

    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda yakin ingin logout ?");
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginKubikasi.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_NAMA, null);
                editor.putString(TAG_EMAIL, null);
                editor.putString(TAG_FOTO, null);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), LoginKubikasi.class);
                finish();
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
