package ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.adapters.HistoriPemesananAdapter;
import com.example.anonymous.cikgood.adapters.PemesananAdapter;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Pesanan extends Fragment {

    private Context context;
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private List<Pemesanan> dataPemesanan = new ArrayList<>();
    private PemesananAdapter pemesananAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private SessionManager sessionManager;

    public Pesanan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.pemesanan, container, false);

        recyclerView = view.findViewById(R.id.rv_history_pemesanan);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        sessionManager = new SessionManager(getActivity());

        pemesananAdapter = new PemesananAdapter(getActivity(), dataPemesanan);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pemesananAdapter);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        loadDataPemesanan();

        return view;
    }

    private void loadDataPemesanan() {
        int id = Integer.parseInt(sessionManager.getMuridProfile().get(SessionManager.ID_MURID));
        Log.d("shimmer id", ""+id);
        apiService.findPemesananDisetujui(id).enqueue(new Callback<ResponsePemesanan>() {
            @Override
            public void onResponse(Call<ResponsePemesanan> call, Response<ResponsePemesanan> response) {
//                progressBar.setVisibility(View.GONE);
                System.out.println("Response Guru :"+response);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        dataPemesanan = response.body().getMaster();
                        pemesananAdapter = new PemesananAdapter(getActivity(), dataPemesanan);
                        recyclerView.setAdapter(pemesananAdapter);
                    }
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
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
