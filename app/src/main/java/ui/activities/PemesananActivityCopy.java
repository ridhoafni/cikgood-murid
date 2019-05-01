package ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.MengajarAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.GuruDataMatpel;
import com.example.anonymous.cikgood.response.ResponseGuruDataMatpel;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivityCopy extends AppCompatActivity {

    public static final String KEY_ID_GURU = "id_guru";
    public static final String KEY_PHOTO_GURU = "photo_profile";
    public static final String KEY_NAMA_GURU = "nama";
    public static final String KEY_JURUSAN_GURU = "jurusan";
    public static final String KEY_UNIV_GURU = "nama_institusi";
    public static final String KEY_GELAR_GURU = "gelar";

    public static final String KEY_ADDRESS= "alamat_lengkap";
    public static final String KEY_LATITUDE= "latitude";
    public static final String KEY_LONGITUDE= "longitude";

    int id, tarif, harga;
    TextView tvDurasiPertemuanJam, tvDurasiPertemuan, tvTotalJam, tvTotalRp, tvNamaGuru, tvGelarGuru, tvUnivGuru, tvJurusanGuru;
    ImageView photo_profil, ivAlamat;
    String nama, gelar, univ, jurusan, photo, x, selectedSpinnerMatpel, selectedSpinnerDurasiPertemuan, alamat;
    double selectedSpinnerDurasiJam;
    Spinner spinnerDurasiPertemuan, spinnerDurasiPertemuanJam, spinnerMatpel;
    EditText etAlamat;
    ApiInterface apiService;
    MengajarAdapter mengajarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_copy);

        spinnerDurasiPertemuan = (Spinner)findViewById(R.id.spinner_durasi_pertemuan);
        spinnerDurasiPertemuanJam = (Spinner)findViewById(R.id.spinner_durasi_pertemuan_jam);
        spinnerMatpel = (Spinner)findViewById(R.id.spinner_matpel);
        tvDurasiPertemuanJam = (TextView) findViewById(R.id.jam);
        tvDurasiPertemuan = (TextView) findViewById(R.id.tv_detail_durasi_pertemuan);
        tvTotalJam = (TextView) findViewById(R.id.tv_total_jam);
        tvTotalRp = (TextView) findViewById(R.id.tv_total_rp);
        tvGelarGuru = (TextView) findViewById(R.id.tv_gelar);
        photo_profil = (ImageView) findViewById(R.id.thumbnail);
        tvNamaGuru = (TextView) findViewById(R.id.tv_nama);
        tvUnivGuru = (TextView) findViewById(R.id.tv_univ);
        tvJurusanGuru = (TextView) findViewById(R.id.tv_jurusan);
        ivAlamat = (ImageView) findViewById(R.id.icon_alamat);
        etAlamat = (EditText) findViewById(R.id.et_alamat_lengkap);

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        initSpinnerDurasiPertemuan();
        initSpinnerDurasiPertemuanJam();
        initSpinnerMatpel();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pemesanan");

        photo = getIntent().getStringExtra(KEY_PHOTO_GURU);
        nama = getIntent().getStringExtra(KEY_NAMA_GURU);
        gelar = getIntent().getStringExtra(KEY_GELAR_GURU);
        univ = getIntent().getStringExtra(KEY_UNIV_GURU);
        jurusan = getIntent().getStringExtra(KEY_JURUSAN_GURU);
        alamat = getIntent().getStringExtra(KEY_ADDRESS);
        tvNamaGuru.setText(nama);
        tvGelarGuru.setText(gelar);
        tvUnivGuru.setText(univ);
        tvJurusanGuru.setText(jurusan);
        Glide.with(this)
                .load(ServerConfig.GURU_PATH+photo)
                .apply(new RequestOptions().override(100, 100))
                .into(photo_profil);

        etAlamat.setText(alamat);

        System.out.println("id guru :"+id);
        System.out.println("photo guru :"+photo);
        System.out.println("gelar guru :"+gelar);
        System.out.println("nama guru :"+nama);
        System.out.println("univ guru :"+univ);
        System.out.println("jurusan guru :"+jurusan);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        } else {
            // Permission has already been granted
//            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }

        ivAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_maps;
                intent_maps = new Intent(PemesananActivityCopy.this, OjekActivity.class);
//                intent_maps.putExtra(OjekActivity.KEY_ID_GURU, id);
//                intent_maps.putExtra(OjekActivity.KEY_NAMA_GURU,nama);
//                intent_maps.putExtra(OjekActivity.KEY_GELAR_GURU,  gelar);
//                intent_maps.putExtra(OjekActivity.KEY_UNIV_GURU,  univ);
//                intent_maps.putExtra(OjekActivity.KEY_JURUSAN_GURU,  jurusan);
//                intent_maps.putExtra(OjekActivity.KEY_PHOTO_GURU,  photo);
                startActivity(intent_maps);
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PemesananActivityCopy.this,
                                android.R.layout.simple_spinner_item, listSpinnerGuruDataMatpel);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMatpel.setAdapter(adapter);


                    spinnerMatpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSpinnerMatpel = parent.getItemAtPosition(position).toString();
