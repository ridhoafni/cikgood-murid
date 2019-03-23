package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.models.Mengajar;
import com.example.anonymous.cikgood.models.Pendidikan;

import java.util.List;

public class MengajarAdapter extends RecyclerView.Adapter<MengajarAdapter.MengajarViewHolder>{

    private static final String KEY_ID_GURU = "key_id_guru";
    private Context context;
    private List<Mengajar> getDataMengajar;

    public MengajarAdapter(Context context, List<Mengajar> getDataMengajar){
        this.context        = context;
        this.getDataMengajar = getDataMengajar;
    }

    @NonNull
    @Override
    public MengajarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_megajar, parent, false);
        return new MengajarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MengajarViewHolder lokasiViewHolder, int i) {
        Mengajar mengajar = getDataMengajar.get(i);
//        Glide.with(context)
//                .load(ServerConfig.GURU_PATH+pengalamanKerja.getPhotoProfile())
//                .apply(new RequestOptions().override(100, 100))
//                .into(lokasiViewHolder.imgViewPhoto);

        String x ="- "+mengajar.getPengalaman();
        lokasiViewHolder.tvPengalaman.setText(x);
    }

    @Override
    public int getItemCount() {
        return getDataMengajar.size();
    }

    public class MengajarViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.tv_lokasi) TextView textViewLokasi;
        TextView tvPengalaman;
        public MengajarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPengalaman = (TextView) itemView.findViewById(R.id.tv_peng_pengalaman);
        }
    }
}

