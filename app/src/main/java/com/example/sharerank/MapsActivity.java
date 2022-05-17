package com.example.sharerank;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

import java.util.Arrays;
//import com.google.android.libraries.places.compat.Place;
//import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
//import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng sydney = new LatLng(-34, 151);
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Places.initialize(getApplicationContext(), "AIzaSyAaSAfET0ZXLFgpwKBlhRcLDPb9cN5LNJI");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        setupCurrentLocation();
        setupAutoCompleteFragment();
    }

    private void setupCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            sydney = new LatLng(location.getLatitude(), location.getLongitude());
                            mapFragment.getMapAsync(MapsActivity.this);
                        }
                    }
                });

    }

    private void setupAutoCompleteFragment() {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
        ));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                sydney = place.getLatLng();
                mapFragment.getMapAsync(MapsActivity.this);
            }


            @Override
            public void onError(@NonNull Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Share Rank")
                .snippet("Classifique as condições desse local")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
        }
    }
}

//import androidx.fragment.app.FragmentActivity;
//
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.widget.SearchView;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.io.IOException;
//import java.util.List;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//
//    SearchView searchView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        // initializing our search view.
//        searchView = findViewById(R.id.idSearchView);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // on below line we are getting the
//                // location name from search view.
//                String location = searchView.getQuery().toString();
//
//                // below line is to create a list of address
//                // where we will store the list of all address.
//                List<Address> addressList = null;
//
//                // checking if the entered location is null or not.
//                if (location != null || location.equals("")) {
//                    // on below line we are creating and initializing a geo coder.
//                    Geocoder geocoder = new Geocoder(MapsActivity.this);
//                    try {
//                        // on below line we are getting location from the
//                        // location name and adding that location to address list.
//                        addressList = geocoder.getFromLocationName(location, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    // on below line we are getting the location
//                    // from our list a first position.
//                    Address address = addressList.get(0);
//
//                    // on below line we are creating a variable for our location
//                    // where we will add our locations latitude and longitude.
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//
//                    // on below line we are adding marker to that position.
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
//
//                    // below line is to animate camera to that position.
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        // at last we calling our map fragment to update.
//        mapFragment.getMapAsync(this);
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            // Add a marker in Sydney and move the camera
////                            LatLng sydney = new LatLng(-34, 151);
////                            mMap.animateCamera( CameraUpdateFactory.zoomTo( 20.0f ) );
//                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
//                        }
//                    }
//                });
//
//
//    }
//}