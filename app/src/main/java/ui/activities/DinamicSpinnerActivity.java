package ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.cikgood.R;

public class DinamicSpinnerActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    EditText edittext_var;
    Spinner spinner;
    String selectedName;
    Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinamic_spinner);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        edittext_var = (EditText) findViewById(R.id.number_edit_text);

//        initSpinnerDurasiPertemuanJam();
    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);

        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onSaveField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);

        edittext_var = (EditText)((View) v.getParent()).findViewById(R.id.number_edit_text) ;

        System.out.println("Nilai :");

        Toast.makeText(this,"Hallo...", Toast.LENGTH_SHORT).show();

        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onSelect(View v){
        edittext_var = (EditText)((View) v.getParent()).findViewById(R.id.number_edit_text) ;
        spinner = (Spinner) ((View) v.getParent()).findViewById(R.id.type_spinner) ;

        initSpinnerDurasiPertemuanJam();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
                selectedName = parent.getItemAtPosition(position).toString();

                Toast.makeText(DinamicSpinnerActivity.this, "Kamu memilih " +selectedName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        System.out.println("The text is = "+ edittext_var.getText());

        Toast.makeText(this,"Hallo..."+ edittext_var.getText()+" & "+selectedName, Toast.LENGTH_SHORT).show();
    }

    private void initSpinnerDurasiPertemuanJam() {
        String[] array_durasi_pertemuan = {"Pagi (09:00 - 14:00)", "Siang (14:00 - 18:00)", "Malam (18:00 - 22:00)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_durasi_pertemuan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Durasi Per Pertemuan");
        spinner.setAdapter( adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
                selectedName = parent.getItemAtPosition(position).toString();

                Toast.makeText(DinamicSpinnerActivity.this, "Kamu memilih " +selectedName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

}
