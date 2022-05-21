package com.example.weatherforecastapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class FragmentB extends Fragment {

    TextView tvResult;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "c6160b2b1a3875ce4a97ee733caf5d34";

    private final String url5day = "https://api.openweathermap.org/data/2.5/forecast";

    String city;

    DecimalFormat df = new DecimalFormat("#.##");

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        city = getArguments().getString("city");

        //tvResult = view.findViewById(R.id.tvResult);

        getWeatherFiveDays();
        //getWeatherDetails();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_b, container, false);
        return view;
    }

    public void getWeatherFiveDays()
    {
        String tmpURL = "";

        if(!city.equals(""))
        {
            tmpURL = url5day + "?q=" + city + "," + "Portugal" + "&appid=" + apiKey;
            //tmpURL = url + "?q=" + city + "&appid=" + apiKey;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tmpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                tvResult = view.findViewById(R.id.tvResult);

                String output = "";

                for (int i=0; i<10; i++)
                {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("list");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(i);
                        String date = jsonObjectWeather.getString("dt_txt");
                        JSONObject jsonObjectMain = jsonObjectWeather.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonObjectWeather.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonObjectWeather.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        //JSONObject jsonObjectSys = jsonObjectWeather.getJSONObject("sys");
                        //String countryName = jsonObjectSys.getString("country");
                        //String cityName = jsonResponse.getString("name");
                        output += "Previsão metereológica para " + date + " em " + city + " (" + "PT" + "):\n"
                                + "\n - Temp: " + df.format(temp) + " °C"
                                + "\n - Sensação: " + df.format(feelsLike) + " °C"
                                + "\n - Humidade: " + humidity + "%"
                                + "\n - Velocidade do Vento: " + wind + "m/s (metros por segundo)"
                                + "\n - Nebulosidade: " + clouds + "%"
                                + "\n - Pressão Atmosférica: " + pressure + " hPa\n"
                                + "--------------------------------------------------\n\n";
                        tvResult.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getCause().toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getWeatherDetails()
    {
        String tmpURL = "";

        if(!city.equals(""))
        {
            tmpURL = url + "?q=" + city + "," + "Portugal" + "&appid=" + apiKey;
            //tmpURL = url + "?q=" + city + "&appid=" + apiKey;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tmpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                tvResult = view.findViewById(R.id.tvResult);

                String output = "";

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");
                    output += "Current weather of " + cityName + " (" + countryName + ")"
                            + "\n Temp: " + df.format(temp) + " °C"
                            + "\n Feels Like: " + df.format(feelsLike) + " °C"
                            + "\n Humidity: " + humidity + "%"
                            + "\n Description: " + description
                            + "\n Wind Speed: " + wind + "m/s (meters per second)"
                            + "\n Cloudiness: " + clouds + "%"
                            + "\n Pressure: " + pressure + " hPa";
                    tvResult.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getCause().toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}