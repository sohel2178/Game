package com.linearbd.gamelibrary.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.linearbd.gamelibrary.R;

/**
 * Created by sohel on 11-09-17.
 */

public class ClockView extends SurfaceView implements Runnable{

    private Thread thread = null;
    private boolean canDraw=false;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;

    Bitmap bitmap;


    //Game Loop Variable
    private double frame_per_second;
    private double tLF,tEOR,t_delta;
    private double single_frame_time_second,single_frame_time_millis,single_frame_time_nanos;

    public ClockView(Context context) {
        this(context,null,0,0);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs){

        this.frame_per_second =20;
        this.single_frame_time_second = 1/frame_per_second;
        this.single_frame_time_millis = single_frame_time_second*1000;
        this.single_frame_time_nanos = single_frame_time_millis*1000000;
        this.surfaceHolder = getHolder();

        if(attrs==null){
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ClockView);

        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    @Override
    public void run() {

        tLF = System.nanoTime();
        t_delta = 0;

        while (canDraw){
            //Carry out Some Drawing

            updateDelta(t_delta);

            if(!surfaceHolder.getSurface().isValid()){
                // If Surface holder not Valid Continue the Loop
                continue;
            }

            draw();
            // Now Calculate EOR
            tEOR = System.nanoTime();

            // Now Calculate Delta timew
            t_delta = single_frame_time_nanos-(tEOR-tLF);

            // Now Sleep the Thread for Delta Time

            try {
                if(t_delta>0){
                    Thread.sleep((long) t_delta/1000000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tLF = System.nanoTime();


        }

    }


    private void updateDelta(double t_delta) {

    }

    private void draw(){
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clock);

        bitmap = Bitmap.createScaledBitmap(bitmap,getWidth(),getHeight(),true);

        canvas = surfaceHolder.lockCanvas();
        canvas.drawBitmap(bitmap,0,0,null);
       /* canvas.drawCircle(getWidth()/2,getHeight()/2,200,red_stroke);
        canvas.drawCircle(getWidth()/2,getHeight()/2-200,60,green_fill);
        canvas.drawCircle(getWidth()/2,getHeight()/2+200,60,blue_fill);
        canvas.drawCircle(mCx,mCy,40,red_stroke);

        canvas.drawText(String.valueOf((int)(theta/(2*Math.PI))),(getWidth()/2-30),(getHeight()/2-30),red_stroke);*/
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    public void pause(){
        canDraw=false;

        while (true){
            try {
                thread.join();
                // After Join Break the Loop
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread=null;


    }

    public void resume(){
        // Do the thing When Activity is Resume
        canDraw = true;
        thread = new Thread(this);
        thread.start();

    }
}