//                           requestDetailDosen(selectedName);

//                            Toast.makeText(PemesananActivity.this, "Kamu memilih " +selectedSpinnerMatpel, Toast.LENGTH_SHORT).show();

//                            System.out.println("Harganya :"+x);
//                            System.out.println("Harganya2 :"+spinnerMatpel.getSelectedItem().toString());

//                            tvDurasiPertemuanJam.setText(String.valueOf(selectedSpinnerDurasiJam));
                            tvTotalRp.setText(addNumbers());

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
                                    System.out.println("tarifnya:"+tarif);
//                                    idMatpel = semuamastermatpel.getIdMasterMatpel();
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
//                           requestDetailDosen(selectedName);

//                Toast.makeText(PemesananActivity.this, "Kamu memilih " +selectedSpinnerDurasiJam, Toast.LENGTH_SHORT).show();

//                            System.out.println("Harganya :"+x);
//                            System.out.println("Harganya2 :"+spinnerMatpel.getSelectedItem().toString());
                String durasi_jam = "("+String.valueOf(selectedSpinnerDurasiJam)+" Jam)";
                tvDurasiPertemuanJam.setText(durasi_jam);
                tvTotalRp.setText(addNumbers());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerDurasiPertemuan() {
        String[] array_durasi_pertemuan_jam = {"8", "9", "10", "11", "13", "14"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_durasi_pertemuan_jam);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerDurasiPertemuan.setPrompt("Jumlah Pertemuan");
       spinnerDurasiPertemuan.setAdapter( adapter);


       spinnerDurasiPertemuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedSpinnerDurasiPertemuan = parent.getItemAtPosition(position).toString();
//                           requestDetailDosen(selectedName);

//               Toast.makeText(PemesananActivity.this, "Kamu memilih " +selectedSpinnerDurasiPertemuan, Toast.LENGTH_SHORT).show();

//                            System.out.println("Harganya :"+x);
//                            System.out.println("Harganya2 :"+spinnerMatpel.getSelectedItem().toString());
               tvDurasiPertemuan.setText(selectedSpinnerDurasiPertemuan);
               tvTotalRp.setText(addNumbers());

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }

    private String addNumbers() {
        int harga_matpel;
        int jumlah_pertemuan;
        double jumlah_pertemuan_jam;

            harga_matpel = tarif;

        if (spinnerDurasiPertemuan.getSelectedItem().toString() != "" && spinnerDurasiPertemuan.getSelectedItem().toString().length() > 0){
            jumlah_pertemuan = Integer.parseInt(selectedSpinnerDurasiPertemuan);
        }else {
            jumlah_pertemuan = 0;
        }

        if (spinnerDurasiPertemuanJam.getSelectedItem().toString() != "" && spinnerDurasiPertemuanJam.getSelectedItem().toString().length() > 0){
            jumlah_pertemuan_jam = selectedSpinnerDurasiJam;
        }else {
            jumlah_pertemuan_jam = 0;
        }

        double total_jam = jumlah_pertemuan_jam * jumlah_pertemuan;

        double harga_total =  harga_matpel * total_jam;
        String jam = String.valueOf(total_jam)+" Jam";
        tvTotalJam.setText(jam);

//        harga_total_db = (int) (harga * porsi);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return formatRupiah.format(harga_total);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//              loadHomeFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
