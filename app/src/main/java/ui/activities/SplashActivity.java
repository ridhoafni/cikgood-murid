package ui.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;

public class SplashActivity extends AppCompatActivity {
    private TextView tv, tv_slogan;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getColor(R.color.colorAccent));
        setContentView(R.layout.activity_splash);
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);
        tv_slogan = (TextView) findViewById(R.id.tv_sloan);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransation);
        Animation myanim2 = AnimationUtils.loadAnimation(this, R.anim.smaltobig);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim2);
        tv_slogan.startAnimation(myanim);
        iv.startAnimation(myanim2);
        final Intent i = new Intent(this, SliderActivity.class);
//        final Intent i = new Intent(this, AmSliderTransformation.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

        // change color in primaryDark
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        // change color in primaryDark

        // change icon color status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // change icon color status bar

    }
}
