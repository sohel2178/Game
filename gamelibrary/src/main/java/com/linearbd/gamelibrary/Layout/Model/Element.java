package com.linearbd.gamelibrary.Layout.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Genius 03 on 8/5/2017.
 */

public class Element {
    private float textSize;
    private int rowId;
    private int columnId;
    private int blockId;
    private String value;

    private Paint blackPaint;

    public Element(int rowId, int columnId, String value) {
        this.rowId = rowId;
        this.columnId = columnId;
        this.value = value;
        this.textSize = 40f;
        initPaint();
        setBlockId();


    }

    private void initPaint() {
        blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(3);
        blackPaint.setTextSize(textSize);
    }

    private void setBlockId(){
        if(rowId<=2){
            if(columnId<=2){
                blockId=0;
            }else if(columnId<=5){
                blockId=1;
            }else{
                blockId=2;
            }
        }else if(rowId<=5){
            if(columnId<=2){
                blockId=3;
            }else if(columnId<=5){
                blockId=4;
            }else{
                blockId=5;
            }
        }else {
            if(columnId<=2){
                blockId=6;
            }else if(columnId<=5){
                blockId=7;
            }else{
                blockId=8;
            }
        }
    }

    public int getBlockId(){
        return blockId;
    }

    public Element(int rowId, int columnId) {
        this(rowId,columnId,"");
    }


    public void draw(Canvas canvas,int margin,int side){
        int startX= margin+side*columnId;
        int startY = margin+side*rowId;

        canvas.drawRect(startX,startY,(startX+side),(startY+side),blackPaint);

        if(!value.equals("")){
            canvas.drawText(value,startX+(side/2)-textSize/3,startY+(side/2)+textSize/3,blackPaint);
        }
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
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

    public Paint getBlackPaint() {
        return blackPaint;
    }

    public void setBlackPaint(Paint blackPaint) {
        this.blackPaint = blackPaint;
    }
}
