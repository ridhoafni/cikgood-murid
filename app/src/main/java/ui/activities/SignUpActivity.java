package ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.utils.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    //defining views
    private Button buttonRegister;
    ConstraintLayout constraintLayout;
    private EditText editTextEmail,editTextNama, editTextNoHP, editTextPwd;
    private ProgressDialog progressDialog;
    String tokenhasil;
    //URL to RegisterDevice.php
    private static final String URL_REGISTER_DEVICE = "http://arslyn.com/cikgood/api/fcm/RegisterDevice.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getting views from xml
        editTextPwd = (EditText) findViewById(R.id.editTextPwd);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextEmail = (EditText) findViewById(R.id.inputEmailAddress);
        editTextNoHP = (EditText) findViewById(R.id.editTextHp);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        constraintLayout = (ConstraintLayout) findViewById(R.id.layoutParent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(SignUpActivity.this, SignInActivity.class);
                intentProfile.putExtra("FromResetPassword", "2");
                startActivity(intentProfile);
            }
        });

        //adding listener to view
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        if (view == buttonRegister) {

            final String pwd = editTextPwd.getText().toString().trim();
            final String email = editTextEmail.getText().toString().trim();
            final String nama = editTextNama.getText().toString().trim();
            final String no_hp = editTextNoHP.getText().toString().trim();

            if (pwd.isEmpty() || email.isEmpty() || nama.isEmpty() || no_hp.isEmpty()){
                Snackbar snackbar = Snackbar.make(constraintLayout, "Harus diisi!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else {
                sendTokenToServered();
            }
        }
    }

    private void sendTokenToServered() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            //if token is not null
                            if (token != null) {
                                //displaying the token
                                Common.currentToken = token;
                                Log.d("token",""+Common.currentToken);
                            } else {
                                //if token is null that means something wrong
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }else{
                        }
                    }
                });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();


        final String token = Common.currentToken;
        final String pwd = editTextPwd.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String nama = editTextNama.getText().toString();
        final String no_hp = editTextNoHP.getText().toString();

        if (Common.currentToken == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);

                            // pusher notif
                            // Instantiate the RequestQueue.
                            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
//                            String url ="http://192.168.1.10/yii2/cikgood/api/pusher/index.php";

                            // Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_DEVICE,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("That didn't work!", "");
                                }
                            });

                            // Add the request to the RequestQueue.
                            queue.add(stringRequest);

                            Snackbar snackbar = Snackbar.make(constraintLayout, obj.getString("message"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("token", Common.currentToken);
                params.put("no_hp", no_hp);
                params.put("email", email);
                params.put("password", pwd);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}