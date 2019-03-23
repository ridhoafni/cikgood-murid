package ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;

public class AddRemoveDynamicActivity extends AppCompatActivity {

    Spinner SpinnerTextIn, SpinnerTextIn2;
    Button buttonAdd;
    LinearLayout container;
    TextView reList, info;
    String selectedName, selectedName2;
    String selectedChild;

    private static final String[] HARI = new String[] {
            "Senin", "Selasa", "Rabu", "Kamis", "Jum'at",
            "Sabtu", "Minggu"
    };

    private static final String[] JAM = new String[] {
            "Pagi (09:00 - 14:00)", "Siang (14:00 - 18:00)", "Malam (18:00 - 22:00)"
    };

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterJam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_dynamic);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, HARI);

        adapterJam = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, JAM);

        SpinnerTextIn = (Spinner)findViewById(R.id.textin);
        SpinnerTextIn.setAdapter(adapter);

        SpinnerTextIn2 = (Spinner)findViewById(R.id.textin2);
        SpinnerTextIn2.setAdapter(adapterJam);

        SpinnerTextIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
                selectedName = parent.getItemAtPosition(position).toString();

                Toast.makeText(AddRemoveDynamicActivity.this, "Kamu memilih " +selectedName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerTextIn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
                selectedName2 = parent.getItemAtPosition(position).toString();

                Toast.makeText(AddRemoveDynamicActivity.this, "Kamu memilih " +selectedName2, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAdd = (Button)findViewById(R.id.add);
        container = (LinearLayout) findViewById(R.id.container);
        reList = (TextView)findViewById(R.id.relist);
        reList.setMovementMethod(new ScrollingMovementMethod());
        info = (TextView)findViewById(R.id.info);
        info.setMovementMethod(new ScrollingMovementMethod());

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                TextView SpinnerTextOut = (TextView) addView.findViewById(R.id.textout);
                TextView SpinnerTextOut2 = (TextView) addView.findViewById(R.id.textout2);
                SpinnerTextOut.setText(selectedName2);
                SpinnerTextOut2.setText(selectedName);
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);

                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        info.append("thisListener called:\t" + this + "\n");
                        info.append("Remove addView: " + addView + "\n\n");
                        ((LinearLayout)addView.getParent()).removeView(addView);

                        listAllAddView();
                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container.addView(addView);

                info.append(
                        "thisListener:\t" + thisListener + "\n"
                                + "addView:\t" + addView + "\n\n"
                );

                listAllAddView();
            }
        });
    }

    private void listAllAddView(){
        reList.setText("");

        int childCount = container.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
            reList.append(thisChild + "\n");

//            Spinner childTextView = (Spinner) thisChild.findViewById(R.id.textout);
                String childTextViewValue = selectedChild;
            reList.append("= " + childTextViewValue + "\n");
        }
    }

}