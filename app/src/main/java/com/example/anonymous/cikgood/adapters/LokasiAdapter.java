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
import com.example.anonymous.cikgood.models.Lokasi;
import com.example.anonymous.cikgood.models.PengalamanKerja;

import java.util.List;

public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.LokasiViewHolder>{

    private static final String KEY_ID_GURU = "key_id_guru";
    private Context context;
    private List<Lokasi> getDataLokasi;

    public LokasiAdapter(Context context, List<Lokasi> getDataLokasi){
        this.context        = context;
        this.getDataLokasi = getDataLokasi;
    }

    @NonNull
    @Override
    public LokasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_lokasi, parent, false);
        return new LokasiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LokasiViewHolder lokasiViewHolder, int i) {
        Lokasi lokasi = getDataLokasi.get(i);
//        Glide.with(context)
//                .load(ServerConfig.GURU_PATH+pengalamanKerja.getPhotoProfile())
//                .apply(new RequestOptions().override(100, 100))
//                .into(lokasiViewHolder.imgViewPhoto);

        String x ="- "+lokasi.getKota();
        lokasiViewHolder.tvKota.setText(x);
        lokasiViewHolder.tvKecamatan.setText(lokasi.getKecamatan());
    }

    @Override
    public int getItemCount() {
        return getDataLokasi.size();
    }

    public class LokasiViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.tv_lokasi) TextView textViewLokasi;
        TextView tvKota, tvKecamatan;
        CardView cardView;
        public LokasiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKota = (TextView) itemView.findViewById(R.id.tv_lok_kota);
            tvKecamatan = (TextView) itemView.findViewById(R.id.tv_lok_kecamatan);
        }
    }
}


