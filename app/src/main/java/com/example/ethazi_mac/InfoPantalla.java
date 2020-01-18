package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class InfoPantalla extends AppCompatActivity {

    private Ostatu ostatu;
    private String latidude, longitude;
    private TextView tvizena, tvdesk, tvmota, tvkokapena, tvtelefonoa, tvemail;

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
        tvmota=(TextView)findViewById(R.id.textViewmota);
        tvkokapena=(TextView)findViewById(R.id.textViewkokapena);
        tvtelefonoa=(TextView)findViewById(R.id.textViewtelefono);
        tvemail=(TextView)findViewById(R.id.textViewemail);

        //Textua ezarri
        tvizena.setText((String) bundle.get("izena"));
        tvdesk.setText((String) bundle.get("desk"));
        tvdesk.setMovementMethod(new ScrollingMovementMethod());
        tvmota.setText((String) bundle.get("mota"));
        tvkokapena.setText((String) bundle.get("kokapena"));
        tvtelefonoa.setText((String) bundle.get("telefono"));
        tvemail.setText((String) bundle.get("email"));


    }



}
