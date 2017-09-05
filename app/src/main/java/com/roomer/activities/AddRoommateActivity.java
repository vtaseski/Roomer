package com.roomer.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.roomer.activities.R;
import com.roomer.data.Data;
import com.roomer.fragments.RoommatesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddRoommateActivity extends AppCompatActivity {

    private AutoCompleteTextView mTitle;
    private AutoCompleteTextView mDescription;
    private AutoCompleteTextView mYears;
    private AutoCompleteTextView mSearchYears;
    private AutoCompleteTextView mFacebook;
    private AutoCompleteTextView mPhone;
    private AutoCompleteTextView mPriceFrom;
    private AutoCompleteTextView mPriceTo;
    private AutoCompleteTextView mM2From;
    private AutoCompleteTextView mM2To;
    private Spinner mSeparateRoom;
    private Spinner mFixedPrice;
    private Spinner mSex;
    private Spinner mSearchSex;
    private Spinner mCategories;
    private Spinner mMunicipalities;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_roommate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mTitle = (AutoCompleteTextView) findViewById(R.id.txtTitle);
        mDescription = (AutoCompleteTextView) findViewById(R.id.txtDescription);
        mYears = (AutoCompleteTextView) findViewById(R.id.txtYears);
        mSearchSex = (Spinner) findViewById(R.id.spnRoommateSex);
        mSex = (Spinner) findViewById(R.id.spnSex);
        mFixedPrice = (Spinner) findViewById(R.id.spnFixPrice);
        mSeparateRoom = (Spinner) findViewById(R.id.spnSeparateRoom);
        mSearchYears = (AutoCompleteTextView) findViewById(R.id.txtRoommateYears);
        mFacebook = (AutoCompleteTextView) findViewById(R.id.txtFacebook);
        mPhone = (AutoCompleteTextView) findViewById(R.id.txtPhone);
        mPriceFrom = (AutoCompleteTextView) findViewById(R.id.txtPriceFrom);
        mPriceTo = (AutoCompleteTextView) findViewById(R.id.txtToPrice);
        mM2From = (AutoCompleteTextView) findViewById(R.id.txtFromSqlMeters);
        mM2To = (AutoCompleteTextView) findViewById(R.id.txtToSqlMeters);
        mCategories = (Spinner) findViewById(R.id.spnCategory);
        mMunicipalities = (Spinner) findViewById(R.id.spnMunicipality);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        Spinner sexSpinner = (Spinner) findViewById(R.id.spnSex);
        Spinner roommateSex = (Spinner) findViewById(R.id.spnRoommateSex);

        Spinner separateRoomSpinner = (Spinner) findViewById(R.id.spnSeparateRoom);
        Spinner fixPrice = (Spinner) findViewById(R.id.spnFixPrice); */



        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> threeChoiceAdapter = ArrayAdapter.createFromResource(this,
                R.array.threeChoice, android.R.layout.simple_spinner_item);
        threeChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> roommateSexAdapter = ArrayAdapter.createFromResource(this,
                R.array.roommateSex, android.R.layout.simple_spinner_item);
        threeChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        threeChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        ArrayAdapter<CharSequence> municipalitiesAdapter = ArrayAdapter.createFromResource(this,
//                R.array.municipalities, android.R.layout.simple_spinner_item);
//        threeChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSex.setAdapter(sexAdapter);
        mSearchSex.setAdapter(roommateSexAdapter);
        mSeparateRoom.setAdapter(threeChoiceAdapter);
        mFixedPrice.setAdapter(threeChoiceAdapter);
        mCategories.setAdapter(categoriesAdapter);
//        mMunicipalities.setAdapter(municipalitiesAdapter);



        Button addRoommate = (Button) findViewById(R.id.btnCreateRoommate);
        addRoommate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRoommate();
            }
        });
    }

    public void addNewRoommate() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        int years = Integer.parseInt(mYears.getText().toString());
        int searchYears = Integer.parseInt(mSearchYears.getText().toString());
        String facebook = mFacebook.getText().toString();
        String phone = mPhone.getText().toString();
        int priceFrom = Integer.parseInt(mPriceFrom.getText().toString());
        int priceTo = Integer.parseInt(mPriceTo.getText().toString());
        int m2From = Integer.parseInt(mM2From.getText().toString());
        int m2To = Integer.parseInt(mM2To.getText().toString());
        String sex = mSex.getSelectedItem().toString();
        String searchSex = mSearchSex.getSelectedItem().toString();
        boolean fixedPrice = mFixedPrice.getSelectedItem().toString().contains("Да") ? true : false;
        boolean separateRoom = mSeparateRoom.getSelectedItem().toString().contains("Да") ? true : false;
        String categories = mCategories.getSelectedItem().toString();
        String municipalities = mMunicipalities.getSelectedItem().toString();


        String municipalityCode = getResources().getStringArray(R.array.municipalitiesValues)[mMunicipalities.getSelectedItemPosition()];

        Log.i("all data ", municipalityCode + " " + description + " " + years + " " + searchSex + " " + searchYears + " " + facebook + " " + phone + " " +
                sex + " " + priceFrom + " " + priceTo + " " + m2From + " " + m2To + " " + fixedPrice + " " + separateRoom + " ");


        UploadRoommate addNewRoommate = new UploadRoommate(title, description, years, searchYears, facebook, phone, priceFrom, priceTo, m2From, m2To,
                sex, searchSex, fixedPrice, separateRoom, categories, municipalities, municipalityCode);
        addNewRoommate.execute();
    }

    public class UploadRoommate extends AsyncTask<Void, Void, String> {


        String title;
        String description;
        int years;
        int searchYears;
        String facebook;
        String phone;
        int priceFrom;
        int priceTo;
        int m2From;
        int m2To;
        String sex;
        String searchSex;
        boolean fixedPrice;
        boolean separateRoom;
        String categories;
        String municipalities;
        String municipalityCode;

        public UploadRoommate(String title, String description, int years, int searchYears, String facebook, String phone,
                              int priceFrom, int priceTo, int m2From, int m2To, String sex, String searchSex, boolean fixedPrice,
                              boolean separateRoom, String categories, String municipalties, String municipalityCode) {
            this.title = title;
            this.description = description;
            this.years = years;
            this.searchYears = searchYears;
            this.facebook = facebook;
            this.phone = phone;
            this.priceFrom = priceFrom;
            this.priceTo = priceTo;
            this.m2From = m2From;
            this.m2To = m2To;
            this.sex = sex;
            this.searchSex = searchSex;
            this.fixedPrice = fixedPrice;
            this.separateRoom = separateRoom;
            this.categories = categories;
            this.municipalities = municipalties;
            this.municipalityCode = municipalityCode;
        }

        @Override
        protected String doInBackground(Void... params) {

            String data = "";
            Data d = new Data(getApplicationContext());
            try {
                URL url = new URL("http://n3mesis-001-site1.htempurl.com/api/Roommates/Create"); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
//                httpURLConnection.setRequestProperty("Authorization", "bearer " + d.accessToken()); // here you are setting the `Content-Type` for the data you are sending which is `application/json`

                httpURLConnection.connect();


                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject JSONmunicipality = new JSONObject();

                JSONmunicipality.put("Iso31662", municipalityCode);
                JSONmunicipality.put("NameMkd", municipalities);

                jsonArray.put(JSONmunicipality);



//                jsonObject.put("UserId", "arg_1");
                jsonObject.put("Title", title);
                jsonObject.put("MeYears", years);
                jsonObject.put("SearchYears", searchYears);
                jsonObject.put("Facebook", facebook);
                jsonObject.put("Phone", phone);
                jsonObject.put("Description", description);
//                jsonObject.put("Created", new Date().toString());
//                jsonObject.put("ValidUntilDate", new Date().toString());
                jsonObject.put("PriceFrom", priceFrom);
                jsonObject.put("PriceTo", priceTo);
                jsonObject.put("M2From", m2From);
                jsonObject.put("M2To", m2To);
                jsonObject.put("SeparateRoom", separateRoom);
                jsonObject.put("FixedPrice", fixedPrice);
                jsonObject.put("Municipalities", jsonArray);

                Log.e("TAG JSON", jsonObject.toString());



                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
            Intent i  = new Intent (AddRoommateActivity.this, MainActivity.class);
            i.putExtra("ActiveFragment", 1);
            startActivity(i);



        }
    }
}
