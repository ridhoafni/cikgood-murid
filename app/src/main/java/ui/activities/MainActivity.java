package ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ui.fragments.BantuanFragment;
import ui.fragments.BerandaFragment;
import ui.fragments.PemesananFragment;
import com.example.anonymous.cikgood.PlaceAutocompleteActivity;
import ui.fragments.ProfileFragment;
import com.example.anonymous.cikgood.R;

public class MainActivity extends AppCompatActivity{

String gelar;

    private ActionBar toolbar;
    private Button btnAutoComplete, btnojek, btnSPinnerDinamik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAutoComplete = (Button)findViewById(R.id.btn_auto_Complete);
        btnojek = (Button)findViewById(R.id.btn_ojek);
        btnSPinnerDinamik = (Button)findViewById(R.id.btn_spinner_dinamic);

        btnSPinnerDinamik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DinamicSpinnerActivity.class));
            }
        });

        btnAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddRemoveDynamicActivity.class));
            }
        });

        btnojek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OjekActivity.class));
            }
        });

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        } else {
            // Permission has already been granted
            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }

        System.out.println("Glearnya ternyata :"+gelar);
    }

//    public void openAutoPlace(View view) {
//        startActivity(new Intent(this, PlaceAutoCompleteActivity.class));
//    }
//
//    public void openDirectionMap(View view) {
//        startActivity(new Intent(this, DirectionActivity.class));
//    }
//
//    public void openOjek(View view) {
//        startActivity(new Intent(this, OjekActivity.class));
//    }
}
