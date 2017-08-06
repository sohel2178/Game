package com.linearbd.gamelibrary.Layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.linearbd.gamelibrary.Layout.Listener.CompleteListener;
import com.linearbd.gamelibrary.Layout.Model.Element;
import com.linearbd.gamelibrary.Layout.Util.InitializeSudoku;
import com.linearbd.gamelibrary.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Genius 03 on 8/5/2017.
 */

public class SudokuView extends View {
    private Context context;
    private int canvasWidth,canvasHeight;

    private CompleteListener listener;

    private Paint selectPaint,borderPaint;

    private int margin;
    private int side;

    private int[][] elementArray = new int[9][9];
    private List<Element> elementList;

    private boolean isTouch;
    private int touchX,touchY;
    private int selectedColumnId,selectedRowId;

    public SudokuView(Context context) {
        super(context);
        this.context=context;

        isTouch = false;

        initElementArray();



        InitializeSudoku initializeSudoku = new InitializeSudoku(elementList,50);
        //initializeSudoku.initBoard();


        //elementList.get(5).setValue("5");

        //Log.d("HHH","Element Size = "+elementList.size());
    }

    public void setCompleteListener(CompleteListener listener){
        this.listener = listener;
    }

    private void initElementArray() {
        elementList = new ArrayList<>();
        for(int i=0;i<elementArray.length;i++){

            for(int j=0;j<elementArray[i].length;j++){
                Element element = new Element(i,j);
                elementList.add(element);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
        this.canvasWidth = canvas.getWidth();
        margin = calculateMargin();
        side = calculateSide();

        Log.d("HHH","Margin = "+margin);
        Log.d("HHH","Side = "+side);

        if(isTouch){
            drawTouchSquare(canvas);
        }

        for(Element x:elementList){
            x.draw(canvas,margin,side);
        }

        drawBorder(canvas);

        if(isFillAllBox()){
            Log.d("HHH","All Box Complete");
            if(rowTest()&&columnTest()){
                Log.d("HHH","YOO BRO You have Done");
                if(listener!=null){
                    listener.complete(true);
                }
            }


        }


    }


    private boolean rowTest(){
        boolean retBool = false;
        int rowCounter=0;
        List<Integer> sumList = new ArrayList<>();
        for (int i=0;i<9;i++){

            int value=0;
            for(Element x:elementList){
                if(x.getRowId()!=i){
                    continue;
                }
                value = value+Integer.parseInt(x.getValue());
            }

            sumList.add(value);

        }


        for(int x:sumList){
            if(x!=45){
                break;
            }else {
                rowCounter++;
            }
        }

        return rowCounter==9;


    }

    private boolean columnTest(){
        int rowCounter=0;
        List<Integer> sumList = new ArrayList<>();
        for (int i=0;i<9;i++){

            int value=0;
            for(Element x:elementList){
                if(x.getColumnId()!=i){
                    continue;
                }
                value = value+Integer.parseInt(x.getValue());
            }

            sumList.add(value);

        }


        for(int x:sumList){
            if(x!=45){
                break;
            }else {
                rowCounter++;
            }
        }

        return rowCounter==9;


    }


    private boolean isFillAllBox(){
        boolean retBool=false;
        int allBox =81;
        int counter=0;

        for(Element x:elementList){
            if(!x.getValue().equals("")){
                counter++;
            }
        }

        return allBox==counter;
    }

    private void initPaint(){
        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setColor(Color.parseColor("#eedd82"));
        selectPaint.setStyle(Paint.Style.FILL);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(ContextCompat.getColor(context, R.color.borderColor));
        borderPaint.setStrokeWidth(8);
    }

    private void drawBorder(Canvas canvas){

        for(int i=0;i<3;i++){

            for(int j=0;j<3;j++){
                drawBorderRect(margin+3*i*side,margin+3*j*side,canvas);
            }
        }


    }

    private void drawBorderRect(int startX,int startY,Canvas canvas){
        canvas.drawRect(startX,startY,startX+3*side,startY+3*side,borderPaint);
    }

    private void drawTouchSquare(Canvas canvas) {
        int xWithoutMargin = touchX-margin;
        int yWithoutMargin = touchY-margin;
        selectedColumnId = (xWithoutMargin/side);
        selectedRowId= (yWithoutMargin/side);

        int startX= margin+selectedColumnId*side;
        int startY=margin+selectedRowId*side;

        canvas.drawRect(startX,startY,(startX+side),(startY+side),selectPaint);

    }


    public void setValue(String value){
        if(isTouch){
            Element element = getElement();
            element.setValue(value);
            postInvalidate();
        }
    }

    private Element getElement(){
        Element element = null;
        for(Element x:elementList){
            if(x.getColumnId()==selectedColumnId && x.getRowId()==selectedRowId){
                element =x;
                break;
            }
        }

        return element;
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

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value= super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                touchX = (int) event.getX();
                touchY= (int) event.getY();

                postInvalidate();

                Log.d("HHH","Touched on "+touchX);
                Log.d("HHH","Touched on "+touchY);

                return true;
        }

        return value;


    }
}
