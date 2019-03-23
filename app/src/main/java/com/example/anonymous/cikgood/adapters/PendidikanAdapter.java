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
import com.example.anonymous.cikgood.models.Pendidikan;

import java.util.List;

public class PendidikanAdapter extends RecyclerView.Adapter<PendidikanAdapter.PendidikanViewHolder>{

    private static final String KEY_ID_GURU = "key_id_guru";
    private Context context;
    private List<Pendidikan> getDataPendidikan;

    public PendidikanAdapter(Context context, List<Pendidikan> getDataPendidikan){
        this.context        = context;
        this.getDataPendidikan = getDataPendidikan;
    }

    @NonNull
    @Override
    public PendidikanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_pendidikan, parent, false);
        return new PendidikanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PendidikanViewHolder lokasiViewHolder, int i) {
        Pendidikan pendidikan = getDataPendidikan.get(i);
//        Glide.with(context)
//                .load(ServerConfig.GURU_PATH+pengalamanKerja.getPhotoProfile())
//                .apply(new RequestOptions().override(100, 100))
//                .into(lokasiViewHolder.imgViewPhoto);

        String x ="- "+pendidikan.getNamaInstitusi();
        lokasiViewHolder.tvUniv.setText(x);
        lokasiViewHolder.tvJurusan.setText(pendidikan.getJurusan());
        lokasiViewHolder.tvTitle.setText(pendidikan.getGelar());
    }

    @Override
    public int getItemCount() {
        return getDataPendidikan.size();
    }

    public class PendidikanViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.tv_lokasi) TextView textViewLokasi;
        TextView tvTitle ,tvUniv, tvJurusan;
        public PendidikanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvUniv = (TextView) itemView.findViewById(R.id.tv_univ);
            tvJurusan = (TextView) itemView.findViewById(R.id.tv_jurusan);
        }
    }
}
