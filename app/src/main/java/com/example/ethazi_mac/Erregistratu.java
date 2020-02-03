package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Erregistratu extends AppCompatActivity {
    private EditText txtizenabizen, txterabiltzaile, txtpasahitza1, txtpasahitza2, txtmail, txttelefono;
    private Button btnReserva;
    private String id;
    private String mota = String.valueOf(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erregistratu);
        this.setTitle(R.string.btnErregistratu);

        txterabiltzaile=(EditText)findViewById(R.id.editTextuser);
        txtpasahitza1=(EditText)findViewById(R.id.editPass1);
        txtpasahitza2=(EditText)findViewById(R.id.editPass2);
        txtizenabizen=(EditText)findViewById(R.id.editTextizenabizen);
        txtmail=(EditText)findViewById(R.id.editTextmail);
        txttelefono=(EditText)findViewById(R.id.editTexttelefonoa);
        btnReserva=(Button) findViewById(R.id.buttonErregis);

        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DatuakOndo();
            }
        });

    }


    public void insertarDatos(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    //Intent
                    //Toast.makeText(Erregistratu.this, "Bien login", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast error login
                    Toast.makeText(Erregistratu.this, "Mal login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TOAST capturar error
                Toast.makeText(Erregistratu.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("pasahitza", getMd5(txtpasahitza1.getText().toString()));
                parametros.put("erabiltzaile", txterabiltzaile.getText().toString());
                parametros.put("mail", txtmail.getText().toString());
                parametros.put("telefonoa", txttelefono.getText().toString());
                parametros.put("Erabiltzaile_mota", mota);
                parametros.put("Izen Abizenak", txtizenabizen.getText().toString());

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void DatuakOndo(){
                    String izen, pasahitza1, pasahitza2, mail, tele, erabil;
                    izen = txtizenabizen.getText().toString();
                    erabil = txterabiltzaile.getText().toString();
                    pasahitza1 = txtpasahitza1.getText().toString();
                    pasahitza2 = txtpasahitza2.getText().toString();
                    mail = txtmail.getText().toString();
                    tele = txttelefono.getText().toString();


                    if(TextUtils.isEmpty(izen) || TextUtils.isEmpty(erabil) || TextUtils.isEmpty(pasahitza1) || TextUtils.isEmpty(pasahitza2) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(tele)) {
                        Toast.makeText(Erregistratu.this, R.string.errorDatuakBete, Toast.LENGTH_SHORT).show();
                        txtizenabizen.setError("");
                        txterabiltzaile.setError("");
                        txtpasahitza1.setError("");
                        txtpasahitza2.setError("");
                        txtmail.setError("");
                        txttelefono.setError("");
                    }
                    else{
                        if (pasahitza1.compareTo(pasahitza2) == 0){
                            insertarDatos("http://192.168.13.16:82/ethazi_mac/insertar_usuario.php");
                            finish();
                        }
                        else {
                            Toast.makeText(Erregistratu.this, R.string.errorPasahitzakBerdin, Toast.LENGTH_SHORT).show();
                            txtpasahitza1.setError("");
                            txtpasahitza2.setError("");
                        }
                    }
                }

    public String getMd5(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
