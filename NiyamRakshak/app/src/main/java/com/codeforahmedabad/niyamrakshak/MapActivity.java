package com.codeforahmedabad.niyamrakshak;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by bhavana on 26-12-2015.
 */
public class MapActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private final LatLng LOC = new LatLng(23.0387521, 72.5222175);

    private FloatingActionButton fab;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_NETWORK_STATE, WRITE_EXTERNAL_STORAGE}, 0);

        // zoom in the camera to Davao city
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOC, 15));
        // animate the zoom process
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//        map.addMarker(new MarkerOptions().position(DAVAO).title("Bhaskar Rai Pandya, Community Hall, AMC"));
        Snackbar.make(fab, "Traffic tracking started.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        fab.setOnClickListener(this);

        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                    (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                    || checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null) {
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
//                adb.setView(alertDialogView);
                adb.setTitle("Traffic Data");
//                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MapActivity.this, "Traffic data sent successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
