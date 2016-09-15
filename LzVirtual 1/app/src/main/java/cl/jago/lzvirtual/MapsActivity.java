package cl.jago.lzvirtual;

import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng PuntoA = new LatLng(-33.015452, -71.548736);
        LatLng PuntoB = new LatLng(-33.015062, -71.544847);
        mMap.addMarker(new MarkerOptions().position(PuntoA).title("Punto A"));
        mMap.addMarker(new MarkerOptions().position(PuntoB).title("Punto B"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(PuntoA));
        drawPolilyne(Posiciones.POLILINEA);
        // siempre usar android.manifest.permission para los permisos del manifiesto
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void drawPolilyne(PolylineOptions options){
        Polyline polyline = mMap.addPolyline(options);
    }
}
