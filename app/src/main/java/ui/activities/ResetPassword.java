package ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.network.ApiServices;
import com.example.anonymous.cikgood.response.ResponseCreateMurid;
import com.example.anonymous.cikgood.response.ResponseCreatePemesanan;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    private ApiInterface apiService;
    private SessionManager sessionManager;

    KProgressHUD hud;
    private Button btnReset;
    private ConstraintLayout constraintLayout;
    private EditText etPwdLama, etPwdBaru, etPwdBaruLagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        sessionManager = new SessionManager(this);
        // API service
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        constraintLayout = findViewById(R.id.layoutParent);

        etPwdLama = findViewById(R.id.et_pwd_lama);
        etPwdBaru = findViewById(R.id.et_pwd_baru);
        btnReset = findViewById(R.id.btn_reset_pwd);
        etPwdBaruLagi = findViewById(R.id.et_pwd_baru_lagi);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(ResetPassword.this, NavigationView.class);
                intentProfile.putExtra("FromResetPassword", "2");
                startActivity(intentProfile);
            }
        });

        // Event button next
        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /* Library KPorgressHUB */
                hud = KProgressHUD.create(ResetPassword.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setDetailsLabel("Downloading data");
                scheduleDismiss();

                String inputPwdLama = etPwdLama.getText().toString().trim();
                String inputPwdBaru = etPwdBaru.getText().toString().trim();
                String inputPwdBaruLagi = etPwdBaruLagi.getText().toString().trim();
                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(inputPwdLama)) {
                    isEmptyFields = true;
                    /* Snackbar */
                    Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Harus diisi!", Snackbar.LENGTH_LONG);
                    snackbar_delete.show();

                    // changing color text
                    snackbar_delete.setActionTextColor(Color.RED);
                }

                if (TextUtils.isEmpty(inputPwdBaru)) {
                    isEmptyFields = true;
                    /* Snackbar */
                    Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Harus diisi!", Snackbar.LENGTH_LONG);
                    snackbar_delete.show();

                    // changing color text
                    snackbar_delete.setActionTextColor(Color.RED);
                }

                if (TextUtils.isEmpty(inputPwdBaruLagi)) {
                    isEmptyFields = true;
                    /* Snackbar */
                    Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Harus diisi!", Snackbar.LENGTH_LONG);
                    snackbar_delete.show();

                    // changing color text
                    snackbar_delete.setActionTextColor(Color.RED);
                }

                if (!isEmptyFields) {
                    if (etPwdBaru.getText().toString().equals(etPwdBaruLagi.getText().toString())) {
                        int id = Integer.parseInt(sessionManager.getMuridProfile().get(SessionManager.ID_MURID));
                        apiService.updatePwdMurid(id, etPwdBaru.getText().toString()).enqueue(new Callback<ResponseCreateMurid>() {
                            @Override
                            public void onResponse(Call<ResponseCreateMurid> call, Response<ResponseCreateMurid> response) {
                                Log.d("response update murid", "" + response);
                                if (response.isSuccessful()) {


                                    if (response.body().getCode().equals(200)) {

                                        /* Snackbar */
                                        Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Berhasil diupdate", Snackbar.LENGTH_LONG);
                                        snackbar_delete.show();

                                        // changing color text
                                        snackbar_delete.setActionTextColor(Color.RED);
                                    } else {
                                        Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Gagal diupdate", Snackbar.LENGTH_LONG);
                                        snackbar_delete.show();

                                        // changing color text
                                        snackbar_delete.setActionTextColor(Color.RED);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseCreateMurid> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error!" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                t.printStackTrace();
                            }
                        });

                    } else {
                        /* Snackbar */
                        Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Password tidak cocok!", Snackbar.LENGTH_LONG);
                        snackbar_delete.show();

                        // changing color text
                        snackbar_delete.setActionTextColor(Color.RED);
                    }
                }
            }

            private void scheduleDismiss() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hud.dismiss();
                    }
                }, 2000);
            }
        });
    }

}
