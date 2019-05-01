package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.JadwalAdapter;
import com.example.anonymous.cikgood.adapters.LokasiAdapter;
import com.example.anonymous.cikgood.adapters.MengajarAdapter;
import com.example.anonymous.cikgood.adapters.PendidikanAdapter;
import com.example.anonymous.cikgood.adapters.PengalamanKerjaAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.Guru;
import com.example.anonymous.cikgood.models.Jadwal;
import com.example.anonymous.cikgood.models.Lokasi;
import com.example.anonymous.cikgood.models.Mengajar;
import com.example.anonymous.cikgood.models.Pendidikan;
import com.example.anonymous.cikgood.models.PengalamanKerja;
import com.example.anonymous.cikgood.response.ResponGuruDetail;
import com.example.anonymous.cikgood.response.ResponseJadwal;
import com.example.anonymous.cikgood.response.ResponseLokasi;
import com.example.anonymous.cikgood.response.ResponseMengajar;
import com.example.anonymous.cikgood.response.ResponsePendidikan;
import com.example.anonymous.cikgood.response.ResponsePengalamanKerja;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruDetailActivityCopy extends AppCompatActivity {

//    Intent intentPesan = new Intent(getApplicationContext(), PemesananActivity.class);
//    private static final String KEY_ID_GURU     = "id_guru";

    public static final String KEY_ID_GURU = "id_guru";
    public static final String KEY_NAMA_GURU = "nama";
    public static final String KEY_PHOTO_GURU = "photo_profile";
    public static final String KEY_JURUSAN_GURU = "jurusan";
    public static final String KEY_UNIV_GURU = "nama_institusi";
    public static final String KEY_GELAR_GURU = "gelar";

    public static final String URL = ServerConfig.API_ENDPOINT;
    LinearLayout layout;
    TextView tv_nama, tv_pen, tv_bio;
    ImageView iv_foto, iv_foto2;
    Button btnPemesanan;
    Toolbar toolbarDetail;
    int id;
    String nama, univ, jurusan, gelar, photo;
    ApiInterface apiService;
    private Context context;
    RecyclerView recyclerView, recyclerViewLokasi, recyclerViewPendidikan,
    recyclerViewMengajar, recyclerViewJadwal;
    PengalamanKerjaAdapter pengalamanKerjaAdapter;
    JadwalAdapter jadwalAdapter;
    LokasiAdapter lokasiAdapter;
    PendidikanAdapter pendidikanAdapter;
    MengajarAdapter mengajarAdapter;

    private List<Lokasi> lokasis = new ArrayList<>();
    private List<PengalamanKerja> datas = new ArrayList<>();
    private List<Pendidikan> pendidikans = new ArrayList<>();
    private List<Mengajar> mengajars= new ArrayList<>();
    private List<Jadwal> jadwals = new ArrayList<>();

    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_detail);
        Intent i = getIntent();
        id = i.getIntExtra(KEY_ID_GURU, 0);

        System.out.println("ID ANDA: "+id);

        apiService  = ApiClient.getClient(URL).create(ApiInterface.class);
        iv_foto     = findViewById(R.id.img_item_photo_detail);
        iv_foto2     = findViewById(R.id.img_item_photo);
        tv_bio      = findViewById(R.id.tv_item_bio_detail);
        btnPemesanan = findViewById(R.id.btn_pemesanan);

        btnPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pemesananJasaGuru();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.rv_peng_kerja);
        recyclerViewLokasi = (RecyclerView)findViewById(R.id.rv_lokasi);
        recyclerViewPendidikan= (RecyclerView)findViewById(R.id.rv_pendidikan);
        recyclerViewMengajar= (RecyclerView)findViewById(R.id.rv_mengajar);
        recyclerViewJadwal= (RecyclerView)findViewById(R.id.rv_jadwal);

        final Display dWidth = getWindowManager().getDefaultDisplay();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                int heightPx = dWidth.getWidth() * 1 / 3;
                setAppBarOffset(heightPx);
            }
        });

//        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
//        ImageView toolbarImage = (ImageView) findViewById(R.id.toolbar_image);
//        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
//        collapsingToolbarLayout.setTitle("Dicoding");

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.colorPrimary));

        iv_foto.getLayoutParams().height = dWidth.getWidth();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.users);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                int mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorPrimary));
