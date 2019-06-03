package ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;

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

public class EditAkun extends AppCompatActivity {

    String MURID_ID                 = "id";
    String NAMA                     = "nama";
    String NO_HP                    = "no_hp";
    String EMAIL                    = "email";
    String JK                       = "jk";
    String NISN                     = "nisn";
    String KELAS                    = "kelas";
    String NAMA_SEKOLAH             = "nama_sekolah";
    String PHOTO                    = "photo";

    /* Path PHP */

    byte[] byteArray;

    private SessionManager sessionManager;

    private URL url;
    Bitmap FixBitmap;
    private int RC, id_jk;
    private boolean check = true;
    private OutputStream outputStream;
    private StringBuilder stringBuilder;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private ByteArrayOutputStream byteArrayOutputStream;


    private ImageView ivProfile;

    private Button btnPickImage, btnUpdateProfile;

    private RadioGroup rgJk;
    private RadioButton rbLk, rbPr;

    String ServerUploadPath = ServerConfig.UPLOAD_UPDATE_FOTO_MURID_ENDPOINT;

    private String nama, no_hp, email, pwd, alamat, jk, nisn, kelas, nama_sekolah, photo, murid_id;
    private EditText etNamaLengkap, etNoHP, etEmail, etPwd, etAlamat, etNisn, etKelas, etNamaSekolah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_akun);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(EditAkun.this, NavigationView.class);
                intentProfile.putExtra("FromEditAkun", "3");
                startActivity(intentProfile);
            }
        });

        initView();

        action();

        byteArrayOutputStream = new ByteArrayOutputStream();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
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
                                btnUpdateProfile.setText("MERUBAH...");
                            }
                            @Override
                            public void onFinish() {
                                btnUpdateProfile.setText("UBAH PROFILE");
                                UploadImageToServer();
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
    }

    private void action() {


        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

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

                progressDialog = ProgressDialog.show(EditAkun.this,"Updating","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(EditAkun.this, string1,Toast.LENGTH_LONG).show();

//                if (string1.equals("Berhasil disimpan...")){
//                startActivity(new Intent(EditAkun.this, SuccessRegisterActivity.class));
//                }
            }

            @Override
            protected String doInBackground(Void... params) {
                id_jk = rgJk.getCheckedRadioButtonId();
                murid_id = sessionManager.getMuridProfile().get(SessionManager.ID_MURID);
                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                jk              = ((RadioButton)findViewById(id_jk)).getText().toString();
                nisn            = etNisn.getText().toString();
                no_hp           = etNoHP.getText().toString();
                kelas           = etKelas.getText().toString();
                email           = etEmail.getText().toString();
                kelas           = etKelas.getText().toString();
                nama            = etNamaLengkap.getText().toString();
                nama_sekolah    = etNamaSekolah.getText().toString();



                Log.d("register jk", ""+jk);
                Log.d("register pwd", ""+nisn);
                Log.d("register nama", ""+nama);
                Log.d("register no_hp", ""+no_hp);
                Log.d("register kelas", ""+kelas);
                Log.d("register email", ""+email);
                Log.d("register nama sekolah", ""+nama_sekolah);

                Log.d("photo murid", ""+photo);

                HashMapParams.put(JK, jk);
                HashMapParams.put(NAMA, nama);
                HashMapParams.put(NISN, nisn);
                HashMapParams.put(KELAS, kelas);
                HashMapParams.put(NO_HP, no_hp);
                HashMapParams.put(PHOTO, photo);
                HashMapParams.put(EMAIL, email);
                HashMapParams.put(MURID_ID, murid_id);
                HashMapParams.put(NAMA_SEKOLAH, nama_sekolah);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
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

        sessionManager = new SessionManager(this);

        etNisn          = (EditText) findViewById(R.id.et_nisn);
        etNoHP          = (EditText) findViewById(R.id.et_no_hp);
        etKelas         = (EditText) findViewById(R.id.et_kelas);
        etEmail         = (EditText) findViewById(R.id.et_email);
        etNamaLengkap   = (EditText) findViewById(R.id.et_nama_lengkap);
        etNamaSekolah   = (EditText) findViewById(R.id.et_nama_sekolah);

        ivProfile       = (ImageView) findViewById(R.id.imageProfile);

        btnPickImage    = (Button) findViewById(R.id.buttonChangePhoto);
        btnUpdateProfile= (Button) findViewById(R.id.btn_update_profile);

        rbLk = findViewById(R.id.radioMale);
        rbPr = findViewById(R.id.radioFemale);
        rgJk = findViewById(R.id.radioGroupSex);

        /* set text widget*/
        etNamaLengkap.setText(sessionManager.getMuridProfile().get(SessionManager.NAMA));
        etNisn.setText(sessionManager.getMuridProfile().get(SessionManager.NISN));
        etNamaSekolah.setText(sessionManager.getMuridProfile().get(SessionManager.NAMA_SEKOLAH));
        etEmail.setText(sessionManager.getMuridProfile().get(SessionManager.EMAIL));
        etKelas.setText(sessionManager.getMuridProfile().get(SessionManager.KELAS));
        etNoHP.setText(sessionManager.getMuridProfile().get(SessionManager.HP));

        Glide.with(EditAkun.this)
                .load(ServerConfig.MURID_PROFILE_PATH+sessionManager.getMuridProfile().get(SessionManager.PHOTO))
                .apply(new RequestOptions().override(200, 300))
                .into(ivProfile);
        String selected_jk = sessionManager.getMuridProfile().get(SessionManager.JK);

        if (selected_jk.equals("Laki-Laki")) {
            rbLk.setChecked(true);
        } else {
            rbPr.setChecked(true);
        }

    }

}
