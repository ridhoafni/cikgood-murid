package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.RecyclerViewAdapter;
import com.example.anonymous.cikgood.adapters.GuruAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.CariGuru;
import com.example.anonymous.cikgood.models.Guru;
import com.example.anonymous.cikgood.response.ResponseCariGuru;
import com.example.anonymous.cikgood.response.ResponseGuru;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.ItemClickSupport;
import com.example.anonymous.cikgood.utils.RecyclerItemClickListener;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ui.fragments.HomeFragment;
import ui.fragments.SearchFragment;

public class GuruActivity extends AppCompatActivity {
    private static final String KEY_ID_GURU = "key_id_guru";
    public static final String URL = ServerConfig.API_ENDPOINT;
    private List<CariGuru> datas = new ArrayList<>();
    private GuruAdapter guruAdapter;
    int tingkatan, matpel;
    String kota;

    public static final String TINGKATAN = "tingkatan";
    public static final String MATPEL = "matpel";
    public static final String KOTA = "kota";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;
    Context context;
    ApiInterface apiService;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Guru Les Privat");

        guruAdapter = new GuruAdapter(this, datas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(guruAdapter);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        loadDataGuru();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        HomeFragment homeFragment = new HomeFragment();

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(GuruActivity.this, NavigationView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHomeFragment() {
        HomeFragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadDataGuru() {
        tingkatan = getIntent().getIntExtra(TINGKATAN, 0);
        matpel = getIntent().getIntExtra(MATPEL, 0);
        kota = getIntent().getStringExtra(KOTA);

        System.out.println("Kota Guru :"+kota);
        System.out.println("Tingkatan Guru :"+tingkatan);
        System.out.println("Matpel Guru :"+matpel);

        apiService.dapatGuru(tingkatan, matpel, kota).enqueue(new Callback<ResponseCariGuru>() {
            @Override
            public void onResponse(Call<ResponseCariGuru> call, Response<ResponseCariGuru> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        datas = response.body().getMaster();
                        guruAdapter = new GuruAdapter(GuruActivity.this, datas);
                        recyclerView.setAdapter(guruAdapter);
                    }
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseCariGuru> call, Throwable t) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
