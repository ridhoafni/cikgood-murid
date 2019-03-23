package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.models.PengalamanKerja;

import java.util.List;

public class PengalamanKerjaAdapter extends RecyclerView.Adapter<PengalamanKerjaAdapter.LokasiViewHolder>{

    private static final String KEY_ID_GURU = "key_id_guru";
    private Context context;
    private List<PengalamanKerja> getDataPengalamanKerja;

    public PengalamanKerjaAdapter(Context context, List<PengalamanKerja> getDataPengalamanKerja){
        this.context        = context;
        this.getDataPengalamanKerja = getDataPengalamanKerja;
    }

    @NonNull
    @Override
    public LokasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_peng_kerja, parent, false);
        return new LokasiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LokasiViewHolder lokasiViewHolder, int i) {
        PengalamanKerja pengalamanKerja = getDataPengalamanKerja.get(i);
//        Glide.with(context)
//                .load(ServerConfig.GURU_PATH+pengalamanKerja.getPhotoProfile())
//                .apply(new RequestOptions().override(100, 100))
//                .into(lokasiViewHolder.imgViewPhoto);

        String x ="- "+pengalamanKerja.getJabatan();
        lokasiViewHolder.textViewPengKerjaJabatan.setText(x);
        lokasiViewHolder.textViewPengKerjaInstansi.setText(pengalamanKerja.getPerusahaan());
        lokasiViewHolder.textViewPengKerjaTahun.setText(pengalamanKerja.getTglMasuk());
    }

    @Override
    public int getItemCount() {
        return getDataPengalamanKerja.size();
    }

    public class LokasiViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_lokasi) TextView textViewLokasi;
        TextView textViewPengKerjaJabatan, textViewPengKerjaInstansi, textViewPengKerjaTahun;
        CardView cardView;
        public LokasiViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPengKerjaJabatan = (TextView) itemView.findViewById(R.id.tv_peng_kerja_jabatan);
            textViewPengKerjaInstansi = (TextView) itemView.findViewById(R.id.tv_peng_kerja_instansi);
            textViewPengKerjaTahun = (TextView) itemView.findViewById(R.id.tv_peng_kerja_tahun);
//            ButterKnife.bind(this, itemView);
        }
    }
}

