package com.example.budi.pergudangan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.budi.pergudangan.Kubikasi.LoginKubikasi;
import com.example.budi.pergudangan.Packer.LoginPacker;

public class PilihActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih);

        sharedpreferences = getSharedPreferences(LoginPacker.my_shared_preferences, Context.MODE_PRIVATE);

    }

    public void packer (View view) {
        Intent packer = new Intent(getApplicationContext(), LoginPacker.class);
        startActivity(packer);
    }

    public void kubikasi (View view) {
        Intent kubikasi = new Intent(getApplicationContext(), LoginKubikasi.class);
        startActivity(kubikasi);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
