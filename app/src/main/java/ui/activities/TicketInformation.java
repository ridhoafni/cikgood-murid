package ui.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TicketInformation extends AppCompatActivity {
    Typeface tf1,tf2,tf3;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
    Button btn1;
    LinearLayout l1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_information);

        // fonts

//        tf1 = Typeface.createFromAsset(getAssets(), "fonts/mlight.ttf");
//        tf2 = Typeface.createFromAsset(getAssets(), "fonts/mmedium.ttf");
//        tf3 = Typeface.createFromAsset(getAssets(), "fonts/mregular.ttf");

        // id

        tv1 = (TextView) findViewById(R.id.ticket_information_tv1);
        tv2 = (TextView) findViewById(R.id.ticket_information_tv2);
        tv3 = (TextView) findViewById(R.id.ticket_information_tv3);
        tv4 = (TextView) findViewById(R.id.ticket_information_tv4);
        tv5 = (TextView) findViewById(R.id.ticket_information_tv5);
        tv6 = (TextView) findViewById(R.id.ticket_information_tv6);
        tv7 = (TextView) findViewById(R.id.ticket_information_tv7);
        tv8 = (TextView) findViewById(R.id.ticket_information_tv8);
        tv9 = (TextView) findViewById(R.id.ticket_information_tv9);
        tv10 = (TextView) findViewById(R.id.ticket_information_tv10);
        btn1 = (Button) findViewById(R.id.btn_print);
        l1 = (LinearLayout) findViewById(R.id.ticket_information_l1);


        // set fonts

//        tv1.setTypeface(tf3);
//        tv2.setTypeface(tf1);
//        tv3.setTypeface(tf3);
//        tv4.setTypeface(tf1);
//
//        tv5.setTypeface(tf3);
//        tv6.setTypeface(tf1);
//        tv7.setTypeface(tf3);
//        tv8.setTypeface(tf1);
//        tv9.setTypeface(tf3);
//        tv10.setTypeface(tf1);
//        btn1.setTypeface(tf2);


//        SharedPreferences getPreferenceRegisteCity = getSharedPreferences("city_parent",0);
//        final String my_trip = getPreferenceRegisteCity.getString("name_trip","");
//        final String my_city_trip = getPreferenceRegisteCity.getString("name_city_trip","");
//        final String my_total = getPreferenceRegisteCity.getString("city_total","");
//        final String time_buy = getPreferenceRegisteCity.getString("time_buy","");
//        final String date_buy = getPreferenceRegisteCity.getString("date_buy","");
//        final String expired_buy = getPreferenceRegisteCity.getString("expired_buy","");
//        final String departure_buy = getPreferenceRegisteCity.getString("departure_buy","");
//        final String maskapai_buy = getPreferenceRegisteCity.getString("maskapai_buy","");
//        tv4.setText(my_total+" Tickets");
//        tv6.setText(date_buy);
//        tv8.setText(time_buy);
//
//        tv2.setText(my_trip);
//        tv3.setText(my_city_trip);
//        tv10.setText("Maskapai : "+ maskapai_buy +".\nKeberangkatan pesawat : "+ departure_buy +".\nMasa berlaku Tiket sampai: "+expired_buy);

        l1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Runnable() {
                    int interfal = 500;
                    @Override
                    public void run() {
                        new CountDownTimer(1500, 1500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                btn1.setText("PRINTING...");
                            }
                            @Override
                            public void onFinish() {
                                btn1.setText("PRINT TICKET");
                            }
                        }.start();
                    }
                }.run();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Bitmap bitmap = takeScreenshot();
                        saveBitmap(bitmap);
                        Toast.makeText(TicketInformation.this, "Successfull Print Ticket on Storage\nPlease check your Gallery or Storage", Toast.LENGTH_SHORT).show();
                    }
                },1500);
            }
        });


        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        // change color in primaryDark

        // change icon color status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar


    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory()+"/tiketsaya_print.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }

    }
}