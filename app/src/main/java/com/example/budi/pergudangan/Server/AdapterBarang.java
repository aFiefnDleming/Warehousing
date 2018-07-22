package com.example.budi.pergudangan.Server;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budi.pergudangan.Kubikasi.Barang.UpdateBarang;
import com.example.budi.pergudangan.R;

import java.util.List;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.HolderBarang> {

    private List<ModelBarang> mItems ;
    private Context context;
    public AdapterBarang (Context context, List<ModelBarang> items) {
        this.mItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderBarang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row,parent,false);
        HolderBarang holderBarang = new HolderBarang(layout);
        return holderBarang;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderBarang holder, final int position) {

        ModelBarang md  = mItems.get(position);
        holder.tvidb.setText(md.getIdb());
        holder.tvnamab.setText(md.getNamab());
        holder.tvlebar.setText(md.getLebar());
        holder.tvpanjang.setText(md.getPanjang());
        holder.tvtinggi.setText(md.getTinggi());
        holder.tvberat.setText(md.getBerat());
        holder.tvharga.setText(md.getHarga());
        holder.tvtujuan.setText(md.getTujuan());
        holder.tvqty.setText(md.getQty());
        holder.tvstock.setText(md.getStock());

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateBarang.class);
                i.putExtra("id_barang", mItems.get(position).getIdb());
                i.putExtra("nama_barang", mItems.get(position).getNamab());
                i.putExtra("lebar", mItems.get(position).getLebar());
                i.putExtra("panjang", mItems.get(position).getPanjang());
                i.putExtra("tinggi", mItems.get(position).getTinggi());
                i.putExtra("berat", mItems.get(position).getBerat());
                i.putExtra("harga", mItems.get(position).getHarga());
                i.putExtra("tujuan", mItems.get(position).getTujuan());
                i.putExtra("qty", mItems.get(position).getQty());
                i.putExtra("stock", mItems.get(position).getStock());
                v.getContext().startActivity(i);
            }
        });

        holder.md = md;

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class HolderBarang extends RecyclerView.ViewHolder {
        TextView tvidb, tvnamab, tvlebar, tvpanjang, tvtinggi, tvberat, tvharga, tvtujuan, tvqty, tvstock;
        ModelBarang md;
        CardView cd;
        public  HolderBarang (View view)
        {
            super(view);
            tvidb = view.findViewById(R.id.idbValue);
            tvnamab = view.findViewById(R.id.namabValue);
            tvlebar = view.findViewById(R.id.lebarValue);
            tvpanjang = view.findViewById(R.id.panjangValue);
            tvtinggi = view.findViewById(R.id.tinggiValue);
            tvberat = view.findViewById(R.id.beratValue);
            tvharga = view.findViewById(R.id.hargaValue);
            tvtujuan = view.findViewById(R.id.tujuanValue);
            tvqty = view.findViewById(R.id.qtyValue);
            tvstock = view.findViewById(R.id.stockValue);
            cd = view.findViewById(R.id.cardview);
        }
    }

}
