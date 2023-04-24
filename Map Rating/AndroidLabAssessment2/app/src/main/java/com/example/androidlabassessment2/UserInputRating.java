package com.example.androidlabassessment2;

import android.content.Context;
import android.media.Rating;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class UserInputRating extends Fragment {

    private EditText mTitleEditText, mDescriptionEditText;
    private RatingBar mRatingBar;
    private LatLng mLatLng;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private OnMarkerInfoEnteredListener mListener;

    public UserInputRating() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_input_rating, container, false);
        mTitleEditText = view.findViewById(R.id.et_title);
        mDescriptionEditText = view.findViewById(R.id.et_description);
        mRatingBar = view.findViewById(R.id.ratingBar2);
        Button saveButton = view.findViewById(R.id.button2);
        Button cancelButton = view.findViewById(R.id.button);
        mMapView = view.findViewById(R.id.lite_listrow_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
            }
        });

        Bundle args = getArguments();
        mLatLng = args.getParcelable("latLng");
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Set the location of the map
                LatLng location = mLatLng;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

                // Make the map static
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                googleMap.getUiSettings().setTiltGesturesEnabled(false);
                googleMap.addMarker(new MarkerOptions().position(mLatLng));
            }
        });


        if (args.getBoolean("newMarker")) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mTitleEditText.getText().toString();
                    String description = mDescriptionEditText.getText().toString();
                    float rating = mRatingBar.getRating();
                    if (mListener != null) {
                        mListener.onMarkerInfoEntered(title, description, rating, mLatLng);
                    }

                    Bundle result = args;
                    result.putString("title", title);
                    result.putString("description", description);
                    result.putFloat("rating", rating);
                    result.putBoolean("isNewMarker", false);
                    getParentFragmentManager().setFragmentResult("markerInfo", result);
                    getParentFragmentManager().popBackStack();
                }
            });
        } else {
            mTitleEditText.setText(args.getString("title"));
            mDescriptionEditText.setText(args.getString("description"));
            mRatingBar.setRating(args.getFloat("rating"));
            mLatLng = args.getParcelable("latLng");
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = mTitleEditText.getText().toString();
                    String description = mDescriptionEditText.getText().toString();
                    float rating = mRatingBar.getRating();

                    MarkerOptions oldMarker = new MarkerOptions()
                            .position(args.getParcelable("latLng"))
                            .title(title)
                            .snippet(description + "\n" + rating);

                    if (mListener != null) {
                        mListener.updateMarker(oldMarker);
                    }
                    Bundle result = args;
                    result.putString("title", title);
                    result.putString("description", description);
                    result.putFloat("rating", rating);
                    getParentFragmentManager().setFragmentResult("markerInfo", result);
                    getParentFragmentManager().popBackStack();
                }
            });
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //return to page and return the marker if one exists
                if (!args.getBoolean("newMarker")) {
                    String title = mTitleEditText.getText().toString();
                    String description = mDescriptionEditText.getText().toString();
                    float rating = mRatingBar.getRating();

                    MarkerOptions oldMarker = new MarkerOptions()
                            .position(args.getParcelable("latLng"))
                            .title(title)
                            .snippet(description + "\n" + rating);

                    if (mListener != null) {
                        mListener.updateMarker(oldMarker);
                    }
                    Bundle result = args;
                    result.putString("title", title);
                    result.putString("description", description);
                    result.putFloat("rating", rating);
                    getParentFragmentManager().setFragmentResult("markerInfo", result);
                    getParentFragmentManager().popBackStack();

                } else {
                    //return empty
                    getParentFragmentManager().popBackStack();
                }

            }
        });

        return view;
    }

    public void setOnMarkerInfoEnteredListener(OnMarkerInfoEnteredListener listener) {
        mListener = listener;
    }
}