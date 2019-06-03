package ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.response.ResponseCreatePemesanan;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.MyVolley;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananJadwalActivity extends AppCompatActivity implements OnMapReadyCallback {

    /* Declaration static variable */
    public static final String KEY_TGL               = "tgl";
    public static final String KEY_NAMA_GURU         = "nama";
    public static final String KEY_SALDO             = "saldo";
    public static final String KEY_HARGA             = "harga";
    public static final String KEY_EMAIL_GURU        = "email";
    public static final String KEY_DURASI            = "durasi";
    public static final String KEY_MATPEL            = "matpel";
    public static final String KEY_SELECTED_JADWAL   = "jadwal";
    public static final String KEY_ID_GURU           = "id_guru";
    public static final String KEY_LATITUDE          = "latitude";
    public static final String KEY_ID_MURID          = "id_murid";
    public static final String KEY_LONGITUDE         = "longitude";
    public static final String KEY_HARGA_TOTAL       = "harga_total";
    public static final String KEY_PHOTO_GURU        = "photo_profile";
    public static final String KEY_JML_PEMESANAN     = "jml_pemesanan";
    public static final String KEY_ADDRESS           = "alamat_lengkap";
    public static final String KEY_PESAN_TAMBAHAN    = "pesan_tambahan";


    // Declare variable
    private String sesi;
    private int length = 0;
    private String hari_tambah;
    private String hari_tanggal;
    private String selectedChild;
    private String[] selectedNameMatpel;
    private ProgressDialog progressDialog;
    private int tarif, guru_id, jml_pemesanan;
    private double durasi, harga_total, lat, lng;
    private double total_harga, total_pembayaran_saldo;
    private int id_guru, harga, jumlah_pesanan, murid_id, saldo;
    private String matpel, id_murid, tgl, jadwal, pesan_tambahan, alamat, nama_guru, photo, email;

    // Declare widgate
    LinearLayout maps;
    RadioButton rbTunai;
    RadioButton rbSaldo;
    ImageView ivBack, ivProfile;
    RadioGroup rgMetodePembayaran;
    Button buttonBayar, buttonAdd;
    DatePickerDialog datePickerDialog;
    ConstraintLayout constraintLayout;
    Spinner SpinnerTextIn, spinnerSesi;
    LinearLayout container, linearLayout;
    RelativeLayout relativeLayoutHariDanTgl, relativeLayoutSesi;
    TextView reList, info, tvTglBulan, tvHari, btnMaps, etAlamat, tvJam, tvSesi, tvSesiRow,
            tvNama, tvMatpel, tvTarif, tvDurasi, tvPertemuan, tvTotalHarga, tvTotalDurasi;

    /* Google maps */
    private GoogleMap mMap;

    /* Dialog */
    private Dialog myDialog;

    /* Snackbar */
    private Snackbar snackbar;

    /* LatLng */
    public LatLng pekanbaru = null;
    private SessionManager sessionManager;
    ApiInterface apiService;

    /* CameraPosistion */
    private CameraPosition cameraPosition;

    private MarkerOptions markerOptions = new MarkerOptions();
    private String API_KEY = "AIzaSyCbX09ztk-EA8E3_HvCfTY8uRF5y0Bc3q8";

    // Array
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayListJadwal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_jadwal);

        progressDialog = new ProgressDialog(this);

        // API service
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        sessionManager = new SessionManager(this);

        myDialog = new Dialog(this, R.style.DialogTrans);

        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // getIntent
        tgl             = getIntent().getStringExtra(KEY_TGL);
        matpel          = getIntent().getStringExtra(KEY_MATPEL);
        alamat          = getIntent().getStringExtra(KEY_ADDRESS);
        nama_guru       = getIntent().getStringExtra(KEY_NAMA_GURU);
        email           = getIntent().getStringExtra(KEY_EMAIL_GURU);
        photo           = getIntent().getStringExtra(KEY_PHOTO_GURU);
        pesan_tambahan  = getIntent().getStringExtra(KEY_PESAN_TAMBAHAN);
        jadwal          = getIntent().getStringExtra(KEY_SELECTED_JADWAL);

        saldo           = getIntent().getIntExtra(KEY_SALDO, 0);
        tarif           = getIntent().getIntExtra(KEY_HARGA, 0);
        guru_id         = getIntent().getIntExtra(KEY_ID_GURU, 0);
        murid_id        = getIntent().getIntExtra(KEY_ID_MURID, 0);

        durasi          = getIntent().getDoubleExtra(KEY_DURASI, 0);
        lat             = getIntent().getDoubleExtra(KEY_LATITUDE, 0);
        lng             = getIntent().getDoubleExtra(KEY_LONGITUDE, 0);
        jml_pemesanan   = getIntent().getIntExtra(KEY_JML_PEMESANAN, 0);
        total_harga     = getIntent().getDoubleExtra(KEY_HARGA_TOTAL, 0);

        // Inisialized widget
        info            = (TextView) findViewById(R.id.info);
        info            = (TextView) findViewById(R.id.info);
        tvJam           = (TextView) findViewById(R.id.tv_jam);
        tvHari          = (TextView) findViewById(R.id.tv_hari);
        tvSesi          = (TextView) findViewById(R.id.tv_sesi);
        tvNama          = (TextView) findViewById(R.id.tv_nama);
        btnMaps         = (TextView) findViewById(R.id.tv_maps);
        tvTarif         = (TextView) findViewById(R.id.tv_tarif);
        tvDurasi        = (TextView) findViewById(R.id.tv_durasi);
        tvMatpel        = (TextView) findViewById(R.id.tv_matpel);
        etAlamat        = (TextView) findViewById(R.id.et_alamat);
        tvTglBulan      = (TextView) findViewById(R.id.tv_tgl_bulan);
        tvPertemuan     = (TextView) findViewById(R.id.tv_pertemuan);
        tvTotalDurasi   = (TextView) findViewById(R.id.tv_total_durasi);
        tvTotalHarga    = (TextView) findViewById(R.id.tv_total_harga_isi);

        buttonAdd       = (Button) findViewById(R.id.btn_add);
        buttonBayar     = (Button) findViewById(R.id.btn_buat_pemesanan);

        ivBack          = (ImageView) findViewById(R.id.btn_back);
        ivProfile       = (ImageView) findViewById(R.id.iv_profile);

        rbSaldo         = (RadioButton) findViewById(R.id.rb_saldo);
        rbTunai         = (RadioButton) findViewById(R.id.rb_tunai);
        rgMetodePembayaran  = (RadioGroup) findViewById(R.id.rg_metode_pembayaran);

        maps            = (LinearLayout) findViewById(R.id.ll_maps);
        container       = (LinearLayout) findViewById(R.id.container);

        relativeLayoutSesi          = (RelativeLayout) findViewById(R.id.rl_sesi);
        relativeLayoutHariDanTgl    = (RelativeLayout) findViewById(R.id.rl_tanggal_mulai);

        constraintLayout            = (ConstraintLayout) findViewById(R.id.constraint_layout);

        // set date now
        Date date = Calendar.getInstance().getTime();
        Calendar newDate = Calendar.getInstance();
        Locale local = new Locale("in", "ID");

        String tanggal01          = "dd-MM-yyyy";
        String hari01             = "EEEE";

        SimpleDateFormat day_now    = new SimpleDateFormat(hari01, local);
        SimpleDateFormat date_now    = new SimpleDateFormat(tanggal01, local);

        // make object Locale to indo
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        /* add */
        String jmlPertemuan = jml_pemesanan+" x Pertemuan";
        String jmlDurasi = durasi+" Jam";

        /* Set text widget */
        tvHari.setText(day_now.format(newDate.getTime()));
        tvTglBulan.setText(date_now.format(newDate.getTime()));
        tvMatpel.setText(matpel);
        tvTarif.setText(formatRupiah.format(tarif));
        tvNama.setText(nama_guru+"");
        tvDurasi.setText(jmlDurasi+"");
        tvPertemuan.setText(jmlPertemuan+"");
        tvTotalHarga.setText(formatRupiah.format(total_harga));

        int total_durasi = (int) (durasi * jml_pemesanan);

        String durasi_total = total_durasi+" Jam";

        tvTotalDurasi.setText(durasi_total);

        Glide.with(this)
                .load(ServerConfig.GURU_PATH+photo)
                .apply(new RequestOptions().override(100, 100))
                .into(ivProfile);

        // Event maps
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Binding data to OjekActivity
                Intent intent_maps;
                intent_maps = new Intent(PemesananJadwalActivity.this, OjekActivity.class);
                intent_maps.putExtra(OjekActivity.KEY_TGL, tgl);
                intent_maps.putExtra(OjekActivity.KEY_HARGA, tarif);
                intent_maps.putExtra(OjekActivity.KEY_EMAIL, email);
                intent_maps.putExtra(OjekActivity.KEY_MATPEL, matpel);
                intent_maps.putExtra(OjekActivity.KEY_SALDO, saldo);
                intent_maps.putExtra(OjekActivity.KEY_DURASI, durasi);
                intent_maps.putExtra(OjekActivity.KEY_ID_GURU, guru_id);
                intent_maps.putExtra(OjekActivity.KEY_PHOTO_GURU, photo);
                intent_maps.putExtra(OjekActivity.KEY_ID_MURID, murid_id);
                intent_maps.putExtra(OjekActivity.KEY_NAMA_GURU, nama_guru);
                intent_maps.putExtra(OjekActivity.KEY_SELECTED_JADWAL, jadwal);
                intent_maps.putExtra(OjekActivity.KEY_HARGA_TOTAL, total_harga);
                intent_maps.putExtra(OjekActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                intent_maps.putExtra(OjekActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                startActivity(intent_maps);
            }
        });

        // Event back
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Event dialog date
        relativeLayoutHariDanTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Method show dialog date
                showDialogDateTglMasuk();
            }
        });

        // Event dialog date
        relativeLayoutSesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Show dialog Sesi */
                showDialogSesi();
            }
        });

        /* Set text widget */
        etAlamat.setText(alamat);

        if (lat != 0.0){
            maps.setVisibility(View.VISIBLE);
        }else{
            maps.setVisibility(View.GONE);
        }

        // Event button add
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set visibility LinearLayout
                container.setVisibility(View.VISIBLE);

                Log.d("arrayList1", ""+arrayListJadwal.size());
                Log.d("arrayList2", ""+jml_pemesanan);

                if (arrayListJadwal.size() < jml_pemesanan - 1){
                    buttonAdd.setVisibility(View.VISIBLE);
                }else{
                    buttonAdd.setVisibility(View.GONE);
                }

                // Initialized
                LayoutInflater layoutInflater   = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView              = layoutInflater.inflate(R.layout.row, null);
                TextView tvHariRow              = (TextView) addView.findViewById(R.id.tv_hari_row);
                tvSesiRow                       = (TextView) addView.findViewById(R.id.textout);
                TextView etHariTgl              = (TextView) addView.findViewById(R.id.textout2);

                /* Snackbar */
                snackbar = Snackbar.make(constraintLayout, "Berhasil ditambah...", Snackbar.LENGTH_LONG);
                snackbar.show();

                // set text spinner hari dan waktu
                String add_row_sesi = tvSesi.getText().toString()+" "+tvJam.getText().toString();
                tvSesiRow.setText(add_row_sesi);
                etHariTgl.setText(tvTglBulan.getText().toString());
                tvHariRow.setText(tvHari.getText().toString());

