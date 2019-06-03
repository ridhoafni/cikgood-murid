package ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.MengajarAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.GuruDataMatpel;
import com.example.anonymous.cikgood.models.Saldo;
import com.example.anonymous.cikgood.response.ResponFindSaldo;
import com.example.anonymous.cikgood.response.ResponseGuruDataMatpel;
import com.example.anonymous.cikgood.response.ResponseSaldo;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivity extends AppCompatActivity {

    // Inisialisai variable static
    public static final String KEY_NAMA_GURU    = "nama";
    public static final String KEY_ID_GURU      = "id_guru";
    public static final String KEY_GELAR_GURU   = "gelar";
    public static final String KEY_JURUSAN_GURU = "jurusan";
    public static final String KEY_PHOTO_GURU   = "photo_profile";
    public static final String KEY_ADDRESS      = "alamat_lengkap";
    public static final String KEY_UNIV_GURU    = "nama_institusi";
    public static final String KEY_EMAIL_GURU    = "email";

    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    ApiInterface apiInterface;

    private int Click=0;
    private Integer money;
    private int my_money = 250000;
    int saldo;
    private String pesan_tambahan;
    private Integer jumlah_pesanan = 0;
    private int id, tarif, harga, murid_id;
    private double selectedSpinnerDurasiJam;
    private String nama, gelar, univ, jurusan, photo, x, selectedSpinnerMatpel, selectedSpinnerDurasiPertemuan, alamat, matpel_sected, email;

    private ApiInterface apiService;
    private PemesananJadwalActivity pemesananJadwalActivity;

    private View view1;
    private Animation in;
    private Animation out;
    private ImageView img2;
    private Dialog myDialog;
    private EditText etPesanTambahan;
    private CircleImageView photo_profil;
    private SessionManager sessionManager;
    private Spinner spinnerDurasiPertemuanJam, spinnerMatpel;
    private TextView tvTotalHargaPesanan, tvJumlahSaldo, tv7;
    private TextView tvNamaGuru, tvGelarGuru, tvUnivGuru, tvJurusanGuru, tvTotalPertemuan;
    private Button btnIncrease, btnDecrease,btnSelanjutnya, btnAlertSaldo, btnTambah10, btnTambahSaldo;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(PemesananActivity.this, GuruDetailActivity.class);
//                i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
                onBackPressed();
            }
        });

        apiInterface = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        // findViewById
        tvNamaGuru                = (TextView) findViewById(R.id.tv_nama);
        tvUnivGuru                = (TextView) findViewById(R.id.tv_univ);
        tvGelarGuru               = (TextView) findViewById(R.id.tv_gelar);
        tvJurusanGuru             = (TextView) findViewById(R.id.tv_jurusan);
        spinnerMatpel             = (Spinner) findViewById(R.id.spinner_matpel);
        btnTambahSaldo            = (Button) findViewById(R.id.btn_tambah_saldo);
        photo_profil              = (CircleImageView) findViewById(R.id.thumbnail);
        etPesanTambahan           = (EditText)findViewById(R.id.et_pesan_tambahan);
        btnSelanjutnya            = (Button) findViewById(R.id.ticket_checkout_btn1);
        btnSelanjutnya            = (Button) findViewById(R.id.ticket_checkout_btn1);
        tvTotalPertemuan          = (TextView) findViewById(R.id.ticket_checkout_tv6);
        tvJumlahSaldo             = (TextView) findViewById(R.id.ticket_checkout_tv10);
        tvTotalHargaPesanan       = (TextView) findViewById(R.id.ticket_checkout_tv12);
        img2                      = (ImageView) findViewById(R.id.ticket_checkoutdrop);
        btnIncrease               = (Button) findViewById(R.id.ticket_checkout_btn_pls);
        btnDecrease               = (Button) findViewById(R.id.ticket_checkout_btn_min);
        btnTambah10               = (Button) findViewById(R.id.ticket_checkout_btn_pls10);
        btnAlertSaldo             = (Button) findViewById(R.id.ticket_checkout_btn_alert);
        spinnerDurasiPertemuanJam = (Spinner)findViewById(R.id.spinner_durasi_pertemuan_jam);

        //Dialog
        myDialog = new Dialog(this, R.style.DialogTrans);

        // SessionManager
        sessionManager = new SessionManager(PemesananActivity.this);
        murid_id = Integer.parseInt((sessionManager.getMuridProfile().get("id")));

        Log.d("log murid id", ""+murid_id);
        // getIntent
        email = getIntent().getStringExtra(KEY_EMAIL_GURU);
        univ = getIntent().getStringExtra(KEY_UNIV_GURU);
        nama = getIntent().getStringExtra(KEY_NAMA_GURU);
        alamat = getIntent().getStringExtra(KEY_ADDRESS);
        photo = getIntent().getStringExtra(KEY_PHOTO_GURU);
        gelar = getIntent().getStringExtra(KEY_GELAR_GURU);
        jurusan = getIntent().getStringExtra(KEY_JURUSAN_GURU);
        id = getIntent().getIntExtra(KEY_ID_GURU, 0);

        getDataSaldo(murid_id);

        // Event tambah saldo
        btnTambahSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PemesananActivity.this, ui.activities.Saldo.class));
            }
        });

        view1   = (View) findViewById(R.id.ticket_checkoutview);
        tv7     = (TextView) findViewById(R.id.ticket_checkout_tv7);

        tv7.setText("The leaning of the Tower of Pisa comes into the story in 1173, when consruction began.");

        // get text
        pesan_tambahan = etPesanTambahan.getText().toString();
        Log.d("pesan  tambahan", ""+pesan_tambahan);

        // set text widget
        tvNamaGuru.setText(nama);
        tvUnivGuru.setText(univ);
        tvGelarGuru.setText(gelar);
        tvJurusanGuru.setText(jurusan);
        Glide.with(this)
                .load(ServerConfig.GURU_PATH+photo)
                .apply(new RequestOptions().override(100, 100))
                .into(photo_profil);

        // ApiService
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.saldoFindById(id).enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                Log.d("response saldo",""+response);
                if (response.isSuccessful()){
                    System.out.println(response.body().toString());
                    ArrayList<Saldo> gurus = new ArrayList<>();
                    gurus.add(response.body().getMaster());
                    Saldo guru = gurus.get(0);
                }
            }
            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // method initialized spinner matpel
        initSpinnerMatpel();

        // method initialized spinner pertemuan
        initSpinnerDurasiPertemuanJam();

        if(saldo == 0) {
//            final Integer money = Integer.valueOf(tvJumlahSaldo.getText().toString());

            // make object Locale to indo
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            // set text
//            tvJumlahSaldo.setText(formatRupiah.format(money));
            btnSelanjutnya.animate().translationY(250).alpha(0).setDuration(350).start();
            btnSelanjutnya.setEnabled(false);
            tvJumlahSaldo.setTextColor(Color.parseColor("#D1206B"));
            tvTotalPertemuan.setTextColor(getResources().getColor(R.color.redPrimary));
            tvTotalPertemuan.setBackgroundResource(R.drawable.sign_in_edittext_bg_pressed2);
            btnAlertSaldo.animate().translationY(0).alpha(1).setDuration(300).start();
        }else {
            final Integer money = my_money;
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            tvJumlahSaldo.setText(formatRupiah.format(money));
        }

