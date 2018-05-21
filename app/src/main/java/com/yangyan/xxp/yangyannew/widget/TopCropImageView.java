package com.yangyan.xxp.yangyannew.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

public class TopCropImageView extends android.support.v7.widget.AppCompatImageView {

    public TopCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.MATRIX);
    }

    public TopCropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScaleType(ScaleType.MATRIX);
    }

    public TopCropImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected boolean setFrame(int frameLeft, int frameTop, int frameRight, int frameBottom) {
        if (getDrawable() != null) {
            Matrix matrix = getImageMatrix();
            float scaleFactor = getWidth()/(float)getDrawable().getIntrinsicWidth();
            matrix.setScale(scaleFactor, scaleFactor, 0, 0);
            setImageMatrix(matrix);
        }
        return super.setFrame(frameLeft, frameTop, frameRight, frameBottom);
    }
}  