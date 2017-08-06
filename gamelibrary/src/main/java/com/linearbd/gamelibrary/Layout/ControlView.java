package com.linearbd.gamelibrary.Layout;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;


import com.linearbd.gamelibrary.Layout.Listener.MyListener;
import com.linearbd.gamelibrary.Layout.Model.Control;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius 03 on 8/5/2017.
 */

public class ControlView extends View {
    private Context context;
    private int canvasWidth;
    private MyListener listener;

    private int margin;
    private int side;

    private List<Control> controlList;

    private int touchX;
    private boolean isTouch;
    private int touchColumnId;


    public ControlView(Context context) {
        super(context);
        this.context = context;
        isTouch = false;

        initControlList();
    }

    private void initControlList() {
        controlList = new ArrayList<>();

        for(int i =0;i<9;i++){
            Control control= new Control(i,String.valueOf(i+1));
            controlList.add(control);
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, 100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value= super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouch =true;
                touchX = (int) event.getX();
                postInvalidate();
                return true;
        }

        return value;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        margin = calculateMargin();
        side = calculateSide();

        for(Control x:controlList){
            x.draw(canvas,margin,side);
        }

        if(isTouch){
            calculateTouchColumnId();
        }
    }

    private void calculateTouchColumnId() {
        int xWithoutMargin = touchX-margin;
        touchColumnId = (xWithoutMargin/side);

        if(listener!=null){
            listener.onClick(controlList.get(touchColumnId));
        }

    }

    public void setListener(MyListener listener){
        this.listener =listener;

    }

    private int calculateSide() {
        return (canvasWidth-2*margin)/9;
    }

    private int calculateMargin() {
        int tempMargin = (canvasWidth%9)/2;

        if(tempMargin<9){
            int minSide = canvasWidth-9*2;
            int remainder = minSide%9;
            tempMargin = 9+remainder/2;
        }
        return tempMargin;
    }
}
