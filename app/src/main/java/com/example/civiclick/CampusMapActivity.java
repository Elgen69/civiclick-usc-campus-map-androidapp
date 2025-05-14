package com.example.civiclick;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class CampusMapActivity extends AppCompatActivity {

    private AutoCompleteTextView fromInput, toInput;
    private Button btnGo;
    private VideoView routeVideo;
    private ImageView campusMap;

    private ImageView markerBunzel, markerSafad, markerLrc, markerOehler, markerEnrique,
            markerMain, markerPhilip, markerRobert, markerMichael, markerSmed;

    private final String[] buildings = {
            "SAFAD", "FR. LAWRENCE BUNZEL BUILDING",
            "LRC", "EDGAR OEHLER", "ENRIQUE", "POPULATION", "PHILIP",
            "ROBERT", "MICHAEL", "SMED", "ALUMNI"
    };

    private final Map<String, String> buildingKeys = new HashMap<String, String>() {{
        put("FR. LAWRENCE BUNZEL BUILDING", "bunzel");
        put("SAFAD", "safad");
        put("LRC", "lrc");
        put("EDGAR OEHLER", "oehler");
        put("ENRIQUE", "enrique");
        put("POPULATION", "population");
        put("PHILIP", "philip");
        put("ROBERT", "robert");
        put("MICHAEL", "michael");
        put("SMED", "smed");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_map);

        fromInput = findViewById(R.id.fromInput);
        toInput = findViewById(R.id.toInput);
        btnGo = findViewById(R.id.btnGo);
        routeVideo = findViewById(R.id.routeVideo);
        campusMap = findViewById(R.id.campusMap);

        markerBunzel = findViewById(R.id.markerBunzel);
        markerSafad = findViewById(R.id.markerSafad);
        markerLrc = findViewById(R.id.markerLrc);
        markerOehler = findViewById(R.id.markerOehler);
        markerEnrique = findViewById(R.id.markerEnrique);
        markerMain = findViewById(R.id.markerPopulation);
        markerPhilip = findViewById(R.id.markerPhilip);
        markerRobert = findViewById(R.id.markerRobert);
        markerMichael = findViewById(R.id.markerMichael);
        markerSmed = findViewById(R.id.markerSmed);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, buildings);
        fromInput.setAdapter(adapter);
        toInput.setAdapter(adapter);

        fromInput.setOnItemClickListener((parent, view, position, id) -> updateMarkers());
        toInput.setOnItemClickListener((parent, view, position, id) -> {
            updateMarkers();
            btnGo.setEnabled(true);
        });

        btnGo.setOnClickListener(v -> playRouteVideo());

        // MARKER BEHAVIOR
        markerBunzel.setOnClickListener(v -> {
            Intent i = new Intent(this, BunzelBuildingActivity.class);
            startActivity(i);
        });

        markerSafad.setOnClickListener(v -> showUnavailableToast());
        markerLrc.setOnClickListener(v -> showUnavailableToast());
        markerOehler.setOnClickListener(v -> showUnavailableToast());
        markerEnrique.setOnClickListener(v -> showUnavailableToast());
        markerMain.setOnClickListener(v -> showUnavailableToast());
        markerPhilip.setOnClickListener(v -> showUnavailableToast());
        markerRobert.setOnClickListener(v -> showUnavailableToast());
        markerMichael.setOnClickListener(v -> showUnavailableToast());
        markerSmed.setOnClickListener(v -> showUnavailableToast());

        routeVideo.setVisibility(View.GONE);
    }

    private void showUnavailableToast() {
        Toast.makeText(this, "No floor plans/navigation available yet for this building.", Toast.LENGTH_SHORT).show();
    }

    private void updateMarkers() {
        String from = fromInput.getText().toString().trim().toUpperCase();
        String to = toInput.getText().toString().trim().toUpperCase();

        setMarkerVisibility(markerBunzel, from, to, "FR. LAWRENCE BUNZEL BUILDING");
        setMarkerVisibility(markerSafad, from, to, "SAFAD");
        setMarkerVisibility(markerLrc, from, to, "LRC");
        setMarkerVisibility(markerOehler, from, to, "EDGAR OEHLER");
        setMarkerVisibility(markerEnrique, from, to, "ENRIQUE");
        setMarkerVisibility(markerMain, from, to, "POPULATION");
        setMarkerVisibility(markerPhilip, from, to, "PHILIP");
        setMarkerVisibility(markerRobert, from, to, "ROBERT");
        setMarkerVisibility(markerMichael, from, to, "MICHAEL");
        setMarkerVisibility(markerSmed, from, to, "SMED");
    }

    private void setMarkerVisibility(ImageView marker, String from, String to, String name) {
        if (from.equals(name) || to.equals(name)) {
            marker.setVisibility(View.VISIBLE);
        } else {
            marker.setVisibility(View.GONE);
        }
    }

    private void playRouteVideo() {
        String from = fromInput.getText().toString().trim();
        String to = toInput.getText().toString().trim();

        if (from.equalsIgnoreCase(to)) {
            Toast.makeText(this, "Cannot select the same building!", Toast.LENGTH_SHORT).show();
            return;
        }

        String fromKey = buildingKeys.getOrDefault(from.toUpperCase(), "");
        String toKey = buildingKeys.getOrDefault(to.toUpperCase(), "");

        if (fromKey.isEmpty() || toKey.isEmpty()) {
            Toast.makeText(this, "Invalid route selected", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoName = fromKey + "_to_" + toKey;
        int videoId = getResources().getIdentifier(videoName, "raw", getPackageName());

        if (videoId != 0) {
            routeVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoId));
            routeVideo.setOnPreparedListener(mp -> mp.setLooping(true));
            routeVideo.start();

            // Keep campusMap visible to retain marker overlays
            routeVideo.setVisibility(View.VISIBLE);
            // campusMap stays visible
        } else {
            Toast.makeText(this, "Video not found", Toast.LENGTH_SHORT).show();
        }

        toInput.setText("");
        btnGo.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (routeVideo.getVisibility() == View.VISIBLE) {
            routeVideo.stopPlayback();
            routeVideo.setVisibility(View.GONE);
            campusMap.setVisibility(View.VISIBLE); // just in case
        } else {
            super.onBackPressed();
        }
    }
}
