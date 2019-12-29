package com.example.checkzip;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class RepFragment extends Fragment {

    private static final String TAG = "repElectActivity";
    private static final String API = "AIzaSyAzktmKV8TyiQpxZlZgOMb3613v2JAV6f8";
    private static final String zipCode = UserProfile.zipcode;
    private RequestQueue requestQueue;


    //Vars
    private ArrayList<String> roles = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> line1 = new ArrayList<>();
    private ArrayList<String> city = new ArrayList<>();
    private ArrayList<String> state = new ArrayList<>();
    private ArrayList<String> zip = new ArrayList<>();
    private ArrayList<String> party = new ArrayList<>();
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ArrayList<String> phone = new ArrayList<>();


    View v;
    private RecyclerView recyclerView;

    //Vars
    private ArrayList<String> headLine = new ArrayList<>();
    private ArrayList<String> mImagesUrls = new ArrayList<>();

    public RepFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_rep, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rep_recylcer);
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();

        return v;
    }

    private void parseJSON() {
        String url = "https://www.googleapis.com/civicinfo/v2/representatives?address=" + zipCode + "&includeOffices=true&levels=administrativeArea1&levels=" +
                "administrativeArea2&levels=country&levels=international&levels=locality&levels=regional&levels=special&levels=subLocality1&levels" +
                "=subLocality2&roles=deputyHeadOfGovernment&roles=executiveCouncil&roles=governmentOfficer&roles=headOfGovernment&roles=headOfState&roles=" +
                "highestCourtJudge&roles=judge&roles=legislatorLowerBody&roles=legislatorUpperBody&roles=schoolBoard&roles=specialPurposeOfficer&key=" + API;
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray office = response.getJSONArray("offices");
                            JSONArray official = response.getJSONArray("officials");

                            for (int i = 0; i < office.length(); i++) {

                                roles.add(office.getJSONObject(i).get("name").toString());
                                names.add(official.getJSONObject(i).get("name").toString());
                                line1.add(official.getJSONObject(i).getJSONArray("address").getJSONObject(0).get("line1").toString());
                                city.add(official.getJSONObject(i).getJSONArray("address").getJSONObject(0).get("city").toString());
                                state.add(official.getJSONObject(i).getJSONArray("address").getJSONObject(0).get("state").toString());
                                zip.add(official.getJSONObject(i).getJSONArray("address").getJSONObject(0).get("zip").toString());
                            }


                            RepRecyclerAdapter repRecyclerAdapter = new RepRecyclerAdapter(roles, names, getContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(repRecyclerAdapter);


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
}