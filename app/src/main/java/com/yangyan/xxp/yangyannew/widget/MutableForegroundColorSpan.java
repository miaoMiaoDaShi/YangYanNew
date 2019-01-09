package com.yangyan.xxp.yangyannew.widget;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2019/1/8
 * Description :
 */
public class MutableForegroundColorSpan extends CharacterStyle implements UpdateAppearance {
    public static final String TAG = "MutableForegroundColorSpan";
    private int mAlpha;

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setAlpha(mAlpha);
    }

    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }

    public int getAlpha() {
        return mAlpha;
    }

}
