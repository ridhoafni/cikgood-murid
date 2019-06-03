package ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.response.ResponseLogin;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.unstoppable.submitbuttonview.SubmitButton;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    public final String TAG = LoginActivity.class.getSimpleName();
    TextView tvCreateNewAccount;
    SubmitButton btnLogin;
    TextView tvForgotPwd;
    LinearLayout linearLayout;
    SessionManager sessionManager;
    ApiInterface apiService;
    private EditText inputEmail, inputPassword;
    Animation animationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnLogin = (SubmitButton) findViewById(R.id.sbtn_login_loading);
        inputEmail = (EditText) findViewById(R.id.inputEmailAddress);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        tvForgotPwd = (TextView) findViewById(R.id.buttonForgotPassword);

        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, ForgotPassword.class));
            }
        });
//        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutLogin);


        apiService      = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        sessionManager  = new SessionManager(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginMurid();
                }
        });


        tvCreateNewAccount = (TextView) findViewById(R.id.buttonRegister);
        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        // change color in primaryDark

        // change icon color status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar

//        animationLayout = AnimationUtils.loadAnimation(this, R.anim.smaltobig);
//        linearLayout.setAnimation(animationLayout);

        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.splashPrimary));
        }
        // change color in primaryDark

        // change icon color status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar

    }

    private void loginMurid() {

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        Log.d(TAG, "loginMurid: "+email+ " "+password);

        if (TextUtils.isEmpty(email)) {
            btnLogin.doResult(false);
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    btnLogin.reset();
                    handler.postDelayed(this, 4000);
                }
            };
            handler.postDelayed(r, 4000);
            Toasty.warning(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            btnLogin.doResult(false);
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    btnLogin.reset();
                    handler.postDelayed(this, 1000);
                }
            };
            handler.postDelayed(r, 1000);
            Toasty.warning(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

//        progressBar.setVisibility(View.VISIBLE);

        apiService.muridLogin(email, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponses : Dapat terhubung ke server");
                    Log.d(TAG, "onResponses : " + response.body().toString());
                    if (response.body().getCode().equals(200)) {

                        btnLogin.doResult(true);
//                      btnLogin.reset();


                        Integer id_murid = response.body().getData().getId();
                        String nama = response.body().getData().getNama();
                        String token= response.body().getData().getToken();
                        String no_hp = response.body().getData().getNoHp();
                        String email = response.body().getData().getEmail();
                        String password = response.body().getData().getPassword();
                        String alamat = response.body().getData().getAlamat();
                        String jk = response.body().getData().getJk();
                        String nisn = response.body().getData().getNisn();
                        String kelas = response.body().getData().getKelas();
                        String nama_sekolah = response.body().getData().getNamaSekolah();
                        String photo = response.body().getData().getPhoto();

                        System.out.println("Response JK : "+response.body().getData().getJk());

                        sessionManager.createLoginSession(id_murid, nama, token, no_hp, email, password, alamat, jk,
                                nisn, kelas, nama_sekolah,
                                photo);

//                        btnLogin.doResult(false);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), NavigationView.class);
                                Toast.makeText(getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                            }
                        }, 4000);


                    } else {
//
                        btnLogin.doResult(false);
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                btnLogin.reset();
                                handler.postDelayed(this, 4000);
                            }
                        };
                        handler.postDelayed(r, 4000);
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    btnLogin.doResult(false);
                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        public void run() {
                            btnLogin.reset();
                            handler.postDelayed(this, 4000);
                    }
                };
                handler.postDelayed(r, 4000);

                    Toast.makeText(getApplicationContext(), "Gagal login"+response.toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                btnLogin.doResult(false);
                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        btnLogin.reset();
                        handler.postDelayed(this, 4000);
                    }
                };
                handler.postDelayed(r, 4000);
                Toast.makeText(getApplicationContext(), "Gagal konek ke server", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }

}
