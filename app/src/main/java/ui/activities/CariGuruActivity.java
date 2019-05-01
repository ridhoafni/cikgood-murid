package ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.DataMatpel;
import com.example.anonymous.cikgood.models.Kabupaten;
import com.example.anonymous.cikgood.models.Tingkatan;
import com.example.anonymous.cikgood.response.ResponseCariGuru;
import com.example.anonymous.cikgood.response.ResponseDataMatpel;
import com.example.anonymous.cikgood.response.ResponseKabupaten;
import com.example.anonymous.cikgood.response.ResponseTingkatan;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariGuruActivity extends AppCompatActivity {

    Spinner spinnerTingkatan, spinnerDataMatpel;
    AutoCompleteTextView autoCompleteKabupaten;
    int tingkatan, matpel;
    String kota;
    Button btnCari;
    SliderLayout sliderLayout;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog loading;
    ProgressBar progressBarTingkatan, progressBarMatpel, progressBarKabupaten, progressBarCari, progressBarBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_guru);

        spinnerTingkatan  = (Spinner)findViewById(R.id.spinner_tingkatan);
        spinnerDataMatpel = (Spinner)findViewById(R.id.spinner_matpel);
        autoCompleteKabupaten  = (AutoCompleteTextView)findViewById(R.id.auto_Complete_kabupaten);
        btnCari = (Button) findViewById(R.id.btn_cari);
        progressBarTingkatan = findViewById(R.id.SpinKitTingkatan);
        progressBarMatpel = findViewById(R.id.SpinKitMatpel);
        progressBarKabupaten = findViewById(R.id.SpinKitKabupaten);
        progressBarCari = findViewById(R.id.SpinKitCari);
        progressBarBtn = findViewById(R.id.SpinKitBtn);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Runnable() {
                    int interfal = 500;
                    @Override
                    public void run() {
                        new CountDownTimer(1500, 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                btnCari.setText("MENCARI...");
                            }
                            @Override
                            public void onFinish() {
                                btnCari.setText("CARI GURU");
                                cariGuru();
                            }
                        }.start();
                    }
                }.run();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
//                        Bitmap bitmap = takeScreenshot();
//                        saveBitmap(bitmap);
//                        Toast.makeText(TicketInformation.this, "Successfull Print Ticket on Storage\nPlease check your Gallery or Storage", Toast.LENGTH_SHORT).show();

                    }
                },1500);
            }
        });

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

