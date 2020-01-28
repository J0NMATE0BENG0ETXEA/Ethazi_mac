package com.example.ethazi_mac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;
import java.util.List;

public class mapa extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener{
    private MapView mapaView;
    private FloatingActionButton btUbicacion;
    private LocationServices servicioUbicacion;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private String longitude, latitude, izena, koka, helbide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "sk.eyJ1IjoiYmFzYXRvciIsImEiOiJjazVncWdicnAwOW5xM2ZwZTJ1OXQ0emgzIn0.y2afr5eNZWb7nByh97d3ZQ");
        setContentView(R.layout.activity_mapa);
        getSupportActionBar().hide();
        mapaView = (MapView) findViewById(R.id.mapView);
        mapaView.onCreate(savedInstanceState);
        mapaView.getMapAsync(this);

        //Beste activity informazioa
        Bundle bundle = getIntent().getExtras();
        izena = (String) bundle.get("izen");
        latitude = (String) bundle.get("lati");
        longitude = (String) bundle.get("longi");
        koka = (String) bundle.get("koka");
        helbide = (String) bundle.get("helbide");

        //CAMARA MAPBOX
        mapaView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(43.257, -2.92344)) // Cogemos de la BD las coordenadas del alojamiento que se ha escogido
                        .zoom(55) // Fija el nivel de zoom
                        .tilt(30) // Fija la inclinación de la cámara
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
            }
        });

    }


    public void localizacion(View view){
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
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapa.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjerxnqt3cgvp2rmyuxbeqme7"),
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapaView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapaView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapaView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapaView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapaView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapaView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapaView.onLowMemory();
    }
}