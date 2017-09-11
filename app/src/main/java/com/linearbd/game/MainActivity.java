package com.linearbd.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linearbd.gamelibrary.Layout.ClockActivity;
import com.linearbd.gamelibrary.Layout.SudokuActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSudoku,btnClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSudoku = (Button) findViewById(R.id.sudoku);
        btnClock = (Button) findViewById(R.id.clock);
        btnSudoku.setOnClickListener(this);
        btnClock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sudoku:
                gotoSudokuActivity();
                break;

            case R.id.clock:
                gotoClockActivity();
                break;
        }

    }

    private void gotoClockActivity() {
        startActivity(new Intent(getApplicationContext(), ClockActivity.class));
    }

    private void gotoSudokuActivity() {
        startActivity(new Intent(getApplicationContext(), SudokuActivity.class));
    }
}
