package com.example.sharerank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class PlacesFragment extends Fragment {

    private GoogleMap mMap;
    private LatLng sydney = new LatLng(-34, 151);
    private SupportMapFragment mapFragment;

    private TextView locationNameText;
    private TextView locationAddressText;
    private ImageView imageView;

    private static final String MAPS_API_KEY = "AIzaSyAaSAfET0ZXLFgpwKBlhRcLDPb9cN5LNJI";

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
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Places.initialize(view.getContext(), MAPS_API_KEY);

        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragmentView =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment = mapFragmentView;

        if (mapFragmentView != null) {
            setupCurrentLocation();
            setupAutoCompleteFragment();
        }

        Button reviewButton = (Button) view.findViewById(R.id.review);
        Button rateBtn = (Button) view.findViewById(R.id.rate);
        locationNameText = view.findViewById(R.id.location_name);
        locationAddressText = view.findViewById(R.id.location_address);
        imageView = view.findViewById(R.id.imageView);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewReviewFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RateFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
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
//                            sydney = new LatLng(location.getLatitude(), location.getLongitude());

                            sydney = new LatLng(-23.6291715, -46.71082579999999);
                            locationNameText.setText("Mercado Carrefour");
                            locationAddressText.setText("Av. das Nações Unidas, 15187 - Cidade Monções, São Paulo - SP");
                            new DownloadImageTask(imageView).execute("https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference=" + "Aap_uEDsVMGRk8SY4PUXRqh9__4bH6MxmKzCpmhlA2gYeHRA5_77JREA-VlXf6b7L_qtST6xkzCg4lnac6FyMuARnq_KaJuHOC8lSVgyNgdO7sB491kF5jFaQIfqJwGfbT6e2xQCsS92-qibhVjUbnb7khIcoi97rOYFTwTgFy_HAkw6moTI" + "&key=" + MAPS_API_KEY);

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
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.PHOTO_METADATAS,
                Place.Field.PHOTO_METADATAS
        ));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                sydney = place.getLatLng();
                locationNameText.setText(place.getName());
                locationAddressText.setText(place.getAddress());
                mapFragment.getMapAsync(callback);

                try {
                    String photoReference = place.getPhotoMetadatas().get(0).zza();
                    new DownloadImageTask(imageView).execute("https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference=" + photoReference + "&key=" + MAPS_API_KEY);
                } catch (Exception ex) {
                    new DownloadImageTask(imageView).execute("https://mapbiomas.org/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png");
                }
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