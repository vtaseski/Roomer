package com.roomer.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roomer.activities.R;
import com.roomer.adapters.MainAdapter;
import com.roomer.models.Apartment;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<String> picturesPath;
    CarouselView carouselView;
    String gps;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //setTitle("My new title");
        picturesPath=new ArrayList<String>();
        Bundle extras = getIntent().getExtras();

        int s = extras.getInt("id");
        gps=extras.getString("gps");
        String title=extras.getString("title");
        setTitle(title);
        Log.i("GPSSS", gps);


        DetailsActivity.getDetails gaa = new DetailsActivity.getDetails();
        gaa.execute("api/Apartments/Get/" + s);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Log.i("Kraaj ee", picturesPath.get(position));
            Glide.with(DetailsActivity.this).load("http://n3mesis-001-site1.htempurl.com/Images/Apartments/"+picturesPath.get(position)).into(imageView);
        }
    };

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        String[] latlong =  gps.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng location = new LatLng(latitude, longitude);

        googleMap.addMarker(new MarkerOptions().position(location).title("Локација на станот"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }


    class getDetails extends AsyncTask<String, Void, String> {

        private Exception exception;
        boolean flag;
        protected void onPreExecute() {
            // progressBar.setVisibility(View.VISIBLE);
            // responseView.setText("");
        }

        protected String doInBackground(String... params) {
            String API_URL = "http://n3mesis-001-site1.htempurl.com/";
            try {
                URL url = new URL(API_URL + params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
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
            Apartment a;
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {;
                Log.i("Details", response);


                try {
                    {
                        JSONObject o = new JSONObject(response);

                        JSONObject c = o.getJSONObject("Currency");
                        JSONObject m = o.getJSONObject("MainPicture");
                        JSONArray  pictures= o.getJSONArray("Pictures");
                        JSONObject cg = o.getJSONObject("Category");

                        a = new Apartment(
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


                        for (int i = 0; i < pictures.length(); i++) {

                            JSONObject p = pictures.getJSONObject(i);
                            String s=p.getString("Path");

                            picturesPath.add(s);
                        }
                    }
                    carouselView = (CarouselView) findViewById(R.id.carouselView);
                    carouselView.setImageListener(imageListener);
                    carouselView.setPageCount(picturesPath.size());
                    TextView txtRoomsNumber=(TextView) findViewById(R.id.txtRoomsNumber);
                    txtRoomsNumber.setText("Бр. на соби:   " + a.getRooms());
                    TextView txtFloor=(TextView) findViewById(R.id.txtFloor);
                    txtFloor.setText("Спрат:   " + a.getFloor());
                    TextView txtPriceNumber=(TextView) findViewById(R.id.txtPriceNumber);
                    txtPriceNumber.setText("Цена:   " + a.getPrice()+ "  " + a.getCurrency());

                    TextView txtIsFurnished=(TextView) findViewById(R.id.txtIsFurnished);
                    txtIsFurnished.setText("Наместен:  " + (a.isFurnished() ? "Да" : "Не"));
                    TextView txtSqMetersNumbers=(TextView) findViewById(R.id.txtSqMetersNumber);
                    txtSqMetersNumbers.setText("Големина:  " + a.getSqMeters()+ " m2");
                    TextView txtBathroom=(TextView) findViewById(R.id.txtBathroom);
                    txtBathroom.setText("WC:  " + (a.isBathroom() ? "Да" : "Не"));
                    TextView txtKitchen=(TextView) findViewById(R.id.txtKitchen);
                    txtKitchen.setText("Кујна:  " + (a.isKitchen() ? "Да" : "Не"));
                    TextView txtLocation1=(TextView) findViewById(R.id.txtLocation1);
                    txtLocation1.setText("Локација:  " + a.getLocation());
                    TextView txtPhone=(TextView) findViewById(R.id.txtPhone);
                    txtPhone.setText("Тел: "+ a.getPhone());
                    TextView txtCreated=(TextView) findViewById(R.id.txtCreated);
                    txtCreated.setText(a.getCreated().split("T")[0]);
                    TextView txtDescription= (TextView) findViewById(R.id.txtDescription);
                    txtDescription.setText("Опис:  \n" + a.getDescription());



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
