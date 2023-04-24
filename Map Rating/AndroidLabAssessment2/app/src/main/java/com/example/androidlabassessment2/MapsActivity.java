package com.example.androidlabassessment2;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.androidlabassessment2.databinding.ActivityMapsBinding;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMarkerInfoEnteredListener {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.androidlabassessment2.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng school = new LatLng(43.724330436,-79.605497578);
        mMap.addMarker(new MarkerOptions().position(school).title("Humber College").snippet("School\n4.0"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 10));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                openMarkerInfoFragment(latLng);
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Marker newMarkerData = marker;
                marker.remove();
                openOldMarkerInfoFragment(newMarkerData);
                return false;
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                // Show the details of the marker
                // You can get the details from the marker's snippet or title
                String title = marker.getTitle();
                String snippet = marker.getSnippet();

                // Show the details in a Toast message
                Toast.makeText(MapsActivity.this, title + ": " + snippet, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // Do nothing
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // Do nothing
            }
        });

    }
    private void openMarkerInfoFragment(LatLng latLng) {
        UserInputRating fragment = new UserInputRating();
        Bundle args = new Bundle();
        args.putParcelable("latLng", latLng);
        args.putBoolean("newMarker", true);
        fragment.setArguments(args);
        fragment.setOnMarkerInfoEnteredListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.map, fragment)
                .addToBackStack(null)
                .commit();
    }

@Override
public void onMarkerInfoEntered(String title, String description, float rating, LatLng latLng) {
    mMap.addMarker(new MarkerOptions()
            .position(latLng)
            .title(title)
            .snippet(description + "\n" + rating));
}

    public void openOldMarkerInfoFragment(Marker marker) {
        String snip = marker.getSnippet();
        String [] snipSplit = snip.split("\n");
        String description = snipSplit[0];
        String rating = snipSplit[1];

        // now send over to Fragment and hope
        UserInputRating fragment = new UserInputRating();
        Bundle args = new Bundle();
        args.putString("title", marker.getTitle());
        args.putString("description", description);
        args.putFloat("rating", Float.parseFloat(rating));
        args.putParcelable("latLng", marker.getPosition());
        args.putBoolean("newMarker", false); // this should trigger something
        fragment.setArguments(args);
        fragment.setOnMarkerInfoEnteredListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.map, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void updateMarker(MarkerOptions oldMarker) {
        LatLng position = oldMarker.getPosition();
        String title = oldMarker.getTitle();
        String snippet = oldMarker.getSnippet();

        mMap.addMarker(new MarkerOptions().position(position).title(title).snippet(snippet));
    }

}