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

        findViewById(R.id.btnBasement).setOnClickListener(v -> loadFloor("basement", R.drawable.placeholder_img));
        findViewById(R.id.btnFloor1).setOnClickListener(v -> loadFloor("floor1", R.drawable.placeholder_img));
        findViewById(R.id.btnFloor2).setOnClickListener(v -> loadFloor("floor2", R.drawable.placeholder_img));
        findViewById(R.id.btnFloor3).setOnClickListener(v -> loadFloor("floor3", R.drawable.placeholder_img));
        findViewById(R.id.btnFloor4).setOnClickListener(v -> loadFloor("floor4", R.drawable.placeholder_img));

        btnSearchRoom.setOnClickListener(v -> {
            String room = floorRoomInput.getText().toString().replace(" ", "_").toLowerCase();
            String videoName = "bunzel_" + currentFloor + "_to_" + room;
            int videoId = getResources().getIdentifier(videoName, "raw", getPackageName());

            if (videoId != 0) {
                floorVideo.setVisibility(View.VISIBLE);
                floorVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoId));
                floorVideo.setOnCompletionListener(mp -> floorVideo.setVisibility(View.GONE));
                floorVideo.start();
            } else {
                Toast.makeText(this, "No video found for this room.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFloor(String floorKey, int floorImgRes) {
        currentFloor = floorKey;
        floorPlan.setImageResource(floorImgRes);
        floorTitle.setText("Selected: " + capitalize(floorKey));
        floorEntranceImage.setImageResource(R.drawable.bunzel_entrance); // or change based on floor

        String[] floorRooms = getRoomsForFloor(floorKey);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, floorRooms);
        floorRoomInput.setAdapter(roomAdapter);
        floorRoomInput.setText("");
    }

    private String capitalize(String key) {
        return key.replace("_", " ").toUpperCase();
    }

    private String[] getRoomsForFloor(String floorKey) {
        switch (floorKey) {
            case "floor4":
                return new String[]{"LB 443", "LB 444", "LB 445", "LB 446", "LB 447", "LB 448", "LB 467", "LB 468", "LB 469", "LB 470", "LB 480", "LB 481", "LB 482", "LB 483", "LB 484", "LB 485", "LB 486", "Fac Office", "Dept Office", "Control room"};
            case "floor3":
                return new String[]{"LB 301", "LB 302", "LB 303", "LB 304", "LB 305", "LB 306", "LB 363", "LB 364", "LB 365", "LB 366", "EE Lab", "Water Laboratory", "D. ENG Room", "Tech Hub Office"};
            case "floor2":
                return new String[]{"LB 264", "LB 265", "LB 266", "LB 267", "LB 245", "LB 246", "LB 247", "LB 248", "CPE Dept Office", "CEACTC Office"};
            case "floor1":
                return new String[]{"LB 143", "LB 144", "LB 146", "LB 167", "LB 168", "LB 172", "Cashier", "Internal Audit", "Rigney Hall", "Controller Office"};
            case "basement":
                return new String[]{"OSFA", "CDC Office", "CDC Testing", "Medical Clinic", "Dental Clinic", "Textbook Section", "CR", "ID Room", "OSS"};
            default:
                return new String[]{};
        }
    }
}
