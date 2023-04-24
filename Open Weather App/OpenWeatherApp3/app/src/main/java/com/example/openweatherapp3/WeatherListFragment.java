package com.example.openweatherapp3;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class WeatherListFragment extends Fragment {
    private final LatLng latLng;
    private final SupportMapFragment supportMapFragment;
    LinearLayout linearLayout;
    public WeatherListFragment(LatLng latLng, SupportMapFragment supportMapFragment) {
        this.latLng = latLng;
        this.supportMapFragment = supportMapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction().remove(WeatherListFragment.this).show(supportMapFragment).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        linearLayout = view.findViewById(R.id.weatherList);
        Bundle bundle = getArguments();
        double latitude = 0, longitude = 0;
        if (bundle != null) {
            // Retrieve the latitude and longitude data from the bundle
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        }
        Log.d("@Harman - lat", String.valueOf(latitude));
        Log.d("@Harman - long", String.valueOf(longitude));


        String apiKey = "2967b336cde2985d755e487555ddb18e";
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=2967b336cde2985d755e487555ddb18e&units=metric";
        AtomicReference<WeatherData> weatherData = new AtomicReference<>(new WeatherData());
        Log.d("@Harman - url", url);
        StringRequest stringRequest = new StringRequest(url, response -> {
//            System.out.println(response);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            weatherData.set(builder.create().fromJson(response, WeatherData.class));

            List<WeatherData.Forecast> forcasts = weatherData.get().getForecasts();
            linearLayout = view.findViewById(R.id.weatherList);

            TextView city, country, timezone, latlongfinal;

            city = view.findViewById(R.id.cityName_tv);
            city.setText(weatherData.get().getCity().getName());

            country = view.findViewById(R.id.countryName_tv);
            country.setText(weatherData.get().getCity().getCountry());

            timezone = view.findViewById(R.id.timeZone_tv);
            timezone.setText(String.valueOf(weatherData.get().getCity().getTimezone()));

            latlongfinal = view.findViewById(R.id.latlng_tv);
            latlongfinal.setText(String.valueOf(latLng));


            //h1 and so on
            TextView h1, h1temp_description;

            h1 = view.findViewById(R.id.h1);
            h1.setText(forcasts.get(0).getDtTxt());

            TextView h1temp, h1temp_high, h1temp_low;
            h1temp = view.findViewById(R.id.h1temp);
            h1temp.setText(String.valueOf(forcasts.get(0).getMain().getTemp()));

            h1temp_high = view.findViewById(R.id.h1temp_high);
            h1temp_high.setText(String.valueOf(forcasts.get(0).getMain().getTempMax()));

            h1temp_low = view.findViewById(R.id.h1temp_low);
            h1temp_low.setText(String.valueOf(forcasts.get(0).getMain().getTempMin()));

            h1temp_description= view.findViewById(R.id.h1Description);
            StringBuilder sbh1 = new StringBuilder();
            sbh1.append("Description: ")
                    .append(forcasts.get(0).getWeather().get(0).getDescription())
                    .append("\n")
                    .append("Wind speed: ")
                    .append(forcasts.get(0).getWind().getSpeed())
                    .append("m/s")
                    .append("\n")
                    .append("Feels like: ")
                    .append(forcasts.get(0).getMain().getFeelsLike())
                    .append("\n")
                    .append("Humidity: ")
                    .append(forcasts.get(0).getMain().getHumidity())
                    .append("\n")
                    .append("Cloudes: ")
                    .append(forcasts.get(0).getClouds().getAll() + "%");
            h1temp_description.setText(sbh1);

            TextView h2, h2temp_description;
            TextView h2temp, h2temp_high, h2temp_low;
            h2temp = view.findViewById(R.id.h2temp);
            h2temp.setText(String.valueOf(forcasts.get(1).getMain().getTemp()));

            h2temp_high = view.findViewById(R.id.h2temp_high);
            h2temp_high.setText(String.valueOf(forcasts.get(1).getMain().getTempMax()));

            h2temp_low = view.findViewById(R.id.h2temp_low);
            h2temp_low.setText(String.valueOf(forcasts.get(1).getMain().getTempMin()));

            h2 = view.findViewById(R.id.h2);
            h2.setText(forcasts.get(1).getDtTxt());

            h2temp_description= view.findViewById(R.id.h2Description);
            StringBuilder sbh2 = new StringBuilder();
            sbh2.append("Description: ").append(forcasts.get(1).getWeather().get(0).getDescription()).append("\n")
                    .append("Wind speed: ").append(forcasts.get(1).getWind().getSpeed()).append("m/s\n")
                    .append("Feels like: ").append(forcasts.get(1).getMain().getFeelsLike()).append("\n")
                    .append("Humidity: ").append(forcasts.get(1).getMain().getHumidity()).append("\n")
                    .append("Clouds: ").append(forcasts.get(1).getClouds().getAll() + "%");
            h2temp_description.setText(sbh2);

            TextView h3, h3temp_description;
            TextView h3temp, h3temp_high, h3temp_low;
            h3temp = view.findViewById(R.id.h3temp);
            h3temp.setText(String.valueOf(forcasts.get(2).getMain().getTemp()));

            h3temp_high = view.findViewById(R.id.h3temp_high);
            h3temp_high.setText(String.valueOf(forcasts.get(2).getMain().getTempMax()));

            h3temp_low = view.findViewById(R.id.h3temp_low);
            h3temp_low.setText(String.valueOf(forcasts.get(2).getMain().getTempMin()));

            h3 = view.findViewById(R.id.h3);
            h3.setText(forcasts.get(2).getDtTxt());

            h3temp_description= view.findViewById(R.id.h3Description);
            StringBuilder sbh3 = new StringBuilder();
            sbh3.append("Description: ").append(forcasts.get(2).getWeather().get(0).getDescription()).append("\n")
                    .append("Wind speed: ").append(forcasts.get(2).getWind().getSpeed()).append("m/s\n")
                    .append("Feels like: ").append(forcasts.get(2).getMain().getFeelsLike()).append("\n")
                    .append("Humidity: ").append(forcasts.get(2).getMain().getHumidity()).append("\n")
                    .append("Clouds: ").append(forcasts.get(2).getClouds().getAll() + "%");
            h3temp_description.setText(sbh3);

            TextView h4, h4temp_description;
            TextView h4temp, h4temp_high, h4temp_low;
            h4temp = view.findViewById(R.id.h4temp);
            h4temp.setText(String.valueOf(forcasts.get(3).getMain().getTemp()));

            h4temp_high = view.findViewById(R.id.h4temp_high);
            h4temp_high.setText(String.valueOf(forcasts.get(3).getMain().getTempMax()));

            h4temp_low = view.findViewById(R.id.h4temp_low);
            h4temp_low.setText(String.valueOf(forcasts.get(3).getMain().getTempMin()));

            h4 = view.findViewById(R.id.h4);
            h4.setText(forcasts.get(3).getDtTxt());

            h4temp_description= view.findViewById(R.id.h4Description);
            StringBuilder sbh4 = new StringBuilder();
            sbh4.append("Description: ").append(forcasts.get(3).getWeather().get(0).getDescription()).append("\n")
                    .append("Wind speed: ").append(forcasts.get(3).getWind().getSpeed()).append("m/s").append("\n")
                    .append("Feels like: ").append(forcasts.get(3).getMain().getFeelsLike()).append("\n")
                    .append("Humidity: ").append(forcasts.get(3).getMain().getHumidity()).append("\n")
                    .append("Clouds: ").append(forcasts.get(3).getClouds().getAll() + "%");
            h4temp_description.setText(sbh4);

            TextView h5, h5temp_description;
            TextView h5temp, h5temp_high, h5temp_low;
            h5temp = view.findViewById(R.id.h5temp);
            h5temp.setText(String.valueOf(forcasts.get(4).getMain().getTemp()));

            h5temp_high = view.findViewById(R.id.h5temp_high);
            h5temp_high.setText(String.valueOf(forcasts.get(4).getMain().getTempMax()));

            h5temp_low = view.findViewById(R.id.h5temp_low);
            h5temp_low.setText(String.valueOf(forcasts.get(4).getMain().getTempMin()));

            h4 = view.findViewById(R.id.h4);
            h4.setText(forcasts.get(3).getDtTxt());

            h5 = view.findViewById(R.id.h5);
            h5.setText(forcasts.get(4).getDtTxt());

            h5temp_description= view.findViewById(R.id.h5Description);
            StringBuilder sbh5 = new StringBuilder();
            sbh5.append("Description: ")
                    .append(forcasts.get(4).getWeather().get(0).getDescription()).append("\n")
                    .append("Wind speed: ").append(forcasts.get(4).getWind().getSpeed()).append("m/s\n")
                    .append("Feels like: ").append(forcasts.get(4).getMain().getFeelsLike()).append("\n")
                    .append("Humidity: ").append(forcasts.get(4).getMain().getHumidity()).append("\n")
                    .append("Clouds: ").append(forcasts.get(4).getClouds().getAll() + "%");
            h5temp_description.setText(sbh5);

        }, error -> System.out.println(error.getMessage()));
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(stringRequest);
        return view;
    }

}