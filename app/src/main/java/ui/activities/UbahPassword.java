package ui.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;

public class UbahPassword extends AppCompatActivity {

    Button btnUbahPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubah_password);

        btnUbahPwd = findViewById(R.id.btn_ubah_pwd);

        btnUbahPwd.setOnClickListener(new View.OnClickListener() {
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
                                btnUbahPwd.setText("MERUBAH...");
                            }
                            @Override
                            public void onFinish() {
                                btnUbahPwd.setText("UBAH PASSWORD");
                            }
                        }.start();
                    }
                }.run();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
//                        Bitmap bitmap = takeScreenshot();
//                        saveBitmap(bitmap);
//                        Toast.makeText(TicketInformation.this, "Successfull Print Ticket on Storage\nPlease check your Gallery or Storage", Toast.LENGTH_SHORT).show();
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
}
