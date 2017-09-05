package com.roomer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.roomer.activities.R;
import com.roomer.models.Apartment;
import com.roomer.models.Roommate;

import java.util.ArrayList;

/**
 * Created by antoniotodorov on 8/12/17.
 */

public class Main2Adapter extends BaseAdapter {


    public ArrayList<Roommate>  roomatesList;

    public Context context;

    public LayoutInflater inflater = null;

    @Override
    public int getCount() {
        return roomatesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Main2Adapter(ArrayList<Roommate> apps, Context context) {
        this.roomatesList = apps;
        this.context = context;
    }
    public class ViewHolder {
        ImageView rmmImage;
        TextView rmmTitle, rmmName, rmmPrice, rmmPhone, rmmDate, rmmSeparateRoom, rmmSqMeters, rmmDescription, rmmMunicipality;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Main2Adapter.ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.roommate, parent,false);
            // configure view holder
            viewHolder = new Main2Adapter.ViewHolder();
            viewHolder.rmmImage = (ImageView) rowView.findViewById(R.id.rmmImage);
            viewHolder.rmmTitle = (TextView) rowView.findViewById(R.id.rmmTitle);
            viewHolder.rmmName = (TextView) rowView.findViewById(R.id.rmmName);
            viewHolder.rmmPrice = (TextView) rowView.findViewById(R.id.rmmPrice);
            viewHolder.rmmSeparateRoom=(TextView) rowView.findViewById(R.id.rmmSeparateRoom);
            viewHolder.rmmSqMeters=(TextView) rowView.findViewById(R.id.rmmSqMeters);
            viewHolder.rmmDate=(TextView) rowView.findViewById(R.id.rmmDate);
            viewHolder.rmmPhone=(TextView) rowView.findViewById(R.id.rmmPhone);
            viewHolder.rmmDescription=(TextView) rowView.findViewById(R.id.rmmDescription);
            viewHolder.rmmMunicipality=(TextView) rowView.findViewById(R.id.rmmMunicipality);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (Main2Adapter.ViewHolder) convertView.getTag();
        }

        viewHolder.rmmTitle.setText(roomatesList.get(position).getTitle()+"");
        viewHolder.rmmName.setText(roomatesList.get(position).getFirstName()+" " + roomatesList.get(position).getSecondName());
        viewHolder.rmmPrice.setText(roomatesList.get(position).getPriceFrom() + "eur.  -  " + roomatesList.get(position).getPriceTo()+"eur.");
        viewHolder.rmmSqMeters.setText(roomatesList.get(position).getM2From() + "  -  " + roomatesList.get(position).getM2To());
        viewHolder.rmmSeparateRoom.setText(roomatesList.get(position).isSeparateRoom() ? "Да" : "Не");
        viewHolder.rmmDate.setText(roomatesList.get(position).getCreated().split("T")[0] + " " + roomatesList.get(position).getCreated().split("T")[1].substring(0,5) + " ч");
        viewHolder.rmmPhone.setText(roomatesList.get(position).getPhone());
        viewHolder.rmmDescription.setText(roomatesList.get(position).getDesctiption());
        viewHolder.rmmMunicipality.setText(roomatesList.get(position).getMunicipality());

        return rowView;

    }
}