//        initSpinnerKabupaten();
        initSpinnerTingkatan();
        initSpinnerKabupaten();
        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        // change color in primaryDark

        // change icon color status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar


    }

    private void initSpinnerTingkatan(){
//        loading = ProgressDialog.show(this, null, "harap tunggu...", true, false);

        progressBarTingkatan.setVisibility(View.VISIBLE);

        apiService.tingkatanFindAll().enqueue(new Callback<ResponseTingkatan>() {
            @Override
            public void onResponse(Call<ResponseTingkatan> call, Response<ResponseTingkatan> response) {
                System.out.println("Response Tingkatan :"+response);
                if (response.isSuccessful()) {
//                    loading.dismiss();
                    progressBarTingkatan.setVisibility(View.INVISIBLE);
                    List<Tingkatan> semuaTingkatanItems = response.body().getMaster();
                    List<String> listSpinnerTingkatan = new ArrayList<String>();
                    for (int i = 0; i < semuaTingkatanItems.size(); i++){
                        listSpinnerTingkatan.add(semuaTingkatanItems.get(i).getTingkatan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CariGuruActivity.this,
                            android.R.layout.simple_spinner_item, listSpinnerTingkatan);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTingkatan.setAdapter(adapter);
                    spinnerTingkatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                           requestDetailDosen(selectedName);

//                            Toast.makeText(getActivity(), "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();


                            for (Tingkatan semuaTingkatan : semuaTingkatanItems){


                                if (semuaTingkatan.getTingkatan().equals(spinnerTingkatan.getSelectedItem().toString())){
                                    System.out.println("ID :"+semuaTingkatan.getId());
                                    loadDataMatpel(semuaTingkatan.getId());
                                    tingkatan = semuaTingkatan.getId();
//                                    idMatpel = semuamastermatpel.getIdMasterMatpel();
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loading.dismiss();
                    Toasty.info(CariGuruActivity.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTingkatan> call, Throwable t) {
                loading.dismiss();
                Toasty.warning(CariGuruActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataMatpel(Integer id) {
        progressBarMatpel.setVisibility(View.VISIBLE);
//      loading = ProgressDialog.show(CariGuruActivity.this, null, "harap tunggu...", true, false);

        apiService.dataMatpelFindByIdTingkatan(id).enqueue(new Callback<ResponseDataMatpel>() {
            @Override
            public void onResponse(Call<ResponseDataMatpel> call, Response<ResponseDataMatpel> response) {
                System.out.println("Responnya :"+response);
                if (response.isSuccessful()) {
                    progressBarMatpel.setVisibility(View.INVISIBLE);
//                    loading.dismiss();
                    List<DataMatpel> semuadatadosenItems = response.body().getMaster();
                    List<String> listSpinnerDataMatpel = new ArrayList<String>();
                    for (int i = 0; i < semuadatadosenItems.size(); i++){
                        listSpinnerDataMatpel.add(semuadatadosenItems.get(i).getMatpelDetail());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CariGuruActivity.this,
                            android.R.layout.simple_spinner_item, listSpinnerDataMatpel);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDataMatpel.setAdapter(adapter);

                    spinnerDataMatpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                            Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
                            for (DataMatpel semuadatamatpel : semuadatadosenItems){
                                if (semuadatamatpel.getMatpelDetail().equals(spinnerDataMatpel.getSelectedItem().toString())){
//                                    loadDataMatpel(semuamastermatpel.getIdMasterMatpel());
                                    matpel = Integer.parseInt(semuadatamatpel.getMatpel());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
//                    loading.dismiss();
                    progressBarMatpel.setVisibility(View.INVISIBLE);
                    Toasty.info(CariGuruActivity.this, "Gagal mengambil data matpel", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataMatpel> call, Throwable t) {
//                loading.dismiss();
                progressBarMatpel.setVisibility(View.INVISIBLE);
                Toasty.warning(CariGuruActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerKabupaten(){
//        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);
        progressBarKabupaten.setVisibility(View.VISIBLE);
        progressBarBtn.setVisibility(View.VISIBLE);

        apiService.getDataKabupaten().enqueue(new Callback<ResponseKabupaten>() {
            @Override
            public void onResponse(Call<ResponseKabupaten> call, Response<ResponseKabupaten> response) {
                if (response.isSuccessful()) {
//                    loading.dismiss();

                    progressBarKabupaten.setVisibility(View.INVISIBLE);
                    progressBarBtn.setVisibility(View.INVISIBLE);
                    List<Kabupaten> semuaKabupatenItems = response.body().getMaster();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuaKabupatenItems.size(); i++){
                        listSpinner.add(semuaKabupatenItems.get(i).getName());
                    }

                    if (this != null){
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CariGuruActivity.this,
                                android.R.layout.simple_list_item_1, listSpinner);
                        autoCompleteKabupaten.setAdapter(adapter);
                    }


                } else {
                    loading.dismiss();
                    Toast.makeText(CariGuruActivity.this, "Gagal mengambil data kabupaten", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKabupaten> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CariGuruActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cariGuru() {
//        loading = ProgressDialog.show(this, null, "harap tunggu...", true, false);
        progressBarCari.setVisibility(View.INVISIBLE);


        kota = autoCompleteKabupaten.getText().toString();


        System.out.println("Kota :"+kota);
        System.out.println("Tingkatan :"+tingkatan);
        System.out.println("Matpel :"+matpel);

        apiService.cariGuru(tingkatan, matpel, kota).enqueue(new Callback<ResponseCariGuru>() {
            @Override
            public void onResponse(Call<ResponseCariGuru> call, Response<ResponseCariGuru> response) {
                System.out.println("Response Home "+response);
                if (response.isSuccessful()){
                    progressBarCari.setVisibility(View.VISIBLE);

                    Intent i ;
                    i = new Intent(CariGuruActivity.this, GuruActivity.class);
                    i.putExtra(GuruActivity.TINGKATAN, tingkatan);
                    i.putExtra(GuruActivity.MATPEL, matpel);
                    i.putExtra(GuruActivity.KOTA, kota);
                    startActivity(i);
//                    Toast.makeText(CariGuruActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();

                }else{
                    Toasty.error(CariGuruActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCariGuru> call, Throwable t) {

            }
        });
    }
}
