package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoPantalla extends AppCompatActivity {

    private Ostatu ostatu;
    private String latidude, longitude, izena, desk, prezioa, idOstatu;
    private TextView tvizena, tvdesk, tvmota, tvkokapena, tvtelefonoa, tvemail;
    private Button btnMapa, btnReserva;

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
        izena = (String) bundle.get("izena");
        tvdesk.setText((String) bundle.get("desk"));
        desk = (String) bundle.get("desk");
        tvdesk.setMovementMethod(new ScrollingMovementMethod());
        tvmota.setText((String) bundle.get("mota"));
        tvkokapena.setText((String) bundle.get("kokapena"));
        tvtelefonoa.setText((String) bundle.get("telefono"));
        tvemail.setText((String) bundle.get("email"));
        latidude = (String) bundle.get("latitude");
        longitude = (String) bundle.get("longitude");
        prezioa = (String) bundle.get("prezioa");
        idOstatu = (String) bundle.get("kod");
    }

    //VER MAPA
    public void ikusiMapa(View view){
        Intent i = new Intent(this, mapa.class );
        i.putExtra("longi", longitude);
        i.putExtra("lati", latidude);
        i.putExtra("izen", izena);
        i.putExtra("deskrip", desk);
        startActivity(i);
    }

    public void ikusiReserba(View view){
        Intent i = new Intent(this, Reserba.class );
        i.putExtra("izen", izena);
        i.putExtra("prezioa", prezioa);
        i.putExtra("Kod", idOstatu);
        startActivity(i);
    }
}
