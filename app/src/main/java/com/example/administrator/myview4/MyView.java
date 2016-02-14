package com.example.administrator.myview4;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by Administrator on 2016/2/13.
 */
public class MyView extends View {

    private float mMinSize;
    private int mWidth;
    private int mHeight;
    private float arcRadius;
    private int mStrokeWidth;
    private float mInnerArcRadius;

    private float arrowDregree=-30+3;

    private int mArcCenterX;
    private int mArcCenterY;
    private RectF mArcRect;
    private RectF mInnerArcRect;

    private Path path;
    private Path arrow;

    private SweepGradient mSweepGradient;

    private Paint mArcPaint;
    private Paint mInnerArcPaint;
    private Paint mTextPaint;
    private Paint  arrowPaint;

    private Thread t;


    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context) {
        super(context, null);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setDither(true);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setPathEffect(new CornerPathEffect(10));

        mInnerArcPaint = new Paint();
        mInnerArcPaint.setAntiAlias(true);
        mInnerArcPaint.setStyle(Paint.Style.STROKE);
        mInnerArcPaint.setDither(true);
        mInnerArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mInnerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerArcPaint.setPathEffect(new CornerPathEffect(10));
        mInnerArcPaint.setColor(Color.RED);
        mInnerArcPaint.setStrokeWidth(4);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(30);

        arrowPaint=new Paint();
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setColor(Color.WHITE);


        DisplayMetrics displayMetrics =getContext().getResources()
                .getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHeight=displayMetrics.heightPixels;
        mMinSize =mWidth/600f;
        mArcCenterX=mWidth/2;
        mArcCenterY=mHeight/2;
        arcRadius=250*mMinSize;
        mInnerArcRadius=190*mMinSize;
        mStrokeWidth = (int) (20*mMinSize);
         int[] colors ={0xFF04b5fb,0xFFECEA48,0xFF04b5fb};
        float[] positions = {0,1};
        mSweepGradient=new SweepGradient(mArcCenterX,mArcCenterY,colors,null);

        mArcRect = new RectF();
        mArcRect.left = mArcCenterX - arcRadius;
        mArcRect.top = mArcCenterY - arcRadius;
        mArcRect.right = mArcCenterX + arcRadius;
        mArcRect.bottom = mArcCenterY + arcRadius;

        mInnerArcRect=new RectF();
        mInnerArcRect.left = mArcCenterX - mInnerArcRadius;
        mInnerArcRect.top = mArcCenterY - mInnerArcRadius;
        mInnerArcRect.right = mArcCenterX + mInnerArcRadius;
        mInnerArcRect.bottom = mArcCenterY + mInnerArcRadius;

        path=new Path();
        path.addArc(mArcRect,-210,240);

        arrow=new Path();
        arrow.moveTo(mArcCenterX - mInnerArcRadius + 3, mArcCenterY);
        arrow.lineTo(mArcCenterX - mInnerArcRadius - 16 + 40, mArcCenterY + 20);
        arrow.lineTo(mArcCenterX - mInnerArcRadius - 16 + 40, mArcCenterY - 20);
        arrow.close();



    }
    @Override
    protected void onDraw(Canvas canvas) {
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setShader(mSweepGradient);
        canvas.drawArc(mArcRect, -210, 240, false, mArcPaint);
        canvas.save();
        canvas.rotate(-120, mArcCenterX, mArcCenterY);
        for (int i=0;i<16;i++){
        canvas.drawText(String.valueOf(i)+"k",mArcCenterX,mArcCenterY-arcRadius+50,mTextPaint);
        canvas.rotate(15.3f,mArcCenterX,mArcCenterY);
       }
         canvas.restore();
        canvas.drawArc(mInnerArcRect, -210, 240, false, mInnerArcPaint);
         canvas.rotate(arrowDregree,mArcCenterX,mArcCenterY);
         canvas.drawPath(arrow, arrowPaint);


    }

    private void animatorStart(){
        Random random = new Random();
        int value=Math.abs(random.nextInt())%15;
        float degree=value*15.3f-30+4;
        moveTo(degree);
    }
    private void moveTo(final float degree){
        float currentDegree=arrowDregree;
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(currentDegree,degree);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arrowDregree=(float)animation.getAnimatedValue();
                invalidate();
            }
        });

                valueAnimator.setDuration(2000).start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
     switch (event.getAction()){
         case MotionEvent.ACTION_DOWN:{
             animatorStart();
             break;
         }
     }

        return true;
    }

}
