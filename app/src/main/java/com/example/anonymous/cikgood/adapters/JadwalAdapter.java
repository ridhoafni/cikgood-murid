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
import com.example.anonymous.cikgood.models.Jadwal;
import com.example.anonymous.cikgood.models.Lokasi;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder>{

    private static final String KEY_ID_GURU = "key_id_guru";
    private Context context;
    private List<Jadwal> getDataJadwal;

    public JadwalAdapter(Context context, List<Jadwal> getDataJadwal){
        this.context        = context;
        this.getDataJadwal = getDataJadwal;
    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_jadwal, parent, false);
        return new JadwalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalViewHolder jadwalViewHolder, int i) {
        Jadwal jadwal = getDataJadwal.get(i);
//        Glide.with(context)
//                .load(ServerConfig.GURU_PATH+pengalamanKerja.getPhotoProfile())
//                .apply(new RequestOptions().override(100, 100))
//                .into(lokasiViewHolder.imgViewPhoto);

        String x ="- "+jadwal.getHari();
        jadwalViewHolder.tvHari.setText(x);
        jadwalViewHolder.tvJam.setText(jadwal.getJam());
    }

    @Override
    public int getItemCount() {
        return getDataJadwal.size();
    }

    public class JadwalViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.tv_lokasi) TextView textViewLokasi;
        TextView tvHari, tvJam;
        CardView cardView;
        public JadwalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHari = (TextView) itemView.findViewById(R.id.tv_hari);
            tvJam = (TextView) itemView.findViewById(R.id.tv_jam);
        }
    }
}
