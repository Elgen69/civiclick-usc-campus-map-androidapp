// This is the campus map screen
// Displays a scrollable campus map image with clickable zones (e.g., Bunzel Building)

package com.example.civiclick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CampusMapActivity extends AppCompatActivity {

    Button btnBunzel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_map);

        btnBunzel = findViewById(R.id.btnBunzel);

        btnBunzel.setOnClickListener(v -> {
            Intent i = new Intent(CampusMapActivity.this, BunzelBuildingActivity.class);
            startActivity(i);
        });
    }
}
