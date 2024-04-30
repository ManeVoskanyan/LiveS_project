package com.example.lives_project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class FullscreenMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private EditText mapSearchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.fullscreen_map_activity);
            mapSearchView = findViewById(R.id.mapSearch);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_google);
            mapSearchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String location = s.toString();
                    List<Address> addressList = null;
                    Geocoder geocoder = new Geocoder(FullscreenMapActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                        if (addressList.isEmpty()) {
                            return;
                        }
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latLng).title(location);
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        myMap.addMarker(options);
                        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            mapFragment.getMapAsync(FullscreenMapActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
    }

}

