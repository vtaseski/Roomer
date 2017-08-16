package com.roomer.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.roomer.activities.R;
import com.roomer.adapters.MainAdapter;
import com.roomer.data.Data;
import com.roomer.models.Apartment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyAdsActivity extends AppCompatActivity {

    public ListView lvMineApartments;

    MainAdapter mainAdapter;

    public ArrayList<Apartment> apartmentArrayList;

    boolean firstLoad = true;

    public boolean flag_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        apartmentArrayList = new ArrayList<>();
        lvMineApartments = (ListView)findViewById(R.id.lvMineApartments);

        lvMineApartments.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Apartment a = apartmentArrayList.get(position);
                Bundle extras = new Bundle();
                extras.putInt("id",a.getId());
                extras.putString("gps",a.getGPS());
                extras.putString("title",a.getTitle());
                Intent myintent = new Intent(MyAdsActivity.this, DetailsActivity.class);
                myintent.putExtras(extras);
                startActivity(myintent);
            }

        });

        flag_loading = false;

        new getMineApartments().execute();
    }

    class getMineApartments extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            // progressBar.setVisibility(View.VISIBLE);
            // responseView.setText("");
        }

        protected String doInBackground(String... params) {
            String API_URL = "http://n3mesis-001-site1.htempurl.com/";
            try {
                URL url = new URL("http://roomer2.gq/api/Apartments/Mine");
                Data d = new Data(MyAdsActivity.this);


                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    Log.i("TAG TOKEN4e", "bearer " + d.accessToken());

                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Authorization", "bearer " + d.accessToken());


                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Log.i("response", stringBuilder.toString());
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {
                Log.d("TAG MINE", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);
                        JSONObject c = o.getJSONObject("Currency");
                        JSONObject m = o.getJSONObject("MainPicture");
                        JSONObject cg = o.getJSONObject("Category");

                        Apartment a = new Apartment(
                                o.getInt("Id"),
                                o.getString("Title"),
                                o.getInt("Rooms"),
                                o.getString("GPS"),
                                o.getInt("SqMeters"),
                                o.getBoolean("Bathroom"),
                                o.getBoolean("Kitchen"),
                                o.getBoolean("Furnished"),
                                o.getString("Phone"),
                                o.getBoolean("Bedroom"),
                                o.getString("Description"),
                                o.getInt("Price"),
                                o.getInt("Floor"),
                                o.getString("Location"),
                                o.getInt("Views"),
                                o.getString("Created"),
                                c.getString("Name"),
                                m.getString("Path"),
                                cg.getString("NameMkd")
                        );

                        apartmentArrayList.add(a);
                    }


                        mainAdapter = new MainAdapter(apartmentArrayList, MyAdsActivity.this);
                        lvMineApartments.setAdapter(mainAdapter);
                        firstLoad = false;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
