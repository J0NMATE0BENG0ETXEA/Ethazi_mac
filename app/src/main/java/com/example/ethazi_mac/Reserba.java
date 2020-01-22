package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class Reserba extends AppCompatActivity {

    private String erabiltzaile;
    private TextView info;
    private CalendarView calendar;

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
        calendar=(CalendarView)findViewById(R.id.Calendar);

        info.setText(izena+ "\n" + prezioa + "€\n" + "cod: " + erabiltzaile);


    }




}
