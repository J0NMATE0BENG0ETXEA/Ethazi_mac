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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

public class Reserba extends AppCompatActivity {

    private String erabiltzaile, dataHasiera, dataAmaiera, idErabiltzaile, idost, prezioaguzti;
    private TextView info;
    private CalendarView Hasiera, Amaiera;
    private Button btnreserba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserba);
        this.setTitle(R.string.btnReserba);

        //Calendar datak
        Calendar calendarNow = new GregorianCalendar();
        int monthDay =calendarNow.get(Calendar.DAY_OF_MONTH);
        int month = calendarNow.get(Calendar.MONTH)+1;
        int year = calendarNow.get(Calendar.YEAR);
        String fechahoy = (year+"-"+month+"-"+monthDay);
        dataHasiera = fechahoy;
        dataAmaiera = fechahoy;

        //Beste activity informazioa
        Bundle bundle = getIntent().getExtras();
        String izena = (String) bundle.get("izen");
        prezioaguzti = (String) bundle.get("prezioa");
        idost = (String) bundle.get("Kod");

        //SHRED
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        erabiltzaile = prefe.getString("usuario", "").toString();
        idErabiltzaile = prefe.getString("id_usuario", "").toString();

        info=(TextView)findViewById(R.id.textViewInformazioa);
        Hasiera=(CalendarView)findViewById(R.id.calenHasiera);
        Amaiera =(CalendarView)findViewById(R.id.calenAmaiera);
        btnreserba = (Button)findViewById(R.id.buttonReserbaCalendar);

        info.setText(izena+ "\n" + prezioaguzti + "€\n" + "User: " + erabiltzaile);

        Date gaur = new Date();
        //Date d = f.parse(string_date);
        long milliseconds = gaur.getTime();
        Hasiera.setMinDate(milliseconds);
        Amaiera.setMinDate(milliseconds);

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
        int emaitza = (Integer.parseInt(prezioaguzti)*calcularDias());
        if (emaitza <= 0){
            Toast.makeText(Reserba.this, R.string.errorSartuOndoDatak, Toast.LENGTH_SHORT).show();
        }else{
            prezioaguzti = String.valueOf(emaitza);
            insertarDatos("http://172.22.28.130/ethazi_mac/insertar_reserba.php");
            Toast.makeText(Reserba.this, R.string.txtReserbaEginda, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void insertarDatos(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TOAST capturar error
                Toast.makeText(Reserba.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("id_Ostatu", idost);
                parametros.put("id_Erabiltzaile", idErabiltzaile);
                parametros.put("prezioGuztira", prezioaguzti);
                parametros.put("hasieraData", dataHasiera);
                parametros.put("amaieraData", dataAmaiera);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public int calcularDias(){
        int emaitza = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaInicial=dateFormat.parse(dataHasiera);
                Date fechaFinal=dateFormat.parse(dataAmaiera);
                int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
                if (dias == 0){
                    emaitza = 1;
                }else{
                    emaitza = dias;
                }
                System.out.println("Hay "+dias+" dias de diferencia");
            }catch (Exception e){

            }

        return emaitza;
    }

}