//                 add all data jadwal to array list
                arrayListJadwal.add(tvHari.getText().toString()+" "+etHariTgl.getText().toString()+"@ "+tvSesiRow.getText().toString());

                jadwal = String.valueOf(arrayListJadwal);

                Log.d("ayam array", ""+jadwal);

                // Initialized widget button
                Button buttonRemove = (Button) addView.findViewById(R.id.remove);

                // Event to remove LinearLayout
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(PemesananJadwalActivity.this);
                        alertdialog.setTitle("Apakah Anda ingin Menghapus Jadwal ?");
                        alertdialog.setMessage("Klik Ya untuk Hapus")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

//                                        String text = tvHari.getText().toString()+" "+etHariTgl.getText().toString()+"@ "+tvSesiRow.getText().toString();

                                        ((LinearLayout)addView.getParent()).removeView(addView);
                                        int a = 0;
                                        Log.d("arrayList1", ""+arrayListJadwal.size());
                                        Log.d("arrayList2", ""+jml_pemesanan);

                                        Log.d("ayam array sesi", ""+sesi);

                                        for(int z = 0; z < arrayListJadwal.size(); z++){

                                            String text = tvHari.getText().toString()+" "+etHariTgl.getText().toString()+"@ "+tvSesiRow.getText().toString();

                                            Log.d("ayam array text", ""+text);

                                            Log.d("ayam array remove", ""+arrayListJadwal.get(z).toString());

                                            if (arrayListJadwal.get(z).toString().equals(text)){
                                                arrayListJadwal.remove(z);
                                            }
                                        }

                                        Log.d("arrayListJadwal", ""+arrayListJadwal);

                                        if (arrayListJadwal.size() <= jml_pemesanan - 1){
                                            buttonAdd.setVisibility(View.VISIBLE);
                                        }else{
                                            buttonAdd.setVisibility(View.GONE);
                                        }

                                        /* Snackbar */
                                        Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Berhasil dihapus...", Snackbar.LENGTH_LONG);
                                        snackbar_delete.show();

                                        // changing color text
                                        snackbar_delete.setActionTextColor(Color.RED);


                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertdialog.create();
                        alertDialog.show();
                    }
                });
                container.addView(addView);
            }
        });

        // Event button next
        buttonBayar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String inputTanggalMulai = etAlamat.getText().toString().trim();
                boolean isEmptyFields   = false;
                boolean isInvalidDouble = false;

                if (TextUtils.isEmpty(inputTanggalMulai)){
                    isEmptyFields = true;
                    etAlamat.setError("Tidak boleh kosong!");
                }

                if (!isEmptyFields && !isInvalidDouble) {

                    int id = rgMetodePembayaran.getCheckedRadioButtonId();

                    switch (id){
                        case R.id.rb_saldo :

                            total_pembayaran_saldo = saldo - total_harga;

                            saveDataPemesananSaldo(guru_id, murid_id,matpel, jml_pemesanan, durasi, alamat, lat, lng, jadwal, pesan_tambahan, tarif, total_harga, saldo);

                            Log.d("total pembayaran saldo", ""+total_pembayaran_saldo);

                            break;
                        case R.id.rb_tunai :
                            saveDataPemesananTunai(guru_id, murid_id,matpel, jml_pemesanan, durasi, alamat, lat, lng, jadwal, pesan_tambahan, tarif, total_harga, saldo);
                            break;
                    }

                }
            }
        });
    }

    private void saveDataPemesananTunai(int guru_id, int murid_id, String matpel, int jml_pemesanan, double durasi, String alamat, double lat, double lng, String jadwal, String pesan_tambahan, int tarif, double total_harga, int saldo) {

        /* Service to save data order */
        apiService.simpanPemesanan(guru_id, murid_id,matpel, jml_pemesanan, durasi, alamat, lat, lng, jadwal, pesan_tambahan, tarif, total_harga).enqueue(new Callback<ResponseCreatePemesanan>() {
            @Override
            public void onResponse(Call<ResponseCreatePemesanan> call, Response<ResponseCreatePemesanan> response) {
                Log.d("response order", ""+response);
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)){

                        sendSinglePush();

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent pembayaranSuccess = new Intent(PemesananJadwalActivity.this, SuccesBuyActivity.class);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_TGL, tgl);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_HARGA, tarif);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_MATPEL, matpel);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_SALDO, saldo);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_DURASI, durasi);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_ID_GURU, guru_id);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_PHOTO_GURU, photo);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_ID_MURID, murid_id);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_NAMA_GURU, nama_guru);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_SELECTED_JADWAL, jadwal);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_HARGA_TOTAL, total_harga);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                        startActivity(pembayaranSuccess);
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent pembayaranSuccess = new Intent(PemesananJadwalActivity.this, SuccesBuyActivity.class);
                        startActivity(pembayaranSuccess);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePemesanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error!" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }

    private void saveDataPemesananSaldo(int guru_id, int murid_id, String matpel, int jml_pemesanan, double durasi, String alamat, double lat,
                                   double lng, String jadwal, String pesan_tambahan, int tarif, double total_harga, double saldo) {

        /* Service to save data order */
        apiService.simpanPemesanan(guru_id, murid_id,matpel, jml_pemesanan, durasi, alamat, lat, lng, jadwal, pesan_tambahan, tarif, total_harga).enqueue(new Callback<ResponseCreatePemesanan>() {
            @Override
            public void onResponse(Call<ResponseCreatePemesanan> call, Response<ResponseCreatePemesanan> response) {
                Log.d("response order", ""+response);
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)){

                        // success
//                        progressDialog.setMessage("Sending Push");
//                        progressDialog.show();

                        sendSinglePush();

                        // push notification
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent pembayaranSuccess = new Intent(PemesananJadwalActivity.this, SuccesBuyActivity.class);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_TGL, tgl);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_HARGA, tarif);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_MATPEL, matpel);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_SALDO, saldo);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_DURASI, durasi);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_ID_GURU, guru_id);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_PHOTO_GURU, photo);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_ID_MURID, murid_id);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_NAMA_GURU, nama_guru);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_SELECTED_JADWAL, jadwal);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_HARGA_TOTAL, total_harga);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                        pembayaranSuccess.putExtra(SuccesBuyActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                        startActivity(pembayaranSuccess);
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePemesanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error!" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

        /* Service to save data order */
        Log.d("data saldo", ""+saldo);
        apiService.updateSaldo(murid_id, total_pembayaran_saldo).enqueue(new Callback<ResponseCreatePemesanan>() {
            @Override
            public void onResponse(Call<ResponseCreatePemesanan> call, Response<ResponseCreatePemesanan> response) {
                Log.d("response saldo", ""+response);
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)){
//                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
//                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePemesanan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error!" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });



    }

    private void sendSinglePush(){
        final String title_push = "CikGood";
        final String message_push = "Pemesanan baru dari "+sessionManager.getMuridProfile().get(SessionManager.NAMA);
        final String image = sessionManager.getMuridProfile().get(SessionManager.PHOTO);
        final String email_push = email;

        Log.d("push title nama", ""+title_push);
        Log.d("push message email", ""+email_push);

        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConfig.URL_SEND_SINGLE_PUSH_GURU,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        MediaPlayer mPlayer = MediaPlayer.create(PemesananJadwalActivity.this, R.raw.notification);
                        mPlayer.start();
                        Toast.makeText(PemesananJadwalActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title_push);
                params.put("message", message_push);
                params.put("email", email_push);
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void showDialogSesi() {

        Spinner spinnerSesiDialog;
        TextView tvClose;
        Button btnOke;

        ArrayAdapter<String> adapterSesi;

        final String[] JAM = new String[]{
                "Pagi 09:00-14:00", "Siang 14:00-18:00", "Malam 18:00-22:00"
        };

        adapterSesi = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, JAM);

        myDialog.setContentView(R.layout.popup_sesi);

        spinnerSesiDialog   = (Spinner) myDialog.findViewById(R.id.spinner_jams);
        btnOke              = (Button) myDialog.findViewById(R.id.btn_oke);
        tvClose             = (TextView) myDialog.findViewById(R.id.tv_close);

        // Event
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        // Event
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        // Spinner jam/waktu
        spinnerSesiDialog.setAdapter(adapterSesi);

        // Event spinner jam/waktu
        spinnerSesiDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            sesi = parent.getItemAtPosition(position).toString();

                            // set text sesi
                            spliteSesi(sesi);
                        }

                        private void spliteSesi(String sesi) {

                            String[] splite_sesi = sesi.split(" ");
                            for (int i = 0; i < 2; i++){
                                System.out.println("splite: "+splite_sesi[1]);
                            }

                            tvSesi.setText(splite_sesi[0]);
                            tvJam.setText(splite_sesi[1]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
    }

    private void showDialogDateTglMasuk() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(PemesananJadwalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Locale local = new Locale("in", "ID");

                newDate.set(Calendar.YEAR, year);
                newDate.set(Calendar.MONTH, monthOfYear);
                newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTanggal    = "EEEE-yyyy-MM-dd";
                String tanggal          = "dd-MM-yyyy";
                String hari             = "EEEE";

                SimpleDateFormat sdfHari    = new SimpleDateFormat(hari, local);
                SimpleDateFormat sdf        = new SimpleDateFormat(formatTanggal, local);
                SimpleDateFormat sdfTanggal = new SimpleDateFormat(tanggal, local);

                hari_tanggal = sdfTanggal.format(newDate.getTime());
                hari_tambah = sdfHari.format(newDate.getTime());

                tvTglBulan.setText(hari_tanggal);
                tvHari.setText(hari_tambah);

                Log.d("kelinci tgl", ""+hari_tanggal);

            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */

        datePickerDialog.show();;
    }

    private void listAllAddView() {
        reList.setText("");

        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View thisChild = container.getChildAt(i);
            reList.append(thisChild + "\n");
            String childTextViewValue = selectedChild;
            reList.append("= " + childTextViewValue + "\n");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(10, 180, 10, 10);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setCompassEnabled(true);

        getMarker();

    }

    private void getMarker() {

        // Tambah Marker
        pekanbaru = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(pekanbaru));
        cameraPosition = new CameraPosition.Builder().target(pekanbaru).zoom(20).build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);

        mMap.animateCamera(cu);
    }
}

