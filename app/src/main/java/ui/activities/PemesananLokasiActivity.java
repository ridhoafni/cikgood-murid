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
import com.example.anonymous.cikgood.response.ResponseCreatePemesanan;
import com.example.anonymous.cikgood.response.ResponseLogin;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananLokasiActivity extends AppCompatActivity {

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

    private View btnMaps;
    private Button btnBayar;
    private double lat, lng;
    private EditText etAlamat;
    private LinearLayout btnBack;
    private ApiInterface apiService;
    private double durasi, total_harga;
    private int guru_id, tarif, jml_pemesanan;
    private String alamat, jadwal, tgl, murid_id, matpel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_lokasi);btnBack = findViewById(R.id.btn_back);

        // ApiInterface
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        // findViewById
        btnMaps     = findViewById(R.id.btn_maps);
        etAlamat    = findViewById(R.id.et_alamat);
        btnBayar    = findViewById(R.id.btn_bayar);

        // getIntent
        tgl             = getIntent().getStringExtra(KEY_TGL);
        matpel          = getIntent().getStringExtra(KEY_MATPEL);
        alamat          = getIntent().getStringExtra(KEY_ADDRESS);
        murid_id        = getIntent().getStringExtra(KEY_ID_MURID);
        jadwal          = getIntent().getStringExtra(KEY_SELECTED_JADWAL);
        tarif           = getIntent().getIntExtra(KEY_HARGA, 0);
        guru_id         = getIntent().getIntExtra(KEY_ID_GURU, 0);
        durasi          = getIntent().getDoubleExtra(KEY_DURASI, 0);
        lat             = getIntent().getDoubleExtra(KEY_LATITUDE, 0);
        lng             = getIntent().getDoubleExtra(KEY_LONGITUDE, 0);
        jml_pemesanan   = getIntent().getIntExtra(KEY_JML_PEMESANAN, 0);
        total_harga     = getIntent().getDoubleExtra(KEY_HARGA_TOTAL, 0);

        Log.d("ayam2 tgl", ""+tgl);
        Log.d("ayam2 lat", ""+lat);
        Log.d("ayam2 lng", ""+lng);
        Log.d("ayam2 durasi", ""+durasi);
        Log.d("ayam2 alamat", ""+alamat);
        Log.d("ayam2 jadwal", ""+jadwal);
        Log.d("ayam2 guru id", ""+guru_id);
        Log.d("ayam2 murid id", ""+murid_id);
        Log.d("ayam2 tarif harga", ""+tarif);
        Log.d("ayam2 tarif matpel", ""+matpel);
        Log.d("ayam2 total harga", ""+total_harga);
        Log.d("ayam2 jumlah tarif", ""+jml_pemesanan);

        // setText to widget
        etAlamat.setText(alamat);

        // getText alamat
        alamat = etAlamat.getText().toString();

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
                startActivity(intent_maps);
            }
        });

        // Event bayar
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputAlamat = etAlamat.getText().toString().trim();
                boolean isEmptyFields   = false;
                boolean isInvalidDouble = false;

                if (TextUtils.isEmpty(inputAlamat)){
                    isEmptyFields = true;
                    etAlamat.setError("Field tidak boleh kosong");
                }

                if (!isEmptyFields && !isInvalidDouble) {

                    // Service to save data order
                    apiService.simpanPemesanan(guru_id, murid_id, matpel, jml_pemesanan, durasi, alamat, lat, lng, "oooo",
                            jadwal, "pesan tambahan", tarif, total_harga).enqueue(new Callback<ResponseCreatePemesanan>() {
                        @Override
                        public void onResponse(Call<ResponseCreatePemesanan> call, Response<ResponseCreatePemesanan> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getCode().equals(200)) {
                                    finish();
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Gagal..."+response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseCreatePemesanan> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Gagal konek ke server", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        // Event back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PemesananLokasiActivity.this, PemesananJadwalActivity.class));
                    onBackPressed();
            }
        });
    }
}
