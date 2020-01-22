package com.example.ethazi_mac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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

import java.util.HashMap;
import java.util.Map;

public class Reserba extends AppCompatActivity {

    private String erabiltzaile, dataHasiera, dataAmaiera, idErabiltzaile;
    private TextView info;
    private CalendarView Hasiera, Amaiera;
    private Button btnreserba;
    private EditText gaukop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserba);
        this.setTitle(R.string.btnReserba);

        //Beste activity informazioa
        Bundle bundle = getIntent().getExtras();
        String izena = (String) bundle.get("izen");
        String prezioa = (String) bundle.get("prezioa");

        //SHRED
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        erabiltzaile = prefe.getString("usuario", "").toString();

        info=(TextView)findViewById(R.id.textViewInformazioa);
        Hasiera=(CalendarView)findViewById(R.id.calenHasiera);
        Amaiera =(CalendarView)findViewById(R.id.calenAmaiera);
        gaukop =(EditText)findViewById(R.id.editTextGaukop);
        btnreserba = (Button)findViewById(R.id.buttonReserbaCalendar);

        info.setText(izena+ "\n" + prezioa + "€\n" + "cod: " + erabiltzaile);

        Hasiera.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dataHasiera = year + "-" + (month+1) + "-" + dayOfMonth;

            }
        });

        Amaiera.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dataAmaiera = year + "-" + (month+1) + "-" + dayOfMonth;
            }
        });

    }

    public void datuakGorde(View view){
        String gaukopuru = gaukop.getText().toString();
        kontsultaIderabiltzaile("http://192.168.13.26/ethazi_mac/id_usuario.php");
    }


        public void kontsultaIderabiltzaile(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarId(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("Erabiltzaile", erabiltzaile);
                return parametros;
            }
        };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    public void CargarId(JSONArray ja) {

        for (int i = 0; i < ja.length(); i ++) {

            try {
                idErabiltzaile = ja.getString(i);
                System.out.println("Coidgo erabiltzaile: " + idErabiltzaile);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
