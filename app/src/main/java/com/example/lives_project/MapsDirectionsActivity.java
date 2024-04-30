package com.example.lives_project;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;

public class MapsDirectionsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editTextOrigin;
    private EditText editTextDestination;

    private boolean locationsEntered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_directions_activity);

        editTextOrigin = findViewById(R.id.editTextOrigin);
        editTextDestination = findViewById(R.id.editTextDestination);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Button directionButton = findViewById(R.id.btnDirection);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirections();
            }
        });
    }
    private void addMarkersAndZoom() {
        LatLng origin = convertToLatLng(editTextOrigin.getText().toString());
        LatLng destination = convertToLatLng(editTextDestination.getText().toString());

        if (origin != null && destination != null) {
            mMap.addMarker(new MarkerOptions().position(origin).title("Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.addMarker(new MarkerOptions().position(destination).title("Destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(origin);
            builder.include(destination);
            LatLngBounds bounds = builder.build();
            int padding = 100;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (locationsEntered) {
            addMarkersAndZoom();
        }
    }
    private void getDirections() {
        String originText = editTextOrigin.getText().toString();
        String destinationText = editTextDestination.getText().toString();

        LatLng origin = convertToLatLng(originText);
        LatLng destination = convertToLatLng(destinationText);

        if (origin == null || destination == null) {
            return;
        }

        mMap.clear();

        locationsEntered = true;

        addMarkersAndZoom();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getString(R.string.my_map_directions_api_key))
                .build();

        com.google.maps.model.LatLng originLatLng = new com.google.maps.model.LatLng(origin.latitude, origin.longitude);
        com.google.maps.model.LatLng destinationLatLng = new com.google.maps.model.LatLng(destination.latitude, destination.longitude);

        DirectionsApi.newRequest(context)
                .origin(originLatLng)
                .destination(destinationLatLng)
                .mode(TravelMode.DRIVING)
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result.routes != null && result.routes.length > 0) {
                                    PolylineOptions polylineOptions = new PolylineOptions();
                                    polylineOptions.color(Color.BLUE); // Задаем синий цвет для линии маршрута
                                    for (com.google.maps.model.LatLng point : result.routes[0].overviewPolyline.decodePath()) {
                                        polylineOptions.add(new LatLng(point.lat, point.lng));
                                    }
                                    mMap.addPolyline(polylineOptions);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("DirectionsError", "Error getting directions: " + e.getMessage());
                    }
                });
    }

    private LatLng convertToLatLng(String input) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(input, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
