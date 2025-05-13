package com.example.civiclick;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class BunzelBuildingActivity extends AppCompatActivity {

    ImageView floorPlan, floorEntranceImage;
    TextView floorTitle;
    AutoCompleteTextView floorRoomInput;
    Button btnSearchRoom;
    VideoView floorVideo;

    String currentFloor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunzel_building);

        floorPlan = findViewById(R.id.floorPlan);
        floorEntranceImage = findViewById(R.id.floorEntranceImage);
        floorTitle = findViewById(R.id.floorTitle);
        floorRoomInput = findViewById(R.id.floorRoomInput);
        btnSearchRoom = findViewById(R.id.btnSearchRoom);
        floorVideo = findViewById(R.id.floorVideo);

        findViewById(R.id.btnBasement).setOnClickListener(v -> loadFloor("basement", R.drawable.basement));
        findViewById(R.id.btnFloor1).setOnClickListener(v -> loadFloor("floor1", R.drawable.bunzel_floor1));
        findViewById(R.id.btnFloor2).setOnClickListener(v -> loadFloor("floor2", R.drawable.bunzel_floor2));
        findViewById(R.id.btnFloor3).setOnClickListener(v -> loadFloor("floor3", R.drawable.bunzel_floor3));
        findViewById(R.id.btnFloor4).setOnClickListener(v -> loadFloor("floor4", R.drawable.bunzel_floor4));

        btnSearchRoom.setOnClickListener(v -> {
            String room = floorRoomInput.getText().toString().trim().replace(" ", "_").toLowerCase();
            String videoName;

            // If starts with digit (0-9), prepend "room_"
            if (room.matches("^[0-9].*")) {
                videoName = "room_" + room;
            } else {
                videoName = room;
            }

            int videoId = getResources().getIdentifier(videoName, "raw", getPackageName());

            if (videoId != 0) {
                floorVideo.setVisibility(View.VISIBLE);
                floorPlan.setVisibility(View.GONE);

                floorVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoId));
                floorVideo.setOnCompletionListener(mp -> {
                    floorVideo.setVisibility(View.GONE);
                    floorPlan.setVisibility(View.VISIBLE);
                });
                floorVideo.start();
            } else {
                Toast.makeText(this, "No video found for this room.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFloor(String floorKey, int floorResId) {
        currentFloor = floorKey;
        floorTitle.setText("Selected: " + floorKey.replace("_", " ").toUpperCase());
        floorEntranceImage.setImageResource(R.drawable.bunzel_entrance);
        floorPlan.setImageResource(floorResId);
        floorPlan.setVisibility(View.VISIBLE);
        floorVideo.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getRoomsForFloor(floorKey));
        floorRoomInput.setAdapter(adapter);
        floorRoomInput.setText("");
    }

    private String[] getRoomsForFloor(String floor) {
        switch (floor) {
            case "floor4":
                return new String[]{"443", "444", "445", "446", "447", "448", "467", "468", "469", "470", "480", "481", "482", "483", "484", "485", "486", "fac_office", "dept_office", "control_room"};
            case "floor3":
                return new String[]{"301", "302", "303", "304", "305", "306", "363", "364", "365", "366", "ee_lab", "water_laboratory", "d_eng_room", "tech_hub_office"};
            case "floor2":
                return new String[]{"264", "265", "266", "267", "245", "246", "247", "248", "cpe_dept_office", "ceactc_office"};
            case "floor1":
                return new String[]{"143", "144", "146", "167", "168", "172", "cashier", "internal_audit", "rigney_hall", "controller_office"};
            case "basement":
                return new String[]{"osfa", "cdc_office", "cdc_testing", "medical_clinic", "dental_clinic", "textbook_section", "cr", "id_room", "oss"};
            default:
                return new String[]{};
        }
    }
}
