package com.example.civiclick;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;

public class CampusMapActivity extends AppCompatActivity {

    private AutoCompleteTextView fromInput, toInput;
    private Button btnGo;
    private VideoView routeVideo;
    private PhotoView campusMap;

    private final String[] buildings = {
            "SAFAD", "FR. LAWRENCE BUNZEL BUILDING", "LRC", "EDGAR OEHLER",
            "SMED BUILDING", "ENRIQUE SHOEMAN", "PHILIP ENGELEN", "ROBERT HOEPPENER",
            "MICHAEL RICHARTZ", "POPULATION OFFICE", "ALUMNI CENTER"
    };

    private final String[] markerIds = {
            "markerSafad", "markerBunzel", "markerLrc", "markerOehler",
            "markerSmed", "markerEnrique", "markerPhilip", "markerRobert",
            "markerMichael", "markerPopulation", "markerAlumni"
    };

    private final ImageView[] markers = new ImageView[markerIds.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_map);

        fromInput = findViewById(R.id.fromInput);
        toInput = findViewById(R.id.toInput);
        btnGo = findViewById(R.id.btnGo);
        routeVideo = findViewById(R.id.routeVideo);
        campusMap = findViewById(R.id.campusMap);

        for (int i = 0; i < markerIds.length; i++) {
            int resId = getResources().getIdentifier(markerIds[i], "id", getPackageName());
            markers[i] = findViewById(resId);
            markers[i].setVisibility(View.GONE);

            // Let all markers be clickable regardless of visibility
            final String building = buildings[i];
            markers[i].setOnClickListener(v -> {
                if (building.equals("FR. LAWRENCE BUNZEL BUILDING")) {
                    startActivity(new Intent(this, BunzelBuildingActivity.class));
                } else {
                    Toast.makeText(this, building + " clicked (no activity yet)", Toast.LENGTH_SHORT).show();
                }
            });
        }

        routeVideo.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, buildings);
        fromInput.setAdapter(adapter);
        toInput.setAdapter(adapter);

        fromInput.setOnItemClickListener((parent, view, position, id) -> updateMarkers());
        toInput.setOnItemClickListener((parent, view, position, id) -> {
            updateMarkers();
            btnGo.setEnabled(true);
        });

        btnGo.setOnClickListener(v -> {
            String from = fromInput.getText().toString().trim();
            String to = toInput.getText().toString().trim();

            if (from.equalsIgnoreCase(to)) {
                Toast.makeText(this, "Cannot select the same building!", Toast.LENGTH_SHORT).show();
                return;
            }

            String videoName = from.toLowerCase().replace(" ", "_") + "_to_" + to.toLowerCase().replace(" ", "_");
            int videoId = getResources().getIdentifier(videoName, "raw", getPackageName());

            if (videoId != 0) {
                routeVideo.setVisibility(View.VISIBLE);
                routeVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoId));
                routeVideo.setOnCompletionListener(mp -> routeVideo.setVisibility(View.GONE));
                routeVideo.start();
            } else {
                Toast.makeText(this, "Video not found for path.", Toast.LENGTH_SHORT).show();
            }

            toInput.setText("");
            btnGo.setEnabled(false);
        });
    }

    private void updateMarkers() {
        String from = fromInput.getText().toString();
        String to = toInput.getText().toString();

        for (int i = 0; i < buildings.length; i++) {
            boolean show = buildings[i].equalsIgnoreCase(from) || buildings[i].equalsIgnoreCase(to);
            if (markers[i] != null) {
                markers[i].setVisibility(show ? View.VISIBLE : View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (routeVideo.getVisibility() == View.VISIBLE) {
            routeVideo.stopPlayback();
            routeVideo.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
