package ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.anonymous.cikgood.R;
import com.github.ybq.android.spinkit.style.Wave;

public class SplitKitActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_kit);

        progressBar = (ProgressBar) findViewById(R.id.SpinKit);
        btnClick = (Button) findViewById(R.id.button4);


        Wave wave = new Wave();

        progressBar.setIndeterminateDrawable(wave);

        progressBar.setVisibility(View.INVISIBLE);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
}
