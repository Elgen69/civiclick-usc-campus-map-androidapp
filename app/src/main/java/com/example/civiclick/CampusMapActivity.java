package com.example.civiclick;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.HashMap;
import java.util.Map;

public class CampusMapActivity extends AppCompatActivity {

    private AutoCompleteTextView fromInput, toInput;
    private Button btnGo;
    private VideoView routeVideo;
    private PhotoView campusMap;
    private ImageView markerBunzel, markerSafad;
    private FrameLayout markerOverlay;

    private final String[] buildings = {
            "SAFAD", "FR. LAWRENCE BUNZEL BUILDING"
    };

    private final Map<String, String> buildingKeys = new HashMap<String, String>() {{
        put("FR. LAWRENCE BUNZEL BUILDING", "bunzel");
        put("SAFAD", "safad");
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
        markerOverlay = findViewById(R.id.markerOverlay);

        markerBunzel = findViewById(R.id.markerBunzel);
        markerSafad = findViewById(R.id.markerSafad);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, buildings);
        fromInput.setAdapter(adapter);
        toInput.setAdapter(adapter);

        fromInput.setOnItemClickListener((parent, view, position, id) -> updateMarkers());
        toInput.setOnItemClickListener((parent, view, position, id) -> {
            updateMarkers();
            btnGo.setEnabled(true);
        });

        btnGo.setOnClickListener(v -> playRouteVideo());

        markerBunzel.setOnClickListener(v -> {
            Intent i = new Intent(this, BunzelBuildingActivity.class);
            startActivity(i);
        });

        // Sync marker scale with map zoom
        campusMap.setOnMatrixChangeListener(new OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF rect) {
                float[] values = new float[9];
                Matrix matrix = campusMap.getImageMatrix();
                matrix.getValues(values);
                float scale = values[Matrix.MSCALE_X];

                markerBunzel.setScaleX(scale);
                markerBunzel.setScaleY(scale);
                markerSafad.setScaleX(scale);
                markerSafad.setScaleY(scale);
            }
        });

        routeVideo.setVisibility(View.GONE);
    }

    private void updateMarkers() {
        String from = fromInput.getText().toString();
        String to = toInput.getText().toString();

        markerBunzel.setVisibility(
                from.equalsIgnoreCase("FR. LAWRENCE BUNZEL BUILDING") || to.equalsIgnoreCase("FR. LAWRENCE BUNZEL BUILDING")
                        ? View.VISIBLE : View.GONE);

        markerSafad.setVisibility(
                from.equalsIgnoreCase("SAFAD") || to.equalsIgnoreCase("SAFAD")
                        ? View.VISIBLE : View.GONE);
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
            routeVideo.setVisibility(View.VISIBLE);
            routeVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoId));
            routeVideo.setOnCompletionListener(mp -> routeVideo.setVisibility(View.GONE));
            routeVideo.start();
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
        } else {
            super.onBackPressed();
        }
    }
}
