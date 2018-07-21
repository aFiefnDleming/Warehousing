package com.example.budi.pergudangan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.budi.pergudangan.Packer.Login;

public class PilihActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih);
    }

    public void packer (View view) {
        Intent packer = new Intent(getApplicationContext(), Login.class);
        startActivity(packer);
    }

//    public void kubikasi (View view) {
//        Intent kubikasi = new Intent(getApplicationContext(), LoginAdmin.class);
//        startActivity(kubikasi);
//    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
