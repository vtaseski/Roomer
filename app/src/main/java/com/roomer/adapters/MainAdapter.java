package com.roomer.adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomer.activities.R;
import com.roomer.models.Apartment;


import com.bumptech.glide.Glide;


import java.util.ArrayList;

/**
 * Created by vladotaseski on 7/18/17.
 */

public class MainAdapter extends BaseAdapter {

    public ArrayList<Apartment> aparmentList;

    public Context context;

    public LayoutInflater inflater = null;


    public class ViewHolder {
        ImageView imgMain;
        TextView title, rooms, sqMeters, price, furnished, location, category;


    }

    public MainAdapter(ArrayList<Apartment> apps, Context context) {
        this.aparmentList = apps;
        this.context = context;
    }

    @Override
    public int getCount() {
        return aparmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.card_aparment, parent,false);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.imgMain = (ImageView) rowView.findViewById(R.id.imgMain);
            viewHolder.title = (TextView) rowView.findViewById(R.id.txtTitle);
            viewHolder.sqMeters = (TextView) rowView.findViewById(R.id.txtSqMeters);
            viewHolder.rooms = (TextView) rowView.findViewById(R.id.txtRooms);
            viewHolder.furnished = (TextView) rowView.findViewById(R.id.txtFurnished);
            viewHolder.price = (TextView) rowView.findViewById(R.id.txtPrice);
            viewHolder.location = (TextView) rowView.findViewById(R.id.txtLocation);
            viewHolder.category = (TextView) rowView.findViewById(R.id.txtCategory);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(aparmentList.get(position).getTitle()+"");
        viewHolder.sqMeters.setText(aparmentList.get(position).getSqMeters()+" m2");
        viewHolder.rooms.setText(aparmentList.get(position).getRooms() + " соби");
        viewHolder.furnished.setText(aparmentList.get(position).isFurnished() ? "Наместен" : "Ненаместен");
        viewHolder.price.setText(aparmentList.get(position).getPrice() + " " + aparmentList.get(position).getCurrency());
        viewHolder.location.setText(aparmentList.get(position).getLocation());
        viewHolder.category.setText(aparmentList.get(position).getCategory());

        Glide.with(context).load("http://n3mesis-001-site1.htempurl.com/Images/Apartments/" + aparmentList.get(position).getMainPicture()).into(viewHolder.imgMain);

        return rowView;

    }
}