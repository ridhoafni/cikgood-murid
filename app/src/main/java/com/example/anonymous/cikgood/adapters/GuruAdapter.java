package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.RecyclerViewAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.CariGuru;
import com.example.anonymous.cikgood.models.Guru;
import com.example.anonymous.cikgood.response.ResponseGuru;
import com.example.anonymous.cikgood.utils.CustomOnItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ui.activities.GuruActivity;
import ui.activities.GuruDetailActivity;

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.GuruViewHolder>{

    private static final String KEY_ID_GURU = "id_guru";
    private Context context;
    private List<CariGuru> getAllDataGuru;

    public GuruAdapter(Context context, List<CariGuru> getAllDataGuru){
        this.context        = context;
        this.getAllDataGuru = getAllDataGuru;
    }

    @NonNull
    @Override
    public GuruViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guru_item, parent, false);
        return new GuruViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruViewHolder guruViewHolder, int i) {

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        CariGuru guru = getAllDataGuru.get(i);
        Glide.with(context)
                .load(ServerConfig.GURU_PATH+guru.getPhotoProfile())
                .apply(new RequestOptions().override(100, 100))
                .into(guruViewHolder.imgViewPhoto);
        guruViewHolder.textViewNama.setText(guru.getNama());
        guruViewHolder.textViewPendidikan.setText(guru.getJurusan());
        guruViewHolder.textViewBiodata.setText(guru.getBiodata());
        guruViewHolder.textViewStatus.setText(guru.getStatus());
        int harga = Integer.parseInt(guru.getTarif());
        guruViewHolder.textViewHarga.setText(formatRupiah.format(harga));
        guruViewHolder.cvGuru.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                int id          = Integer.parseInt(getAllDataGuru.get(position).getGuruId());
                String nama     = getAllDataGuru.get(position).getNama();
//                String pen      = getAllDataGuru.get(position).getNamaInstitusi();
                String bio      = getAllDataGuru.get(position).getBiodata();
                String photo    = getAllDataGuru.get(position).getPhotoProfile();
                Intent i = new Intent(context, GuruDetailActivity.class);
                System.out.println("IDNYA :"+id);
                i.putExtra(GuruDetailActivity.KEY_ID_GURU, id);
                i.putExtra(GuruDetailActivity.KEY_NAMA_GURU, nama);
                i.putExtra(GuruDetailActivity.KEY_PHOTO_GURU, photo);
                context.startActivity(i);
                Toast.makeText(context, "You are click " +id, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getAllDataGuru.size();
    }

    public class GuruViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail) ImageView imgViewPhoto2;
        @BindView(R.id.imageView2) ImageView imgViewPhoto;
        @BindView(R.id.tv_nama) TextView textViewNama;
        @BindView(R.id.tv_pendidikan) TextView textViewPendidikan;
        @BindView(R.id.tv_rating) TextView textViewRating;
        @BindView(R.id.tv_biodata) TextView textViewBiodata;
        @BindView(R.id.tv_harga) TextView textViewHarga;
        @BindView(R.id.tv_status) TextView textViewStatus;
        @BindView(R.id.linear_layout) LinearLayout cvGuru;

        public GuruViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
