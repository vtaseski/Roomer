package com.roomer.activities;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

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

        ArrayAdapter<CharSequence> municipalitiesAdapter = ArrayAdapter.createFromResource(this,
                R.array.municipalities, android.R.layout.simple_spinner_item);
        threeChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSex.setAdapter(sexAdapter);
        mSearchSex.setAdapter(roommateSexAdapter);
        mSeparateRoom.setAdapter(threeChoiceAdapter);
        mFixedPrice.setAdapter(threeChoiceAdapter);
        mCategories.setAdapter(categoriesAdapter);
        mMunicipalities.setAdapter(municipalitiesAdapter);



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
        String years = mYears.getText().toString();
        String searchYears = mSearchYears.getText().toString();
        String facebook = mFacebook.getText().toString();
        String phone = mPhone.getText().toString();
        String priceFrom = mPriceFrom.getText().toString();
        String priceTo = mPriceTo.getText().toString();
        String m2From = mM2From.getText().toString();
        String m2To = mM2To.getText().toString();
        String sex = mSex.getSelectedItem().toString();
        String searchSex = mSearchSex.getSelectedItem().toString();
        String fixedPrice = mFixedPrice.getSelectedItem().toString();
        String separateRoom = mSeparateRoom.getSelectedItem().toString();
        String categories = mCategories.getSelectedItem().toString();
        String municipalities = mMunicipalities.getSelectedItem().toString();


        Log.i("all data", title + " " + description + " " + years + " " + searchSex + " " + searchYears + " " + facebook + " " + phone + " " +
                sex + " " + priceFrom + " " + priceTo + " " + m2From + " " + m2To + " " + fixedPrice + " " + separateRoom + " ");


        UploadRoommate gaa = new UploadRoommate(title);
        gaa.execute();
    }

    public class UploadRoommate extends AsyncTask<Void, Void, String> {

        String title;
        String description;
        String years;
        String searchYears;
        String facebook;
        String phone;
        String priceFrom;
        String priceTo;
        String m2From;
        String m2To;
        String sex;
        String searchSex;
        String fixedPrice;
        String separateRoom;
        String categories;
        String municipalities;

        public UploadRoommate(String title) {
//                , String description, String years, String searchYears, String facebook, String phone,
//                              String priceFrom, String priceTo, String m2From, String m2To, String sex, String searchSex, String fixedPrice,
//                              String separateRoom, String categories, String municipalties) {
            this.title = title;
//            this.description = description;
//            this.years = years;
//            this.searchYears = searchYears;
//            this.facebook = facebook;
//            this.phone = phone;
//            this.priceFrom = priceFrom;
//            this.priceTo = priceTo;
//            this.m2From = m2From;
//            this.m2To = m2To;
//            this.sex = sex;
//            this.searchSex = searchSex;
//            this.fixedPrice = fixedPrice;
//            this.separateRoom = separateRoom;
//            this.categories = categories;
//            this.municipalities = municipalties;
//
        }



        @Override
        protected String doInBackground(Void... params) {

            String data = "";

            try {
                URL url = new URL("http://n3mesis-001-site1.htempurl.com/api/Roommates/Create"); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.setRequestProperty("Content-Length", "133"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();


                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("UserId", "arg_1");
                jsonObject.put("Title", title);
                jsonObject.put("MeYears", 22);
                jsonObject.put("SearchYears", 22);
                jsonObject.put("Facebook", "facebook.com/cimer");
                jsonObject.put("Phone", "099898765");
                jsonObject.put("Description", "arg_1");
//                jsonObject.put("Created", new Date().toString());
//                jsonObject.put("ValidUntilDate", new Date().toString());
                jsonObject.put("PriceFrom", 100);
                jsonObject.put("PriceTo", 200);
                jsonObject.put("M2From", 0);
                jsonObject.put("M2To", 200);
                jsonObject.put("SeparateRoom", true);
                jsonObject.put("FixedPrice", false);
//                jsonObject.put("Municipalities", "arg_1");

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
        }
    }
}
