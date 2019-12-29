package com.example.checkzip;

import android.os.AsyncTask;
import android.widget.TextView;

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

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed = "";
    String zipurl="";
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL(zipurl);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject obj = new JSONObject(data);

            JSONArray jsonArray = new JSONArray();
            jsonArray = obj.getJSONObject("datafinder").getJSONArray("results");
            for(int i =0; i<jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                //singleParsed = "County: "+jsonObject.getString("County")+"\n";
                 singleParsed = "Zipcode: "+jsonObject.getString("Zip5")+"\n"+
                        "County: "+jsonObject.getString("County")+"\n"+
                         "Latitude: "+jsonObject.getString("Latitude")+"\n"+
                        "Longitude: "+jsonObject.getString("Longitude")+"\n"+
                        "Population Urban Area: "+jsonObject.getString("PopulationUrbanArea")+"\n"+
                        "SquareMiles: "+jsonObject.getString("SquareMiles")+"\n"+
                        "State: "+jsonObject.getString("State")+"\n"+
                        "Total Household with Earnings " +jsonObject.getString("TotalHhldswithEarnings")+"\n"+
                         "CBSAVerbose: "+jsonObject.getString("CBSAVerbose")+"\n"+
                         "Households 11 2018: "+jsonObject.getString("Hhlds112018")+"\n"+
                         "Population 11 2018:"+jsonObject.getString("Population112018")+"\n"+
                         "Population Density: "+jsonObject.getString("PopulationDensity")+"\n"+
                         "Population Urban Area: "+jsonObject.getString("PopulationUrbanArea")+"\n"+
                         "Population Asian:  "+jsonObject.getString("PopulationNotHispanicAsianAlone")+"\n"+
                         "Population Black: "+jsonObject.getString("PopulationNotHispanicBlackAlone")+"\n"+
                         "Population Native American: "+jsonObject.getString("PopulationNotHispanicNativeAmericanAlone")+"\n"+
                         "Population Other Race Alone: "+jsonObject.getString("PopulationNotHispanicOtherRaceAlone")+"\n"+
                         "Population Twoor More Races:"+jsonObject.getString("PopulationNotHispanicTwoorMoreRaces")+"\n"+
                         "Population White Alone: "+jsonObject.getString("PopulationNotHispanicWhiteAlone")+"\n"+
                         "Population Not Hispanic "+jsonObject.getString("PopulationNotHispanic")+"\n";



                dataParsed = dataParsed+ singleParsed+"\n";


            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //TextView textView = find
        zipcheck.age.setText(this.dataParsed);
       // Home.info.setText(this.dataParsed);
        //textView.setText(this.dataParsed);
        //MainFragment.textView.setText(this.dataParsed);
        //news_header.seText(this.dataParsed);
    }
    public void setZipcode(String zipcode){
        //zipurl="https://api.myjson.com/bins/pit9s";
        zipurl = "https://api.datafinder.com/v2/qdf.php?service=zip5&k2=1fmnw5ifn7c0nrxdl3ua28nz&service=zip5&output=json&d_zip="+zipcode;
    }
}
