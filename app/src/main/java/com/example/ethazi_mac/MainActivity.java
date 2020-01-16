package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtErabiltzaile, txtPasahitza;
    Button btnSartu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtErabiltzaile=(EditText)findViewById(R.id.editTextUser);
        txtPasahitza=(EditText)findViewById(R.id.editTextPass);
        btnSartu=(Button)findViewById(R.id.buttonSartu);

        btnSartu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                validarUsuario("http://192.168.13.26/ethazi_mac/validar_usuario.php");
            }
        });

    }

    public void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    //Intent
                    Toast.makeText(MainActivity.this, "Bien login", Toast.LENGTH_SHORT).show();
                    pantallaOstatuak();
                }
                else {
                    //Toast error login
                    Toast.makeText(MainActivity.this, "Mal login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TOAST capturar error
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("usuario", txtErabiltzaile.getText().toString());
                parametros.put("password", txtPasahitza.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void pantallaOstatuak() {
        Intent i = new Intent(this, ostatuak.class );
        startActivity(i);
        finish();
    }

}
