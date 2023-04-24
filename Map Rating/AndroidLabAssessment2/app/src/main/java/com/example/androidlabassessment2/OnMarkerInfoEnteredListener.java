package com.example.androidlabassessment2;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public interface OnMarkerInfoEnteredListener {
    void onMarkerInfoEntered(String title, String description, float rating, LatLng latLng);
    void updateMarker(MarkerOptions OldMarker);

}

