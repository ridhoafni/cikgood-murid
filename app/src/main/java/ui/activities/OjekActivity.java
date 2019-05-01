package ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.models.ListLocationModel;
import com.example.anonymous.cikgood.models.LocationModel;
import com.example.anonymous.cikgood.network.ApiClient;
import com.example.anonymous.cikgood.network.ApiServices;
import com.example.anonymous.cikgood.network.InitLibrary;
import com.example.anonymous.cikgood.response.Distance;
import com.example.anonymous.cikgood.response.Duration;
import com.example.anonymous.cikgood.response.LegsItem;
import com.example.anonymous.cikgood.response.ResponseRoute;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OjekActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String KEY_TGL                  = "tgl";
    public static final String KEY_HARGA                = "harga";
    public static final String KEY_MATPEL               = "matpel";
    public static final String KEY_DURASI               = "durasi";
    public static final String KEY_SELECTED_JADWAL      = "jadwal";
    public static final String KEY_ID_GURU              = "id_guru";
    public static final String KEY_ID_MURID             = "id_murid";
    public static final String KEY_HARGA_TOTAL          = "harga_total";
    public static final String KEY_JML_PEMESANAN        = "jml_pemesanan";
    public static final String KEY_ADDRESS              = "alamat_lengkap";
    public static final String KEY_PESAN_TAMBAHAN       = "pesan_tambahan";

    // declare variable
    private String alamat;
    private GoogleMap mMap;
    private Button btnPosisi;
    private LinearLayout infoPanel;
    public LatLng pekanbaru = null;
    public LatLng pickUpLatLng = null;
    double lat_awal, long_awal;
    public LatLng locationLatLng = null;
    private TextView tvPrice, tvDistance;
    private CameraPosition cameraPosition;
    private int tarif, jml_pemesanan, guru_id, murid_id;
    private double durasi, total_harga, harga;
    private String tgl, jadwal, matpel, pesan_tambahan;
    private MarkerOptions markerOptions = new MarkerOptions();
    private String API_KEY = "AIzaSyCbX09ztk-EA8E3_HvCfTY8uRF5y0Bc3q8";

    // declare variable
    public int REQUEST_CODE  = 0;
    public static final int PICK_UP  = 0;
    public static final int DEST_LOC = 1;
    private TextView tvPickUpFrom, tvDestLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ojek);

        // Inisialisasi Widget
        wigetInit();

        infoPanel.setVisibility(View.GONE);
        // Event OnClick
        tvPickUpFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jalankan Method untuk menampilkan Place Auto Complete
                // Bawa constant PICK_UP
                showPlaceAutoComplete(PICK_UP);
            }
        });

        // method to collect getIntent from PemesananLokasiActivity
        getDataPutExtraPemesananLokasi();
    }

    private void getDataPutExtraPemesananLokasi() {

        // getIntent
        tgl           = getIntent().getStringExtra(KEY_TGL);
        matpel        = getIntent().getStringExtra(KEY_MATPEL);
        alamat        = getIntent().getStringExtra(KEY_ADDRESS);
        pesan_tambahan= getIntent().getStringExtra(KEY_PESAN_TAMBAHAN);
        jadwal        = getIntent().getStringExtra(KEY_SELECTED_JADWAL);
        murid_id      = getIntent().getIntExtra(KEY_ID_MURID, murid_id);
        tarif         = getIntent().getIntExtra(KEY_HARGA, 0);
        harga         = getIntent().getIntExtra(KEY_HARGA, 0);
        guru_id       = getIntent().getIntExtra(KEY_ID_GURU, 0);
        durasi        = getIntent().getDoubleExtra(KEY_DURASI, 0);
        jml_pemesanan = getIntent().getIntExtra(KEY_JML_PEMESANAN, 0);
        total_harga   = getIntent().getDoubleExtra(KEY_HARGA_TOTAL, 0);

        Log.d("maps tgl", ""+tgl);
        Log.d("maps tarif", ""+tarif);
        Log.d("maps matpel", ""+matpel);
        Log.d("maps durasi", ""+durasi);
        Log.d("maps jadwal", ""+jadwal);
        Log.d("maps guru id", ""+guru_id);
        Log.d("maps tarif harga", ""+tarif);
        Log.d("maps murid id", ""+murid_id);
        Log.d("maps total harga", ""+total_harga);
        Log.d("maps jumlah tarif", ""+jml_pemesanan);
        Log.d("maps pesan tambahan", ""+pesan_tambahan);
    }


    private void getCurrentLocation() {
        // GET CURRENT LOCATION
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());

                    // method ambil lokasi dari maps
                    getAddress(getApplicationContext(), location.getLatitude(), location.getLongitude());
                    pickUpLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }

    // Method untuk Inisilisasi Widget agar lebih rapih
    private void wigetInit() {

        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // findViewById
        btnPosisi       = (Button) findViewById(R.id.btnNext);
        tvPrice         = (TextView)findViewById(R.id.tvPrice);
        tvDistance      = (TextView)findViewById(R.id.tvDistance);
        tvPickUpFrom    = (TextView)findViewById(R.id.tvPickUpFrom);
        infoPanel       = (LinearLayout) findViewById(R.id.infoPanel);
    }

    // Method menampilkan input Place Auto Complete
    private void showPlaceAutoComplete(int typeLocation) {
        // isi RESUT_CODE tergantung tipe lokasi yg dipilih.
        // titik jmput atau tujuan
        REQUEST_CODE = typeLocation;
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale("ID"); // <---- your target language
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        // Filter hanya tmpat yg ada di Indonesia
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("ID").build();
        try {
            // Intent untuk mengirim Implisit Intent
            Intent mIntent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(this);
            // jalankan intent impilist
            startActivityForResult(mIntent, REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace(); // cetak error
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace(); // cetak error
            // Display Toast
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show();
        }

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
            // Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAddress(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            // Object GoeCode
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

            // set current location true
//            mMap.setMyLocationEnabled(true);

            // GeoCode sesuai Lat & Lng
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null && addresses.size() > 0) {

                // Object LatLng
                LatLng x = new LatLng(LATITUDE, LONGITUDE);

                // get value from object List<Address>
                lat_awal = addresses.get(0).getLatitude();
                long_awal = addresses.get(0).getLongitude();
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String address = addresses.get(0).getAddressLine(0);

                // Marker Option
                mMap.addMarker(markerOptions);
                markerOptions.position(x);
                markerOptions.title(city);
                markerOptions.snippet(address);
                cameraPosition = new CameraPosition.Builder().target(x).zoom(50).build();
                CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cu);

//              mMap.addMarker(new MarkerOptions().position(x).title(city).snippet(address));

                // set value
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                System.out.println("Lat awal"+lat_awal);
                System.out.println("Lng awal"+long_awal);

                putExtraDataToPemesananLokasi(lat_awal, long_awal);

                // setText
                tvPrice.setText(knownName);
                tvDistance.setText(address);
                tvPickUpFrom.setText(address);

                // set nilai alamat
                alamat = tvPickUpFrom.getText().toString();

                /** END
                 * Logic untuk membuat layar berada ditengah2 dua koordinat
                 */
                // Tampilkan info panel

                infoPanel.setVisibility(View.VISIBLE);
                mMap.setPadding(10, 180, 10, 180);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    // Method binding data to PemesananLokasiActivity
    private void putExtraDataToPemesananLokasi(double lat_awal, double long_awal) {

        // Event binding data to PemesananLokasiActivity
        btnPosisi.setOnClickListener(new View.OnClickListener(){
            double lat = lat_awal;
            double lng = long_awal;
            @Override
            public void onClick(View v) {
                Log.d("Lat pemesanan", ""+lat);
                Log.d("Lng pemesanan", ""+lng);
                Log.d("maps jadwal2", ""+jadwal);

                Intent intent;
                intent = new Intent(OjekActivity.this, PemesananLokasiActivity.class);
                intent.putExtra(PemesananLokasiActivity.KEY_TGL, tgl);
                intent.putExtra(PemesananLokasiActivity.KEY_HARGA, tarif);
                intent.putExtra(PemesananLokasiActivity.KEY_LATITUDE, lat);
                intent.putExtra(PemesananLokasiActivity.KEY_LONGITUDE, lng);
                intent.putExtra(PemesananLokasiActivity.KEY_MATPEL, matpel);
                intent.putExtra(PemesananLokasiActivity.KEY_DURASI, durasi);
                intent.putExtra(PemesananLokasiActivity.KEY_ADDRESS, alamat);
                intent.putExtra(PemesananLokasiActivity.KEY_ID_GURU, guru_id);
                intent.putExtra(PemesananLokasiActivity.KEY_ID_MURID, murid_id);
                intent.putExtra(PemesananLokasiActivity.KEY_SELECTED_JADWAL, jadwal);
                intent.putExtra(PemesananLokasiActivity.KEY_HARGA_TOTAL, total_harga);
                intent.putExtra(PemesananLokasiActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                intent.putExtra(PemesananLokasiActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // Pastikan Resultnya OK
        if (resultCode == RESULT_OK) {
            // Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
            // Tampung Data tempat ke variable
            Place placeData = PlaceAutocomplete.getPlace(this, data);

            if (placeData.isDataValid()) {
                // Show in Log Cat
                Log.d("autoCompletePlace Data", placeData.toString());

                // Dapatkan Detail
                String placeAddress = placeData.getAddress().toString();
                LatLng placeLatLng = placeData.getLatLng();
                String placeName = placeData.getName().toString();

                // Cek user milih titik jemput atau titik tujuan
                switch (REQUEST_CODE) {
                    case PICK_UP:
                        // Set ke widget lokasi asal
                        tvPickUpFrom.setText(placeAddress);
                        pickUpLatLng = placeData.getLatLng();
                        break;
                    case DEST_LOC:
                        // Set ke widget lokasi tujuan
                        tvDestLocation.setText(placeAddress);
                        locationLatLng = placeData.getLatLng();
                        break;
                }
                // Jalankan Action Route
                if (pickUpLatLng != null){
                    getMarker(placeLatLng , placeName, placeAddress);
                }
                    getMarker(placeLatLng , placeName, placeAddress);

            } else {
                // Data tempat tidak valid
                Toast.makeText(this, "Invalid Place !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getMarker(LatLng placeLatLng, String placeName, String placeAddress) {
        mMap.clear();
        mMap.addMarker(markerOptions);
        markerOptions.position(placeLatLng);
        markerOptions.title(placeName);
        markerOptions.snippet(placeAddress);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        cameraPosition = new CameraPosition.Builder().target(placeLatLng).zoom(100).build();
        mMap.animateCamera(cu);
        mMap.setMyLocationEnabled(true);

        /** END
         * Logic untuk membuat layar berada ditengah2 dua koordinat
         */
        // Tampilkan info panel

        String alamat_lengkap = placeName+", "+placeAddress;
        tvDistance.setText(placeAddress);
        tvPrice.setText(placeName);
        alamat = alamat_lengkap;
        infoPanel.setVisibility(View.VISIBLE);
        mMap.setPadding(10, 180, 10, 180);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getCurrentLocation();

        mMap.setPadding(10, 180, 10, 10);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        pekanbaru = new LatLng(3.597031, 98.678513);
        markerOptions.position(pekanbaru);
        cameraPosition = new CameraPosition.Builder().target(pekanbaru).zoom(100).build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cu);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pekanbaru));

    }

    private void actionRoute(LatLng placeLatLng, int requestCode) {
        String lokasiAwal = pickUpLatLng.latitude + "," + pickUpLatLng.longitude;
        String lokasiAkhir = locationLatLng.latitude + "," + locationLatLng.longitude;

        // Clear dulu Map nya
        mMap.clear();
        // Panggil Retrofit
        ApiServices api = InitLibrary.getInstance();
        // Siapkan request
        Call<ResponseRoute> routeRequest = api.request_route(lokasiAwal, lokasiAkhir, API_KEY);
        // kirim request
        routeRequest.enqueue(new Callback<ResponseRoute>() {
            @Override
            public void onResponse(Call<ResponseRoute> call, Response<ResponseRoute> response) {
                System.out.println("Response Maps:"+response);
                if (response.isSuccessful()){
                    // tampung response ke variable
                    ResponseRoute dataDirection = response.body();
                    System.out.println("Response dataDirection :"+dataDirection);

                    // Tambah Marker
                    mMap.addMarker(new MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"));
                    mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Lokasi Akhir"));
                    // Dapatkan jarak dan waktu

                    LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();
                    latLongBuilder.include(pickUpLatLng);
                    latLongBuilder.include(locationLatLng);

                    // Bounds Coordinata
                    LatLngBounds bounds = latLongBuilder.build();

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int paddingMap = (int) (width * 0.2); //jarak dari
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap);
                    mMap.animateCamera(cu);

                    /** END
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                    // Tampilkan info panel
                    infoPanel.setVisibility(View.VISIBLE);
                    mMap.setPadding(10, 180, 10, 180);
                }
            }

            @Override
            public void onFailure(Call<ResponseRoute> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addMarker(LatLng latlng, final String title) {
        markerOptions.position(latlng);
        markerOptions.title(title);
        mMap.addMarker(markerOptions);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}