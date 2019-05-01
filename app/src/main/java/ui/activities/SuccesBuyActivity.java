package ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;

public class SuccesBuyActivity extends AppCompatActivity {

    Typeface tf1,tf2,tf3;
    Button btn1,btn2;
    TextView tv1,tv2;
    ImageView img1;
    Animation an1,an2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy);

        // fonts

//        tf1 = Typeface.createFromAsset(getAssets(), "font/mlight.ttf");
//        tf2 = Typeface.createFromAsset(getAssets(), "font/mmedium.ttf");
//        tf3 = Typeface.createFromAsset(getAssets(), "font/mregular.ttf");
//
//        // anim
//
        an1 = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        an2 = AnimationUtils.loadAnimation(this, R.anim.app_splash2);

        // id

        tv1 = (TextView) findViewById(R.id.ticket_success_tv1);
        tv2 = (TextView) findViewById(R.id.ticket_success_tv2);
        btn1 = (Button) findViewById(R.id.ticket_success_btn1);
        btn2 = (Button) findViewById(R.id.ticket_success_btn2);
        img1 = (ImageView) findViewById(R.id.ticket_success_img1);

        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        btn1.setTypeface(tf2);
        btn2.setTypeface(tf2);

        img1.startAnimation(an1);
        tv1.startAnimation(an1);
        tv2.startAnimation(an1);
        btn1.startAnimation(an2);
        btn2.startAnimation(an2);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent goDetailAct = new Intent (SuccesBuyActivity.this, TicketInformation.class);
                startActivity(goDetailAct);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(SuccesBuyActivity.this, NavigationView.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(launchIntent);
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent launchIntent = new Intent(SuccesBuyActivity.this, NavigationView.class);
//        launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        finish();
//        startActivity(launchIntent);
//    }
}
