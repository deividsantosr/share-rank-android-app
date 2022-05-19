package com.example.sharerank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.os.Bundle;
import android.service.controls.templates.RangeTemplate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private LatLng sydney = new LatLng(-34, 151);
    private SupportMapFragment mapFragment;

    private Button button;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
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
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Places.initialize(view.getContext(), "AIzaSyAaSAfET0ZXLFgpwKBlhRcLDPb9cN5LNJI");

        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragmentView =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment = mapFragmentView;

        if (mapFragmentView != null) {
            setupCurrentLocation();
            setupAutoCompleteFragment();
        }

        Button b = (Button) view.findViewById(R.id.rate);
        Button r = (Button) view.findViewById(R.id.review);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });
    }

    private void setupCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            sydney = new LatLng(location.getLatitude(), location.getLongitude());
                            mapFragment.getMapAsync(callback);
                        }
                    }
                });

    }

    private void setupAutoCompleteFragment() {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                mapFragment.getParentFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

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
                mapFragment.getMapAsync(callback);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("Error", status.getStatusMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
        }
    }
}