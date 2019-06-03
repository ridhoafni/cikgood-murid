package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.HistoriPemesananAdapter;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.Pemesanan;
import com.example.anonymous.cikgood.response.ResponsePemesanan;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalActivity extends AppCompatActivity {

    private Context context;
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private List<Pemesanan> dataPemesanan = new ArrayList<>();
    private HistoriPemesananAdapter historiPemesananAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        recyclerView = findViewById(R.id.rv_history_pemesanan);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        sessionManager = new SessionManager(this);

//        historiPemesananAdapter = new HistoriPemesananAdapter(this, dataPemesanan);
//        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(historiPemesananAdapter);
//        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        loadDataHistoryPemesanan();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(JadwalActivity.this, NavigationView.class);
                intentProfile.putExtra("FromJadwal", "5");
                startActivity(intentProfile);
            }
        });
    }

    private void loadDataHistoryPemesanan() {
        int id = Integer.parseInt(sessionManager.getMuridProfile().get(SessionManager.ID_MURID));
        Log.d("shimmer id", ""+id);
        apiService.findPemesananDiproses(id).enqueue(new Callback<ResponsePemesanan>() {
            @Override
            public void onResponse(Call<ResponsePemesanan> call, Response<ResponsePemesanan> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        dataPemesanan = response.body().getMaster();
//                        historiPemesananAdapter = new HistoriPemesananAdapter(JadwalActivity.this, dataPemesanan);
//                        recyclerView.setAdapter(historiPemesananAdapter);
                        // Stopping Shimmer Effect's animation after data is loaded to ListView
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponsePemesanan> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
