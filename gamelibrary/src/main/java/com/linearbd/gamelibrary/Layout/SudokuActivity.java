package com.linearbd.gamelibrary.Layout;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.linearbd.gamelibrary.Layout.Listener.CompleteListener;
import com.linearbd.gamelibrary.Layout.Listener.MyListener;
import com.linearbd.gamelibrary.Layout.Model.Control;
import com.linearbd.gamelibrary.R;

public class SudokuActivity extends AppCompatActivity implements MyListener,CompleteListener {

    private SudokuView sudokuView;
    private ControlView controlView;

    private FrameLayout frameLayout,controllerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sudokuView = new SudokuView(this);
        sudokuView.setCompleteListener(this);
        controlView = new ControlView(this);
        controlView.setListener(this);
        setContentView(R.layout.activity_sudoku);
        frameLayout = (FrameLayout) findViewById(R.id.sudoku_container);
        controllerContainer = (FrameLayout) findViewById(R.id.controller_container);
        frameLayout.addView(sudokuView);
        controllerContainer.addView(controlView);
    }

    @Override
    public void complete(boolean value) {
        if(value){
            showAlertDialog();
        }

    }

    @Override
    public void onClick(Control control) {
        Log.d("HHH",control.getValue());

        sudokuView.setValue(control.getValue());

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Game Complete");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click yes to play Again!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
