package com.roomer.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.roomer.adapters.Main2Adapter;
import com.roomer.adapters.MainAdapter;
import com.roomer.models.Apartment;
import com.roomer.models.Roommate;

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
 * {@link RoommatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoommatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoommatesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListView lvRoomMates;

    Main2Adapter main2Adapter;

    public ArrayList<Roommate> roommateArrayList;

    boolean firstLoad = true;

    public boolean flag_loading;

    public int skip = 0;

    private OnFragmentInteractionListener mListener;

    public RoommatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoommatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoommatesFragment newInstance(String param1, String param2) {
        RoommatesFragment fragment = new RoommatesFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roommates, container, false);
        roommateArrayList = new ArrayList<>();

        flag_loading = false;
        lvRoomMates = (ListView)view.findViewById(R.id.lvRoomMates);
        new getRoomMates().execute("api/Roommates/Get?take=6");

        return view;
    }
    public void addItems() {
        skip += 3;
        String skipString = skip + "";
        new getRoomMates().execute("api/Roommates/Get?skip=" + skipString + "&take=4");
    }

    class getRoomMates extends AsyncTask<String, Void, String> {

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
                        JSONObject u = o.getJSONObject("User");




                        Roommate r = new Roommate (
                                o.getInt("Id"),
                                o.getString("UserId"),
                                o.getString("Title"),
                                o.getString("Facebook"),
                                o.getString("Phone"),
                                o.getString("Description"),
                                o.getString("Created"),
                                o.getInt("PriceFrom"),
                                o.getInt("PriceTo"),
                                o.getInt("M2From"),
                                o.getInt("M2To"),
                                o.getBoolean("SeparateRoom"),
                                o.getBoolean("FixedPrice"),
                                u.getString("FirstName"),
                                u.getString("LastName"),
                                u.getString("PicturePath")


                        );
                        roommateArrayList.add(r);
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                        main2Adapter = new Main2Adapter(roommateArrayList, getActivity());
                        lvRoomMates.setAdapter(main2Adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
