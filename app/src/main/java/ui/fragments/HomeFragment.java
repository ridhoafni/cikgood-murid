package ui.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.DataMatpel;
import com.example.anonymous.cikgood.models.Kabupaten;
import com.example.anonymous.cikgood.models.Tingkatan;
import com.example.anonymous.cikgood.response.ResponseCariGuru;
import com.example.anonymous.cikgood.response.ResponseDataMatpel;
import com.example.anonymous.cikgood.response.ResponseKabupaten;
import com.example.anonymous.cikgood.response.ResponseTingkatan;
import com.example.anonymous.cikgood.rests.ApiClient;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.SessionManager;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.activities.GuruActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private CardView CvCars, CvBikes, CvBluebird, CvOthers;
    Spinner spinnerTingkatan, spinnerDataMatpel, spinnerKabupaten;
    int tingkatan, matpel;
    String kota;
    Button btnCari;
    SliderLayout sliderLayout;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog loading;

    public static final String TINGKATAN = "tingkatan";
    public static final String MATPEL = "matpel";
    public static final String KOTA = "kota";

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
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
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
        spinnerTingkatan  = (Spinner)view.findViewById(R.id.spinnerTingkatan);
        spinnerDataMatpel = (Spinner)view.findViewById(R.id.spinnerMatpel);
        spinnerKabupaten  = (Spinner)view.findViewById(R.id.spinnerKabupaten);
        btnCari = (Button)view.findViewById(R.id.btn_cari);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cariGuru();
            }
        });

        setSliderViews();

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        initSpinnerKabupaten();
        initSpinnerTingkatan();
        return view;
    }

    private void initSpinnerTingkatan(){
        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

        apiService.tingkatanFindAll().enqueue(new Callback<ResponseTingkatan>() {
            @Override
            public void onResponse(Call<ResponseTingkatan> call, Response<ResponseTingkatan> response) {
                System.out.println("Response Tingkatan :"+response);
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Tingkatan> semuaTingkatanItems = response.body().getMaster();
                    List<String> listSpinnerTingkatan = new ArrayList<String>();
                    for (int i = 0; i < semuaTingkatanItems.size(); i++){
                        listSpinnerTingkatan.add(semuaTingkatanItems.get(i).getTingkatan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinnerTingkatan);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTingkatan.setAdapter(adapter);
                    spinnerTingkatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                           requestDetailDosen(selectedName);

//                            Toast.makeText(getActivity(), "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();


                            for (Tingkatan semuaTingkatan : semuaTingkatanItems){


                                if (semuaTingkatan.getTingkatan().equals(spinnerTingkatan.getSelectedItem().toString())){
                                    System.out.println("ID :"+semuaTingkatan.getId());
                                    loadDataMatpel(semuaTingkatan.getId());
                                    tingkatan = semuaTingkatan.getId();
//                                    idMatpel = semuamastermatpel.getIdMasterMatpel();
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTingkatan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataMatpel(Integer id) {
        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

        apiService.dataMatpelFindByIdTingkatan(id).enqueue(new Callback<ResponseDataMatpel>() {
            @Override
            public void onResponse(Call<ResponseDataMatpel> call, Response<ResponseDataMatpel> response) {
                System.out.println("Responnya :"+response);
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<DataMatpel> semuadatadosenItems = response.body().getMaster();
                    List<String> listSpinnerDataMatpel = new ArrayList<String>();
                    for (int i = 0; i < semuadatadosenItems.size(); i++){
                        listSpinnerDataMatpel.add(semuadatadosenItems.get(i).getMatpelDetail());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinnerDataMatpel);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDataMatpel.setAdapter(adapter);

                    spinnerDataMatpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                            Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
                            for (DataMatpel semuadatamatpel : semuadatadosenItems){
                                if (semuadatamatpel.getMatpelDetail().equals(spinnerDataMatpel.getSelectedItem().toString())){
//                                    loadDataMatpel(semuamastermatpel.getIdMasterMatpel());
                                    matpel = Integer.parseInt(semuadatamatpel.getMatpel());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal mengambil data matpel", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataMatpel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerKabupaten(){
//        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

        apiService.getDataKabupaten().enqueue(new Callback<ResponseKabupaten>() {
            @Override
            public void onResponse(Call<ResponseKabupaten> call, Response<ResponseKabupaten> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Kabupaten> semuaKabupatenItems = response.body().getMaster();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuaKabupatenItems.size(); i++){
                        listSpinner.add(semuaKabupatenItems.get(i).getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerKabupaten.setAdapter(adapter);
                    spinnerKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                           requestDetailDosen(selectedName);

//                            Toast.makeText(getActivity(), "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();


                            for (Kabupaten semuaKabupaten : semuaKabupatenItems){


                                if (semuaKabupaten.getName().equals( spinnerKabupaten.getSelectedItem().toString())){
//                                    loadDataMatpel(semuaTingkatan.getId());
                                    kota = semuaKabupaten.getName();
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal mengambil data kabupaten", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKabupaten> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(BerandaFragment.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void cariGuru() {
        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

        System.out.println("Kota :"+kota);
        System.out.println("Tingkatan :"+tingkatan);
        System.out.println("Matpel :"+matpel);

        apiService.cariGuru(tingkatan, matpel, kota).enqueue(new Callback<ResponseCariGuru>() {
            @Override
            public void onResponse(Call<ResponseCariGuru> call, Response<ResponseCariGuru> response) {
                System.out.println("Response Home "+response);
                if (response.isSuccessful()){
                    loading.dismiss();
                    Intent i ;
                    i = new Intent(getActivity(), GuruActivity.class);
                    i.putExtra(GuruActivity.TINGKATAN, tingkatan);
                    i.putExtra(GuruActivity.MATPEL, matpel);
                    i.putExtra(GuruActivity.KOTA, kota);
                    startActivity(i);
                    Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseCariGuru> call, Throwable t) {

            }
        });
    }

}
