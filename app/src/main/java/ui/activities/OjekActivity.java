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
import com.google.maps.android.PolyUtil;
//import com.khilman.www.cikgood.network.ApiServices;
//import com.khilman.www.cikgood.network.InitLibrary;
//import com.khilman.www.cikgood.response.Distance;
//import com.khilman.www.cikgood.response.Duration;
//import com.khilman.www.cikgood.response.LegsItem;
//import com.khilman.www.cikgood.response.ResponseRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OjekActivity extends AppCompatActivity implements OnMapReadyCallback {

//    String photo, gelar, univ, jurusan, nama;
//    int id;
//    public OjekActivity(int id, String photo, String nama, String gelar, String univ, String jurusan){
//        this.id = id;
//        this.photo = photo;
//        this.nama = nama;
//        this.gelar = gelar;
//        this.univ = univ;
//        this.jurusan = jurusan;
//    }
    public static final String KEY_ID_GURU = "id_guru";
    public static final String KEY_PHOTO_GURU = "photo_profile";
    public static final String KEY_NAMA_GURU = "nama";
    public static final String KEY_JURUSAN_GURU = "jurusan";
    public static final String KEY_UNIV_GURU = "nama_institusi";
    public static final String KEY_GELAR_GURU = "gelar";

    private GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    double lat_awal;
    double long_awal;
    String alamat;
    Spinner spinnerDurasiPertemuan, spinnerDurasiPertemuanJam, spinnerMatpel;


    private String API_KEY = "AIzaSyCbX09ztk-EA8E3_HvCfTY8uRF5y0Bc3q8";

    public LatLng pickUpLatLng = null;
    public LatLng locationLatLng = null;
    public LatLng pekanbaru = null;

    private TextView tvStartAddress, tvEndAddress;
    private TextView tvPrice, tvDistance;
    private Button btnPosisi;
    private LinearLayout infoPanel;
    int id_guru;
    String nama, photo, jurusan, univ, gelar;

    // Deklarasi variable
    private TextView tvPickUpFrom, tvDestLocation;

    public static final int PICK_UP = 0;
    public static final int DEST_LOC = 1;
    private static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ojek);
//        getSupportActionBar().setTitle("Ojek Hampir Online");
        getCurrentLocation();
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
//        // Event OnClick
//        tvDestLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Jalankan Method untuk menampilkan Place Auto Complete
//                // Bawa constant DEST_LOC
//                showPlaceAutoComplete(DEST_LOC);
//            }
//        });



    }



    private void getCurrentLocation() {
        // GET CURRENT LOCATION
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());

                    getAddress(getApplicationContext(), location.getLatitude(), location.getLongitude());

                    //LatLng mCircle = new LatLng(0.470135,101.364275);

                    //Toast.makeText(OjekActivity.this,
                    // "Lat : " + location.getLatitude() + " Long : " + location.getLongitude() + " distance : " + distance[0],
                    //   Toast.LENGTH_LONG).show();

                    pickUpLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }

    // Method untuk Inisilisasi Widget agar lebih rapih
    private void wigetInit() {
        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        infoPanel = findViewById(R.id.infoPanel);
        spinnerDurasiPertemuan = (Spinner)findViewById(R.id.spinner_durasi_pertemuan);
        spinnerDurasiPertemuanJam = (Spinner)findViewById(R.id.spinner_durasi_pertemuan_jam);
        spinnerMatpel = (Spinner)findViewById(R.id.spinner_matpel);
        // Widget
        tvPickUpFrom = findViewById(R.id.tvPickUpFrom);
//        tvDestLocation = findViewById(R.id.tvDestLocation);

        tvPrice = findViewById(R.id.tvPrice);
        tvDistance = findViewById(R.id.tvDistance);
        btnPosisi = findViewById(R.id.btnNext);

        System.out.println("ALamatnya mungkin :"+alamat);
//        System.out.println("Gelarnya mungkin :"+gelar);

        id_guru = getIntent().getIntExtra(KEY_ID_GURU, 0);
        photo = getIntent().getStringExtra(KEY_PHOTO_GURU);
        nama = getIntent().getStringExtra(KEY_NAMA_GURU);
        gelar = getIntent().getStringExtra(KEY_GELAR_GURU);
        univ = getIntent().getStringExtra(KEY_UNIV_GURU);
        jurusan = getIntent().getStringExtra(KEY_JURUSAN_GURU);

        System.out.println("GURUS :"+id_guru);
        System.out.println("GURUS :"+photo);
        System.out.println("GURUS :"+nama);
        System.out.println("GURUS :"+gelar);
        System.out.println("GURUS :"+univ);
        System.out.println("GURUS :"+jurusan) ;

        btnPosisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ambil_posisi;
                ambil_posisi = new Intent(OjekActivity.this, PemesananActivity.class);
                ambil_posisi.putExtra(PemesananActivity.KEY_ID_GURU, id_guru);
                ambil_posisi.putExtra(PemesananActivity.KEY_ADDRESS, alamat);
                ambil_posisi.putExtra(PemesananActivity.KEY_PHOTO_GURU, photo);
                ambil_posisi.putExtra(PemesananActivity.KEY_NAMA_GURU, nama);
                ambil_posisi.putExtra(PemesananActivity.KEY_GELAR_GURU, gelar);
                ambil_posisi.putExtra(PemesananActivity.KEY_JURUSAN_GURU, jurusan);
                ambil_posisi.putExtra(PemesananActivity.KEY_UNIV_GURU, univ);
                startActivity(ambil_posisi);
            }
        });
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
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

            mMap.setMyLocationEnabled(true);

            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                lat_awal = addresses.get(0).getLatitude();
                long_awal = addresses.get(0).getLongitude();
                LatLng x = new LatLng(lat_awal, long_awal);
                markerOptions.position(x);
                markerOptions.title(city);
                markerOptions.snippet(address);
                mMap.addMarker(markerOptions);
                cameraPosition = new CameraPosition.Builder().target(x).zoom(50).build();
                CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cu);
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                System.out.println("Lat awal"+lat_awal);
                System.out.println("Lng awal"+long_awal);
                Log.d("TAG", "getAddress:  address" + address);
                Log.d("TAG", "getAddress:  city" + city);
                Log.d("TAG", "getAddress:  state" + state);
                Log.d("TAG", "getAddress:  postalCode" + postalCode);
                Log.d("TAG", "getAddress:  knownName" + knownName);
                tvDistance.setText(address);
                tvPrice.setText(knownName);
                tvPickUpFrom.setText(address);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // Pastikan Resultnya OK
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
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
                        System.out.println("PickUpLatLng: "+pickUpLatLng);
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
//                if (pickUpLatLng != null && locationLatLng != null) {
//                    actionRoute(placeLatLng, REQUEST_CODE);
//                }

            } else {
                // Data tempat tidak valid
                Toast.makeText(this, "Invalid Place !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getMarker(LatLng placeLatLng, String placeName, String placeAddress) {
        mMap.clear();
        markerOptions.position(pickUpLatLng);
        markerOptions.title(placeName);
        markerOptions.snippet(placeAddress);
        mMap.addMarker(markerOptions);
        cameraPosition = new CameraPosition.Builder().target(pickUpLatLng).zoom(100).build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
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

        // Add a marker in Sydney and move the camera
        pekanbaru = new LatLng(3.597031, 98.678513);


//        cameraPosition = new CameraPosition.Builder().target(pekanbaru).zoom(100).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        markerOptions.position(pekanbaru);
        cameraPosition = new CameraPosition.Builder().target(pekanbaru).zoom(100).build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cu);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(pekanbaru));

        mMap.setPadding(10, 180, 10, 10);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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

