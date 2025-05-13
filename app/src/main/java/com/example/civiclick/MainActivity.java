// This is for the home page of the app
// Handles splash, app title, and navigation buttons like “View Campus Map”

package com.example.civiclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnMap, btnCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap = findViewById(R.id.btnMap);
        btnCredits = findViewById(R.id.btnCredits);

        btnMap.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CampusMapActivity.class);
            startActivity(i);
        });

//        btnCredits.setOnClickListener(v -> {
//            Intent i = new Intent(MainActivity.this, CreditsActivity.class);
//            startActivity(i);
//        });
    }
}
