package ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.ResetPasswordActivity;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.response.ResponseCreateMurid;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.RequestHandler;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

//    public static final String URL = ServerConfig.API_ENDPOINT;
    EditText etNama, etNoHP, etEmail, etPassword, etAlamat, etJk, etNisn, etKelas, etNamaSekolah, etPhoto;
    Spinner spinnerJK;
    Button btnSubmit;
    ApiInterface apiService;
    private Button btnSignIn, btnSignUp, btnResetPassword, btnChoose;
    private TextView tvResponse;
    private ProgressBar progressBar;
    Context context;
    private static final String TAG = SignupActivity.class.getSimpleName();
    public static final String UPLOAD_URL = ServerConfig.UPLOAD_FOTO_ENDPOINT;
    public static final String UPLOAD_KEY = "photo";
    public static final String TAG2 = "MY MESSAGE";
    private ImageView imageView;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
//        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        etNama = findViewById(R.id.et_nama);
        etNoHP = findViewById(R.id.et_no_hp);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSubmit = findViewById(R.id.btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        tvResponse = findViewById(R.id.tv_response);

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                kirimDataMurid();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void kirimDataMurid() {

        String nama         = etNama.getText().toString();
        String email        = etEmail.getText().toString();
        String no_hp        = etNoHP.getText().toString();
        String password     = etPassword.getText().toString();

        apiService.simpanMurid(nama, email, no_hp, password).enqueue(new Callback<ResponseCreateMurid>() {
            @Override
            public void onResponse(Call<ResponseCreateMurid> call, Response<ResponseCreateMurid> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateMurid> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error!" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}