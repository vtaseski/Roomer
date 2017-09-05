package com.roomer.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.roomer.activities.R;

import static com.roomer.activities.R.id.view;

public class AddAdsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE=1;
    private Spinner currencySpinner;
    private Spinner categoriesSpinner;
    private Spinner municipalitiesSpinner;
    private Spinner tipSpinner;
    private ImageView imageView;
    private Button selectButton;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView=(ImageView) findViewById(R.id.imageView3);
        selectButton=(Button) findViewById(R.id.selectButton);
        uploadButton=(Button) findViewById(R.id.uploadButton);

        imageView.setOnClickListener(this);
        selectButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage=data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView3:
                break;
            case R.id.selectButton:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;
            case R.id.uploadButton:
                imageView.setImageResource(R.color.cardview_shadow_end_color);
                Snackbar.make(v, "Сликата е прикачена", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }
}
