package com.example.checkzip;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherFragment extends Fragment implements WeatherRecyclerAdapter.OnItemClickListener {


    private static final String TAG = "Home";

    //ErrorMessage during Buffer
    TextView queryErrorMessage;

    //Vars
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> current_temp = new ArrayList<>();
    private ArrayList<String> min_temp = new ArrayList<>();
    private ArrayList<String> max_temp = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private RequestQueue requestQueue;


    View v;
    RecyclerView recyclerView;
    String zipCode = UserProfile.zipcode;
    String API = "7bbf84c06241db26372120b5cb54bd7f";


    public WeatherFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_weather, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.weather_recycler);
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void parseJSON() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?zip=" + zipCode + "&units=imperial&appid=a03ccfd93b214850782be044d4671ab9";
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("list");
                            //JSONArray

                            for (int i = 0; (i * 4) < jsonArray.length(); i++) {
                                JSONObject list = jsonArray.getJSONObject(i * 4);
                                JSONObject main = list.getJSONObject("main");
                                String cur_temp = main.get("temp").toString();
                                String low_temp = main.get("temp_min").toString();
                                String high_temp = main.get("temp_max").toString();
                                String date_var = ((jsonArray.getJSONObject(i * 4)).get("dt_txt")).toString();
                                String descrip_var = jsonArray.getJSONObject(i * 4).getJSONArray("weather").getJSONObject(0).get("description").toString();

                                description.add(descrip_var);
                                date.add(date_var);
                                min_temp.add(low_temp);
                                max_temp.add(high_temp);
                                current_temp.add(cur_temp);
                            }

                            WeatherRecyclerAdapter weatherRecyclerAdapter = new WeatherRecyclerAdapter(description, date, min_temp, max_temp, current_temp, getContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(weatherRecyclerAdapter);
                            weatherRecyclerAdapter.setOnItemClickListener(WeatherFragment.this);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getContext(), WeatherInfo.class);
        startActivity(detailIntent);
    }
}
