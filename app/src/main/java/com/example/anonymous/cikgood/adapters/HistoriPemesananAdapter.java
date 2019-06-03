package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.CariGuru;
import com.example.anonymous.cikgood.models.Pemesanan;
import com.example.anonymous.cikgood.utils.CustomOnItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ui.activities.GuruDetailActivity;
import ui.activities.HistoryPemesananDetailActivity;
import ui.activities.SuccesBuyActivity;
import ui.activities.TicketInformation;

public class HistoriPemesananAdapter extends RecyclerView.Adapter<HistoriPemesananAdapter.PemesananViewHolder>{

    private Context context;
    private List<Pemesanan> getAllDataPemesanan;

    public HistoriPemesananAdapter(Context context, List<Pemesanan> getAllDataPemesanan){
        this.context        = context;
        this.getAllDataPemesanan = getAllDataPemesanan;
    }

    @NonNull
    @Override
    public PemesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_1, parent, false);
        return new PemesananViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananViewHolder pemesananViewHolder, int i) {

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        Pemesanan pemesanan = getAllDataPemesanan.get(i);
        pemesananViewHolder.tvNamaPemesanan.setText(pemesanan.getNama());
        pemesananViewHolder.tvMatpelPemesanan.setText(pemesanan.getMatpel());
        pemesananViewHolder.btnStatus.setText(pemesanan.getStatusPemesanan());
        pemesananViewHolder.tvTglPemesanan.setText(pemesanan.getTglPemesanan());
        int total_harga = Integer.parseInt(pemesanan.getHargaTotal());
        pemesananViewHolder.tvTotalPemesanan.setText(formatRupiah.format(total_harga));
        pemesananViewHolder.clHistoryPemesanan.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                String alamat, nama, photo;
                double durasi, total_harga, harga;
                String tgl, jadwal, matpel, pesan_tambahan;
                int tarif, jml_pemesanan, guru_id, murid_id, saldo;

                tgl    = pemesanan.getTglPemesanan();
                pesan_tambahan = pemesanan.getPesanTambahan();
                nama    = pemesanan.getNama();
                jadwal  = pemesanan.getJadwal();
                matpel  = pemesanan.getMatpel();
                photo   = pemesanan.getPhotoProfile();

                Log.d("log photo", ""+photo);

                tarif           = Integer.parseInt(pemesanan.getHarga());
                durasi          = Integer.parseInt(pemesanan.getDurasi());
                guru_id         = Integer.parseInt(pemesanan.getGuruId());
                murid_id        = Integer.parseInt(pemesanan.getMuridId());
                total_harga     = Integer.parseInt(pemesanan.getHargaTotal());
                jml_pemesanan   = Integer.parseInt(pemesanan.getJumlahPertemuan());

                Log.d("log guru id adapter" ,""+guru_id);


                Intent goDetailAct = new Intent (context, HistoryPemesananDetailActivity.class);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_TGL, tgl);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_HARGA, tarif);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_MATPEL, matpel);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_DURASI, durasi);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_NAMA_GURU, nama);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_ID_GURU, guru_id);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_ID_MURID, murid_id);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_PHOTO_GURU, photo);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_SELECTED_JADWAL, jadwal);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_HARGA_TOTAL, total_harga);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                goDetailAct.putExtra(HistoryPemesananDetailActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                context.startActivity(goDetailAct);
            }
        }
        ));
    }

    @Override
    public int getItemCount() {
        return getAllDataPemesanan.size();
    }

    public class PemesananViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_status) Button btnStatus;
        @BindView(R.id.tv_tgl_pemesanan) TextView tvTglPemesanan;
        @BindView(R.id.tv_pem_nama) TextView tvNamaPemesanan;
        @BindView(R.id.tv_pem_matpel) TextView tvMatpelPemesanan;
        @BindView(R.id.tv_pem_total) TextView tvTotalPemesanan;
        @BindView(R.id.layoutHistoryPemesanan) ConstraintLayout clHistoryPemesanan;

        public PemesananViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
