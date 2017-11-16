package com.smart.bean.waveviewpro;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * auther   : bean
 * on       : 2017/11/14
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class BaseView extends View {

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}
