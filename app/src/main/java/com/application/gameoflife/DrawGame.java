package com.application.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrawGame extends View {
    public DrawGame(Context context) { super(context);init();}

    public DrawGame(Context context, @Nullable AttributeSet attrs) { super(context, attrs);init();}


    private Paint paint=new Paint();
    private Canvas canvas;
    private int screenHeight;
    private int screenWidth;
    private int offsetX;
    private int offsetY;
    private int strokeWidth=8;
    private int[][] life;
    private boolean FLAG=true;
    ExecutorService executorService= Executors.newFixedThreadPool(2);
    LifeEvolution lifeEvolution;
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;
        super.onDraw(canvas);
        if(FLAG){
            init();
            this.setClickable(true);
            lifeEvolution=new LifeEvolution(executorService,this,life);
            //lifeEvolution.lifeEvolution();
            FLAG=false;
        }
        // 绘制边框
        drawBorder();
        // 绘制生命
        drawLife(life);

    }
    AppCompatActivity appCompatActivity=(AppCompatActivity)getContext();
    MyBottomDialog myBottomDialog;

    float firstTouchDownX = 0;
    float firstTouchDownY = 0;
    boolean isFirstShowDialog = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstTouchDownX = event.getX();
                firstTouchDownY = event.getY();
                //isFirstShowDialog = true;
                break;
            case MotionEvent.ACTION_UP:
                // 点击弹出设置菜单
                if( event.getEventTime()-event.getDownTime()<=200 ){
                    myBottomDialog =new MyBottomDialog(lifeEvolution,this);
                    myBottomDialog.show(appCompatActivity.getSupportFragmentManager());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /* 底部上滑弹出设置菜单,会和设置生命冲突,上滑的时候也在设置生命,建议不用
                if(isFirstShowDialog && firstTouchDownY - event.getY() > 100 && firstTouchDownY > screenHeight -200) {
                    isFirstShowDialog = false;
                    myBottomDialog = new MyBottomDialog(lifeEvolution, this);
                    myBottomDialog.show(appCompatActivity.getSupportFragmentManager());
                }
                 */
                    int lifeX = ((int) event.getX() - offsetX) / 50;
                    int lifeY = ((int) event.getY() - offsetY) / 50;
                    life[lifeX][lifeY] = 1;
                    invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void drawLife(int[][] life){
        paint.setColor(Color.BLACK);
        Rect rect = null;
            for (int i = 0; i < life.length; i++)
                for (int j = 0; j < life[i].length; j++) {
                    if (life[i][j] == 1)
                        rect = new Rect(i * 50 + 2 + offsetX, j * 50 + 2 + offsetY, i * 50 + 48 + offsetX, j * 50 + 48 + offsetY);
                    if (rect != null)
                        canvas.drawRect(rect, paint);
                }
    }
    private void drawBorder() {
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(strokeWidth);
        float[] floats = {offsetX, offsetY, screenWidth-offsetX, offsetY};
        for (int i = 0; i * 50 <= screenHeight; i++) {
            floats[1] = i * 50 +offsetY;
            floats[3] = i * 50 +offsetY;
            canvas.drawLines(floats, paint);
        }

        float[] floats2 = {offsetX, offsetY, offsetX, screenHeight-offsetY};
        for (int i = 0; i * 50 <= screenWidth; i++) {
            floats2[0] = i * 50 +offsetX;
            floats2[2] = i * 50 +offsetX;
            canvas.drawLines(floats2, paint);
        }
    }
    
    public int[][] setStart() {
        int xNum=screenWidth/50;
        int yNum=screenHeight/50;
        return LifeStart.getLife(xNum,yNum);
    }
    
    public void setLife(int[][] life){
        this.life=life;
    }
    
    public int[][] getLife(){ return life; }
    private void init() {
        //screenHeight=getHeight();
        //screenWidth=getWidth();
        screenWidth=getMeasuredWidth();
        screenHeight=getMeasuredHeight();
        offsetX=screenWidth%50/2;
        offsetY=screenHeight%50/2;
        life=setStart();

    }

}