//        money = my_money;
        btnDecrease.animate().alpha(0).setDuration(300).start();
        btnDecrease.setEnabled(false);
        btnAlertSaldo.setAlpha(0);
        tvTotalPertemuan.setTextColor(getResources().getColor(R.color.blackPrimary));
        tvTotalPertemuan.setBackgroundResource(R.drawable.sign_in_edittext_bg_pressed);
        in = new AlphaAnimation(0.0f,1.0f);
        in.setDuration(300);
        out = new AlphaAnimation(1.0f,0.0f);
        out.setDuration(300);
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                tvTotalHargaPesanan.startAnimation(in);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

        });

        // Event decrease
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_pesanan -= 1;
                tvTotalPertemuan.setText(jumlah_pesanan.toString());
                tvTotalHargaPesanan.setText(String.valueOf(addResults()));
                if(jumlah_pesanan < 2) {
                    btnDecrease.animate().alpha(0).setDuration(300).start();
                    btnDecrease.setEnabled(false);
                }
                tvTotalHargaPesanan.startAnimation(out);
                Locale localeID = new Locale("in", "ID");
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            }
        });

        // Event button increase
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_pesanan += 1;

                tvTotalPertemuan.setText(jumlah_pesanan.toString());

                tvTotalHargaPesanan.setText(String.valueOf(addResults()));

                if(jumlah_pesanan > 1) {
                    btnDecrease.animate().alpha(1).setDuration(300).start();
                    btnDecrease.setEnabled(true);
                    tvTotalHargaPesanan.startAnimation(out);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                }
                SharedPreferences sp2 = getSharedPreferences("myfunds",0);
                sp2.edit().putBoolean("my_funds_money",true).apply();
                SharedPreferences.Editor edit2 = sp2.edit();
                edit2.commit();
            }
        });

        }

    private void getDataSaldo(int id) {
        apiInterface.getSaldo(id).enqueue(new Callback<ResponFindSaldo>() {
            @Override
            public void onResponse(Call<ResponFindSaldo> call, Response<ResponFindSaldo> response) {
                if (response.isSuccessful()){
                    saldo = response.body().getMaster().getTotalSaldo();

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    Log.d("result saldo", ""+saldo+"");
                    tvJumlahSaldo.setText(formatRupiah.format(saldo));
                }
            }

            @Override
            public void onFailure(Call<ResponFindSaldo> call, Throwable t) {

            }
        });
    }

    private void initSpinnerDurasiPertemuanJam() {
        String[] array_durasi_pertemuan = {"1", "1.5", "2", "2.5", "3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_durasi_pertemuan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDurasiPertemuanJam.setPrompt("Durasi Per Pertemuan");
        spinnerDurasiPertemuanJam.setAdapter( adapter);
        spinnerDurasiPertemuanJam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerDurasiJam = Double.parseDouble(parent.getItemAtPosition(position).toString());
                tvTotalHargaPesanan.setText(String.valueOf(addResults()));
                tvTotalHargaPesanan.startAnimation(out);
                String durasi_jam = "("+String.valueOf(selectedSpinnerDurasiJam)+" Jam)";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initSpinnerMatpel() {
        id = getIntent().getIntExtra(KEY_ID_GURU, 0);
        apiService.getGuruDataMatpel(id).enqueue(new Callback<ResponseGuruDataMatpel>() {
            @Override
            public void onResponse(Call<ResponseGuruDataMatpel> call, Response<ResponseGuruDataMatpel> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                        System.out.println("Response matpel: "+response);
                        List<GuruDataMatpel> semuaGuruDataMatpelItems = response.body().getMaster();
                        List<String> listSpinnerGuruDataMatpel = new ArrayList<String>();
                        for (int i = 0; i < semuaGuruDataMatpelItems.size(); i++){
                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            harga = Integer.parseInt(semuaGuruDataMatpelItems.get(i).getTarif());
                            String rupiah = formatRupiah.format(harga);
                            x = semuaGuruDataMatpelItems.get(i).getMatpelDetail()+ " - "+rupiah;
                            System.out.println("Spinner :"+semuaGuruDataMatpelItems.get(i).getMatpelDetail());
                            listSpinnerGuruDataMatpel.add(x);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PemesananActivity.this,
                                android.R.layout.simple_spinner_item, listSpinnerGuruDataMatpel);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMatpel.setAdapter(adapter);


                    spinnerMatpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSpinnerMatpel = parent.getItemAtPosition(position).toString();

                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                            for (GuruDataMatpel semuaDataMatpel : semuaGuruDataMatpelItems){

                                    String y = semuaDataMatpel.getMatpelDetail();
                                    int y2 = Integer.parseInt(semuaDataMatpel.getTarif());
                                    String y3 = formatRupiah.format(y2);
                                    String oke = y+" - "+y3;
                                System.out.println("Oke:" +oke);
                                if (oke.equals(spinnerMatpel.getSelectedItem().toString())){
//                                    loadDataMatpel(semuaTingkatan.getId());

                                    tarif = Integer.parseInt(semuaDataMatpel.getTarif());
                                    matpel_sected = semuaDataMatpel.getMatpelDetail();

                                    // baru

                                    tvTotalHargaPesanan.setText(String.valueOf(addResults()));
                                    tvTotalHargaPesanan.startAnimation(out);
                                    // baru


                                    hitungPemesanan(tarif, matpel_sected);
                                    System.out.println("tarifnya:"+tarif);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseGuruDataMatpel> call, Throwable t) {

            }
        });
    }

    private String addResults() {

            int number1_tarif;
            double number2_jam;
            int number3;

            if( Integer.toString(tarif) != "" && tarif > 0) {
                number1_tarif = tarif;
            } else {
                number1_tarif = 0;
            }

            if( tvTotalPertemuan.getText().toString() != "" && Integer.parseInt(tvTotalPertemuan.getText().toString()) > 0) {
                number3 = Integer.parseInt(tvTotalPertemuan.getText().toString());
            } else {
                number3 = 0;
            }

            if(Double.toString(selectedSpinnerDurasiJam) != "" && selectedSpinnerDurasiJam > 0) {
                number2_jam = selectedSpinnerDurasiJam;
                Log.d("hasil", ""+number2_jam);

            } else {
                number2_jam = 0;
             }

             double hasil = (number1_tarif * number2_jam * number3);

        if(hasil > saldo) {

            DialogAlertSaldo();

            btnSelanjutnya.animate().translationY(250).alpha(0).setDuration(350).start();
            btnSelanjutnya.setEnabled(false);
            btnIncrease.animate().translationY(250).alpha(0).setDuration(350).start();
            btnIncrease.setEnabled(false);
            tvJumlahSaldo.setTextColor(Color.parseColor("#D1206B"));
            btnAlertSaldo.animate().translationY(0).alpha(1).setDuration(300).start();
            tvTotalPertemuan.setTextColor(getResources().getColor(R.color.redPrimary));
            tvTotalPertemuan.setBackgroundResource(R.drawable.sign_in_edittext_bg_pressed2);
        }

        if(hasil < saldo) {
            btnSelanjutnya.animate().translationY(0).alpha(1).setDuration(350).start();
            btnSelanjutnya.setEnabled(true);
            btnIncrease.animate().translationY(0).alpha(1).setDuration(350).start();
            btnIncrease.setEnabled(true);
            tvJumlahSaldo.setTextColor(Color.parseColor("#203dd1"));
            btnAlertSaldo.animate().alpha(0).setDuration(300).start();
            tvTotalPertemuan.setTextColor(getResources().getColor(R.color.blackPrimary));
            tvTotalPertemuan.setBackgroundResource(R.drawable.sign_in_edittext_bg_pressed);
        }

            methodSelanjutnya(id, murid_id, number3, number1_tarif, number2_jam, matpel_sected, hasil, pesan_tambahan);

            return formatRupiah.format(hasil);

    }

    // method event next
    private void methodSelanjutnya(int id, int murid_id, int number3, int number1_tarif, double number2_jam, String matpel_sected, double hasil, String pesan_tambahan) {
        btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pembayaranSelanjutnya = new Intent(PemesananActivity.this, PemesananJadwalActivity.class);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_ID_GURU, id);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_EMAIL_GURU, email);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_NAMA_GURU, nama);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_PHOTO_GURU, photo);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_SALDO, saldo);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_ID_MURID, murid_id);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_HARGA_TOTAL, hasil);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_DURASI, number2_jam);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_HARGA, number1_tarif);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_MATPEL, matpel_sected);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_JML_PEMESANAN, number3);
                pembayaranSelanjutnya.putExtra(PemesananJadwalActivity.KEY_PESAN_TAMBAHAN,  etPesanTambahan.getText().toString());
                startActivity(pembayaranSelanjutnya);
            }
        });
    }

    private void hitungPemesanan(int valueTarif, String tarif) {

        btnAlertSaldo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogAlertSaldo();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Click==0) {
                    img2.setImageResource(R.drawable.ic_droptop);
                    tv7.startAnimation(in);
                    view1.startAnimation(in);
                    view1.setVisibility(View.VISIBLE);
                    tv7.setVisibility(View.VISIBLE);
                    Click++;
                }
                else if(Click==1) {
                    Click=0;
                    img2.setImageResource(R.drawable.ic_dropdown);
                    view1.setVisibility(View.GONE);
                    tv7.setVisibility(View.GONE);
                }
            }
        });

    }

    public void DialogAlertSaldo() {
        TextView stv1,stv2,stv3,stv4,stv5,stv6;
        final RadioButton srb1;
        RadioButton srb2, srb3, srb4, srb5;
        Button btnTambahSaldoPop;

        myDialog.setContentView(R.layout.popup_ticket);

        btnTambahSaldoPop = (Button) myDialog.findViewById(R.id.btn_tambah_saldo_pop);

        btnTambahSaldoPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PemesananActivity.this, TambahSaldo.class));
            }
        });


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
