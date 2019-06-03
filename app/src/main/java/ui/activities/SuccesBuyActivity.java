package ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;

public class SuccesBuyActivity extends AppCompatActivity {

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


    Typeface tf1,tf2,tf3;
    Button btn1,btn2;
    TextView tv1,tv2;
    ImageView img1;
    Animation an1,an2;

    private double durasi, total_harga, harga;
    private String alamat, nama, photo;
    private String tgl, jadwal, matpel, pesan_tambahan;
    private int tarif, jml_pemesanan, guru_id, murid_id, saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy);
        an1 = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        an2 = AnimationUtils.loadAnimation(this, R.anim.app_splash2);

        // id

        tv1 = (TextView) findViewById(R.id.ticket_success_tv1);
        tv2 = (TextView) findViewById(R.id.ticket_success_tv2);
        btn1 = (Button) findViewById(R.id.ticket_success_btn1);
        btn2 = (Button) findViewById(R.id.ticket_success_btn2);
        img1 = (ImageView) findViewById(R.id.ticket_success_img1);

        img1.startAnimation(an1);
        tv1.startAnimation(an1);
        tv2.startAnimation(an1);
        btn1.startAnimation(an2);
        btn2.startAnimation(an2);

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

        Log.d("maps tgl", ""+tgl);
        Log.d("maps saldo", ""+saldo);
        Log.d("maps tarif", ""+tarif);
        Log.d("maps matpel", ""+matpel);
        Log.d("maps durasi", ""+durasi);
        Log.d("maps jadwal", ""+jadwal);
        Log.d("maps guru id", ""+guru_id);
        Log.d("maps tarif harga", ""+tarif);
        Log.d("maps murid id", ""+murid_id);
        Log.d("maps total harga", ""+total_harga);
        Log.d("maps jumlah tarif", ""+jml_pemesanan);
        Log.d("maps pesan tambahan", ""+pesan_tambahan);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent goDetailAct = new Intent (SuccesBuyActivity.this, TicketInformation.class);
                goDetailAct.putExtra(TicketInformation.KEY_TGL, tgl);
                goDetailAct.putExtra(TicketInformation.KEY_HARGA, tarif);
                goDetailAct.putExtra(TicketInformation.KEY_MATPEL, matpel);
                goDetailAct.putExtra(TicketInformation.KEY_SALDO, saldo);
                goDetailAct.putExtra(TicketInformation.KEY_DURASI, durasi);
                goDetailAct.putExtra(TicketInformation.KEY_ID_GURU, guru_id);
                goDetailAct.putExtra(TicketInformation.KEY_PHOTO_GURU, photo);
                goDetailAct.putExtra(TicketInformation.KEY_ID_MURID, murid_id);
                goDetailAct.putExtra(TicketInformation.KEY_NAMA_GURU, nama);
                goDetailAct.putExtra(TicketInformation.KEY_SELECTED_JADWAL, jadwal);
                goDetailAct.putExtra(TicketInformation.KEY_HARGA_TOTAL, total_harga);
                goDetailAct.putExtra(TicketInformation.KEY_JML_PEMESANAN, jml_pemesanan);
                goDetailAct.putExtra(TicketInformation.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                startActivity(goDetailAct);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(SuccesBuyActivity.this, NavigationView.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(launchIntent);
            }
        });

    }
}
