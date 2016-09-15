package cl.jago.lazarillo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;

    //Clase Location
    TextView tv_TextView;
    TextView tv_TextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Clase Location
        tv_TextView = (TextView) findViewById(R.id.mesage_id);
        tv_TextView2 = (TextView) findViewById(R.id.mesage_id2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setMapsActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,(LocationListener)mlocListener);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Controles UI
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(false);
        // Marcadores
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // ¿Permisos asignados?
        if (requestCode == LOCATION_REQUEST_CODE) if (permissions.length > 0 &&
                permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);
        else {
            Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para obtener direccion de la calle a partir de la latitud y longitud
    public void setLocation (Location loc){

        if (loc.getLatitude()!=0.0&& loc.getLongitude()!=0.0){
            try{
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(),1);
                if (!list.isEmpty()){
                    Address address = list.get(0);
                    tv_TextView2.setText("Mi direccion es: \n" +address.getAddressLine(0));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public class MyLocationListener implements LocationListener{
        MapsActivity mapsActivity;

        public MapsActivity getMapsActivity(){
            return mapsActivity;
        }

        public void setMapsActivity(MapsActivity mapsActivity) {
            this.mapsActivity = mapsActivity;
        }


        @Override
        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();
            String Text = "Mi ubicacion actual es= "+ "\nLat= "+location.getLatitude() + "\nLong= " + location.getLongitude();
            tv_TextView.setText(Text);
            this.mapsActivity.setLocation(location);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            //este metodo se ejecuta cada vez que se detecta un cambio en el status del proveedor de localizacion GPS
            //OUT_OF_SERVICE
            //TEMPORARILY_UNAVAILABLE
            //AVAILABLE
        }

        @Override
        public void onProviderEnabled(String s) {
            tv_TextView.setText("GPS acticado");
        }

        @Override
        public void onProviderDisabled(String s) {
            tv_TextView.setText("GPS desacticado");
        }
    }

}
