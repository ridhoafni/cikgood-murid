package ui.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.models.Matpel;
import com.example.anonymous.cikgood.response.ResponseMatpel;
import com.example.anonymous.cikgood.rests.ApiInterface;
import com.example.anonymous.cikgood.utils.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.activities.GuruActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ImageView ImgPaud, ImgSd, ImgSmp, ImgSma;
    private Context mContext;
    EditText TxtNama, TxtTingkatan;
    LayoutInflater inflater;
    View dialogView;
    AlertDialog.Builder dialog;
    Spinner SpinnerJk, SpinnerMateri;
    public String tingkatan;
    Context context;
    ApiInterface mApiInterface;
    ProgressDialog loading;

//    String[] jenisKelamin   = {"Laki-Laki", "Perempuan"};
    String[] materi         = {"A", "B"};

    public static final String EXTRA_TINGKATAN = "extra_jenjang";

    @Override
    public void onAttach(final Activity activity){
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ImgPaud     = (ImageView) view.findViewById(R.id.iv_paud);
        ImgSd       = (ImageView) view.findViewById(R.id.iv_sd);
        ImgSmp      = (ImageView) view.findViewById(R.id.iv_smp);
        ImgSma      = (ImageView) view.findViewById(R.id.iv_sma);

        ImgPaud.setOnClickListener(this);
        ImgSd.setOnClickListener(this);
        ImgSmp.setOnClickListener(this);
        ImgSma.setOnClickListener(this);

        mApiInterface = UtilsApi.getAPIService();

        return view;
    }

    private void initSpinnerDosen(){

        mApiInterface.matpelFindAll().enqueue(new Callback<ResponseMatpel>() {
            @Override
            public void onResponse(Call<ResponseMatpel> call, Response<ResponseMatpel> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<Matpel> matpels = response.body().getMaster();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < matpels.size(); i++){
                        listSpinner.add(matpels.get(i).getMatpel());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpinnerJk.setAdapter(adapter);
                } else {
                    loading.dismiss();
//                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMatpel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showForm() {
        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        dialog      = new AlertDialog.Builder(mContext);
        inflater    = getLayoutInflater();
        dialogView  = inflater.inflate(R.layout.form_search, null);

        // set title dialog
        dialog.setTitle("Cari Guru Privat");

        // set pesan dari dialog
        dialog
        .setView(dialogView)
        .setCancelable(false)
        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // jika tombol diklik, maka akan menutup activity ini
                SearchFragment.this.getActivity().finish();
            }
        })
        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // jika tombol ini diklik, akan menutup dialog
                // dan tidak terjadi apa2
                dialog.cancel();
            }
        });

        TxtNama        = (EditText) dialogView.findViewById(R.id.txt_nama);
        TxtTingkatan   = (EditText) dialogView.findViewById(R.id.txt_tingkatan);
        SpinnerMateri  = (Spinner) dialogView.findViewById(R.id.spinner_materi);
        SpinnerJk      = (Spinner) dialogView.findViewById(R.id.spinner_jk);

        List<String> jk = new ArrayList<String>();
        jk.add("Laki-Laki");
        jk.add("Perempuan");

        SpinnerMateri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
//                Toast.makeText(mContext, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TxtTingkatan.setText(tingkatan);

        ArrayAdapter<String> adapterJK = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, jk);
        adapterJK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerJk.setAdapter(adapterJK);

        mApiInterface.matpelFindAll().enqueue(new Callback<ResponseMatpel>() {
            @Override
            public void onResponse(Call<ResponseMatpel> call, Response<ResponseMatpel> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();

                    List<Matpel> matpelItems = response.body().getMaster();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < matpelItems.size(); i++){
                        listSpinner.add(matpelItems.get(i).getMatpel());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, listSpinner);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpinnerMateri.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMatpel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        String jenjang;
        String matpel;
        switch (v.getId()){
            case R.id.iv_paud:
                tingkatan  = "Paud";
                showForm();
                break;

            case R.id.iv_sd:
                tingkatan  = "SD";
                showForm();
                break;

            case R.id.iv_smp:
                i = new Intent(SearchFragment.this.getActivity(), GuruActivity.class);
                startActivity(i);
                break;

            case R.id.iv_sma:
                tingkatan  = "SMA";

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
