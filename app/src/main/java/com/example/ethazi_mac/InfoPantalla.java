package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoPantalla extends AppCompatActivity {

    private Ostatu ostatu;
    private String latidude, longitude;
    private TextView tvizena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pantalla);
        getSupportActionBar().hide();
        //Dato de la otra Actividad
        Bundle bundle = getIntent().getExtras();
        //Deklarazioak
        tvizena=(TextView)findViewById(R.id.textViewIzena);
        //Textua ezarri
        tvizena.setText((String) bundle.get("izena"));
    }



}
