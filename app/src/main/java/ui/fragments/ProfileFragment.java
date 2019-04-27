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
import com.example.anonymous.cikgood.adapters.InsertMapsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;
import ui.activities.EditAkun;
import ui.activities.LoginActivity;
import ui.activities.MainActivity;
import ui.activities.PemesananActivity;
import ui.activities.TambahSaldo;
import ui.activities.UbahPassword;


/**
 * A simple {@link } subclass.
 */
public class ProfileFragment extends Fragment {

    private RelativeLayout saldo, ubahPwd;
    TextView editAkun, logout;
    private Dialog mDialog;
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

        saldo = (RelativeLayout) view.findViewById(R.id.rl_saldo);
        editAkun = (TextView)view.findViewById(R.id.rl_edit_akun);
        ubahPwd = (RelativeLayout)view.findViewById(R.id.rl_edit_ubah_pwd);
        logout = (TextView) view.findViewById(R.id.rl_logout);

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
                startActivity(new Intent(getActivity(), UbahPassword.class));
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
//                startActivity(new Intent(getActivity(), TambahSaldo.class));
            }
        });

        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        // change color in primaryDark

        // Find the toolbar view inside the activity layout
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        toolbar.setTitle("Profile");
//        toolbar.getResources().setTitleTextColor(R.color.colorAc cent);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);

        // click button back pada title bar
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        //get firebase auth instance
//        auth = FirebaseAuth.getInstance();

        //get current user
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        signOut = (Button) view.findViewById(R.id.sign_out);
//
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    startActivity(new Intent(getActivity(), MainActivity.class));
//
//            }
//        });

//        authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    // user auth state is changed - user is null
//                    // launch login activity
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//            }
//        };

//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Logout();
//            }
//        });
        // change icon color status bar
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar
        return view;

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
