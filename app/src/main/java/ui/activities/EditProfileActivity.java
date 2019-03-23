package ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.response.ResponseCreateMurid;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private RadioButton radioSelJK;
    private ProgressDialog progress;
    private static final String KEY_ID_MURID        = "id";
    private static final String KEY_NAMA            = "nama";
    private static final String KEY_HP              = "no_hp";
    private static final String KEY_EMAIL           = "email";
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

    @BindView(R.id.editTextNama)
    EditText etNama;
    @BindView(R.id.editTextHP) EditText etNo_hp;
    @BindView(R.id.editTextEmail) EditText etEmail;
    @BindView(R.id.editTextAlamat) EditText etAlamat;
    @BindView(R.id.radioGroupJk)
    RadioGroup radioGroupJk;
    @BindView(R.id.radioLk) RadioButton radioButtonLk;
    @BindView(R.id.radioPr) RadioButton radioButtonPr;
    @BindView(R.id.editTextNisn) EditText etNisn;
    @BindView(R.id.editTextKelas) EditText etKelas;
    @BindView(R.id.editTextNamaSekolah) EditText etNama_sekolah;
    @BindView(R.id.editTextPhoto) EditText etPhoto;

    ApiInterface apiService;
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

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

        System.out.println("ID anda :"+SESSION_ID_MURID);
        System.out.println("Nama anda :"+SESSION_NAMA);
        System.out.println("HP anda   :"+SESSION_HP);

        etNama.setText(SESSION_NAMA);
        etNo_hp.setText(SESSION_HP);
        etEmail.setText(SESSION_EMAIL);
        etAlamat.setText(SESSION_ALAMAT);
        if (SESSION_JK.equals("Laki-laki")){
            radioButtonLk.setChecked(true);
        }else {
            radioButtonPr.setChecked(true);
        }
        etNisn.setText(SESSION_NISN);
        etKelas.setText(SESSION_KELAS);
        etNama_sekolah.setText(SESSION_NAMA_SEKOLAH);
        etPhoto.setText(SESSION_PHOTO);
    }

    @OnClick(R.id.buttonUbah) void ubah(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();

        int id              = SESSION_ID_MURID;
        String nama         = etNama.getText().toString();
        String no_hp        = etNo_hp.getText().toString();
        String email        = etEmail.getText().toString();
        String alamat       = etAlamat.getText().toString();
        int selectId        = radioGroupJk.getCheckedRadioButtonId();
        radioSelJK          = (RadioButton) findViewById(selectId);
        String jk           = radioSelJK.getText().toString();
        String nisn         = etNisn.getText().toString();
        String kelas        = etKelas.getText().toString();
        String nama_sekolah = etNama_sekolah.getText().toString();
        String photo        = etPhoto.getText().toString();

        apiService.updateMurid(id, nama, no_hp, email, alamat, jk, nisn, kelas, nama_sekolah, photo).enqueue(new Callback<ResponseCreateMurid>() {
            @Override
            public void onResponse(Call<ResponseCreateMurid> call, Response<ResponseCreateMurid> response) {
                if (response.isSuccessful()){
                    progress.dismiss();
                    if (response.body().getCode() == 200){
                        Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e(TAG, "Gagal Update");

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateMurid> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(EditProfileActivity.this, "Jaringan Error!", Toast.LENGTH_LONG).show();
            }
        });
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
}
