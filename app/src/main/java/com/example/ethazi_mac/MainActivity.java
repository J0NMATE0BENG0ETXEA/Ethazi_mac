package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.*;

public class MainActivity extends AppCompatActivity {

    TextView kontuberria;
    EditText txtErabiltzaile, txtPasahitza;
    Button btnSartu;
    String usuario, id;
    private boolean jarraitu = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.txtlogina);

        txtErabiltzaile=(EditText)findViewById(R.id.editTextUser);
        txtPasahitza=(EditText)findViewById(R.id.editTextPass);
        btnSartu=(Button)findViewById(R.id.buttonSartu);
        btnSartu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                usuario = txtErabiltzaile.getText().toString();
                //Kontsulta
                kontsultaErabiltzaileId("http://192.168.13.26/ethazi_mac/id_usuario.php");
                comprobarUsuario("http://192.168.13.26/ethazi_mac/validar_usuario.php");
            }
        });

        kontuberria = (TextView) findViewById(R.id.textViewKontua);
        kontuberria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KontuBerriBat(v);
            }
        });

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);

    }

    //PANTALLAK
    public void pantallaOstatuak() {
        Intent i = new Intent(this, ostatuak.class );
        startActivity(i);
        finish();
    }

    public void KontuBerriBat(View view) {
        Intent i = new Intent(this, Erregistratu.class );
        startActivity(i);
    }

    //KONTSULTA ERABILTZAILE
    public void GordeId(JSONArray ja){

        //KOLTSULTAREN EMAITZAK OBJEKTUETAN SARTU
        for(int i=0;i<ja.length();i+=2){

            try {
                if (ja.getString(i+1).equals(usuario)){
                    id = ja.getString(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void kontsultaErabiltzaileId(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        GordeId(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    //ENCRIPTAR
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

    //COMPROBAR USUARIO
    public void validarUsuario(JSONArray ja){
        String pass = getMd5(txtPasahitza.getText().toString());
        //KOLTSULTAREN EMAITZAK OBJEKTUETAN SARTU
        for(int i=0;i<ja.length();i+=2){
            try {
                if (ja.getString(i).equals(usuario) && ja.getString(i+1).equals(pass)){
                    jarraitu = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void comprobarUsuario(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        validarUsuario(ja);
                        if (jarraitu==true){
                            //Intent
                            Toast.makeText(MainActivity.this, "Bien login", Toast.LENGTH_SHORT).show();
                            //SHARED
                            SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferencias.edit();
                            editor.putString("usuario", usuario);
                            editor.putString("id_usuario", id);
                            editor.commit();

                            pantallaOstatuak();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Mal login", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

}
