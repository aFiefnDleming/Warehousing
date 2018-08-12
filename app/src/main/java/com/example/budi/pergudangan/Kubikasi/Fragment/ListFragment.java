package com.example.budi.pergudangan.Kubikasi.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.budi.pergudangan.Kubikasi.HomeKubikasi;
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
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

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
    public static final String TAG_TGL = "tgl_masuk";
    public static final String TAG_TUJUAN = "tujuan";
    public static final String TAG_QTY = "qty";
    public static final String TAG_TOTAL = "total";

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("List Barang");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //menampilkan data lokasi dari database ke recycleview
        mRecyclerview = view.findViewById(R.id.recyclerviewTemp);
        pd = new ProgressDialog(getActivity());
        mItems = new ArrayList<>();
        loadJson();
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterBarang(getActivity(),mItems);
        mRecyclerview.setAdapter(mAdapter);

        return view;
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
                        md.setTgl_masuk(data.getString(TAG_TGL));
                        md.setTujuan(data.getString(TAG_TUJUAN));
                        md.setQty(data.getString(TAG_QTY));
                        md.setTotal(data.getString(TAG_TOTAL));
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

}
