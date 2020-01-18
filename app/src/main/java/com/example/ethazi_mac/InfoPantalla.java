package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class InfoPantalla extends AppCompatActivity {

    private Ostatu ostatu;
    private String latidude, longitude;
    private TextView tvizena, tvdesk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pantalla);
        getSupportActionBar().hide();
        //Dato de la otra Actividad
        Bundle bundle = getIntent().getExtras();
        //Deklarazioak
        tvizena=(TextView)findViewById(R.id.textViewIzena);
        tvdesk=(TextView)findViewById(R.id.textViewDesc);
        //Textua ezarri
        tvizena.setText((String) bundle.get("izena"));
        tvdesk.setText((String) bundle.get("desk"));
        tvdesk.setMovementMethod(new ScrollingMovementMethod());
    }



}
