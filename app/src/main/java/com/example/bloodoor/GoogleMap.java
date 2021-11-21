package com.example.bloodoor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class GoogleMap extends AppCompatActivity {

    private Spinner spType;
    private Button btFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        spType = findViewById(R.id.sp_type);
        btFind = findViewById(R.id.bt_find);

        //Initialize array of place type
        String[] placeTypeList = {"current location", "nearby blood banks", "nearby atms", "nearby banks", "nearby hospitals"};

        //Initialize array of place name
        String[] placeNameList = {"Select", "Blood Banks", "ATM", "Banks", "Hospitals"};

        //Set adapter on spinner
        spType.setAdapter(new ArrayAdapter<>(GoogleMap.this, android.R.layout.simple_spinner_dropdown_item, placeNameList));

        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = spType.getSelectedItemPosition();
                Uri uri = Uri.parse("geo:0, 0?q=" + placeTypeList[i]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }
}