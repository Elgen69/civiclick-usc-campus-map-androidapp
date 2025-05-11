// This is the Bunzel Building screen
// Shows an image of the building and lets users view any floor plan (1st–4th floor) in one page

package com.example.civiclick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BunzelBuildingActivity extends AppCompatActivity {

    ImageView floorPlan;
    TextView floorTitle;
    Button floor1, floor2, floor3, floor4;
    Button[] floorButtons;
    boolean isViewingFloor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunzel_building);

        floorPlan = findViewById(R.id.floorPlan);
        floorTitle = findViewById(R.id.floorTitle);

        floor1 = findViewById(R.id.floor1);
        floor2 = findViewById(R.id.floor2);
        floor3 = findViewById(R.id.floor3);
        floor4 = findViewById(R.id.floor4);

        // Group the buttons for easy hiding/showing
        floorButtons = new Button[]{floor1, floor2, floor3, floor4};

        floor1.setOnClickListener(v -> showFloor("1", R.drawable.bunzel_floor1));
        floor2.setOnClickListener(v -> showFloor("2", R.drawable.bunzel_floor2));
        floor3.setOnClickListener(v -> showFloor("3", R.drawable.bunzel_floor3));
        floor4.setOnClickListener(v -> showFloor("4", R.drawable.bunzel_floor4));
    }

    private void showFloor(String floor, int resId) {
        floorTitle.setText("Floor " + floor);
        floorPlan.setImageResource(resId);

        // Hide buttons
        for (Button btn : floorButtons) {
            btn.setVisibility(View.GONE);
        }

        isViewingFloor = true;
    }

    @Override
    public void onBackPressed() {
        if (isViewingFloor) {
            // Show floor buttons again
            for (Button btn : floorButtons) {
                btn.setVisibility(View.VISIBLE);
            }

            floorPlan.setImageDrawable(null);
            floorTitle.setText("Select a floor");
            isViewingFloor = false;
        } else {
            // Exit activity normally
            super.onBackPressed();
        }
    }
}
