package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.Murid;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.squareup.picasso.Picasso;

import ui.fragments.BerandaFragment;

public class ProfileActivity extends AppCompatActivity {
    private Context context;
    TextView tvNama, tvEmail, tvJk, tvNisn, tvHP;
    ImageView imgProfile;
    SessionManager sessionManager;
    private String SESSION_NAMA, SESSION_HP, SESSION_EMAIL,SESSION_ALAMAT, SESSION_NISN, SESSION_KELAS, SESSION_JK, SESSION_NAMA_SEKOLAH, SESSION_PHOTO;
    int SESSION_ID_MURID;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile  = findViewById(R.id.img_profile);
        tvNama      = findViewById(R.id.tv_nama);
        tvEmail     = findViewById(R.id.tv_email);
        tvHP        = findViewById(R.id.tv_no_hp);
        tvJk        = findViewById(R.id.tv_jk);
        tvNisn      = findViewById(R.id.tv_nisn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Murid");

        sessionManager = new SessionManager(this);

        tvNama.setText(sessionManager.getMuridProfile().get("nama"));
        tvEmail.setText(sessionManager.getMuridProfile().get("email"));
        tvHP.setText(sessionManager.getMuridProfile().get("no_hp"));
        tvJk.setText(sessionManager.getMuridProfile().get("jk"));
        tvNisn.setText(sessionManager.getMuridProfile().get("nisn"));
        Picasso.get().load(ServerConfig.MURID_PATH+sessionManager.getMuridProfile().get("photo")).into(imgProfile);


        SESSION_ID_MURID        = Integer.parseInt(sessionManager.getMuridProfile().get("id"));
        SESSION_NAMA            = sessionManager.getMuridProfile().get("nama");
        SESSION_HP              = sessionManager.getMuridProfile().get("no_hp");
        SESSION_EMAIL           = sessionManager.getMuridProfile().get("email");
        SESSION_ALAMAT          = sessionManager.getMuridProfile().get("alamat");
        SESSION_JK              = sessionManager.getMuridProfile().get("jk");
        SESSION_NISN            = sessionManager.getMuridProfile().get("nisn");
        SESSION_KELAS           = sessionManager.getMuridProfile().get("kelas");
        SESSION_NAMA_SEKOLAH    = sessionManager.getMuridProfile().get("nama_sekolah");
        SESSION_PHOTO           = sessionManager.getMuridProfile().get("photo");

        System.out.println("Photonya :"+sessionManager.getMuridProfile().get("photo"));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), MainTabActivity.class);
                startActivity(i);
                break;

            case R.id.action_edit_profile:
                Intent edit = new Intent(getApplicationContext(), UploadImage.class);
                edit.putExtra(ProfileActivity.KEY_ID_MURID,     SESSION_ID_MURID);
                edit.putExtra(ProfileActivity.KEY_NAMA,         SESSION_NAMA);
                edit.putExtra(ProfileActivity.KEY_HP,           SESSION_HP);
                edit.putExtra(ProfileActivity.KEY_EMAIL,        SESSION_EMAIL);
                edit.putExtra(ProfileActivity.KEY_ALAMAT,       SESSION_ALAMAT);
                edit.putExtra(ProfileActivity.KEY_JK,           SESSION_JK);
                edit.putExtra(ProfileActivity.KEY_NISN,         SESSION_NISN);
                edit.putExtra(ProfileActivity.KEY_KELAS,        SESSION_KELAS);
                edit.putExtra(ProfileActivity.KEY_NAMA_SEKOLAH, SESSION_NAMA_SEKOLAH);
                edit.putExtra(ProfileActivity.KEY_PHOTO,        SESSION_PHOTO);
                startActivity(edit);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