//                collapsingToolbarLayout.setContentScrimColor(mutedColor);
//            }
//        });

        jadwalAdapter = new JadwalAdapter(this, jadwals);
        RecyclerView.LayoutManager layoutManagerJadwal = new LinearLayoutManager(getApplicationContext());
        recyclerViewJadwal.setLayoutManager(layoutManagerJadwal);
        recyclerViewJadwal.setItemAnimator(new DefaultItemAnimator());
        recyclerViewJadwal.setAdapter(jadwalAdapter);

        loadDataJadwal();

        mengajarAdapter = new MengajarAdapter(this, mengajars);
        RecyclerView.LayoutManager layoutManagerMengajar = new LinearLayoutManager(getApplicationContext());
        recyclerViewMengajar.setLayoutManager(layoutManagerMengajar);
        recyclerViewMengajar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMengajar.setAdapter(mengajarAdapter);

        loadDataMengajar();

        lokasiAdapter = new LokasiAdapter(this, lokasis);
        RecyclerView.LayoutManager layoutManagerLokasi = new LinearLayoutManager(getApplicationContext());
        recyclerViewLokasi.setLayoutManager(layoutManagerLokasi);
        recyclerViewLokasi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLokasi.setAdapter(lokasiAdapter);

        loadDataPendidikan();

        pendidikanAdapter = new PendidikanAdapter(this, pendidikans);
        RecyclerView.LayoutManager layoutManagerPendidikan= new LinearLayoutManager(getApplicationContext());
        recyclerViewPendidikan.setLayoutManager(layoutManagerPendidikan);
        recyclerViewPendidikan.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPendidikan.setAdapter(pendidikanAdapter);


        loadDataLokasi();

        pengalamanKerjaAdapter = new PengalamanKerjaAdapter(this, datas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pengalamanKerjaAdapter);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);


        loadDataPengKerja();

        layout = (LinearLayout)findViewById(R.id.data_detil_guru);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitleTextColor(Color.BLUE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        apiService.guruFindById(id).enqueue(new Callback<ResponGuruDetail>() {
            @Override
            public void onResponse(Call<ResponGuruDetail> call, Response<ResponGuruDetail> response) {
                System.out.println(response.toString());

                if (response.isSuccessful()){
                    layout.setVisibility(View.VISIBLE);
                        System.out.println(response.body().toString());
                        System.out.println(id);
                        ArrayList<Guru> gurus = new ArrayList<>();
                        gurus.add(response.body().getMaster());
                        Guru guru = gurus.get(0);
                        nama = guru.getNama();
                        collapsingToolbarLayout.setTitle(nama);
//                      tv_pendidikan.setText(guru.getRiwayat_pendidikan());
                        tv_bio.setText(guru.getBiodata());

//                        id = guru.getIdGuru();
//                        nama = guru.getNama();
                        photo = guru.getPhotoProfile();

                    Glide.with(GuruDetailActivityCopy.this)
                            .load(ServerConfig.GURU_PATH+guru.getPhotoProfile())
                            .apply(new RequestOptions().override(200, 300))
                            .into(iv_foto);


//                    Glide.with(GuruDetailActivity.this)
//                            .load(ServerConfig.GURU_PATH+guru.getPhotoProfile())
//                            .apply(new RequestOptions().override(100, 100).error(R.drawable.user))
//                            .into(iv_foto2);
                }
            }

            @Override
            public void onFailure(Call<ResponGuruDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });

//        OjekActivity ojekActivity = new OjekActivity(1, "isma", "a", "f", "h", "y");


    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0});
    }

    private void loadDataJadwal() {
        apiService.getDataJadwal(id).enqueue(new Callback<ResponseJadwal>() {
            @Override
            public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        jadwals = response.body().getMaster();
                        jadwalAdapter = new JadwalAdapter(GuruDetailActivityCopy.this, jadwals);
                        recyclerViewJadwal.setAdapter(jadwalAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJadwal> call, Throwable t) {

            }
        });
    }

    private void loadDataMengajar() {
        apiService.getDataMengajar(id).enqueue(new Callback<ResponseMengajar>() {
            @Override
            public void onResponse(Call<ResponseMengajar> call, Response<ResponseMengajar> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        mengajars = response.body().getMaster();
                        mengajarAdapter = new MengajarAdapter(GuruDetailActivityCopy.this, mengajars);
                        recyclerViewMengajar.setAdapter(mengajarAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMengajar> call, Throwable t) {

            }
        });
    }

    private void loadDataPendidikan() {
        apiService.getDataPendidikan(id).enqueue(new Callback<ResponsePendidikan>() {
            @Override
            public void onResponse(Call<ResponsePendidikan> call, Response<ResponsePendidikan> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        pendidikans = response.body().getMaster();
                        pendidikanAdapter = new PendidikanAdapter(GuruDetailActivityCopy.this, pendidikans);
                        recyclerViewPendidikan.setAdapter(pendidikanAdapter);

                        Pendidikan pendidikan = pendidikans.get(0);

                        gelar = pendidikan.getGelar();
                        univ = pendidikan.getNamaInstitusi();
                        jurusan = pendidikan.getJurusan();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePendidikan> call, Throwable t) {

            }
        });
    }

    private void loadDataPengKerja() {
        apiService.pengalamanKerja(id).enqueue(new Callback<ResponsePengalamanKerja>() {
            @Override
            public void onResponse(Call<ResponsePengalamanKerja> call, Response<ResponsePengalamanKerja> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        datas = response.body().getMaster();
                        pengalamanKerjaAdapter = new PengalamanKerjaAdapter(GuruDetailActivityCopy.this, datas);
                        recyclerView.setAdapter(pengalamanKerjaAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePengalamanKerja> call, Throwable t) {

            }
        });
    }

    private void loadDataLokasi() {
        apiService.getDataLokasi(id).enqueue(new Callback<ResponseLokasi>() {
            @Override
            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        lokasis = response.body().getMaster();
                        lokasiAdapter = new LokasiAdapter(GuruDetailActivityCopy.this, lokasis);
                        recyclerViewLokasi.setAdapter(lokasiAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLokasi> call, Throwable t) {

            }
        });
    }

    private void pemesananJasaGuru() {
        Intent intentPesan = new Intent(getApplicationContext(), PemesananActivity.class);
        intentPesan.putExtra(PemesananActivity.KEY_ID_GURU, id);
        intentPesan.putExtra(PemesananActivity.KEY_PHOTO_GURU, photo);
        intentPesan.putExtra(PemesananActivity.KEY_NAMA_GURU, nama);
        intentPesan.putExtra(PemesananActivity.KEY_GELAR_GURU, gelar);
        intentPesan.putExtra(PemesananActivity.KEY_UNIV_GURU, univ);
        intentPesan.putExtra(PemesananActivity.KEY_JURUSAN_GURU, jurusan);
        startActivity(intentPesan);
    }
}
