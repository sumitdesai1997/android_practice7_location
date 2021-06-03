package com.example.android_practice7_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 2nd step - declaring location Manager and location listener
    LocationManager locationManager;
    LocationListener locationListener;

    public static final String TAG = "Location exercise";
    public static final int REQUEST_CODE = 1;

    TextView locationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTV = findViewById(R.id.location_tv);

        // 3rd step - instantiate locationManager and locationListener
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // In android, we can get any service by using getSystemService method
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "On location changed: " + location);
                locationTV.setText(String.format("lat: %s, long: %s", location.getLatitude(), location.getLongitude()));
            }
        };

        // 4th step - request for the permission in onCreate method
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    // 5th step - after granting or denying permissions, the following method will be executed
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
}