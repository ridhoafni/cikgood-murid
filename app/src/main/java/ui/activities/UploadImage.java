package ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadImage extends AppCompatActivity {
    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView ShowSelectedImage;

    EditText GetNama, GetNoHp, GetEmail, GetPassword, GetAlamat, GetJk, GetNisn, GetKelas, GetNamaSekolah;

    Bitmap FixBitmap;

    String PHOTO = "photo" ;
    String ID = "id" ;
    String NAMA = "nama" ;
    String PASSWORD = "password" ;
    String HP = "no_hp" ;
    String EMAIL = "email" ;
    String ALAMAT = "alamat" ;
    String JK = "jk" ;
    String NISN = "nisn" ;
    String KELAS = "kelas" ;
    String NAMA_SEKOLAH = "nama_sekolah" ;

    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_ENDPOINT;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String GetNameFromEditText, GetNoHpFromEditText, GetEmailFromEditText, GetPasswordFromEditText,
           GetAlamatFromEditText, GetJkFromRadioButton, GetNisnFromEditText, GetKelasFromEditText, GetNamaSekolahFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    private RadioButton radioSelJK, GetPrRadioButton, GetLkRadioButton;

    private RadioGroup GetJkRadioGroup;

    int GetId, selectId;

    private ProgressDialog progress;
    private static final String KEY_ID_MURID        = "id";
    private static final String KEY_NAMA            = "nama";
    private static final String KEY_HP              = "no_hp";
    private static final String KEY_EMAIL           = "email";
    private static final String KEY_PASSWORD        = "password";
    private static final String KEY_ALAMAT          = "alamat";
    private static final String KEY_JK              = "jk";
    private static final String KEY_NISN            = "nisn";
    private static final String KEY_KELAS           = "kelas";
    private static final String KEY_NAMA_SEKOLAH    = "nama_sekolah";
    private static final String KEY_PHOTO           = "photo";

    private int SESSION_ID_MURID;
    private String SESSION_NAMA;
    private String SESSION_HP;
    private String SESSION_EMAIL;
    private String SESSION_ALAMAT;
    private String SESSION_NISN;
    private String SESSION_KELAS;
    private String SESSION_JK;
    private String SESSION_NAMA_SEKOLAH;
    private String SESSION_PHOTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");

        GetImageFromGalleryButton = (Button)findViewById(R.id.button);

        UploadImageOnServerButton = (Button)findViewById(R.id.button2);

        ShowSelectedImage = (ImageView)findViewById(R.id.imageView);

        GetNama = (EditText)findViewById(R.id.editTextNama);
        GetNoHp = (EditText)findViewById(R.id.editTextHP);
        GetEmail = (EditText)findViewById(R.id.editTextEmail);
        GetPassword = (EditText)findViewById(R.id.editTextPass);
        GetAlamat = (EditText)findViewById(R.id.editTextAlamat);
        GetJkRadioGroup = (RadioGroup)findViewById(R.id.radioGroupJk);
        GetLkRadioButton = (RadioButton)findViewById(R.id.radioLk);
        GetPrRadioButton = (RadioButton)findViewById(R.id.radioPr);
        GetNisn = (EditText)findViewById(R.id.editTextNisn);
        GetKelas = (EditText)findViewById(R.id.editTextKelas);
        GetNamaSekolah = (EditText)findViewById(R.id.editTextNamaSekolah);

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });


        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetId                       = SESSION_ID_MURID;
                GetNameFromEditText         = GetNama.getText().toString();
                GetNoHpFromEditText         = GetNoHp.getText().toString();
                GetEmailFromEditText        = GetEmail.getText().toString();
                GetPasswordFromEditText     = GetPassword.getText().toString();
                GetAlamatFromEditText       = GetAlamat.getText().toString();;
                selectId                    = GetJkRadioGroup.getCheckedRadioButtonId();
                radioSelJK                  = (RadioButton)findViewById(selectId);
                GetJkFromRadioButton        = radioSelJK.getText().toString();
                GetNisnFromEditText         = GetNisn.getText().toString();
                GetKelasFromEditText        = GetKelas.getText().toString();
                GetNamaSekolahFromEditText  = GetNamaSekolah.getText().toString();

              System.out.println("ID saya ini:"+GetId);

//              GetImageNameFromEditText = GetImageName.getText().toString();

                UploadImageToServer();

            }
        });

        ButterKnife.bind(this);

        Intent i = getIntent();
        SESSION_ID_MURID        = i.getIntExtra(KEY_ID_MURID, 0);
        SESSION_NAMA            = i.getStringExtra(KEY_NAMA);
        SESSION_HP              = i.getStringExtra(KEY_HP);
        SESSION_EMAIL           = i.getStringExtra(KEY_EMAIL);
        SESSION_ALAMAT          = i.getStringExtra(KEY_ALAMAT);
        SESSION_JK              = i.getStringExtra(KEY_JK);
        SESSION_NISN            = i.getStringExtra(KEY_NISN);
        SESSION_KELAS           = i.getStringExtra(KEY_KELAS);
        SESSION_NAMA_SEKOLAH    = i.getStringExtra(KEY_NAMA_SEKOLAH);
        SESSION_PHOTO           = i.getStringExtra(KEY_PHOTO);

//        System.out.println("ID saya ini:"+SESSION_ID_MURID);
//        System.out.println("ID saya ini:"+SESSION_NAMA);

//
        GetNama.setText(SESSION_NAMA);
        GetNoHp.setText(SESSION_HP);
        GetEmail.setText(SESSION_EMAIL);
        GetAlamat.setText(SESSION_ALAMAT);
        if (SESSION_JK.equals("Laki-laki")){
            GetLkRadioButton.setChecked(true);
        }else {
            GetPrRadioButton.setChecked(true);
        }
        GetNisn.setText(SESSION_NISN);
        GetKelas.setText(SESSION_KELAS);
        GetNamaSekolah.setText(SESSION_NAMA_SEKOLAH);

        Picasso.get().load(ServerConfig.MURID_PATH+SESSION_PHOTO).into(ShowSelectedImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ShowSelectedImage.setImageBitmap(FixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(UploadImage.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(UploadImage.this,string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ID, String.valueOf((GetId)));
                HashMapParams.put(NAMA, GetNameFromEditText);
                HashMapParams.put(HP, GetNoHpFromEditText);
                HashMapParams.put(EMAIL, GetEmailFromEditText);
                HashMapParams.put(PASSWORD, GetPasswordFromEditText);
                HashMapParams.put(ALAMAT, GetAlamatFromEditText);
                HashMapParams.put(JK, GetJkFromRadioButton);
                HashMapParams.put(NISN, GetNisnFromEditText);
                HashMapParams.put(KELAS, GetKelasFromEditText);
                HashMapParams.put(NAMA_SEKOLAH, GetNamaSekolahFromEditText);
                HashMapParams.put(PHOTO, ConvertImage);

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
                e.getLocalizedMessage();
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
}