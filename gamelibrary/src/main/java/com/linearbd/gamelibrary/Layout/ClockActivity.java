package com.linearbd.gamelibrary.Layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linearbd.gamelibrary.R;

public class ClockActivity extends AppCompatActivity {

    private ClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        clockView = (ClockView) findViewById(R.id.clock_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clockView.resume();
    }

    @Override
    protected void onPause() {
        clockView.pause();
        super.onPause();
    }
}