//                    LegsItem dataLegs = dataDirection.getRoutes().get(0).getLegs().get(0);

                    // Dapatkan garis polyline
//                    String polylinePoint = dataDirection.getRoutes().get(0).getOverviewPolyline().getPoints();
                    // Decode
//                    List<LatLng> decodePath = PolyUtil.decode(polylinePoint);
                    // Gambar garis ke maps
//                    mMap.addPolyline(new PolylineOptions().addAll(decodePath)
//                            .width(8f).color(Color.argb(255, 56, 167, 252)))
//                            .setGeodesic(true);

                    // Tambah Marker
                    mMap.addMarker(new MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"));
                    mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Lokasi Akhir"));
                    // Dapatkan jarak dan waktu
//                    Distance dataDistance = dataLegs.getDistance();
//                    Duration dataDuration = dataLegs.getDuration();

                    // Set Nilai Ke Widget
//                    double price_per_meter = 250;
//                    double priceTotal = dataDistance.getValue() * price_per_meter; // Jarak * harga permeter

//                    tvDistance.setText(dataDistance.getText());
//                    tvPrice.setText(String.valueOf(priceTotal));
                    /** START
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

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

//    private void getMarkers() {
//        ApiServices api = InitRetrofit.getInstance();
//        // Siapkan request
//        Call<ResponseRawan> routeRequest = api.rawan();
//
//        Toast.makeText(OjekActivity.this, "marker", Toast.LENGTH_SHORT).show();
//        // kirim request
//        routeRequest.enqueue(new Callback<ResponseRawan>() {
//            @Override
//            public void onResponse(Call<ResponseRawan> call, Response<ResponseRawan> response) {
//
//                if (response.isSuccessful()){
//
//                    Toast.makeText(OjekActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
//                    // tampung response ke variable
//                    ResponseRawan dataDirection = response.body();
//
////                    try {
//                    List<RawanItem> Rawan = response.body().getRawan();
////                        JSONArray jsonArray = new JSONArray(response.body().getRawan());
//                    //LegsItem dataLegs = dataDirection.getRoutes().get(0).getLegs().get(0);
//                    for (int i = 0; i < Rawan.size(); i++) {
////                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        title = Rawan.get(i).getNama().toString();
//                        Toast.makeText(OjekActivity.this, title, Toast.LENGTH_SHORT).show();
//                        latLng = new LatLng(
//                                Double.valueOf(Rawan.get(i).getLatitude().toString() ),
//                                Double.parseDouble(Rawan.get(i).getLongitude())
//                        );
//
//                        // Menambah data marker untuk di tampilkan ke google map
//                        addMarker(latLng, title);
//                    }
////                    } catch (JSONException e){
////                        e.printStackTrace();
////
////                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseRawan> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//    }
}