package ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegisterAccountActivity extends AppCompatActivity implements OnMapReadyCallback {

    /* Declaration static variable */
    public static final String KEY_LATITUDE             = "latitude";
    public static final String KEY_LONGITUDE            = "longitude";
    public static final String KEY_ADDRESS              = "alamat_lengkap";

    String MURID_ID                 = "murid_id";
    String NAMA                     = "nama";
    String NO_HP                    = "no_hp";
    String EMAIL                    = "email";
    String PWD                      = "password";
    String ALAMAT                   = "alamat";
    String LAT                      = "lat";
    String LNG                      = "lng";
    String JK                       = "jk";
    String NISN                     = "nisn";
    String KELAS                    = "kelas";
    String NAMA_SEKOLAH             = "nama_sekolah";
    String PHOTO                    = "photo";

    /* Path PHP */
    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_MURID_ENDPOINT;

    byte[] byteArray;

    /* Google maps */
    GoogleMap mMap;

    /* LatLng pekanbaru */
    public LatLng pekanbaru = null;

    /* CameraPosistion */
    CameraPosition cameraPosition;

    private URL url;
    Bitmap FixBitmap;
    private int RC, getGuruId;
    private boolean check = true;
    private OutputStream outputStream;
    private StringBuilder stringBuilder;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private ByteArrayOutputStream byteArrayOutputStream;

    private Spinner spinnerJk;
    private LinearLayout btn_back, maps;
    private ImageView ivProfile, ivSave;
    private Button btnContinue, btnPickImage, btnMaps;
    private EditText etNamaLengkap, etNoHP, etEmail, etPwd, etAlamat, etNisn, etKelas, etNamaSekolah;

    String photo;
    String[] array_jk   =   {"Jenis Kelamin", "Laki-Laki", "Perempuan"};
    String nama, no_hp, email, pwd, alamat, jk, nisn, kelas, nama_sekolah;

    double lat, lng;

    private MarkerOptions markerOptions = new MarkerOptions();
    private String API_KEY = "AIzaSyCbX09ztk-EA8E3_HvCfTY8uRF5y0Bc3q8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        initView();

        action();

        getTextValue();

        byteArrayOutputStream = new ByteArrayOutputStream();

        alamat          = getIntent().getStringExtra(KEY_ADDRESS);
        lat             = getIntent().getDoubleExtra(KEY_LATITUDE, 0);
        lng             = getIntent().getDoubleExtra(KEY_LONGITUDE, 0);

        etAlamat.setText(alamat);

        if (lat != 0.0){
            maps.setVisibility(View.VISIBLE);
        }else{
            maps.setVisibility(View.GONE);
        }

//        btn_back = (LinearLayout) findViewById(R.id.btn_back);
//        btnContinue = (Button) findViewById(R.id.btn_continue);
//        btnContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisterAccountActivity.this, RegisterAccount2Activity.class));
//            }
//        });
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisterAccountActivity.this, SignInActivity.class));
//            }
//        });
    }

//    private void setTextValue() {
//        etAlamat.setText(alamat);
//    }

    private void getTextValue() {

    }

    private void action() {

        spinnerJk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jk = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterJK = new ArrayAdapter<String>(RegisterAccountActivity.this, android.R.layout.simple_spinner_item, array_jk);
        adapterJK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJk.setPrompt("Pilih Nama Bank");
        spinnerJk.setAdapter( adapterJK);

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageToServer();
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(RegisterAccountActivity.this, MapsMurid.class);
                startActivity(maps);

            }
        });
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivProfile.setImageBitmap(FixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        photo     = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(RegisterAccountActivity.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(RegisterAccountActivity.this, string1,Toast.LENGTH_LONG).show();

//                if (string1.equals("Berhasil disimpan...")){
                    startActivity(new Intent(RegisterAccountActivity.this, SuccessRegisterActivity.class));
//                }
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                pwd             = etPwd.getText().toString();
                nisn            = etNisn.getText().toString();
                no_hp           = etNoHP.getText().toString();
                kelas           = etKelas.getText().toString();
                email           = etEmail.getText().toString();
                kelas           = etKelas.getText().toString();
                alamat          = etAlamat.getText().toString();
                nama            = etNamaLengkap.getText().toString();
                nama_sekolah    = etNamaSekolah.getText().toString();

                Log.d("register pwd", ""+pwd);
                Log.d("register pwd", ""+nisn);
                Log.d("register nama", ""+nama);
                Log.d("register pwd", ""+no_hp);
                Log.d("register pwd", ""+kelas);
                Log.d("register pwd", ""+email);
                Log.d("register pwd", ""+alamat);
                Log.d("register pwd", ""+nama_sekolah);
                Log.d("register pwd", ""+nama_sekolah);

                Log.d("photo murid", ""+photo);

                HashMapParams.put(JK, jk);
                HashMapParams.put(LNG, String.valueOf(lng));
                HashMapParams.put(LAT, String.valueOf(lat));
                HashMapParams.put(PWD, pwd);
                HashMapParams.put(NAMA, nama);
                HashMapParams.put(NISN, nisn);
                HashMapParams.put(KELAS, kelas);
                HashMapParams.put(NO_HP, no_hp);
                HashMapParams.put(PHOTO, photo);
                HashMapParams.put(EMAIL, email);
                HashMapParams.put(ALAMAT, alamat);
                HashMapParams.put(NAMA_SEKOLAH, nama_sekolah);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
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

        Log.d("marker lat", ""+lat);
        Log.d("marker lng", ""+lng);
        pekanbaru = new LatLng(lat, lng);

        mMap.addMarker(new MarkerOptions().position(pekanbaru));
        cameraPosition = new CameraPosition.Builder().target(pekanbaru).zoom(20).build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);

        mMap.animateCamera(cu);
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

    private void initView() {
        etPwd           = (EditText) findViewById(R.id.et_pwd);
        etNisn          = (EditText) findViewById(R.id.et_nisn);
        etNoHP          = (EditText) findViewById(R.id.et_no_hp);
        etKelas         = (EditText) findViewById(R.id.et_kelas);
        etEmail         = (EditText) findViewById(R.id.et_email);
        etAlamat        = (EditText) findViewById(R.id.et_alamat);
        etNamaLengkap   = (EditText) findViewById(R.id.et_nama_lengkap);
        etNamaSekolah   = (EditText) findViewById(R.id.et_nama_sekolah);

        ivSave          = (ImageView) findViewById(R.id.iv_save);
        ivProfile       = (ImageView) findViewById(R.id.iv_profile);

        spinnerJk       = (Spinner) findViewById(R.id.spinner_jk);

        btnMaps         = (Button) findViewById(R.id.btn_maps);
        btnPickImage    = (Button) findViewById(R.id.btn_pick_image);

        maps            = (LinearLayout) findViewById(R.id.ll_maps);

    }

}
