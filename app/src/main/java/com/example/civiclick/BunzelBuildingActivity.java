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

        // Default bunzel building shown
        floorEntranceImage.setImageResource(R.drawable.bunzel_building);

        findViewById(R.id.btnBasement).setOnClickListener(v -> loadFloor("basement", R.drawable.basement, R.drawable.b_basement));
        findViewById(R.id.btnFloor1).setOnClickListener(v -> loadFloor("floor1", R.drawable.bunzel_floor1, R.drawable.b_first_floor));
        findViewById(R.id.btnFloor2).setOnClickListener(v -> loadFloor("floor2", R.drawable.bunzel_floor2, R.drawable.b_second_floor));
        findViewById(R.id.btnFloor3).setOnClickListener(v -> loadFloor("floor3", R.drawable.bunzel_floor3, R.drawable.b_third_floor));
        findViewById(R.id.btnFloor4).setOnClickListener(v -> loadFloor("floor4", R.drawable.bunzel_floor4, R.drawable.b_fourth_floor_front));

        btnSearchRoom.setOnClickListener(v -> {
            String input = floorRoomInput.getText().toString().trim().toLowerCase().replace(" ", "_");

            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter a room", Toast.LENGTH_SHORT).show();
                return;
            }

            String videoName = input.matches("\\d+") ? "room_" + input : input;
            int videoId = getResources().getIdentifier(videoName, "raw", getPackageName());

            if (videoId != 0) {
                floorVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoId));
                floorVideo.setOnPreparedListener(mp -> mp.setLooping(true));
                floorVideo.start();
                floorVideo.setVisibility(View.VISIBLE);
                floorPlan.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "No animation available for: " + input, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFloor(String floorKey, int floorResId, int entranceResId) {
        currentFloor = floorKey;
        floorTitle.setText("Selected: " + floorKey.replace("_", " ").toUpperCase());
        floorEntranceImage.setImageResource(entranceResId);
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
                return new String[]{
                        "443", "444", "445", "446", "447", "448",
                        "467", "468", "469", "470", "480", "481",
                        "483", "484", "485", "486",
                        "fac_office", "dept_office", "control_room",
                        "techhub", "treasury_office"
                };
            case "floor3":
                return new String[]{
                        "301", "302", "303", "304", "305", "306",
                        "342", "363", "364", "365", "366",
                        "ncr_design_lab", "pcb_lab", "rigney_hall",
                        "robotics_automation", "techhub_office"
                };
            case "floor2":
                return new String[]{
                        "245", "246", "247", "248", "264", "265", "266", "267",
                        "chemeng_department", "csst_office", "cpe_dept_office",
                        "ceactc_office", "dml_office", "deng_room"
                };
            case "floor1":
                return new String[]{
                        "143", "144", "146", "167", "168", "172",
                        "285", "286", "cashier", "internal_audit",
                        "rigney_hall", "controller_office", "lbch1", "lbch2"
                };
            case "basement":
                return new String[]{
                        "osfa", "cdc_office", "cdc_testing", "medical_clinic",
                        "dental_clinic", "textbook_section", "id_room", "oss",
                        "authorized", "main_door_tech"
                };
            default:
                return new String[]{};
        }
    }

    @Override
    public void onBackPressed() {
        if (floorVideo.getVisibility() == View.VISIBLE) {
            floorVideo.stopPlayback();
            floorVideo.setVisibility(View.GONE);
            floorPlan.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
