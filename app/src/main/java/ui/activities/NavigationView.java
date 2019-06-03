package ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.anonymous.cikgood.R;

import ui.fragments.ChatFragment;
import ui.fragments.HomeFragment;
import ui.fragments.PemesananFragment;
import ui.fragments.ProfileFragment;

public class NavigationView extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private PemesananFragment pemesananFragment;

    //    private Toolbar toolbar;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);

//      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = getSupportActionBar();

//        setupBottomNavigation();

        mMainFrame = findViewById(R.id.fragment_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        pemesananFragment = new PemesananFragment();

        startFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_homes:
                        startFragment(homeFragment);
//                        toolbar.setTitle("Home");
                        return true;

                    case R.id.navigation_users:
                        startFragment(profileFragment);
//                        toolbar.setTitle("Profil");
                        return true;

                    case R.id.navigation_orders:
                        startFragment(pemesananFragment);
//                        toolbar.setTitle("Pemesanan");
                        return true;
                }
                return false;
            }
        });

        Intent i = getIntent();
        Intent i2 = getIntent();

        String data = i.getStringExtra("FromGuruActivity");
        String dataFromResetPwd = i2.getStringExtra("FromResetPassword");
        String dataFromEditAkun= i2.getStringExtra("FromEditAkun");
        String dataFromCari= i2.getStringExtra("FromCariGuru");
        String dataJadwal= i2.getStringExtra("FromJadwal");
        String dataPemesananDetail= i2.getStringExtra("FromPemesananDetail");

        if (dataPemesananDetail != null && dataPemesananDetail.contentEquals("6")){
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_frame, new PemesananFragment());
            fragmentTransaction2.commitNow();
            bottomNavigationView.setSelectedItemId(R.id.navigation_orders);
        }

        if (dataJadwal != null && dataJadwal.contentEquals("5")){
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_frame, new ProfileFragment());
            fragmentTransaction2.commitNow();
            bottomNavigationView.setSelectedItemId(R.id.navigation_users);
        }

        if (dataFromCari != null && dataFromCari.contentEquals("4")){
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_frame, new HomeFragment());
            fragmentTransaction2.commitNow();
            bottomNavigationView.setSelectedItemId(R.id.navigation_homes);
        }

        if (dataFromEditAkun != null && dataFromEditAkun.contentEquals("3")){
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_frame, new ProfileFragment());
            fragmentTransaction2.commitNow();
            bottomNavigationView.setSelectedItemId(R.id.navigation_users);
        }

        if (dataFromResetPwd != null && dataFromResetPwd.contentEquals("2")){
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_frame, new ProfileFragment());
            fragmentTransaction2.commitNow();
            bottomNavigationView.setSelectedItemId(R.id.navigation_users);
        }

        if (data != null && data.contentEquals("1")){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_frame, new HomeFragment());
            fragmentTransaction.commitNow();
            bottomNavigationView.setSelectedItemId(R.id.navigation_homes);
        }
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}

