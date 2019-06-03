package ui.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class TicketInformation extends AppCompatActivity {

    public static final String KEY_TGL                  = "tgl";
    public static final String KEY_NAMA_GURU            = "nama";
    public static final String KEY_HARGA                = "harga";
    public static final String KEY_SALDO                = "saldo";
    public static final String KEY_MATPEL               = "matpel";
    public static final String KEY_DURASI               = "durasi";
    public static final String KEY_SELECTED_JADWAL      = "jadwal";
    public static final String KEY_ID_GURU              = "id_guru";
    public static final String KEY_ID_MURID             = "id_murid";
    public static final String KEY_HARGA_TOTAL          = "harga_total";
    public static final String KEY_JML_PEMESANAN        = "jml_pemesanan";
    public static final String KEY_PHOTO_GURU           = "photo_profile";
    public static final String KEY_ADDRESS              = "alamat_lengkap";
    public static final String KEY_PESAN_TAMBAHAN       = "pesan_tambahan";

    private String alamat, nama, photo;
    private double durasi, total_harga, harga;
    private String tgl, jadwal, matpel, pesan_tambahan;
    private int tarif, jml_pemesanan, guru_id, murid_id, saldo;

    Button btn1;
    LinearLayout l1;
    ImageView ivProfile;
    Typeface tf1,tf2,tf3;
    TextView tvNama,tvMatpel,tvTotalPertemuan,tvTarif,tvTgl,tvDurasi,tvTotalDurasi,tvTotalHarga,tvJadwal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_information);

        tvTgl               = (TextView) findViewById(R.id.tv_tgl_ti);
        tvNama              = (TextView) findViewById(R.id.tv_nama_ti);
        tvTarif             = (TextView) findViewById(R.id.tv_tarif_ti);
        tvMatpel            = (TextView) findViewById(R.id.tv_matpel_ti);
        tvJadwal            = (TextView) findViewById(R.id.tv_jadwal_ti);
        tvDurasi            = (TextView) findViewById(R.id.tv_durasi_isi);;
        tvTotalHarga        = (TextView) findViewById(R.id.tv_total_harga_isi);
        tvTotalPertemuan    = (TextView) findViewById(R.id.tv_total_pertemuan_isi_ti);

        ivProfile           = (ImageView) findViewById(R.id.iv_profile_ti);

        btn1                = (Button) findViewById(R.id.btn_print);
        l1                  = (LinearLayout) findViewById(R.id.ticket_information_l1);

        // getIntent
        tgl           = getIntent().getStringExtra(KEY_TGL);
        matpel        = getIntent().getStringExtra(KEY_MATPEL);
        alamat        = getIntent().getStringExtra(KEY_ADDRESS);
        nama          = getIntent().getStringExtra(KEY_NAMA_GURU);
        photo         = getIntent().getStringExtra(KEY_PHOTO_GURU);
        pesan_tambahan= getIntent().getStringExtra(KEY_PESAN_TAMBAHAN);
        jadwal        = getIntent().getStringExtra(KEY_SELECTED_JADWAL);

        murid_id      = getIntent().getIntExtra(KEY_ID_MURID, murid_id);
        saldo         = getIntent().getIntExtra(KEY_SALDO, 0);
        tarif         = getIntent().getIntExtra(KEY_HARGA, 0);
        harga         = getIntent().getIntExtra(KEY_HARGA, 0);
        guru_id       = getIntent().getIntExtra(KEY_ID_GURU, 0);
        durasi        = getIntent().getDoubleExtra(KEY_DURASI, 0);
        jml_pemesanan = getIntent().getIntExtra(KEY_JML_PEMESANAN, 0);
        total_harga   = getIntent().getDoubleExtra(KEY_HARGA_TOTAL, 0);

        // make object Locale to indo
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        // set text to widget
        tvNama.setText(nama);
        tvTarif.setText(formatRupiah.format(tarif));
        tvMatpel.setText(matpel);
        String durasi_total = durasi+" Jam";
        tvDurasi.setText(durasi_total);
        tvTotalHarga.setText(formatRupiah.format(total_harga));

        String total_pertemuan = jml_pemesanan+ " x Pertemuan";
        tvTotalPertemuan.setText(total_pertemuan+"");

        /* Replace character " [ " */
        String replace_character_jadwal = jadwal;
        String new_replace = replace_character_jadwal.replace('[', ' ');

        /* Replace character " ] " */
        String replace_character_jadwal2 = new_replace;
        String replace_jadwal = replace_character_jadwal2.replace(']', ' ');

        String replace_at = replace_jadwal.replace('@', ' ');


        /* Splite jadwal */
        String[] splite_jadwal = replace_at.split(",");
        for (int i = 0; i < splite_jadwal.length; i++){
            tvJadwal.setText(calculatePondok(splite_jadwal));
        }

        Glide.with(this)
                .load(ServerConfig.GURU_PATH+photo)
                .apply(new RequestOptions().override(100, 100))
                .into(ivProfile);

        Log.d("ti tgl", ""+tgl);
        Log.d("ti saldo", ""+saldo);
        Log.d("ti tarif", ""+tarif);
        Log.d("ti matpel", ""+matpel);
        Log.d("ti durasi", ""+durasi);
        Log.d("ti jadwal", ""+jadwal);
        Log.d("ti guru id", ""+guru_id);
        Log.d("ti tarif harga", ""+tarif);
        Log.d("ti murid id", ""+murid_id);
        Log.d("ti total harga", ""+total_harga);
        Log.d("ti jumlah tarif", ""+jml_pemesanan);
        Log.d("ti pesan tambahan", ""+pesan_tambahan);

        l1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Runnable() {
                    int interfal = 500;
                    @Override
                    public void run() {
                        new CountDownTimer(1500, 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                btn1.setText("PRINTING...");
                            }
                            @Override
                            public void onFinish() {
                                btn1.setText("PRINT TICKET");
                            }
                        }.start();
                    }
                }.run();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Bitmap bitmap = takeScreenshot();
                        saveBitmap(bitmap);
                        Toast.makeText(TicketInformation.this, "Successfull Print Ticket on Storage\nPlease check your Gallery or Storage", Toast.LENGTH_SHORT).show();
                    }
                },1500);
            }
        });
    }

    private String calculatePondok(String[] splite_jadwal) {
        StringBuilder output = new StringBuilder();
        int no = 1;
        for (String s : splite_jadwal) {
            output.append("\n ").append(no++).append(". ").append(s);
//            no++;
        }
        return output.toString();
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory()+"/tiketsaya_print.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }

    }
}