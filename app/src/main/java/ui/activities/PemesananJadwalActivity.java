package ui.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PemesananJadwalActivity extends AppCompatActivity {

    public static final String KEY_HARGA            = "harga";
    public static final String KEY_DATA_JADWAL      = "durasi";
    public static final String KEY_DURASI           = "durasi";
    public static final String KEY_MATPEL           = "matpel";
    public static final String KEY_ID_MURID         = "id_guru";
    public static final String KEY_ID_GURU          = "id_murid";
    public static final String KEY_HARGA_TOTAL      = "harga_total";
    public static final String KEY_JML_PEMESANAN    = "jml_pemesanan";
    public static final String KEY_PESAN_TAMBAHAN   = "pesan_tambahan";

    // Declare variable
    private String sesi;
    private int length = 0;
    private double total_harga;
    private String hari_tambah;
    private String hari_tanggal;
    private String selectedChild;
    private double durasi, harga_total;
    private String[] selectedNameMatpel;
    private int tarif, guru_id, jml_pemesanan;
    private int id_guru, harga, jumlah_pesanan, murid_id;
    private String matpel, id_murid, tgl, jadwal, pesan_tambahan;

    // Declare widgate
    ImageView ivBack;
    TextView reList, info;
    EditText etHariDanTgl;
    Button buttonAdd, buttonLanjut;
    DatePickerDialog datePickerDialog;
    ConstraintLayout constraintLayout;
    Spinner SpinnerTextIn, spinnerSesi;
    LinearLayout container, linearLayout;

    /* Snackbar */
    Snackbar snackbar;

    // Array
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterSesi;
    ArrayList<String> arrayListJadwal = new ArrayList<>();

    private static final String[] JAM = new String[]{
            "Pagi (09:00 - 14:00)", "Siang (14:00 - 18:00)", "Malam (18:00 - 22:00)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_jadwal);

        // getIntent from PemesananActivity
        matpel          = getIntent().getStringExtra(KEY_MATPEL);
        pesan_tambahan  = getIntent().getStringExtra(KEY_PESAN_TAMBAHAN);
        tarif           = getIntent().getIntExtra(KEY_HARGA, 0);
        guru_id         = getIntent().getIntExtra(KEY_ID_GURU, 0);
        murid_id        = getIntent().getIntExtra(KEY_ID_MURID, 0);
        durasi          = getIntent().getDoubleExtra(KEY_DURASI, 0);
        jml_pemesanan   = getIntent().getIntExtra(KEY_JML_PEMESANAN, 0);
        total_harga     = getIntent().getDoubleExtra(KEY_HARGA_TOTAL, 0);

        // Log
        Log.d("kambing durasi", ""+durasi);
        Log.d("kambing guru id", ""+guru_id);
        Log.d("kambing murid id", ""+murid_id);
        Log.d("kambing tarif harga", ""+tarif);
        Log.d("kambing tarif matpel", ""+matpel);
        Log.d("kambing total harga", ""+total_harga);
        Log.d("kambing jumlah tarif", ""+jml_pemesanan);
        Log.d("kambing pesan tambahan", ""+pesan_tambahan);

        // Declare Array
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, HARI);
        adapterSesi = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, JAM);

        // Inisialized widget
        info            = (TextView) findViewById(R.id.info);
        buttonAdd       = (Button) findViewById(R.id.btn_add);
        buttonLanjut    = (Button) findViewById(R.id.btn_lanjut);
        ivBack          = (ImageView) findViewById(R.id.iv_back);
        container       = (LinearLayout) findViewById(R.id.container);
        etHariDanTgl    = (EditText) findViewById(R.id.et_tanggal_mulai);
        linearLayout    = (LinearLayout) findViewById(R.id.ticket_checkout_layout2);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout);

        // Spinner jam/waktu
        spinnerSesi = (Spinner) findViewById(R.id.spinner_jams);
        spinnerSesi.setAdapter(adapterSesi);

        // Event back
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Event dialog date
        etHariDanTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Method show dialog date
                showDialogDateTglMasuk();
            }
        });

        /* Set text widget */
        hari_tanggal = etHariDanTgl.getText().toString();

        // Event spinner jam/waktu
        spinnerSesi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sesi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Event button add
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set visibility LinearLayout
                linearLayout.setVisibility(View.VISIBLE);

                // Initialized
                LayoutInflater layoutInflater   = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView              = layoutInflater.inflate(R.layout.row, null);
                TextView tvHari                 = (TextView) addView.findViewById(R.id.tv_hari);
                TextView spinnerSesi            = (TextView) addView.findViewById(R.id.textout);
                TextView etHariTgl              = (TextView) addView.findViewById(R.id.textout2);

                /* Snackbar */
                snackbar = Snackbar.make(constraintLayout, "Berhasil ditambah...", Snackbar.LENGTH_LONG);
                snackbar.show();

                // set text spinner hari dan waktu
                spinnerSesi.setText(sesi);
                etHariTgl.setText(hari_tanggal);
                tvHari.setText(hari_tambah);

                // add all data jadwal to array list
                arrayListJadwal.add(hari_tanggal+"@ "+sesi);

                jadwal = String.valueOf(arrayListJadwal);

                Log.d("ayam array", ""+jadwal);

                // Initialized widget button
                Button buttonRemove = (Button) addView.findViewById(R.id.remove);

                // Event to remove LinearLayout
                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(PemesananJadwalActivity.this);
                        alertdialog.setTitle("Apakah Anda ingin Menghapus Jadwal ?");
                        alertdialog.setMessage("Klik Ya untuk Hapus")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String text= etHariTgl.getText().toString()+"@ "+spinnerSesi.getText().toString();

                                        ((LinearLayout)addView.getParent()).removeView(addView);
                                        int a = 0;
                                        for(int z = 0;z<arrayListJadwal.size();z++){
                                            if (arrayListJadwal.get(z).toString().equals(text)){
                                                arrayListJadwal.remove(z);
                                            }
                                        }

                                        /* Snackbar */
                                        Snackbar snackbar_delete = Snackbar.make(constraintLayout, "Berhasil dihapus...", Snackbar.LENGTH_LONG);
                                        snackbar_delete.show();

                                        // changing color text
                                        snackbar_delete.setActionTextColor(Color.RED);


                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertdialog.create();
                        alertDialog.show();
                    }
                });
                container.addView(addView);
            }
        });

        // Event button next
        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTanggalMulai = etHariDanTgl.getText().toString().trim();
                boolean isEmptyFields   = false;
                boolean isInvalidDouble = false;

                if (TextUtils.isEmpty(inputTanggalMulai)){
                    isEmptyFields = true;
                    etHariDanTgl.setError("Tidak boleh kosong!");
                }

                if (!isEmptyFields && !isInvalidDouble) {
                    Intent pembayaranSelanjutnya = new Intent(PemesananJadwalActivity.this, PemesananLokasiActivity.class);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_TGL, tgl);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_HARGA, tarif);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_MATPEL, matpel);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_DURASI, durasi);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_ID_GURU, guru_id);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_ID_MURID, murid_id);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_SELECTED_JADWAL, jadwal);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_HARGA_TOTAL, total_harga);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_JML_PEMESANAN, jml_pemesanan);
                    pembayaranSelanjutnya.putExtra(PemesananLokasiActivity.KEY_PESAN_TAMBAHAN, pesan_tambahan);
                    startActivity(pembayaranSelanjutnya);
                }
            }
        });
    }

    private void showDialogDateTglMasuk() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(PemesananJadwalActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                Locale local = new Locale("in", "ID");

                newDate.set(Calendar.YEAR, year);
                newDate.set(Calendar.MONTH, monthOfYear);
                newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTanggal    = "EEEE-yyyy-MM-dd";
                String tanggal          = "dd-MM-yyyy";
                String hari             = "EEEE";

                SimpleDateFormat sdfHari    = new SimpleDateFormat(hari, local);
                SimpleDateFormat sdf        = new SimpleDateFormat(formatTanggal, local);
                SimpleDateFormat sdfTanggal = new SimpleDateFormat(tanggal, local);

                hari_tambah = sdfHari.format(newDate.getTime());

                etHariDanTgl.setText(sdf.format(newDate.getTime()));
                hari_tanggal = sdfTanggal.format(newDate.getTime());
                Log.d("kelinci tgl", ""+hari_tanggal);

            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */

        datePickerDialog.show();;
    }

    private void listAllAddView() {
        reList.setText("");

        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View thisChild = container.getChildAt(i);
            reList.append(thisChild + "\n");
            String childTextViewValue = selectedChild;
            reList.append("= " + childTextViewValue + "\n");
        }
    }

}

