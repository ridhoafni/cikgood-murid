package ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.response.ResponseCreateMurid;
import com.example.anonymous.cikgood.response.ResponseCreatePemesanan;
import com.example.anonymous.cikgood.response.ResponseLogin;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananLokasiActivity extends AppCompatActivity {

    /* Declaration static variable */
    public static final String KEY_TGL                  = "tgl";
    public static final String KEY_HARGA                = "harga";
    public static final String KEY_DURASI               = "durasi";
    public static final String KEY_MATPEL               = "matpel";
    public static final String KEY_SELECTED_JADWAL      = "jadwal";
    public static final String KEY_ID_GURU              = "id_guru";
    public static final String KEY_LATITUDE             = "latitude";
    public static final String KEY_ID_MURID             = "id_murid";
    public static final String KEY_LONGITUDE            = "longitude";
    public static final String KEY_HARGA_TOTAL          = "harga_total";
    public static final String KEY_JML_PEMESANAN        = "jml_pemesanan";
    public static final String KEY_ADDRESS              = "alamat_lengkap";
    public static final String KEY_PESAN_TAMBAHAN       = "pesan_tambahan";

    /* Declaration widget */
    private View btnMaps;
    private Button btnBayar;
    private double lat, lng;
    private EditText etAlamat;
    private LinearLayout btnBack;
    private double durasi, total_harga;
    private int guru_id, murid_id, tarif, jml_pemesanan;
    private String alamat, jadwal, tgl, matpel, pesan_tambahan;

    private LatLng latLng = null;

    /* Declare & make object of ApiInterface */
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_lokasi);btnBack = findViewById(R.id.btn_back);

        // Initialization object api service
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        // findViewById
        btnMaps     = findViewById(R.id.btn_maps);
        etAlamat    = findViewById(R.id.et_alamat);
        btnBayar    = findViewById(R.id.btn_bayar);

        // getIntent
        tgl             = getIntent().getStringExtra(KEY_TGL);
        matpel          = getIntent().getStringExtra(KEY_MATPEL);
        alamat          = getIntent().getStringExtra(KEY_ADDRESS);
        pesan_tambahan  = getIntent().getStringExtra(KEY_PESAN_TAMBAHAN);
        jadwal          = getIntent().getStringExtra(KEY_SELECTED_JADWAL);
        tarif           = getIntent().getIntExtra(KEY_HARGA, 0);
        guru_id         = getIntent().getIntExtra(KEY_ID_GURU, 0);
        murid_id        = getIntent().getIntExtra(KEY_ID_MURID, 0);
        durasi          = getIntent().getDoubleExtra(KEY_DURASI, 0);
        lat             = getIntent().getDoubleExtra(KEY_LATITUDE, 0);
        lng             = getIntent().getDoubleExtra(KEY_LONGITUDE, 0);
        jml_pemesanan   = getIntent().getIntExtra(KEY_JML_PEMESANAN, 0);
        total_harga     = getIntent().getDoubleExtra(KEY_HARGA_TOTAL, 0);

        /* Method to redirect to save data order */
        saveDataPemesanan(guru_id, murid_id, matpel, jml_pemesanan, durasi, alamat, lat, lng, jadwal, pesan_tambahan, tarif, total_harga);

        /* Set data to widget */
        etAlamat.setText(alamat);

        // getText alamat
        alamat = etAlamat.getText().toString();

        /* Event intent to ObjekActivity */
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Binding data to OjekActivity
                Intent intent_maps;
                intent_maps = new Intent(PemesananLokasiActivity.this, OjekActivity.class);
                intent_maps.putExtra(OjekActivity.KEY_TGL, tgl);
                intent_maps.putExtra(OjekActivity.KEY_HARGA, tarif);
                intent_maps.putExtra(OjekActivity.KEY_MATPEL, matpel);
                intent_maps.putExtra(OjekActivity.KEY_DURASI, durasi);
                intent_maps.putExtra(OjekActivity.KEY_ID_GURU, guru_id);
                intent_maps.putExtra(OjekActivity.KEY_ID_MURID, murid_id);
                intent_maps.putExtra(OjekActivity.KEY_SELECTED_JADWAL, jadwal);
                intent_maps.putExtra(OjekActivity.KEY_HARGA_TOTAL, total_harga);
                intent_maps.putExtra(OjekActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                intent_maps.putExtra(OjekActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                startActivity(intent_maps);
            }
        });

        /* Event back */
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /* Method to back before activity */
                    onBackPressed();
            }
        });
    }

    /* method to binding data and save order */
    private void saveDataPemesanan(int guru_id, int murid_id, String matpel, int jml_pemesanan, double durasi, String alamat, double lat, double lng, String jadwal, String pesan_tambahan, int tarif, double total_harga) {

        /* Splite data jadwal */
        Log.d("data array schedule", ""+jadwal);

        /* Replace character " [ " */
        String replace_character_jadwal = jadwal;
        String new_replace = replace_character_jadwal.replace('[', ' ');

        /* Replace character " ] " */
        String replace_character_jadwal2 = new_replace;
        String replace_jadwal = replace_character_jadwal2.replace(']', ' ');

        /* Splite jadwal */
        String[] splite_jadwal = replace_jadwal.split(",");
        for (String value_jadwal : splite_jadwal){
            Log.d("result splite jadwal", ""+value_jadwal);
        }

        // Event bayar
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Declaration inner variable */
                String inputAlamat = etAlamat.getText().toString().trim();
                boolean isEmptyFields   = false;
                boolean isInvalidDouble = false;

                /* cek if data not null or null */
                if (TextUtils.isEmpty(inputAlamat)){
                    isEmptyFields = true;
                    etAlamat.setError("Tidak boleh kosong!");
                }

                if (!isEmptyFields && !isInvalidDouble) {
                    Log.d("data durasi", ""+durasi);

                /* Service to save data order */
                apiService.simpanPemesanan(guru_id, murid_id,matpel, jml_pemesanan, durasi, alamat, lat, lng, jadwal, pesan_tambahan, tarif, total_harga).enqueue(new Callback<ResponseCreatePemesanan>() {
                    @Override
                    public void onResponse(Call<ResponseCreatePemesanan> call, Response<ResponseCreatePemesanan> response) {
                        Log.d("response order", ""+response);
                        if (response.isSuccessful()){
                            if (response.body().getCode().equals(200)){
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
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

                }
            }
        });

    }
}
