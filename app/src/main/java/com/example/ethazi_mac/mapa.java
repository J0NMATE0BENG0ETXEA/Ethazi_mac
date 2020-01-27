package com.example.ethazi_mac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class mapa extends AppCompatActivity {
    private MapView mapaView;
    private String longitude, latitude, izena, koka, helbide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "sk.eyJ1IjoiYmFzYXRvciIsImEiOiJjazVncWdicnAwOW5xM2ZwZTJ1OXQ0emgzIn0.y2afr5eNZWb7nByh97d3ZQ");
        setContentView(R.layout.activity_mapa);
        getSupportActionBar().hide();
        mapaView = (MapView) findViewById(R.id.mapView);
        mapaView.onCreate(savedInstanceState);
        mapaView.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style){

                    }
                });


            }
        });
        //Beste activity informazioa
        Bundle bundle = getIntent().getExtras();
        izena = (String) bundle.get("izen");
        latitude = (String) bundle.get("lati");
        longitude = (String) bundle.get("longi");
        koka = (String) bundle.get("koka");
        helbide = (String) bundle.get("helbide");

        //Konbertsioak
        final Double lati, longi;
        lati = Double.parseDouble(latitude);
        longi = Double.parseDouble(longitude);

        //MAPBOX
        mapaView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(
                                lati, longi))//Cogemos de la BD las coordenadas del alojamiento que se ha escogido
                        .title(izena)//Cogemos de la BD el nombre del alojamiento que se ha escogido
                        .snippet(koka + "\n" + helbide));//Cogemos de la BD la descripcion del alojamiento que se ha escogido

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(lati, longi)) // Cogemos de la BD las coordenadas del alojamiento que se ha escogido
                        .zoom(15) // Fija el nivel de zoom
                        .tilt(30) // Fija la inclinación de la cámara
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
            }
        });
    }


    //MAPBOX UTILS
    @Override
    public void onStart(){
        super.onStart();

        mapaView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapaView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapaView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapaView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapaView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapaView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapaView.onSaveInstanceState(outState);
    }

}
