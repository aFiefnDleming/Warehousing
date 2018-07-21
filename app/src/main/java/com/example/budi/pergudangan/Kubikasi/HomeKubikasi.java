package com.example.budi.pergudangan.Kubikasi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.budi.pergudangan.R;

public class HomeKubikasi extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id_kubikasi";
    public final static String TAG_NAMA = "nama";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_FOTO = "foto";
    String idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kubikasi);

        sharedpreferences = getSharedPreferences(LoginKubikasi.my_shared_preferences, Context.MODE_PRIVATE);
        idx = sharedpreferences.getString(TAG_ID, null);

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
