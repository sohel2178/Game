package com.linearbd.gamelibrary.Layout.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Genius 03 on 8/5/2017.
 */

public class Control {
    private float textSize;
    private int columnId;
    private String value;
    private Paint blackPaint;



    public Control(int columnId, String value) {
        this.columnId = columnId;
        this.value = value;
        this.textSize=40f;
        initPaint();
    }

    private void initPaint() {
        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(3);
        blackPaint.setTextSize(textSize);
    }

    public void draw(Canvas canvas, int margin, int side){
        int startX= margin+side*columnId;
        int startY = margin+side*0;

        canvas.drawRect(startX,startY,(startX+side),(startY+side),blackPaint);

        if(!value.equals("")){
            canvas.drawText(value,startX+(side/2)-textSize/2,startY+(side/2)+textSize/2,blackPaint);
        }
    }

    public Control(int columnId) {
        this(columnId,"");
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
