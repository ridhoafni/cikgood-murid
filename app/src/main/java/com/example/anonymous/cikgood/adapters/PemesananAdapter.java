package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.models.Pemesanan;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.PemesananViewHolder>{

    private Context context;
    private List<Pemesanan> getAllDataPemesanan;

    public PemesananAdapter(Context context, List<Pemesanan> getAllDataPemesanan){
        this.context        = context;
        this.getAllDataPemesanan = getAllDataPemesanan;
    }

    @NonNull
    @Override
    public PemesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new PemesananViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananViewHolder pemesananViewHolder, int i) {

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        Pemesanan pemesanan = getAllDataPemesanan.get(i);
        pemesananViewHolder.tvNamaPemesanan.setText(pemesanan.getNama());
        pemesananViewHolder.tvMatpelPemesanan.setText(pemesanan.getMatpel());
        pemesananViewHolder.tvTglPemesanan.setText(pemesanan.getTglPemesanan());
        int total_harga = Integer.parseInt(pemesanan.getHargaTotal());
        pemesananViewHolder.tvTotalPemesanan.setText(formatRupiah.format(total_harga));

//        Glide.with(context)
//                .load(ServerConfig.GURU_PATH+pemesanan.get())
//                .apply(new RequestOptions().override(100, 100))
//                .into(guruViewHolder.imgViewPhoto);
//        guruViewHolder.textViewNama.setText(guru.getNama());
    }

    @Override
    public int getItemCount() {
        return getAllDataPemesanan.size();
    }

    public class PemesananViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tgl_pemesanan) TextView tvTglPemesanan;
        @BindView(R.id.tv_pem_nama) TextView tvNamaPemesanan;
        @BindView(R.id.tv_pem_matpel) TextView tvMatpelPemesanan;
        @BindView(R.id.tv_pem_total) TextView tvTotalPemesanan;

        public PemesananViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
