package com.roomer.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.roomer.activities.R;

public class AddAdsActivity extends AppCompatActivity {
    private Spinner currencySpinner;
    private Spinner categoriesSpinner;
    private Spinner municipalitiesSpinner;
    private Spinner tipSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currencySpinner=(Spinner) findViewById(R.id.PriceSpinner);
        categoriesSpinner=(Spinner) findViewById(R.id.Category);
        municipalitiesSpinner= (Spinner) findViewById(R.id.Municipality);
        tipSpinner=(Spinner) findViewById(R.id.Tip);

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> municipalitiesAdapter = ArrayAdapter.createFromResource(this,
                R.array.municipalities, android.R.layout.simple_spinner_item);
        municipalitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> tipAdapter = ArrayAdapter.createFromResource(this,
                R.array.tip, android.R.layout.simple_spinner_item);
        tipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        municipalitiesSpinner.setAdapter(municipalitiesAdapter);
        categoriesSpinner.setAdapter(categoriesAdapter);
        tipSpinner.setAdapter(tipAdapter);
        currencySpinner.setAdapter(currencyAdapter);
    }

}
