package com.smart.bean.waveviewpro;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * auther   : bean
 * on       : 2017/11/15
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class WaveView extends BaseView implements View.OnClickListener {
    Path path = new Path();
    private int mOffset;
    public final String TAG = this.getClass().getSimpleName();
    protected Paint mPaint;
    protected int mViewWidth;
    protected int mViewHeight;

    public WaveView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(this);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = measureWidth(widthMeasureSpec);//得到view的宽度
        mViewHeight = measureHeight(heightMeasureSpec);//view 的高度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.green));
        //        canvas.drawRect(0, 0, mViewWidth, mViewHeight, mPaint);
        //        mPaint.setStyle(Paint.Style.STROKE);
        int waveShowNum = 4;//现实的波的个数
        int waveLen = mViewWidth / waveShowNum;//波长
        int waveLenHalf = waveLen / 2;//半波长


        int startPosX = -waveLen;//起始波的起始点

        int centerY = mViewHeight / 3;//半波长的三分之一；振动轴

        int firstStartControllerX = startPosX + waveLenHalf / 2;//第一个控制点x坐标
        int secondStartControllerX = firstStartControllerX + waveLenHalf;//第二个控制点的 x 坐标 ，由第一个平移半个波长

        int controllerY = waveLenHalf / 3;//半个波长的3分之一 ；为了波形的协调

        int offsetY = centerY > controllerY ? controllerY : centerY;//控制距离震动中心的长度

        int upperControllerY = centerY - offsetY;//上控制点
        int downControllerY = centerY + offsetY;//下控制点

        path.reset();
        path.moveTo(startPosX, centerY);//移动到 起始点

        int firstEndPosX = startPosX + waveLenHalf;
        int secondEndPosX = firstEndPosX + waveLenHalf;
        int realNum = waveShowNum + 2;
        for (int i = 0; i < realNum; i++) {
            path.quadTo(firstStartControllerX + i * waveLen + mOffset, upperControllerY, firstEndPosX + i * waveLen + mOffset, centerY);
            path.quadTo(secondStartControllerX + i * waveLen + mOffset, downControllerY, secondEndPosX + i * waveLen + mOffset, centerY);
        }
        path.lineTo(secondEndPosX + (realNum - 1) * waveLen + mOffset, mViewHeight);
        path.lineTo(startPosX, mViewHeight);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mViewWidth / 4);
        valueAnimator.setDuration(800);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffset = (int) valueAnimator.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: " + mOffset);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    protected int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 300;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }

    protected int measureWidth(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 1080;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;

    }
}
