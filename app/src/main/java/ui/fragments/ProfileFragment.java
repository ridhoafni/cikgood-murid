package ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.GuruAdapter;
import com.example.anonymous.cikgood.adapters.InsertMapsActivity;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.response.ResponFindSaldo;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.activities.EditAkun;
import ui.activities.GuruActivity;
import ui.activities.JadwalActivity;
import ui.activities.LoginActivity;
import ui.activities.MainActivity;
import ui.activities.PemesananActivity;
import ui.activities.ResetPassword;
import ui.activities.Saldo;
import ui.activities.TambahSaldo;
import ui.activities.UbahPassword;


/**
 * A simple {@link } subclass.
 */
public class ProfileFragment extends Fragment {
    ApiInterface apiInterface;
    SessionManager sessionManager ;

    private RelativeLayout saldo, ubahPwd, rlJadwal;
    TextView editAkun, logout,tv_nama,tv_alamat,tv_email,tv_hp,tv_jk, tv_saldo ;
    private Dialog mDialog;

    int id;

//    private FirebaseAuth.AuthStateListener authListener;
//    private FirebaseAuth auth;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sessionManager = new SessionManager(getActivity());

        apiInterface = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        id = Integer.parseInt(sessionManager.getMuridProfile().get("id"));

        ubahPwd     = (RelativeLayout)view.findViewById(R.id.rl_edit_ubah_pwd);
        saldo       = (RelativeLayout) view.findViewById(R.id.rl_saldo);
        rlJadwal    = (RelativeLayout)view.findViewById(R.id.rl_jadwal);
        logout      = (TextView) view.findViewById(R.id.buttonSignOut);
        editAkun    = (TextView)view.findViewById(R.id.buttonEdit);
        tv_email    = (TextView) view.findViewById(R.id.labelEmail);
        tv_nama     = (TextView) view.findViewById(R.id.labelName);
        tv_alamat   = (TextView) view.findViewById(R.id.tv_alamat);
        tv_hp       = (TextView) view.findViewById(R.id.labelNoHp);
        tv_jk       = (TextView) view.findViewById(R.id.labelSex);
        tv_saldo    = (TextView) view.findViewById(R.id.tv_saldo);

        /*set text widget*/
        tv_nama.setText(sessionManager.getMuridProfile().get("nama"));
        tv_alamat.setText(sessionManager.getMuridProfile().get("alamat"));
        tv_email.setText(sessionManager.getMuridProfile().get("email"));
        tv_hp.setText(sessionManager.getMuridProfile().get("no_hp"));
        tv_jk.setText(sessionManager.getMuridProfile().get("jk"));
        tv_saldo.setText(sessionManager.getMuridProfile().get("saldo"));

        getDataSaldo(id);

        mDialog = new Dialog(getActivity(), R.style.DialogTrans);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        ubahPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ResetPassword.class));
            }
        });

        rlJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JadwalActivity.class));
            }
        });


        editAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditAkun.class));
            }
        });

        saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Saldo.class));
            }
        });

        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.grayPrimary));
        }

        // change icon color status bar
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar
        return view;

    }

    private void getDataSaldo(int id) {
        apiInterface.getSaldo(id).enqueue(new Callback<ResponFindSaldo>() {
            @Override
            public void onResponse(Call<ResponFindSaldo> call, Response<ResponFindSaldo> response) {
                if (response.isSuccessful()){
                    int saldo = response.body().getMaster().getTotalSaldo();

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                    Log.d("result saldo", ""+saldo+"");
                    tv_saldo.setText(formatRupiah.format(saldo));
                }
            }

            @Override
            public void onFailure(Call<ResponFindSaldo> call, Throwable t) {

            }
        });
    }

    private void dialogLogout() {
//        Button btnTambahSaldoPop;

        mDialog.setContentView(R.layout.pop_up_log_out);

//        btnTambahSaldoPop = (Button) myDialog.findViewById(R.id.btn_tambah_saldo_pop);
//
//        btnTambahSaldoPop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PemesananActivity.this, TambahSaldo.class));
//            }
//        });


        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

}
