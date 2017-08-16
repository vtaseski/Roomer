package com.roomer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.roomer.activities.DetailsActivity;
import com.roomer.activities.R;
import com.roomer.adapters.MainAdapter;
import com.roomer.models.Apartment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FlatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FlatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlatsFragment extends Fragment {

    public ListView lvApartments;

    MainAdapter mainAdapter;

    public ArrayList<Apartment> apartmentArrayList;

    boolean firstLoad = true;

    public boolean flag_loading;

    public int skip = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FlatsFragment() {
        // Required empty public constructor
    }

    public static FlatsFragment newInstance(String param1, String param2) {
        FlatsFragment fragment = new FlatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_flats, container, false);

        apartmentArrayList = new ArrayList<>();

        flag_loading = false;
        lvApartments = (ListView)view.findViewById(R.id.lvApartments);
        lvApartments.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("test", "on scrool");
            }
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
                    if(flag_loading == false) {
                        flag_loading = true;
                        addItems();
                    }
                }
            }
        });

        lvApartments.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Apartment a = apartmentArrayList.get(position);
                Bundle extras = new Bundle();
                extras.putInt("id",a.getId());
                extras.putString("gps",a.getGPS());
                extras.putString("title",a.getTitle());
                Intent myintent = new Intent(getActivity(), DetailsActivity.class);
                myintent.putExtras(extras);
                Log.e("Slikaa", a.getMainPicture());
                startActivity(myintent);
            }

        });


        getAllApartments gaa = new getAllApartments();
        gaa.execute("api/Apartments?take=3");
        return view;

    }

    public void addItems() {
        skip += 3;
        String skipString = skip + "";
        new getAllApartments().execute("api/Apartments?skip=" + skipString + "&take=4");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    class getAllApartments extends AsyncTask<String, Void, String> {

        private Exception exception;
        private ProgressDialog dialog = new ProgressDialog(getActivity());

        protected void onPreExecute() {
            // progressBar.setVisibility(View.VISIBLE);
            // responseView.setText("");
            this.dialog.setMessage("Please wait");
            this.dialog.show();
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
            Log.i("INFO", response);
            if(response == null) {
                response = "THERE WAS AN ERROR";
            } else {;
                Log.i("INFO", response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);
                        JSONObject c = o.getJSONObject("Currency");
                        JSONObject m = o.getJSONObject("MainPicture");
                        JSONObject cg = o.getJSONObject("Category");

                        Apartment a = new Apartment (
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
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if(firstLoad) {
                        mainAdapter = new MainAdapter(apartmentArrayList, getActivity());
                        lvApartments.setAdapter(mainAdapter);
                        firstLoad = false;
                    }

                    flag_loading = false;
                    mainAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
